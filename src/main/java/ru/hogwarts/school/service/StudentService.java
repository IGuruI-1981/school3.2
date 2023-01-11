package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.*;
import java.util.stream.Collectors;

import static javax.print.attribute.standard.MediaSizeName.A;


@Service
public class StudentService {

    private final StudentRepository studentRepository;

    Logger logger = LoggerFactory.getLogger(AvatarService.class);

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        logger.debug("Method called:createStudent");
        return studentRepository.save(student);
    }

    public Student findStudent(long id) {
        logger.debug("Method called:findStudent");
        return studentRepository.findById(id).orElse(null);
    }


    public Student editStudent(Student student) {
        logger.debug("Method called:editStudent");
        Student findStudent = findStudent(student.getId());
        if (findStudent == null) {
           throw new StudentNotFoundException("Такой студент не найден");
        }
      return studentRepository.save(student);
    }


    public void deleteStudent(long id) {
        logger.debug("Method called:deleteStudent");
        studentRepository.deleteById(id);
    }


    public List<String> getStudentsWithNameStartingWithA() {
        logger.debug("Method called: getStudentsWithNameStartingWithA");
        List<String> result = getAllStudent().stream()
                .filter(s -> s.getName().startsWith(String.valueOf(A)))
                .map(s -> s.getName().toUpperCase())
                .sorted()
                .collect(Collectors.toList());
        return result;
    }

    public Collection<Student> findStudentAge(int age) {
        logger.debug("Method called:findStudentAge");
        return studentRepository.findByAge(age);
    }

    public Collection<Student> findByAgeBetween(int minAge, int maxAge) {
        logger.debug("Method called:findByAgeBetween");
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    public Student findStudentById(Long id) {
        logger.debug("Method called:findStudentById");
        return studentRepository.findStudentById(id);
    }

    public Faculty getFacultyByStudent(long id) {
        logger.debug("Method called:getFacultyByStudent");
        return findStudent(id).getFaculty();
    }

    public Integer getCountStudent() {
        logger.debug("Method called:getCountStudent");
        return studentRepository.getCountStudent();
    }

    public Double getAvaregeAgeAllStudent() {
        logger.debug("Method called:getAvaregeAgeAllStudent");
        return studentRepository.getAvaregeAgeAllStudent();
    }

    public List<Student> getLastFiveStudent() {
        logger.debug("Method called:getLastFiveStudent");
        return studentRepository.getLastFiveStudent();
    }

    public OptionalDouble getAvaregeAgeAllStudentNew() {
        logger.debug("Method called:getAvaregeAgeAllStudentNew");
        return getAllStudent().stream()
                .mapToInt(s -> s.getAge())
                .average();
    }

    public List<Student> getAllStudent() {
        logger.debug("Method called:getAllStudent");
        return studentRepository.findAll();
    }
}
