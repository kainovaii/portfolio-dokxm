package fr.kainovaii.portfolio.component;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("webAccess")
public class RouteAccessChecker
{
    @Autowired
    private HttpServletRequest request;

    public String getCurrentPath()
    {
        return request.getRequestURI();
    }

    public boolean isRouteMatch(String pattern)
    {
        String currentPath = getCurrentPath();

        if ("/posts/:slug".equals(pattern)) {
            if (currentPath.startsWith("/posts/") && currentPath.length() > "/posts/".length()) {
                return true;
            }
            return false;
        }
        return currentPath.equals(pattern);
    }
}