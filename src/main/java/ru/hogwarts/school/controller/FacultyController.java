package ru.hogwarts.school.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

    @RestController
    @RequestMapping("/faculty")
    @Profile("!test")
    public class FacultyController {

        private final FacultyService facultyService;

        public FacultyController(FacultyService facultyService) {
            this.facultyService = facultyService;
        }


        @GetMapping("{id}")
        public ResponseEntity<Faculty> getFacultyInfo(@PathVariable Long id) {
            Faculty faculty = facultyService.findFaculty(id);
            if (faculty == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(faculty);
        }

        @GetMapping
        public ResponseEntity getAllFacultyInfo(@RequestParam(required = false) String nameOrColor) {
                if (nameOrColor != null && !nameOrColor.isBlank()) {
                return ResponseEntity.ok(facultyService.findFacultyByNameOrColor(nameOrColor));
            }
            return ResponseEntity.ok(facultyService.getAllFaculties());
        }

        @GetMapping(path = "color")
        public ResponseEntity<Collection<Faculty>> getFacultyColor(@RequestParam String color) {
            return ResponseEntity.ok(facultyService.findFacultyColor(color));
        }

        @PostMapping
        public Faculty createFaculty(@RequestBody Faculty faculty) {
            return facultyService.createFaculty(faculty);
        }

        @PutMapping
        public Faculty editFaculty(@RequestBody Faculty faculty) {
             return facultyService.editFaculty(faculty);
        }

        @DeleteMapping("{id}")
        public ResponseEntity deleteFaculty(@PathVariable Long id) {
            facultyService.deleteFaculty(id);
            return ResponseEntity.ok().build();
        }

        @GetMapping("{id}/student")
        public  Collection<Student> getStudentsByFacultyId(@PathVariable Long id) {
             return facultyService.getStudentsByFacultyId(id);
        }

    }

