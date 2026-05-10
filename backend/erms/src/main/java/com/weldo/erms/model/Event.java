package com.weldo.erms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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

    public int getEventId() { 
        return eventId; 
        }
    public String getEventName() { 
        return eventName; 
        }
    public String getDescription() { 
        return description; 
        }
    public String getEventDate() { 
        return eventDate; 
        }
    public String getLocation() { 
        return location; 
        }
    public int getMaxSlots() { 
        return maxSlots; 
        }
    public int getCurrentSlots() { 
        return currentSlots; 
        }
    public int getCreatedBy() { 
        return createdBy; 
        }


        /// We add getters for the thing on the thing for the management thingy ... \
        ///feel ko di naman binabasa ni sir ung source code hehe~ may crush ako taga nursing uwu wala naman babasa neto
        public void setEventName(String eventName) {
             this.eventName = eventName; 
        }
        public void setDescription(String description) {
             this.description = description; 
        }
        public void setEventDate(String eventDate) {
             this.eventDate = eventDate; 
        }
        public void setLocation(String location) {
             this.location = location; 
        }
        public void setMaxSlots(int maxSlots) {
             this.maxSlots = maxSlots; 
        }
        public void setCurrentSlots(int currentSlots) {
             this.currentSlots = currentSlots; 
        }
        public void setCreatedBy(int createdBy) {
             this.createdBy = createdBy; 
        }
    }