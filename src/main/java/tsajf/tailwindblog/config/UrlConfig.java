package tsajf.tailwindblog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"default", "prod"})
public class UrlConfig {

    @Value("${server.port}")
    private int serverPort;

    @Value("${app.domain}")
    private String url;

    public String getBaseUrl(String path) {
        if (isLocalEnvironment()) {
            return "http://localhost:" + serverPort + "/" + path;
        } else {
            return url + "/" + path;
        }
    }

    private boolean isLocalEnvironment() {
        return url.isEmpty();
    }

}
