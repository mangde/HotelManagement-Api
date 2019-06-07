

package com.nuance.him.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import com.nuance.him.config.bookingconfig.BookControllerConfig;
import com.nuance.him.config.roomconfig.RoomControllerConfig;

/**
 * Start point for application
 */
@SpringBootApplication
@Import({SpringHotelApp.Config.class})
@ImportResource({"classpath:room-sql-queries.xml","classpath:customer_queries.xml"})
public class SpringHotelApp {


    /**
     *
     * @param args  method arguments String array
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringHotelApp.class, args);
    }

    /**
     * validation bean for validation
     * @param messageSource validation message
     * @return  validation message
     */
    @Bean
    public LocalValidatorFactoryBean validator(MessageSource messageSource) {
        LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
        validatorFactoryBean.setValidationMessageSource(messageSource);
        return validatorFactoryBean;
    }


    /**
     * Aggregates all application Spring config into one
     */
    @Configuration
    @Import({ BookControllerConfig.class, RoomControllerConfig.class, com.nuance.him.config.customerconfig.CustomerControllerConfig.class})
    public static class Config{}

}
