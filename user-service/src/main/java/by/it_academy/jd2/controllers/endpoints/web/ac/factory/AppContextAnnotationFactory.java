package by.it_academy.jd2.controllers.endpoints.web.ac.factory;

import by.it_academy.jd2.controllers.endpoints.web.ac.AppContextAnnotation;
import by.it_academy.jd2.controllers.endpoints.web.ac.api.IAppContextAnnotation;

public class AppContextAnnotationFactory {
    private static volatile IAppContextAnnotation instance;

    private AppContextAnnotationFactory() {
    }

    public static IAppContextAnnotation getInstance() {
        if (instance == null) {
            synchronized (AppContextAnnotationFactory.class) {
                if (instance == null) {
                    instance = new AppContextAnnotation();
                }
            }

        }
        return instance;
    }
}
