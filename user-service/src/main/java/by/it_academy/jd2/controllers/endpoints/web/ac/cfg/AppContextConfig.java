package by.it_academy.jd2.controllers.endpoints.web.ac.cfg;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@ComponentScan("by.it_academy.jd2")
@PropertySources({
        @PropertySource("classpath:db.properties"),
        @PropertySource("classpath:hb.properties"),
        @PropertySource("classpath:root.properties")})

public class AppContextConfig {

    private static final String DEFAULT_SCHEMA = "hibernate.default_schema";

    private static final String SHOW_SQL = "hibernate.show_sql";

    private static final String HBM2DDL = "hibernate.hbm2ddl.auto";

    private final Environment environment;

    public AppContextConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new ParameterNamesModule());
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        return objectMapper;
    }

    @Bean
    public DataSource dataSource() {
        try {
            ComboPooledDataSource dataSource = new ComboPooledDataSource();

            dataSource.setDriverClass(environment.getProperty("jdbc.driver"));
            dataSource.setJdbcUrl(environment.getProperty("jdbc.url"));
            dataSource.setUser(environment.getProperty("root.user"));
            dataSource.setPassword(environment.getProperty("root.password"));
            return dataSource;
        } catch (Exception e) {
            throw new RuntimeException("Unable to create DataSource", e);
        }
    }

    @Bean
    @Primary
    public EntityManagerFactory entityManagerFactory(DataSource dataSource) {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setDataSource(dataSource);
        factory.setPackagesToScan("by.it_academy.jd2.dao.entity");
        factory.setPersistenceUnitName("userService");
        factory.setJpaProperties(hibernateProperties());
        factory.afterPropertiesSet();

        return factory.getObject();
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty(DEFAULT_SCHEMA, environment.getProperty(DEFAULT_SCHEMA));
        properties.setProperty(SHOW_SQL, environment.getProperty(SHOW_SQL));
        properties.setProperty(HBM2DDL, environment.getProperty(HBM2DDL));
        return properties;
    }

}
