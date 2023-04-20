import java.io.IOException;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ProxyDemo
{
    // 代理隧道验证信息
    final static String ProxyUser = "H01234567890123D";
    final static String ProxyPass = "0123456789012345";

    // 代理服务器
    final static String ProxyHost = "http-dyn.abuyun.com";
    final static Integer ProxyPort = 9020;

    public static String getUrlProxyContent(String url)
    {
        Authenticator.setDefault(new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(ProxyUser, ProxyPass.toCharArray());
            }
        });

        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ProxyHost, ProxyPort));

        try
        {
            // 此处自己处理异常、其他参数等
            Document doc = Jsoup.connect(url).timeout(3000).proxy(proxy).get();

            if(doc != null) {
                System.out.println(doc.body().html());
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) throws Exception
    {
        // 要访问的目标页面
        String targetUrl = "http://test.abuyun.com";
        //String targetUrl = "http://proxy.abuyun.com/switch-ip";
        //String targetUrl = "http://proxy.abuyun.com/current-ip";

        // JDK 8u111版本后，目标页面为HTTPS协议，启用proxy用户密码鉴权
        System.setProperty("jdk.http.auth.tunneling.disabledSchemes", "");

        getUrlProxyContent(targetUrl);
    }
}