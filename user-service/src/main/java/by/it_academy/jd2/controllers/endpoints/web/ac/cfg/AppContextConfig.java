package by.it_academy.jd2.controllers.endpoints.web.ac.cfg;

import by.it_academy.jd2.dao.api.IUserDao;
import by.it_academy.jd2.dao.db.UserJDBCDao;
import by.it_academy.jd2.service.UserService;
import by.it_academy.jd2.service.api.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    //todo this check
    @Autowired
    private Environment env;


    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty(DEFAULT_SCHEMA, env.getProperty(DEFAULT_SCHEMA));
        properties.setProperty(SHOW_SQL, env.getProperty(SHOW_SQL));
        properties.setProperty(HBM2DDL, env.getProperty(HBM2DDL));

        return properties;
    }

    @Bean
    public IUserDao userJDBCDao() {
        return new UserJDBCDao(entityManager());
    }

    @Bean
    public IUserService userService() {
        return new UserService(userJDBCDao());
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public DataSource dataSource() {
        try {
            ComboPooledDataSource dataSource = new ComboPooledDataSource();

            dataSource.setDriverClass(env.getProperty("jdbc.driver"));
            dataSource.setJdbcUrl(env.getProperty("jdbc.url"));
            dataSource.setUser(env.getProperty("root.user"));
            dataSource.setPassword(env.getProperty("root.password"));
            return dataSource;
        } catch (Exception e) {
            throw new RuntimeException("Unable to create DataSource", e);
        }
    }

    @Bean
    public EntityManagerFactory entityManager() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();

        emf.setDataSource(dataSource());
        emf.setPackagesToScan("by.it_academy.jd2.dao.entity");
        emf.setJpaVendorAdapter(hibernateJpaVendorAdapter);
        emf.setJpaProperties(hibernateProperties());
        emf.afterPropertiesSet();

        return emf.getObject();
    }

//    @Bean
//    public DataSource dataSource() {
}