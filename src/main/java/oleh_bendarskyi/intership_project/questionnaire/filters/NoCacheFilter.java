package oleh_bendarskyi.intership_project.questionnaire.filters;

import org.apache.log4j.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter disables data caching
 */
public class NoCacheFilter implements Filter {
    private final static Logger LOG = Logger.getLogger(NoCacheFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {
        LOG.trace("NoCacheFilter init method");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        response.setHeader("Cache-Control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        LOG.trace("NoCacheFilter destroy method");
    }
}