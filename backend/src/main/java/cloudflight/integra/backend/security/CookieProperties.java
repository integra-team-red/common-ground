package cloudflight.integra.backend.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "app.security.cookie")
public class CookieProperties {
    private boolean httpOnly = false;
    private boolean secure = true;
    private String sameSite = "Strict";
    private String path = "/";
    private long maxAgeHours = 24;

    public boolean isHttpOnly() { return httpOnly; }
    public void setHttpOnly(boolean httpOnly) { this.httpOnly = httpOnly; }

    public boolean isSecure() { return secure; }
    public void setSecure(boolean secure) { this.secure = secure;  }

    public String getSameSite() { return sameSite; }
    public void setSameSite(String sameSite) { this.sameSite = sameSite; }

    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }

    public long getMaxAgeHours() { return maxAgeHours; }
    public void setMaxAgeHours(long maxAgeHours) { this.maxAgeHours = maxAgeHours; }
}
