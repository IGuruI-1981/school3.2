package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student findStudent(long id) {
        return studentRepository.findById(id).orElse(null);
    }


    public Student editStudent(Student student) {
        Student findStudent = findStudent(student.getId());
        if (findStudent == null) {
           throw new StudentNotFoundException("Такой студент не найден");
        }
      return studentRepository.save(student);
    }


    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }


    public Collection<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Collection<Student> findStudentAge(Integer age) {
        return studentRepository.findByAge(age);
    }

    public Collection<Student> findByAgeBetween(Integer minAge, Integer maxAge) {
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    public Student findStudentById(Long id) {
        return studentRepository.findStudentById(id);
    }

    public Collection<Student> findStudentByFaculty_Id(Long facultyId) {
        return studentRepository.findStudentByFaculty_Id(facultyId);
    }

}
