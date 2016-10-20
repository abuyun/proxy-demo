import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.message.BasicHeader;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpClient4xProxyDemo
{
    final static String ProxyUser = "H01234567890123P";
    final static String ProxyPass = "0123456789012345";

    final static String ProxyHost = "proxy.abuyun.com";
    final static Integer ProxyPort = 9010;

    final static String ProxyHeadKey = "Proxy-Switch-Ip";
    final static String ProxyHeadVal = "yes";

    public static void getUrlProxyContent(String url)
    {
        BasicHeader header = new BasicHeader(ProxyHeadKey, ProxyHeadVal);
        List<Header>list = new ArrayList<Header>();
        list.add(header);

        HttpHost target = new HttpHost(ProxyHost, ProxyPort, "http");

        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
            new AuthScope(target.getHostName(), target.getPort()),
            new UsernamePasswordCredentials(ProxyUser, ProxyPass));

        CloseableHttpClient httpClient = HttpClients.custom()
            .setDefaultCredentialsProvider(credsProvider)
            .setDefaultHeaders(list).build();

        //HttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(credsProvider).build();

        // Create AuthCache instance
        AuthCache authCache = new BasicAuthCache();
        // Generate BASIC scheme object and add it to the local
        // auth cache
        BasicScheme basicAuth = new BasicScheme();
        authCache.put(target, basicAuth);

        // Add AuthCache to the execution context
        HttpClientContext localContext = HttpClientContext.create();
        localContext.setAuthCache(authCache);

        HttpGet httpGet = new HttpGet(url);

        CloseableHttpResponse response = null;
        //HttpResponse response = null;
        try
        {
            response = httpClient.execute(target, httpGet, localContext);

            System.out.println(EntityUtils.toString(response.getEntity()));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
        }
    }

    public static void main(String[] args)
    {
        getUrlProxyContent("http://proxy.abuyun.com/current-ip");

        try
        {
            Thread.sleep(10000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
