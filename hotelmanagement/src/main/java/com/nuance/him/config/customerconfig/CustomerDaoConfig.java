/*
 * COPYRIGHT: Copyright (c) 2018 by Nuance Communications, Inc.* COPYRIGHT: Copyright (c) 2018 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */

package com.nuance.him.config.customerconfig;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import com.nuance.him.config.DBConnectionConfig;
import com.nuance.him.dao.CustomerDao;
import com.nuance.him.dao.CustomerDaoImpl;
import java.util.logging.Logger;
import static org.springframework.util.Assert.notNull;

/**
 * Configuration for CustomerDao.
 */
@Configuration
@Import(DBConnectionConfig.class)
@PropertySource(value = {"classpath:customer_queries.xml"})
public class CustomerDaoConfig {

    private static final Logger LOG = Logger.getLogger(CustomerDaoConfig.class.getName());
    private static final String INSERT_CUSTOMER = "CustomerDaoImpl.addCustomer";
    private static final String SELECT_ALL_CUSTOMERS = "CustomerDaoImpl.selectAllCustomers";
    private static final String DELETE_CUSTOMER  = "CustomerDaoImpl.deleteCustomer";
    private static final String UPDATE_CUSTOMER = "CustomerDaoImpl.updateCustomer";
    private static final String SEARCH_CUSTOMER = "CustomerDaoImpl.searchCustomer";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${"+INSERT_CUSTOMER+"}")
    private String insertCustomer;

    @Value("${"+SELECT_ALL_CUSTOMERS+"}")
    private String selectAllCustomers;

    @Value("${"+DELETE_CUSTOMER+"}")
    private String deleteCustomer;

    @Value("${"+UPDATE_CUSTOMER+"}")
    private String updateCustomer;

    @Value("${"+SEARCH_CUSTOMER+"}")
    private String searchCustomer;

    /**
     * Validates jdbcTemplate and database queries.
     */
    @PostConstruct
    public void postConstruct() {
        LOG.info("running method postConstruct");
        notNull(jdbcTemplate,"missing bean jdbcTemplate");
        notNull(INSERT_CUSTOMER,"null value : INSERT_CUSTOMER");
        notNull(SELECT_ALL_CUSTOMERS,"null value : SELECT_ALL_CUSTOMERS");
        notNull(DELETE_CUSTOMER,"null value : DELETE_CUSTOMER");
        notNull(UPDATE_CUSTOMER,"null value : UPDATE_CUSTOMER");
        notNull(SEARCH_CUSTOMER,"null value : SEARCH_CUSTOMER");
    }

    /**
     * Factory for CustomerDao.
     * @return CustomerDaoImpl object
     */
    @Bean
    @Primary
       public CustomerDao customerDao() {
        LOG.info("running method customerDao");
        return new CustomerDaoImpl(jdbcTemplate,insertCustomer,selectAllCustomers,deleteCustomer,updateCustomer,searchCustomer);
    }
}
