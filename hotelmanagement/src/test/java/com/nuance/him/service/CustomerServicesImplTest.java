/*
 * COPYRIGHT: Copyright (c) 2018 by Nuance Communications, Inc.* COPYRIGHT: Copyright (c) 2018 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */

package com.nuance.him.service;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.nuance.him.Exception.CustomerDaoException;
import com.nuance.him.Exception.CustomerServicesException;
import com.nuance.him.dao.CustomerDao;
import com.nuance.him.model.Customer;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.testng.AssertJUnit.assertNotNull;

/**
 * Test class for CustomerServicesImpl.
 */
@RunWith(SpringRunner.class)
public class CustomerServicesImplTest extends AbstractTestNGSpringContextTests {

    @Mock
    private CustomerDao customerDao;

    private CustomerServices customerServices;
    private Customer customer;

    /**
     * Setup for running test cases.
     * Injecting mocks
     */
    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        customerServices = new CustomerServicesImpl(customerDao);
        customer = new Customer("Riya","riya@gmail.com",1234567898);
    }

    /**
     * Tests the addCustomer method.
     * @throws Exception CustomerServicesException
     */
    @Test
    public void testAddCustomer() throws Exception{
        when(customerDao.addCustomer(customer)).thenReturn(anyInt());
        assertEquals(0,customerServices.addCustomer(customer));
        Mockito.verify(customerDao).addCustomer(customer);
    }

    /**
     * Tests the addCustomer method failure.
     * @throws Exception CustomerServicesException
     */
    @Test(expectedExceptions = CustomerServicesException.class)
    public void testAddCustomerFailure() throws Exception {
        doThrow(CustomerDaoException.class).when(customerDao).addCustomer(customer);
        try {
            customerServices.addCustomer(customer);
        } catch (CustomerServicesException e) {
            assertEquals(CustomerDaoException.class,e.getCause().getClass());
            Mockito.verify(customerDao).addCustomer(customer);
            throw e;
        }
    }

    /**
     * Tests getAllCustomers method.
     * @throws Exception CustomerServicesException
     */
    @Test
    public void testGetAllCustomers() throws Exception{
        List<Customer> customers = new ArrayList<>();
        when(customerDao.getAllCustomers()).thenReturn(customers);
        assertEquals(customers,customerServices.getAllCustomers());
        Mockito.verify(customerDao).getAllCustomers();
    }

    /**
     * Tests getAllCustomers method failure.
     * @throws Exception CustomerServicesException
     */
    @Test(expectedExceptions = CustomerServicesException.class)
    public void testGetAllCustomerFailure() throws Exception{
        doThrow(CustomerDaoException.class).when(customerDao).getAllCustomers();
        try {
            customerServices.getAllCustomers();
        } catch (CustomerServicesException e) {
            assertEquals(CustomerDaoException.class,e.getCause().getClass());
            Mockito.verify(customerDao).getAllCustomers();
            throw e;
        }
    }

    /**
     * Tests deleteCustomer method.
     * @throws Exception CustomerServicesException
     */
    @Test
    public void testDeleteCustomer() throws Exception {
        when(customerDao.deleteCustomer(anyInt())).thenReturn(anyInt());
        assertEquals(0,customerServices.deleteCustomer(1));
        Mockito.verify(customerDao).deleteCustomer(anyInt());
    }

    /**
     * Tests deleteCustomer method failure.
     * @throws Exception CustomerServicesException
     */
    @Test(expectedExceptions = CustomerServicesException.class)
    public void testDeleteCustomerFailure() throws Exception {
        doThrow(CustomerDaoException.class).when(customerDao).deleteCustomer(anyInt());
        try {
            customerServices.deleteCustomer(anyInt());
        } catch (CustomerServicesException e) {
            assertEquals(CustomerDaoException.class,e.getCause().getClass());
            Mockito.verify(customerDao).deleteCustomer(anyInt());
            throw e;
        }
    }

    /**
     * Tests updateCustomerInfo method.
     * @throws Exception CustomerServicesException
     */
    @Test
    public void testUpdateCustomerInfo() throws Exception {
        when(customerDao.updateCustomerInfo(anyInt(),anyString(),anyString())).thenReturn(1);
        assertEquals(1,customerServices.updateCustomerInfo(1,"email_id","12345"));
        Mockito.verify(customerDao).updateCustomerInfo(anyInt(),anyString(),anyString());
    }

    /**
     * Tests updateCustomerInfo method failure.
     * @throws Exception CustomerServicesException
     */
    @Test(expectedExceptions = CustomerServicesException.class)
    public void testUpdateCustomerInfoFailure() throws Exception {
        doThrow(CustomerDaoException.class).when(customerDao).updateCustomerInfo(anyInt(),anyString(),anyString());
        try {
            customerServices.updateCustomerInfo(anyInt(),anyString(),anyString());
        } catch (CustomerServicesException e) {
            assertEquals(CustomerDaoException.class,e.getCause().getClass());
            Mockito.verify(customerDao).updateCustomerInfo(anyInt(),anyString(),anyString());
            throw e;
        }
    }
    /**
     * Tests searchCustomer method.
     * @throws Exception CustomerServicesException
     */
    @Test
    public void testSearchCustomer() throws Exception {
        when(customerDao.searchCustomer(anyInt())).thenReturn(customer);
        assertNotNull(customerServices.searchCustomer(1));
        Mockito.verify(customerDao).searchCustomer(anyInt());
    }

    /**
     * Tests searchCustomer method failure.
     * @throws Exception CustomerServicesException
     */
    @Test(expectedExceptions = CustomerServicesException.class)
    public void testSearchCustomerFailure() throws Exception {
        doThrow(CustomerDaoException.class).when(customerDao).searchCustomer(anyInt());
        try {
            customerServices.searchCustomer(anyInt());
        } catch (CustomerServicesException e) {
            assertEquals(CustomerDaoException.class,e.getCause().getClass());
            Mockito.verify(customerDao).searchCustomer(anyInt());
            throw e;
        }
    }


}
