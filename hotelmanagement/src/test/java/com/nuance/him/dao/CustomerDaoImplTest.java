/*
 * COPYRIGHT: Copyright (c) 2018 by Nuance Communications, Inc.* COPYRIGHT: Copyright (c) 2018 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */

package com.nuance.him.dao;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.nuance.him.Exception.CustomerDaoException;
import com.nuance.him.config.DBConnectionConfig;
import com.nuance.him.model.Customer;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doThrow;
import static org.testng.Assert.assertNull;

/**
 * Test class for CustomerDaoImpl.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {DBConnectionConfig.class})
@TestPropertySource(value = {"classpath:customer_queries.xml"})
@Transactional
public class CustomerDaoImplTest extends AbstractTransactionalTestNGSpringContextTests {
    private static final String INSERT_CUSTOMER = "CustomerDaoImpl.addCustomer";
    private static final String SELECT_ALL_CUSTOMERS = "CustomerDaoImpl.selectAllCustomers";
    private static final String DELETE_CUSTOMER = "CustomerDaoImpl.deleteCustomer";
    private static final String UPDATE_CUSTOMER = "CustomerDaoImpl.updateCustomer";
    private static final String SEARCH_CUSTOMER = "CustomerDaoImpl.searchCustomer";
    @Autowired
    private JdbcTemplate jdbcTemplateAuto;
    @Mock
    private JdbcTemplate jdbcTemplate;
    private CustomerDao customerDaoAuto;
    private CustomerDao customerDaoMock;
    private Customer customer;
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
     * Injects the mock.
     */
    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        customerDaoAuto=new CustomerDaoImpl(jdbcTemplateAuto,insertCustomer,selectAllCustomers,deleteCustomer,updateCustomer,searchCustomer);
        customerDaoMock=new CustomerDaoImpl(jdbcTemplate,insertCustomer,selectAllCustomers,deleteCustomer,updateCustomer,searchCustomer);
        customer = new Customer("rinki","rinki@gmail.com",123445);
    }

    /**
     * Tests the addCustomer method.
     * @throws Exception CustomerDaoException
     */
    @Test
    public void testAddCustomer() throws Exception{
        //addCustomer returns the Id of the newly created customer
        assertNotNull(customerDaoAuto);
        int key=customerDaoAuto.addCustomer(customer);
        assertNotNull(key);

    }

    /**
     * Tests the getAllCustomers method.
     * @throws Exception CustomerDaoException
     */
    @Test
    public void testGetAllCustomers() throws Exception {
        assertNotNull(customerDaoAuto);
        List<Customer> customers =customerDaoAuto.getAllCustomers();
        assertNotNull(customers);
    }

    /**
     * Tests deleteCustomer method.
     * @throws Exception CustomerDaoException
     */
    @Test
    public void testDeleteCustomer() throws Exception{
        assertNotNull(customerDaoAuto);
        assertEquals(1,customerDaoAuto.deleteCustomer(1));
        assertEquals(0,customerDaoAuto.deleteCustomer(26));
    }

    /**
     * Tests the deleteCustomer method failure.
     * @throws Exception CustomerDaoException
     */
    @Test(expectedExceptions = CustomerDaoException.class)
    public void testDeleteCustomerFailure() throws Exception {
        doThrow(DataIntegrityViolationException.class).when(jdbcTemplate).update(deleteCustomer,26);
        try {
            customerDaoMock.deleteCustomer(26);
        } catch (CustomerDaoException e) {
            assertEquals(DataIntegrityViolationException.class,e.getCause().getClass());
            Mockito.verify(jdbcTemplate).update(deleteCustomer,26);
            throw e;
        }
    }
    /**
     * Tests searchCustomer method.
     * @throws Exception CustomerDaoException
     */
    @Test
    public void testSearchCustomer() throws Exception{
        assertNotNull(customerDaoAuto);
        assertNotNull(customerDaoAuto.searchCustomer(1));
        assertNull(customerDaoMock.searchCustomer(200));
    }


    /**
     * Tests updateCustomerInfo method.
     * @throws Exception CustomerDaoException
     */
    @Test
    public void testUpdateCustomerInfo() throws Exception{
        assertNotNull(customerDaoAuto);
        assertEquals(1,customerDaoAuto.updateCustomerInfo(1,"neha@gmail.com","567"));
        assertEquals(0,customerDaoAuto.updateCustomerInfo(200,"neha@gmail.com","567"));

    }



}
