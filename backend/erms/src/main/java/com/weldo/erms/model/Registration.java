package com.weldo.erms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_registration")
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "registration_id")
    private int registrationId;

    @Column(name = "event_id")
    private int eventId;

    @Column(name = "attendee_firstName")
    private String firstName;

    @Column(name = "attendee_lastName")
    private String lastName;

    @Column(name = "attendee_email")
    private String email;

    @Column(name = "attendee_contactNumber")
    private String contactNumber;

    @Column(name = "registration_date")
    private String registrationDate;

    @Column(name = "attendance_status")
    private String attendanceStatus;

    public int getRegistrationId() { 
        return registrationId; 
    }
    public int getEventId() { 
        return eventId; }
    public String getFirstName() { 
        return firstName; 
    }
    public String getLastName() { 
        return lastName; 
    }
    public String getFullName() { 
        return firstName + " " + lastName; 
    }
    public String getEmail() { 
        return email; 
    }
    public String getContactNumber() { 
        return contactNumber; 
    }
    public String getRegistrationDate() { 
        return registrationDate; 
    }
    public String getAttendanceStatus() { 
        return attendanceStatus; 
    }
}