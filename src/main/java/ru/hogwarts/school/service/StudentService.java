package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.*;

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

    public Collection<Student> findStudentAge(int age) {
        return studentRepository.findByAge(age);
    }

    public Collection<Student> findByAgeBetween(int minAge, int maxAge) {
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    public Student findStudentById(Long id) {
        return studentRepository.findStudentById(id);
    }

    public Faculty getFacultyByStudent(long id) {
        return findStudent(id).getFaculty();
    }

    public Integer getCountStudent() {
        return studentRepository.getCountStudent();
    }

    public Double getAvaregeAgeAllStudent() {
        return studentRepository.getAvaregeAgeAllStudent();
    }

    public List<Student> getLastFiveStudent() {
        return studentRepository.getLastFiveStudent();
    }

}
