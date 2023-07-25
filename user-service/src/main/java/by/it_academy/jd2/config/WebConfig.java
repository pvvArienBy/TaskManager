package by.it_academy.jd2.config;

import by.it_academy.jd2.service.util.ErrorResponseJsonComponent;
import by.it_academy.jd2.core.dto.StructuredErrorDTO;
import by.it_academy.jd2.service.convert.StringToLocalDateTimeConverter;
import by.it_academy.jd2.service.convert.UserCreateDtoToEntityConverter;
import by.it_academy.jd2.service.convert.UserEntityToDtoConverter;
import by.it_academy.jd2.service.convert.UserRegistrationDtoToEntityConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
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
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json()
                .modules(new ParameterNamesModule())
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .build();

        SimpleModule module = new SimpleModule();
        module.addSerializer(StructuredErrorDTO.class, new ErrorResponseJsonComponent.StructuredErrorResponseSerializer());
        objectMapper.registerModule(module);

        converters.add(new MappingJackson2HttpMessageConverter(objectMapper));
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new UserEntityToDtoConverter());
        registry.addConverter(new UserCreateDtoToEntityConverter());
        registry.addConverter(new UserRegistrationDtoToEntityConverter());
        registry.addConverter(new StringToLocalDateTimeConverter());
    }
}
