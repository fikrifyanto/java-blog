package tsajf.tailwindblog.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class SecurityUtils {

    public static UserDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            return userDetails;
        }
        return null;
    }

    public static String getBaseUrl(String path) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();

            String serverName = request.getServerName();
            int serverPort = request.getServerPort();
            String protocol = request.getScheme();

            StringBuilder baseUrlBuilder = new StringBuilder();
            baseUrlBuilder.append(protocol).append("://").append(serverName);

            if (("http".equals(protocol) && serverPort != 80) || ("https".equals(protocol) && serverPort != 443)) {
                baseUrlBuilder.append(":").append(serverPort);
            }

            if (!path.isEmpty()) {
                baseUrlBuilder.append("/").append(path);
            }

            return baseUrlBuilder.toString();
        }

        return null;
    }

}
