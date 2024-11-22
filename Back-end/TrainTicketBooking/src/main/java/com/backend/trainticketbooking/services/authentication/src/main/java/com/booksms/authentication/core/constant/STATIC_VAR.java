package com.booksms.authentication.core.constant;

public class STATIC_VAR {
    public static final String IMAGE_STORAGE_PATH = "D:/book micro-service/upload/user/";
    public static final int MAX_FAILED_ATTEMPTS = 5;
    public static final Object INSUFFICIENT_ROLE_EXCEPTION_MESSAGE = "You are not allowed to access this resource";
    public static final long EXPIRATION_JWT_IN_MILLISECONDS=  1000 * 60 * 60 * 24 * 7;

    public static final String BLACKLISTED_TOKEN_MESSAGE = "your token has been block";
    public static final String SIGNATURE_EXCEPTION_MESSAGE = "Invalid Token";
    public static final String INTERNAL_SERVER_ERROR_MESSAGE = "Internal Server Error,please contact admin";
    public static final String ACCESS_DENIED_PROPERTY_REASON = "Access denied";

    public static final String SESSION_STORAGE="SESSION_STORAGE";


    public static final String GLOBAL_ADMIN_ROLE_NAME= "GLOBAL ADMIN";
    public static final String ACCOUNTING_ADMIN_ROLE_NAME= "ACCOUNTING MANAGER";
    public static final String TICKET_ADMIN_ROLE_NAME= "TICKET MANAGER";

}
