package ru.hogwarts.school.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final HashMap<Long, Student> students = new HashMap<>();

    private long lastId = 0;

    public Student createStudent(Student student) {
        student.setId(++lastId);
        students.put(lastId, student);
        return student;
    }

    public Student findStudent(long id) {
        return students.get(id);
    }


    public Student editStudent(Student student) {
        if (students.containsKey(student.getId())) {
            students.put(student.getId(), student);
            return student;
        }
        return null;
    }


    public Student deleteStudent(long id) {
        return students.remove(id);
    }


    public Collection<Student> getAllStudents() {
        return students.values();
    }

    public Collection<Student> findStudentAge(int age) {
        return getAllStudents().stream()
                .filter(a -> a.getAge() == age)
                .collect(Collectors.toList());
    }

}
