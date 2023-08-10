package by.it_academy.jd2.config;

import by.it_academy.jd2.service.converters.*;
import by.it_academy.jd2.service.utils.ErrorResponseJsonComponent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.example.mylib.tm.itacademy.errors.ErrorResponse;
import org.example.mylib.tm.itacademy.errors.StructuredErrorResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Bean
    public Jackson2ObjectMapperBuilder jacksonBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();

        builder.propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        builder.modules(new ParameterNamesModule());
        builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return builder;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        ObjectMapper objectMapper = jacksonBuilder().build();

        SimpleModule module = new SimpleModule();
        module.addSerializer(StructuredErrorResponse.class, new ErrorResponseJsonComponent.StructuredErrorResponseSerializer());
        module.addSerializer(ErrorResponse.class, new ErrorResponseJsonComponent.ErrorResponseSerializer());
        objectMapper.registerModule(module);

        converters.add(new MappingJackson2HttpMessageConverter(objectMapper));
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new ProjectCreateUpdateDtoToEntityConverter());
        registry.addConverter(new ProjectEntityToDtoConverter());
        registry.addConverter(new TaskCreateUpdateDtoToEntityConverter());
        registry.addConverter(new TaskEntityToDtoConverter());
        registry.addConverter(new StringToLocalDateTimeConverter());
        registry.addConverter(new UserDtoToUserCheckDtoConverter());
        registry.addConverter(new PageEntityToPageDtoConverter(
                new ProjectEntityToDtoConverter(), new TaskEntityToDtoConverter()));
    }
}
