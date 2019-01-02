/*
 * COPYRIGHT: Copyright (c) 2018 by Nuance Communications, Inc.* COPYRIGHT: Copyright (c) 2018 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */

package com.nuance.him.service;

import org.springframework.stereotype.Service;
import com.nuance.him.Exception.CustomerDaoException;
import com.nuance.him.Exception.CustomerServicesException;
import com.nuance.him.dao.CustomerDao;
import com.nuance.him.model.Customer;
import java.util.List;
import java.util.logging.Logger;

/**
 * Calls the customer CRUD operations from Dao.
 */
@Service
public class CustomerServicesImpl implements CustomerServices {

    private static final Logger LOG = Logger.getLogger(CustomerServicesImpl.class.getName());
    private final CustomerDao customerDAO;

    /**
     *Constructor.
     *
     * @param customerDAO for injecting the customerDao reference variable.
     */
    public CustomerServicesImpl(CustomerDao customerDAO) {
        this.customerDAO=customerDAO;
    }

    @Override
    public int addCustomer(Customer customer) throws CustomerServicesException {
        LOG.info("running method addCustomer");
        try {
            return customerDAO.addCustomer(customer);
        } catch (CustomerDaoException e) {
            throw new CustomerServicesException(e.getMessage(),e);
        }
    }

    @Override
    public List<Customer> getAllCustomers() throws CustomerServicesException {
        LOG.info("running method getAllCustomers");
        try {
            return customerDAO.getAllCustomers();
        } catch (CustomerDaoException e) {
            throw new CustomerServicesException(e.getMessage(),e);
        }
    }

    @Override
    public int deleteCustomer(final int id) throws CustomerServicesException {
        LOG.info("running method deleteCustomer");
        try {
            return customerDAO.deleteCustomer(id);
        } catch (CustomerDaoException e) {
            throw new CustomerServicesException(e.getMessage(),e);
        }
    }

    @Override
    public int updateCustomerInfo(final int id,final String email,final String phone) throws CustomerServicesException {
        LOG.info("running method updateCustomerInfo");
        try {
            return customerDAO.updateCustomerInfo(id,email,phone);
        } catch (CustomerDaoException e) {
            throw new CustomerServicesException(e.getMessage(),e);
        }
    }

    @Override
    public Customer searchCustomer(int id) throws CustomerServicesException{
        LOG.info("running method searchCustomer");
        try {
            return customerDAO.searchCustomer(id);
        } catch (CustomerDaoException e) {
            throw new CustomerServicesException(e.getMessage(),e);
        }
    }
}
