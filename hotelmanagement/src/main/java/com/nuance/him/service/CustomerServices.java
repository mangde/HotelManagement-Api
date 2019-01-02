/*
 * COPYRIGHT: Copyright (c) 2018 by Nuance Communications, Inc.* COPYRIGHT: Copyright (c) 2018 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */

package com.nuance.him.service;

import org.springframework.stereotype.Service;
import com.nuance.him.Exception.CustomerServicesException;
import com.nuance.him.model.Customer;
import java.util.List;

/**
 * Declares the customer CRUD operations.
 */
@Service
public interface CustomerServices {

    /**
     * To add the customer to table.
     *
     * @param customer object of the Customer class
     * @return int 1 if the customer is added else 0
     * @throws CustomerServicesException exception thrown by the jdbctemplate update method
     */
    int addCustomer(Customer customer) throws CustomerServicesException;

    /**
     * To get all the customers.
     *
     * @return List<Customer> list of the all customers
     * @throws CustomerServicesException exception thrown by the jdbctemplate update method
     */
    List<Customer> getAllCustomers() throws CustomerServicesException;

    /**
     * To delete the customer.
     *
     * @param id customer id for deleting the customer
     * @return int 1 if the customer is deleted else 0
     * @throws CustomerServicesException exception thrown by the jdbctemplate update method
     */
    int deleteCustomer(final int id) throws CustomerServicesException;

    /**
     * To update the customer information.
     *
     * @param id customer id
     * @param email email id to be updated
     * @param phone phone number to be updated
     * @return boolean true if the customer all values get updated else false
     * @throws CustomerServicesException exception thrown by the jdbctemplate update method
     */
    int updateCustomerInfo(final int id, final String email,final String phone) throws CustomerServicesException;

    /**
     * Search customer by customer ID.
     * @param id customer id
     * @return Customer if found or null if not found
     * @throws CustomerServicesException exception thrown by the jdbctemplate update method
     */
    Customer searchCustomer(int id) throws CustomerServicesException;



}
