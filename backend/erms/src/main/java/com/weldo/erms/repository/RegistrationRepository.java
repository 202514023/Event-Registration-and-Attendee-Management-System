package com.weldo.erms.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.weldo.erms.model.Registration;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Integer> {
    long countByAttendanceStatus(String status);
    List<Registration> findByEventId(int eventId);
    boolean existsByEventIdAndEmail(int eventId, String email);
    Optional<Registration> findByEventIdAndEmail(int eventId, String email);
}