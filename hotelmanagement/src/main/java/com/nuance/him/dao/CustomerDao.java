

package com.nuance.him.dao;

import com.nuance.him.Exception.CustomerDaoException;
import com.nuance.him.model.Customer;
import java.util.List;

/**
 * Defines the customer CRUD operations.
 */
public interface CustomerDao {

    /**
     * Adds customer.
     *
     * @param customer object of the Customer
     * @return int 1 if the customer is added else 0
     * @throws CustomerDaoException exception thrown by the jdbcTemplate update method
     */
    int addCustomer(Customer customer) throws CustomerDaoException;

    /**
     * Gets all the customers.
     *
     * @return List<Customer> list of the all customers
     * @throws CustomerDaoException exception thrown by the jdbctemplate update method
     */
    List<Customer> getAllCustomers() throws CustomerDaoException;

    /**
     * Deletes the customer.
     *
     * @param id customer id
     * @return int 1 if the customer is deleted else 0
     * @throws CustomerDaoException exception thrown by the jdbctemplate update method
     */
    int deleteCustomer(final int id) throws CustomerDaoException;

    /**
     * Updates the customer information.
     *
     * @param id customer id
     * @param email email id to be updated
     * @param phone phone number to be updated
     * @return boolean true if the customer all values get updated else false
     * @throws CustomerDaoException exception thrown by the jdbctemplate update method
     */
    int updateCustomerInfo(final int id,final String email,final String phone) throws CustomerDaoException;
    /**
     * Search customer by customer ID.
     * @param id customer id
     * @return Customer if found or null if not found
     * @throws CustomerDaoException exception thrown by the jdbctemplate query method
     */
    Customer searchCustomer(int id) throws CustomerDaoException;


}
