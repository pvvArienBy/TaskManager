package by.it_academy.jd2.controllers.endpoints.web.ac.api;


import by.it_academy.jd2.service.api.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface IAppContextAnnotation {

    IUserService getUserService();

    ObjectMapper getObjectMapper();

}
