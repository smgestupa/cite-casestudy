package com.college.casestudy.model;

import com.college.casestudy.model.parent.Account;

public class Teacher extends Account {

    public Teacher( int teacherCode, String username,
                    String password, String firstName,
                    String lastName, String college,
                    String campus ) {
        // Use the constructor from the
        // parent class
        super( false, teacherCode, username, password, firstName, lastName, college, campus );
    }
}
