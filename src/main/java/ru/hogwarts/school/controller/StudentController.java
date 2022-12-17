package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;


    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudentInfo(@PathVariable Long id) {
        Student findStudent = studentService.findStudentById(id);
        if (findStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentService.findStudentById(id));
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> getAllStudents(@RequestParam(required = false) Integer minAge,
                                                              @RequestParam(required = false) Integer maxAge,
                                                              @RequestParam(required = false) Long facultyId,
                                                              @RequestParam (required = false) Integer age) {
        if (minAge !=null && maxAge !=null ) {
            return ResponseEntity.ok(studentService.findByAgeBetween(minAge, maxAge));
        }
        if (facultyId != null) {
            return ResponseEntity.ok(studentService.findStudentByFaculty_Id(facultyId));
        }
        if (age != null) {
            return ResponseEntity.ok(studentService.findStudentAge(age));
        }
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
            return ResponseEntity.ok(studentService.editStudent(student));
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

}
