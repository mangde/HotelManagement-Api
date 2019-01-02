/*
 * COPYRIGHT: Copyright (c) 2018 by Nuance Communications, Inc.* COPYRIGHT: Copyright (c) 2018 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */

package com.nuance.him.controller;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.nuance.him.Exception.CustomerServicesException;
import com.nuance.him.model.Customer;
import com.nuance.him.service.CustomerServices;
import java.util.List;
import java.util.logging.Logger;

/**
 * Calls the customer services
 * for performing the customer operations.
 */
@RestController
@ComponentScan("com.nuance.him.customer.services")
@Validated
@RequestMapping("/hotel")
public class CustomerController {
    private static final Logger LOG = Logger.getLogger(CustomerController.class.getName());
    private CustomerServices customerServices;

    /**
     * Constructor.
     *
     * @param customerServices to inject the customerServices
     */
    public CustomerController(final CustomerServices customerServices) {
        this.customerServices = customerServices;
    }


    /**
     * To input the customer details and call addCustomer service.
     *
     * @param name customer name
     * @param email customer email
     * @param phone customer phone
     * @return ResponseEntity<String>
     */
    @RequestMapping(value = "/getCustomerDetails",method = RequestMethod.POST)
    public ResponseEntity<?> getCustomerDetails(
        @Valid @RequestParam("name") @NotNull @Size(min = 1,max=20) @Pattern(regexp="[A-Za-z]+",message = "name should contain only alphabets") final String name,
        @Valid @RequestParam("email") @Size(max=20) @Email final String email,
        @Valid @RequestParam("phone") @NotNull @Pattern(regexp="([0-9]{10})?",message = "phone should contain 10 digits") final String phone) {
        LOG.info("running method getCustomerDetails");
        int customerId=0;
        Customer customer = new Customer(name,email,Integer.parseInt(phone));
        try {
            customerId =  customerServices.addCustomer(customer);
        } catch (CustomerServicesException e) {
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        if(customerId>=0) {
            return new ResponseEntity<String>("Customer is registered successfully\n customer ID: "+customerId, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<String>("Customer is not registered",HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    /**
     * Displays all customers.
     *
     * @return ResponseEntity
     */
    @RequestMapping(value = "/displayCustomers",method = RequestMethod.GET)
    public ResponseEntity<?> displayCustomers() {
        LOG.info("running method displayCustomers");
        List<Customer> customerList;
        try {
            customerList=customerServices.getAllCustomers();
        } catch (CustomerServicesException e) {
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<List>(customerList,HttpStatus.OK);
    }

    /**
     * To input the customer id and delete the customer.
     *
     * @param id customer id
     * @return ResponseEntity<String>
     */
    @RequestMapping(value="/removeCustomer",method = RequestMethod.DELETE)
    public ResponseEntity<String> removeCustomer(@Valid @RequestParam("id") @NotNull @Min(value = 1,message = "Id should be minimum 1") final int id) {
        LOG.info("running method removeCustomer");
        int result=0;
        Customer customer;
        try {
            customer = customerServices.searchCustomer(id);
            if(customer!=null && customer.getId()==id) {
                result = customerServices.deleteCustomer(id);

            } else if(customer==null){

                return new ResponseEntity<String>("Customer not found",HttpStatus.NOT_FOUND);
            }
        } catch (CustomerServicesException e) {
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        if(result==1) {
            return new ResponseEntity<String>("Customer is deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Customer is not deleted",HttpStatus.NOT_FOUND);
        }
    }


    /**
     * To input the customer id and details to update the customer information.
     *
     * @param id customer id
     * @param email customer email
     * @param phone customer phone
     * @return ResponseBody<String>
     */
    @RequestMapping(value = "/updateCustomer",method = RequestMethod.PUT)
    public ResponseEntity<String> updateCustomer(
        @Valid @RequestParam("id") @NotNull @Min(value = 1,message = "Id should be minimum 1") final int id,
        @Valid @RequestParam(value = "email",required = false) @Size(max=20) @Email final String email,
        @Valid @RequestParam(value = "phone",required = false) @Pattern(regexp="([0-9]{10})?",message = "phone should contain 10 digits")final String phone) {
        LOG.info("running method updateCustomer");
        int result;
        Customer customer;
        try {
            customer = customerServices.searchCustomer(id);
            if(customer!=null && customer.getId()==id) {
                result = customerServices.updateCustomerInfo(id, email, phone);
            } else {
                return new ResponseEntity<String>("Customer not found",HttpStatus.NOT_FOUND);
            }
        } catch (CustomerServicesException e) {
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.ACCEPTED);
        }
        if(result==1) {
            return new ResponseEntity<String>("Customer is updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Not able to update customer Information",HttpStatus.NOT_FOUND);
        }
    }


    /**
     * Exception handler for ConstraintViolationException.
     * @param exception ConstraintViolationException
     * @return error message
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody String annotationExceptionHandler(ConstraintViolationException exception) {
        return exception.getMessage();
    }
}
