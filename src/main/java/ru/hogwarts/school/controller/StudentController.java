package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;
import java.util.OptionalDouble;

@RestController
@RequestMapping("/student")
@Profile("!test")
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
        return ResponseEntity.ok(findStudent);
    }

    @GetMapping("{id}/faculty")
    public Faculty getFacultyByStudent(@PathVariable Long id) {
        return studentService.getFacultyByStudent(id);
    }

    @GetMapping(params = {"minAge","maxAge"})
    public ResponseEntity<Collection<Student>> getStudentsAgeBetween(@RequestParam(required = false) int minAge,
                                                                     @RequestParam(required = false) int maxAge) {
            return ResponseEntity.ok(studentService.findByAgeBetween(minAge, maxAge));
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> getStudentsAge(@RequestParam(required = false) int age) {
        return ResponseEntity.ok(studentService.findStudentAge(age));
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

    @GetMapping("/starting-name-with-a")
    public List<String> getStudentsWithNameStartingWithA() {
        return studentService.getStudentsWithNameStartingWithA();
    }

    @GetMapping("/student-count")
    public Integer getCountStudent() {
        return studentService.getCountStudent();
    }

    @GetMapping("/avg-age")
    public Double getAvaregeAgeAllStudent() {
        return studentService.getAvaregeAgeAllStudent();
    }

    @GetMapping("/avg-age2")
    public OptionalDouble getAvaregeAgeAllStudentNew() {
        return studentService.getAvaregeAgeAllStudentNew();
    }

    @GetMapping("/last-five-student")
    public List<Student> getLastFiveStudent() {
        return studentService.getLastFiveStudent();
    }

    @GetMapping("/all-name-student-stream")
    public void getAllNameStudentStream() {
        studentService.getAllNameStudentStream();
    }

    @GetMapping("/all-name-student-stream-synchronized")
    public void getAllNameStudentSynchronized() {
        studentService.getAllNameStudentSynchronized();
    }



}




