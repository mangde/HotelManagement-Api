

package com.nuance.him.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import com.nuance.him.Exception.CustomerDaoException;
import com.nuance.him.model.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Logger;

/**
 *  Implements the customer operations.
 */
public class CustomerDaoImpl implements CustomerDao {
    private static final Logger LOG = Logger.getLogger(CustomerDaoImpl.class.getName());
    private final JdbcTemplate jdbcTemplate;
    private final String insertCustomer;
    private final String selectAllCustomers;
    private final String deleteCustomer;
    private final String updateCustomer;
    private final String searchCustomer;
    private String updateEmail;
    private int updatePhone;

    /**
     * Constructor.
     * @param jdbcTemplate jdbcTemplate
     * @param insertCustomer insert query for customer
     * @param selectAllCustomers select all customer query
     * @param deleteCustomer delete query for customer
     * @param updateCustomer update query for customer
     * @param searchCustomer search with specific id query for customer
     */
    public CustomerDaoImpl(JdbcTemplate jdbcTemplate,String insertCustomer,String selectAllCustomers,String deleteCustomer,String updateCustomer,String searchCustomer) {
        this.jdbcTemplate=jdbcTemplate;
        this.insertCustomer=insertCustomer;
        this.selectAllCustomers=selectAllCustomers;
        this.deleteCustomer=deleteCustomer;
        this.updateCustomer=updateCustomer;
        this.searchCustomer=searchCustomer;
    }

    @Override
    public int addCustomer(Customer customer) throws CustomerDaoException {
        LOG.info("running method addCustomer");
        KeyHolder holder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(insertCustomer, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, customer.getName());
                    ps.setString(2, customer.getEmail());
                    ps.setInt(3, customer.getPhone());
                    return ps;
                }
            }, holder);
            return holder.getKey().intValue();
        }
        catch (DataAccessException e) {
            throw new CustomerDaoException("Failed to add customer",e);
        }
    }

    @Override
    public List<Customer> getAllCustomers() throws CustomerDaoException {
        LOG.info("running method getAllCustomers");
        try{
            return jdbcTemplate.query(selectAllCustomers,new CustomerMapper());
        } catch (DataAccessException e) {
            throw new CustomerDaoException("Failed to display customer details",e);
        }
    }

    @Override
    public int deleteCustomer(final int id) throws CustomerDaoException {
        LOG.info("running method deleteCustomer");
        try {
            return jdbcTemplate.update(deleteCustomer,id);
        } catch (DataAccessException e) {
            throw new CustomerDaoException("Failed to delete the customer",e);
        }
    }

    @Override
    public int updateCustomerInfo(final int id,final String email,final String phone) throws CustomerDaoException {
        LOG.info("running method updateCustomerInfo");
        getFieldUpdateValues(id,email,phone);
        try {
            return jdbcTemplate.update(updateCustomer,updatePhone,updateEmail,id);
        } catch(EmptyResultDataAccessException e) {
            throw new CustomerDaoException("Failed to update the customer information",e);
        }
    }
    @Override
    public Customer searchCustomer(int id) throws CustomerDaoException{
        LOG.info("running method searchCustomer");
        Customer customer=null;
        List<Customer> customers;
        try {
            customers = jdbcTemplate.query(searchCustomer, new CustomerMapper(), id);
        } catch (DataAccessException e) {
            throw new CustomerDaoException("Failed to search the customer information",e);
        }
        if (customers.size() > 0) {
            customer = customers.get(0);
        }
        return customer;
    }


    /**
     * Checks whether the field value contains the new value or is empty
     * if the new value is empty an old value is assigned to the field variable.
     * @param id id of the customer
     * @param email email id to be updated
     * @param phone phone number to be updated
     * @throws CustomerDaoException exception for query failure
     */
    private void getFieldUpdateValues(int id,String email,String phone) throws CustomerDaoException {
        LOG.info("running in getFieldUpdateValues");
        Customer customer = searchCustomer(id);
        if(email.equals("")) {
            updateEmail=customer.getEmail();
        } else {
            updateEmail=email;
        }
        if(phone.equals("")) {
            updatePhone=customer.getPhone();
        } else {
            updatePhone=Integer.parseInt(phone);
        }
    }

}
