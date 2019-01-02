/*
 * COPYRIGHT: Copyright (c) 2018 by Nuance Communications, Inc.
 *  Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 *
 */

package com.nuance.him.customerror;



public class CustomErrorType {

    private final String errorMessage;

    /**
     *
     * @param errorMessage  getting error message
     */
    public CustomErrorType(final String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     *
     * @return errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }
}
