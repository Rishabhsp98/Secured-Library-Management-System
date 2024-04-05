package com.example.SecuredLibrarySystem.Utils;

public class Constants {

    //Accessible by only students
    public static final String STUDENT_SELF_INFO_AUTHORITY = "STUDENT_SELF_INFO";
    public static final String MAKE_PAYMENT_AUTHORITY = "MAKE_PAYMENT";


    //Accessible by only admin
    public static final String STUDENT_INFO_AUTHORITY = "STUDENT_INFO";  // for get info of any student via id
    public static final String CREATE_ADMIN_AUTHORITY = "CREATE_ADMIN";
    public static final String CREATE_BOOK_AUTHORITY = "CREATE_BOOK";
    public static final String DELETE_BOOK_AUTHORITY = "DELETE_BOOK";
    public static final String UPDATE_BOOK_AUTHORITY = "UPDATE_BOOK";
    public static final String INITIATE_TRANSACTION_AUTHORITY = "INITIATE_TXN";



    //Accessible by students and admin both
    public static final String READ_BOOK_AUTHORITY = "READ_BOOK";



    //Users and Delimiter
    public static final String DELIMITER = "::";
    public static final String STUDENT_USER = "student";
    public static final String ADMIN_USER = "admin";


    //Redis Variables
    public static final String STUDENT_CACHE_KEY_PREFIX = "std::";
    public static final Integer STUDENT_CACHE_KEY_EXPIRY = 600;
}
