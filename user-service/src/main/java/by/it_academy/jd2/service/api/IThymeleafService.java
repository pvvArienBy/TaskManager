package by.it_academy.jd2.service.api;

import java.util.Map;

public interface IThymeleafService {

    String createContent(String template, Map<String, Object> variables);
}