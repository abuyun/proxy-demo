import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;
import org.apache.http.util.EntityUtils;

public class JavaHttpClient45Demo
{
    // 代理服务器
    final static String proxyHost = "http-dyn.abuyun.com";
    final static Integer proxyPort = 9020;

    // 代理隧道验证信息
    final static String proxyUser = "H01234567890123D";
    final static String proxyPass = "0123456789012345";

    private static PoolingHttpClientConnectionManager cm = null;
    private static HttpRequestRetryHandler httpRequestRetryHandler = null;
    private static HttpHost proxy = null;

    private static CredentialsProvider credsProvider = null;
    private static RequestConfig reqConfig = null;

    static {
        ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
        LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();

        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
            .register("http", plainsf)
            .register("https", sslsf)
            .build();

        cm = new PoolingHttpClientConnectionManager(registry);
        cm.setMaxTotal(20);
        cm.setDefaultMaxPerRoute(5);

        proxy = new HttpHost(proxyHost, proxyPort, "http");

        credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
            AuthScope.ANY,
            //new AuthScope(proxyHost, proxyPort),
            new UsernamePasswordCredentials(proxyUser, proxyPass));

        reqConfig = RequestConfig.custom()
            .setConnectionRequestTimeout(5000)
            .setConnectTimeout(5000)
            .setSocketTimeout(5000)
            .setExpectContinueEnabled(false)
            .setProxy(new HttpHost(proxyHost, proxyPort))
            //.setAuthenticationEnabled(true)
            //.setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
            .build();
    }

    public static void doRequest(HttpRequestBase httpReq) {
        CloseableHttpResponse httpResp = null;

        try {
            setHeaders(httpReq);

            httpReq.setConfig(reqConfig);

            CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .setDefaultCredentialsProvider(credsProvider)
                .build();

            AuthCache authCache = new BasicAuthCache();
            authCache.put(proxy, new BasicScheme());

            HttpClientContext localContext = HttpClientContext.create();
            localContext.setAuthCache(authCache);

            httpResp = httpClient.execute(httpReq, localContext);
            //httpResp = httpClient.execute(proxy, httpReq, localContext);

            int statusCode = httpResp.getStatusLine().getStatusCode();

            System.out.println(statusCode);

            BufferedReader rd = new BufferedReader(new InputStreamReader(httpResp.getEntity().getContent()));

            String line = "";
            while((line = rd.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (httpResp != null) {
                    httpResp.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 设置请求头
     *
     * @param httpReq
     */
    private static void setHeaders(HttpRequestBase httpReq) {
        httpReq.setHeader("Accept-Encoding", null);
    }

    /**
     * 处理response
     *
     * @param httpResp
     * @return
     */
    private static CloseableHttpResponse handleResponse(CloseableHttpResponse httpResp) {
        Header header = httpResp.getEntity().getContentEncoding();
        if (header != null) {
            HeaderElement[] elem = header.getElements();
            for (int i = 0; i < elem.length; i++) {
                if (elem[i].getName().equalsIgnoreCase("gzip")) {
                    httpResp.setEntity(new GzipDecompressingEntity(httpResp.getEntity()));
                }
            }
        }
        return httpResp;
    }

    public static void doPostRequest() {
        try {
            // 要访问的目标页面
            HttpPost httpPost = new HttpPost("https://test.abuyun.com");

            // 设置表单参数
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name", "http-cloud-proxy"));
            params.add(new BasicNameValuePair("params", "{\"broker\":\"abuyun\":\"site\":\"https://www.abuyun.com\"}"));

            httpPost.setEntity(new UrlEncodedFormEntity(params, "utf-8"));

            doRequest(httpPost);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void doGetRequest() {
        // 要访问的目标页面
        String targetUrl = "https://test.abuyun.com";
        //String targetUrl = "http://proxy.abuyun.com/switch-ip";
        //String targetUrl = "http://proxy.abuyun.com/current-ip";

        try {
            HttpGet httpGet = new HttpGet(targetUrl);

            doRequest(httpGet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //doGetRequest();

        doPostRequest();
    }
}
