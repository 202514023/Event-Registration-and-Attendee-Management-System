package com.weldo.erms.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private int eventId;

    @Column(name = "event_name")
    private String eventName;

    @Column(name = "description")
    private String description;

    @Column(name = "event_date")
    private String eventDate;

    @Column(name = "location")
    private String location;

    @Column(name = "max_slots")
    private int maxSlots;

    @Column(name = "current_slots")
    private int currentSlots;

    @Column(name = "created_by")
    private int createdBy;

    public int getEventId() { return eventId; }
    public String getEventName() { return eventName; }
    public String getDescription() { return description; }
    public String getEventDate() { return eventDate; }
    public String getLocation() { return location; }
    public int getMaxSlots() { return maxSlots; }
    public int getCurrentSlots() { return currentSlots; }
    public int getCreatedBy() { return createdBy; }
}