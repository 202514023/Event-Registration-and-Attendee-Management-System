package com.weldo.erms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.weldo.erms.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    Student findByStudentNumberAndPassword(String studentNumber, String password);
}