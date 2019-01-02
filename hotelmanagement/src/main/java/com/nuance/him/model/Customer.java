/*
 * COPYRIGHT: Copyright (c) 2018 by Nuance Communications, Inc.* COPYRIGHT: Copyright (c) 2018 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */

package com.nuance.him.model;

import org.springframework.data.annotation.Id;

/**
 * Represents the attributes of the customer.
 * Bean class
 */
public class Customer {
    private final String name;
    private final String email;
    private final int phone;
    @Id
    private int id;

    /**
     * Constructor.
     *
     * @param name customer name
     * @param email customer email
     * @param phone customer phone
     */
    public Customer(String name,String email,int phone) {
        this.name=name;
        this.email=email;
        this.phone=phone;
    }

    /**
     * Gets the customer id.
     * @return int customer id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the customer id.
     * @param id customer id
     */
    public void setId(int id) {
        this.id=id;
    }

    /**
     * Gets the customer email.
     *
     * @return String
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the customer name.
     *
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the customer phone.
     *
     * @return int
     */
    public int getPhone() {
        return phone;
    }

    /**
     * Prints the object.
     *
     * @return String
     */
    @Override
    public String toString() {
        return id+"\t"+name+"\t"+email+"\t"+phone;
    }
}
