package com.codeurs.students.repository;

import com.codeurs.students.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Philémon Globléhi <philemon.globlehi@gmail.com>
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
