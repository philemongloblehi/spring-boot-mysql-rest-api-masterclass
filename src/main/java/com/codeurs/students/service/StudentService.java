package com.codeurs.students.service;

import com.codeurs.students.exceptions.StudentNotFoundException;
import com.codeurs.students.model.Student;
import com.codeurs.students.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Philémon Globléhi <philemon.globlehi@gmail.com>
 */
@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return this.studentRepository.findAll();
    }

    public Optional<Student> getOneStudent(Long id) {
        Optional<Student> student = this.studentRepository.findById(id);
        if (!student.isPresent()) {
            throw new StudentNotFoundException(String.format("Student with id %s not found!", id));
        }

        return student;
    }

    public Student saveStudent(Student student) {
        return this.studentRepository.save(student);
    }

    public Student updateStudent(Student student, Long id) {
        Optional<Student> studentExist = this.studentRepository.findById(id);
        if (!studentExist.isPresent()) {
            throw new StudentNotFoundException(String.format("Student with id %s not found!", id));
        }

        return this.studentRepository.save(student);
    }

    public void removeStudent(Long id) {
        Optional<Student> student = this.studentRepository.findById(id);
        if (!student.isPresent()) {
            throw new StudentNotFoundException(String.format("Student with id %s not found!", id));
        }

        this.studentRepository.delete(student.get());
    }
}
