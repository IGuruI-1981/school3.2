package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
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
        public ResponseEntity getAllFacultyInfo(@RequestParam(required = false) String name,
                                                @RequestParam(required = false) String color) {
            if (name != null && !name.isBlank()) {
                return ResponseEntity.ok(facultyService.findFacultyByNameOrColor(name,color));
            }
            if (color != null && !color.isBlank()) {
                return ResponseEntity.ok(facultyService.findFacultyByNameOrColor(name, color));
            }
            return ResponseEntity.ok(facultyService.getAllFaculties());
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

    }

