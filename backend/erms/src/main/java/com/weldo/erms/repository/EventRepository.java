package com.weldo.erms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.weldo.erms.model.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {  
    ///this <event, integer> thingy tells which table
    long countByEventDateGreaterThanEqual(String date); // basically these are the sql syntax na pinadali ng JPA
    /// count = Select count(*)
    ///By = where
    ///EventDate = event date column
    ///GreaterThanEqual = >=
    ///String date = condition
    ///this gives the value for upcoming events
}