/*
 * COPYRIGHT: Copyright (c) 2018 by Nuance Communications, Inc.* COPYRIGHT: Copyright (c) 2018 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */

package com.nuance.him.dao;

import org.springframework.jdbc.core.RowMapper;
import com.nuance.him.model.Customer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * RowMapper to display the table rows mapping them to the objects.
 */
public  class CustomerMapper implements RowMapper<Customer> {

    private static final Logger LOG = Logger.getLogger(CustomerMapper.class.getName());
    /**
     * Maps table row to the Customer object.
     *
     * @param rs result set
     * @param rowNum current row number
     * @return customer
     * @throws SQLException SQLException
     */
    @Override
    public Customer mapRow(ResultSet rs,int rowNum) throws SQLException {
        LOG.info("running method mapRow of CustomerMapper");
        Customer customer= new Customer(rs.getString("customerName"),rs.getString("email"),rs.getInt("phone"));
        customer.setId(rs.getInt("customerId"));
        return customer;
    }
}
