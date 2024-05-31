import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ProxyDemo {
    private static void createProxyAuthExtension(String proxyHost, String proxyPort, String proxyUser, String proxyPass, String pluginPath) throws IOException {

        String manifestJson = """
                {
                    "version": "1.0.0",
                    "manifest_version": 2,
                    "name": "Abuyun Proxy",
                    "permissions": [
                        "proxy",
                        "tabs",
                        "unlimitedStorage",
                        "storage",
                        "<all_urls>",
                        "webRequest",
                        "webRequestBlocking"
                    ],
                    "background": {
                        "scripts": ["background.js"]
                    },
                    "minimum_chrome_version":"22.0.0"
                }
                """;

        String backgroundJs = """
                var config = {
                    mode: "fixed_servers",
                    rules: {
                        singleProxy: {
                            scheme: "${scheme}",
                            host: "${host}",
                            port: parseInt(${port})
                        },
                        bypassList: ["foobar.com"]
                    }
                  };

                chrome.proxy.settings.set({value: config, scope: "regular"}, function() {});

                function callbackFn(details) {
                    return {
                        authCredentials: {
                            username: "${username}",
                            password: "${password}"
                        }
                    };
                }

                chrome.webRequest.onAuthRequired.addListener(
                    callbackFn,
                    {urls: ["<all_urls>"]},
                    ['blocking']
                );
                """
                .replace("${host}", proxyHost)
                .replace("${port}", proxyPort)
                .replace("${username}", proxyUser)
                .replace("${password}", proxyPass)
                .replace("${scheme}", "http");

        try (ZipOutputStream zp = new ZipOutputStream(new FileOutputStream(pluginPath))) {
            zp.putNextEntry(new ZipEntry("manifest.json"));
            zp.write(manifestJson.getBytes());
            zp.closeEntry();
            zp.putNextEntry(new ZipEntry("background.js"));
            zp.write(backgroundJs.getBytes());
            zp.closeEntry();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        String proxyHost = "http-cla.abuyun.com";
        String proxyPort = "9030";
        // 隧道密码
        // TODO: 改成用户后台隧道列表中的值
        // 获取环境变量
        String proxyUser = "H01234567890123C";
        String proxyPass = "0123456789012345";
        // TODO: 可根据实际情况调整
        // 生成用户名和密码对应的浏览器插件压缩包用于后面启动加载
        String pluginPath = "/tmp/http-cla.abuyun.com_9030.zip";
        createProxyAuthExtension(proxyHost, proxyPort, proxyUser, proxyPass, pluginPath);

        // chromedriver
        // TODO: 改成实际路径
        // @see https://chromedriver.chromium.org/downloads
        System.setProperty("webdriver.chrome.driver", "/tmp/chromedriver");

        ChromeOptions chromeOptions = new ChromeOptions();

        // The traditional --headless, and since version 96,
        // Chrome has a new headless mode that allows users to get the full browser functionality (even run extensions).
        // Between versions 96 to 108 it was --headless=chrome, after version 109 --headless=new.
        // Using --headless=new should bring a better experience when using headless with Selenium.
        // @see https://www.selenium.dev/blog/2023/headless-is-going-away/
        // 无头模式
        chromeOptions.addArguments("--headless=new");
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.addExtensions(new File(pluginPath));

        WebDriver driver = new ChromeDriver(chromeOptions);

        driver.get("https://test.abuyun.com");

        System.out.println(driver.getPageSource());

        // 对网页进行截图
        File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

        try {
            // 保存截图文件
            FileUtils.copyFile(screenshot, new File("/tmp/screenshot.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 休眠30秒
        Thread.sleep(30000);

        // 关闭浏览器
        driver.quit();
    }
}

