package com.college.casestudy.model;

import com.college.casestudy.model.parent.Account;

public class Student extends Account {

    public Student( int studentCode, String username,
                    String password, String firstName,
                    String lastName, int yearLevel,
                    String program, String campus ) {
        // Use the constructor from the
        // parent class
        super( true, studentCode, username, password, firstName, lastName, yearLevel, program, campus );
    }
}
