package by.it_academy.jd2.controllers.endpoints.web;

import by.it_academy.jd2.controllers.endpoints.web.ac.api.IAppContextAnnotation;
import by.it_academy.jd2.controllers.endpoints.web.ac.factory.AppContextAnnotationFactory;
import by.it_academy.jd2.service.api.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;

public class UserServlet extends HttpServlet {

    private IUserService userService;

    private ObjectMapper objectMapper;

    public UserServlet() {
    }

    @Override
    public void init() throws ServletException {
        super.init();
        IAppContextAnnotation appContextAnnotation = AppContextAnnotationFactory.getInstance();
        this.userService = appContextAnnotation.getUserService();
        this.objectMapper =  appContextAnnotation.getObjectMapper();
    }
}
