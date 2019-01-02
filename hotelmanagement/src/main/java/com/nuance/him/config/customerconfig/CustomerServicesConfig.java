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
import com.nuance.him.dao.CustomerDao;
import com.nuance.him.service.CustomerServices;
import com.nuance.him.service.CustomerServicesImpl;
import java.util.logging.Logger;
import static org.springframework.util.Assert.notNull;

/**
 * Configuration for CustomerServices.
 */
@Configuration
@Import(CustomerDaoConfig.class)
public class CustomerServicesConfig {

    private static final Logger LOG = Logger.getLogger(CustomerServicesConfig.class.getName());

    @Autowired
    private CustomerDao customerDAO;

    /**
     * Validate the variables.
     */
    @PostConstruct
    public void postConstruct() {
        LOG.info("running in postConstruct");
        notNull(customerDAO,"missing bean customerDao");
    }

    /**
     * Factory for CustomerServices.
     * @return CustomerServicesImpl object
     */
    @Bean
    public CustomerServices customerServices() {
        LOG.info("running method customerServices");
        return new CustomerServicesImpl(customerDAO);
    }
}
