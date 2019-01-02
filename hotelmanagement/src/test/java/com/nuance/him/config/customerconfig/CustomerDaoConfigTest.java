/*
 * COPYRIGHT: Copyright (c) 2018 by Nuance Communications, Inc.* COPYRIGHT: Copyright (c) 2018 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */

package com.nuance.him.config.customerconfig;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import com.nuance.him.dao.CustomerDao;
import static org.junit.Assert.assertNotNull;

/**
 * Tests for CustomerDaoConfiguration.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {CustomerDaoConfig.class})
public class CustomerDaoConfigTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private CustomerDao customerDao;

    /**
     * Tests the CustomerDao bean.
     */
    @Test
    public void testCustomerDao() {
        assertNotNull(customerDao);
    }
}
