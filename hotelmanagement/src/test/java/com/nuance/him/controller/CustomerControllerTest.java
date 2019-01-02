/*
 * COPYRIGHT: Copyright (c) 2018 by Nuance Communications, Inc.* COPYRIGHT: Copyright (c) 2018 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */

package com.nuance.him.controller;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.nuance.him.app.SpringHotelApp;
import com.nuance.him.model.Customer;
import com.nuance.him.service.CustomerServices;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testng.Assert.assertTrue;

/**
 * Test class for CustomerController.
 */
@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringHotelApp.class)
public class CustomerControllerTest extends AbstractTestNGSpringContextTests {
    @Mock
    private CustomerServices customerServices;

    private MockMvc mockMvc;
    private MockMvc mockMvcWeb;
    private CustomerController customerController;
    private Customer customer;

    @Autowired
    private WebApplicationContext webApplicationContext;

    /**
     * Set up method.
     * injecting mocks
     */
    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new CustomerController(customerServices)).build();      //for testing the links
        mockMvcWeb = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();                     //for testing the validations applied to the request parameters
        customerController=new CustomerController(customerServices);
        customer =new Customer("moni","kirange@gmail.com",12345);
        customer.setId(1);
    }

    /**
     * Tests /getCustomerDetails link.
     * @throws Exception exception
     */
    @Test
    public void testGetCustomerDetails() throws Exception{
        when(customerServices.addCustomer(any())).thenReturn(1);
        mockMvcWeb.perform(MockMvcRequestBuilders.post("/hotel/getCustomerDetails")
           .param("name","moni")
           .param("email","kirange@gmail.com")
           .param("phone","1234567898")).
            andExpect(status().isCreated()).andReturn();
    }

    /**
     * Tests the Validation of request parameters for getCustomerDetails link.
     * @throws Exception exception
     */
    @Test//(expectedExceptions = ConstraintViolationException.class)
    public void testDisplayCustomersInvalidParameters() throws Exception {
        try {
            mockMvcWeb.perform(MockMvcRequestBuilders.post("/hotel/getCustomerDetails")
                .param("name","neha123")
                .param("email","nehgm.com")
                .param("phone","455"));
        } catch (NestedServletException e) {
            assertTrue(e.getMessage().contains("name should contain only alphabets"));
            assertTrue(e.getMessage().contains("must be a well-formed email address"));
            assertTrue(e.getMessage().contains("phone should contain 10 digits"));
        }
    }

    /**
     * Tests /displayCustomers link and getAllCustomers method.
     * @throws Exception exception
     */
    @Test
    public void testDisplayCustomers() throws Exception {
        List<Customer> customers = new ArrayList<>();
        when(customerServices.getAllCustomers()).thenReturn(customers);
        mockMvc.perform(MockMvcRequestBuilders.get("/hotel/displayCustomers")).andExpect(status().isOk());
    }
    @Test
    public void testRemoveCustomer() throws Exception {
        when(customerServices.searchCustomer(anyInt())).thenReturn(customer);
        when(customerServices.deleteCustomer(anyInt())).thenReturn(1);
        mockMvc.perform(MockMvcRequestBuilders.delete("/hotel/removeCustomer").param("id","1")).andExpect(status().isOk());
    }


    /**
     * Tests the Validation of request parameter of removeCustomer link.
     * @throws Exception exception
     */
    @Test
    public void testRemoveCustomerInvalidId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/hotel/removeCustomer")
        .param("id","adf"))
            .andExpect(status().isBadRequest());

        try {
            mockMvcWeb.perform(MockMvcRequestBuilders.delete("/hotel/removeCustomer")
                .param("id","-1"));
        } catch (NestedServletException e) {
            assertTrue(e.getMessage().contains("Id should be minimum 1"));
        }
    }




    @Test
    public void testUpdateCustomer() throws Exception{
        when(customerServices.searchCustomer(anyInt())).thenReturn(customer);
        when(customerServices.updateCustomerInfo(1,"kirange@gmail.com","12345")).thenReturn(1);
        mockMvc.perform(MockMvcRequestBuilders.put("/hotel/updateCustomer")
            .param("id","1")
            .param("email","kirange@gmail.com")
            .param("phone","12345")).andExpect(status().isOk());
    }



    /**
     * Tests the validation of customer Id for updateCustomer link.
     * @throws Exception exception
     */
    @Test
    public void testUpdateCustomerInvalidId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/hotel/updateCustomer")
        .param("id","abc")
        .param("email","abcdef")
        .param("phone","123sd"))
            .andExpect(status().isBadRequest());

        try {
            mockMvcWeb.perform(MockMvcRequestBuilders.put("/hotel/updateCustomer")
                .param("id","-1")
                .param("email","abcdef")
                .param("phone","123sd"));
        } catch (NestedServletException e) {
            assertTrue(e.getMessage().contains("Id should be minimum 1"));
        }
    }

    /**
     * Tests the validation of request parameters of updateCustomer link.
     * @throws Exception exception
     */
    @Test
    public void testUpdateCustomerInfoInvalidInfo() throws Exception {
        try {
            mockMvcWeb.perform(MockMvcRequestBuilders.put("/hotel/updateCustomer")
                .param("id","1")
                .param("email","abcdef")
                .param("phone","123sd"));
        } catch (NestedServletException e) {
            System.out.println(e.getMessage());
            assertTrue(e.getMessage().contains("must be a well-formed email address"));
            assertTrue(e.getMessage().contains("phone should contain 10 digits"));
        }
    }
}
