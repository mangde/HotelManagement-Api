
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
