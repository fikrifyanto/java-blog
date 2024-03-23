package tsajf.tailwindblog.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import tsajf.tailwindblog.model.User;
import tsajf.tailwindblog.utils.SecurityUtils;

@Component
public class ViewInterceptor implements HandlerInterceptor {

    @Override
    public void postHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                           @NonNull Object handler, ModelAndView modelAndView) {
        if (modelAndView != null) {
            User authenticatedUser = SecurityUtils.getCurrentUser();
            modelAndView.addObject("auth", authenticatedUser);
        }
    }
}
