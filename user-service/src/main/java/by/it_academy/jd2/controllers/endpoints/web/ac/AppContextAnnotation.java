package by.it_academy.jd2.controllers.endpoints.web.ac;

import by.it_academy.jd2.controllers.endpoints.web.ac.api.IAppContextAnnotation;
import by.it_academy.jd2.controllers.endpoints.web.ac.cfg.AppContextConfig;
import by.it_academy.jd2.service.api.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AppContextAnnotation implements IAppContextAnnotation {

    private ApplicationContext context;

    public AppContextAnnotation() {
        this.context = new AnnotationConfigApplicationContext(AppContextConfig.class);
    }

    @Override
    public IUserService getUserService() {
        return context.getBean(IUserService.class);
    }

    @Override
    public ObjectMapper getObjectMapper() {
        return context.getBean(ObjectMapper.class);
    }
}
