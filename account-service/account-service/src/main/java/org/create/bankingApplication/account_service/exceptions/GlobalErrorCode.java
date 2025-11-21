package org.create.bankingApplication.account_service.exceptions;

public class GlobalErrorCode {
	private GlobalErrorCode() {} //noone should initialise this class as it only contain constant fileds
	public static final String NOT_FOUND = "404";
    public static final String CONFLICT = "409";

    public static final String BAD_REQUEST = "400";
}
