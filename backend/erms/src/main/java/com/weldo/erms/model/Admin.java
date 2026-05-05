package com.weldo.erms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_admin")
public class Admin {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)

    @Column(name="user_id")
    private int userId;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    public int getUserId() { 
        return userId; 
        }
    public String getUsername() { 
        return username; 
        }
    public String getPassword() { 
        return password; 
        }
    public String getFirstName() { 
        return firstName; 
        }
    public String getLastName() { 
        return lastName; 
        }
    public String getFullName() { 
        return firstName + " " + lastName; 
        }
}

