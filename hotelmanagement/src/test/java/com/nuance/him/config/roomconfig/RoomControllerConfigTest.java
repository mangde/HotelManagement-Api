/*
 * COPYRIGHT: Copyright (c) 2018 by Nuance Communications, Inc.
 *  Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 *
 */

package com.nuance.him.config.roomconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import com.nuance.him.controller.RoomController;
import static org.testng.Assert.assertNotNull;

@Import(RoomControllerConfig.class)
public class RoomControllerConfigTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private RoomController roomController;

    @Test
    public void testRoomController()  {
        assertNotNull(roomController);
    }
}