package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

    @RestController
    @RequestMapping("/faculty")
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
        public ResponseEntity<Collection<Faculty>> getAllFacultyInfo() {
            return ResponseEntity.ok(facultyService.getAllFaculties());
        }

        @PostMapping
        public Faculty createFaculty(@RequestBody Faculty faculty) {
            return facultyService.createFaculty(faculty);
        }

        @PutMapping
        public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
            Faculty findFaculty = facultyService.editFaculty(faculty);
            if (findFaculty == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(findFaculty);
        }

        @DeleteMapping("{id}")
        public Faculty deleteFaculty(@PathVariable Long id) {
            return facultyService.deleteFaculty(id);
        }

        @GetMapping(path = "color")
        public ResponseEntity<Collection<Faculty>> getFacultyColor(@RequestParam String color) {
            return ResponseEntity.ok(facultyService.findFacultyColor(color));
        }

    }

