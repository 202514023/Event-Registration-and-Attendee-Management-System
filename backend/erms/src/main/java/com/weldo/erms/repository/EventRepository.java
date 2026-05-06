package com.weldo.erms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.weldo.erms.model.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    long countByEventDateGreaterThanEqual(String date);
}