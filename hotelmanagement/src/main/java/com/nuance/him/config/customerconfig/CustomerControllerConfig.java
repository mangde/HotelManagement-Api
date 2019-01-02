/*
 * COPYRIGHT: Copyright (c) 2018 by Nuance Communications, Inc.* COPYRIGHT: Copyright (c) 2018 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */

package com.nuance.him.config.customerconfig;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import com.nuance.him.controller.CustomerController;
import com.nuance.him.service.CustomerServices;
import java.util.logging.Logger;
import static org.springframework.util.Assert.notNull;

/**
 * Configuration for CustomerController.
 */
@Configuration
@Import(CustomerServicesConfig.class)
public class CustomerControllerConfig {

    private static final Logger LOG = Logger.getLogger(CustomerControllerConfig.class.getName());

    @Autowired
    private CustomerServices customerServices;

    /**
     * Validate the variables.
     */
    @PostConstruct
    public void postConstruct() {
        LOG.info("running in postConstruct");
        notNull(customerServices,"missing bean customerServices");
    }

    /**
     * Factory for CustomerController.
     *
     * @return CustomerController object
     */
    @Bean
    public CustomerController customerController() {
        LOG.info("running in method customerController");
        return new CustomerController(customerServices);
    }
}
