package puppteer;

import com.ruiyun.jvppeteer.core.Puppeteer;
import com.ruiyun.jvppeteer.core.browser.Browser;
import com.ruiyun.jvppeteer.core.browser.BrowserFetcher;
import com.ruiyun.jvppeteer.core.page.Page;
import com.ruiyun.jvppeteer.options.LaunchOptions;
import com.ruiyun.jvppeteer.options.LaunchOptionsBuilder;
import com.ruiyun.jvppeteer.protocol.webAuthn.Credentials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ProxyDemo {

    // 代理隧道验证信息
    final static String ProxyUser = "H01234567890123D";
    final static String ProxyPass = "0123456789012345";

    // 代理服务器
    final static String ProxyHost = "http-dyn.abuyun.com";
    final static Integer ProxyPort = 9020;

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("--no-sandbox");
        arrayList.add("--disable-setuid-sandbox");

        String proxy = ProxyHost + ":" + ProxyPort;
        arrayList.add("--proxy-server=" + proxy);

        // 自动下载
        // BrowserFetcher.downloadIfNotExist(null);

        LaunchOptions options = new LaunchOptionsBuilder()
                .withArgs(arrayList)
                .withExecutablePath("/Applications/Chromium.app/Contents/MacOS/Chromium")
                // 是否开启界面
                .withHeadless(true)
                .build();

        Browser browser = Puppeteer.launch(options);

        Page page = browser.newPage();
        Map<String, String> headers = new HashMap<>();

        page.authenticate(new Credentials(ProxyUser, ProxyPass));
        page.setExtraHTTPHeaders(headers);
        page.goTo("https://test.abuyun.com");
        page.screenshot("/tmp/screenshot_test_abu.png");
    }
}
