package tsajf.tailwindblog.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class ViewInterceptor implements HandlerInterceptor {

    @Override
    public void postHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                           @NonNull Object handler, ModelAndView modelAndView) {
        if (modelAndView != null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth.isAuthenticated()) {
                modelAndView.addObject("auth", auth);
            }
        }
    }
}
