package oleh_bendarskyi.intership_project.questionnaire.filters;


import oleh_bendarskyi.intership_project.questionnaire.models.User;
import org.apache.log4j.Logger;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;


public class MyFaceListener implements PhaseListener {

    private static final Logger LOGGER =
            Logger.getLogger(MyFaceListener.class);

//    @Override
//    public void doFilter(ServletRequest servletRequest,
//                         ServletResponse servletResponse, FilterChain filterChain)
//            throws IOException, ServletException {
//
//        HttpServletRequest httpServletRequest =
//                (HttpServletRequest) servletRequest;
//        HttpServletResponse httpServletResponse =
//                (HttpServletResponse) servletResponse;
//
//        Enumeration<String> en = httpServletRequest
//                .getSession().getAttributeNames();
//
//        while (en.hasMoreElements()){
//            System.out.println(en.nextElement() + "^^^^^^^^^^^^^^^^^^^");
//        }
//
//        FacesContext context = FacesContext.getCurrentInstance();
//        System.out.println(context == null);
//        if(context!=null) {
//            Map map = context.getAttributes();
//
//            for (Object n : map.keySet()) {
//                System.out.println(n + "###############");
//            }
//        }
//
//        User user = (User) httpServletRequest
//                .getSession().getAttribute("user");
//
//        System.out.println("/////////////////////////////////////" + user);
//        String uri = httpServletRequest.getRequestURI();
//        String resource = uri.replaceAll(".*/", "");
//        System.out.println("*******************" + resource);
//
//        if (user == null && !resource.equals("login")) {
//            httpServletRequest.getRequestDispatcher("login").forward(servletRequest, servletResponse);
//        } else {
//            filterChain.doFilter(servletRequest, servletResponse);
//        }// Logged-in user found, so just continue request.
//
//      /*  if (user == null && (!resource.equals("/")|!resource.equals("login"))) {
//            httpServletResponse.sendRedirect("login"); // No logged-in user found, so redirect to login page.
//        } else if(resource.equals("login")){
//            System.out.println("1111111111111111111111");
//            httpServletResponse.sendRedirect( "/");
//            System.out.println("2222222222222222222222");
//        } else {
//            filterChain.doFilter(servletRequest, servletResponse); // Logged-in user found, so just continue request.
//        }*/
//
//
//        /*if (user != null) {
//            if (userManager.isLoggedIn()) {
//                LOGGER.debug("user is logged in");
//                // user is logged in, continue request
//                filterChain.doFilter(servletRequest, servletResponse);
//            } else {
//                LOGGER.debug("user is not logged in");
//                // user is not logged in, redirect to login page
//                httpServletResponse.sendRedirect(
//                        httpServletRequest.getContextPath() + LOGIN_PAGE);
//            }
//        } else {
//            LOGGER.debug("userManager not found");
//            // user is not logged in, redirect to login page
//            httpServletResponse.sendRedirect(
//                    httpServletRequest.getContextPath() + LOGIN_PAGE);
//        }*/
//    }



    @Override
    public void afterPhase(PhaseEvent phaseEvent) {
        LOGGER.debug("loginFilter initialized");
    }

    @Override
    public void beforePhase(PhaseEvent phaseEvent) {

    }

    @Override
    public PhaseId getPhaseId() {
        return null;
    }
}