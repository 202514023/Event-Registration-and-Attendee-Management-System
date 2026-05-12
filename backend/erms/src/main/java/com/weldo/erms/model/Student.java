package com.weldo.erms.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "student_number")
    private String studentNumber;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "course")
    private String course;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    public int getId() { return id; }
    public String getStudentNumber() { return studentNumber; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getCourse() { return course; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getFullName() { return firstName + " " + lastName; }
}