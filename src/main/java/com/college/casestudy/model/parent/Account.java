package com.college.casestudy.model.parent;

public class Account {

    private final Object[] accountInfo; // Store the account info here

    /**
     * We'll be using two constructors to
     * utilize polymorphism, since we will
     * be having two types of account:
     * Teacher and Student
     *
     *
     * And for the [boolean student] parameter,
     * we'll be using that to check whether
     * the account is a type of Student or
     * not
     */

    public Account( boolean student, int studentCode, String username,
                    String password, String firstName, String lastName,
                    int yearLevel, String program, String campus ) {
        // Place [studentCode] at index 2 so that
        // we can check the access code efficiently
        // when logging in
        accountInfo = new Object[] { student, studentCode, username,
                                    password, firstName, lastName,
                                    yearLevel, program, campus };
    }

    public Account( boolean student, int teacherCode, String username,
                    String password, String firstName, String lastName,
                    String college, String campus ) {
        // Place [teacherCode] at index 2 so that
        // we can check the access code efficiently
        // when logging in
        accountInfo = new Object[] { student, teacherCode, username,
                                    password, firstName, lastName,
                                    college, campus };
    }

    public Object[] getAccountInfo() {
        return accountInfo;
    }

    public String getFirstName() {
        return accountInfo[4].toString();
    }

    public String getLastName() {
        return accountInfo[5].toString();
    }
}
