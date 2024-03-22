package tsajf.tailwindblog.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class UrlService {

    public String getBaseUrl(String path) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();

            String serverName = request.getServerName();

            String protocol = request.getScheme();

            if (path.isEmpty()) {
                return protocol + "://" + serverName;
            }

            return protocol + "://" + serverName + "/" + path;

        }

        return null;
    }

}
