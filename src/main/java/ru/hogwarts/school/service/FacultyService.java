package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;

    Logger logger = LoggerFactory.getLogger(AvatarService.class);

    public FacultyService(FacultyRepository facultyRepository,StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }


    public Faculty createFaculty(Faculty faculty) {
        logger.debug("Method called:createFaculty");
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) {
        logger.debug("Method called:findFaculty");
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty editFaculty(Faculty faculty) {
        logger.debug("Method called:editFaculty");
        Faculty findFaculty = findFaculty(faculty.getId());
        if (findFaculty == null) {
            throw new FacultyNotFoundException("Такой факультет не найден");
        }
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        logger.debug("Method called:deleteFaculty");
        facultyRepository.deleteById(id);
    }


    public Collection<Faculty> getAllFaculties() {
        logger.debug("Method called:getAllFaculties");
        return facultyRepository.findAll();
    }


    public Collection<Faculty> findFacultyColor (String color) {
        logger.debug("Method called:findFacultyColor");
            return facultyRepository.findByColorIgnoreCase(color);
    }

    public Collection<Faculty> findFacultyByNameOrColor(String nameOrColor) {
        logger.debug("Method called:findFacultyByNameOrColor");
        return facultyRepository.findFacultyByNameIgnoreCaseOrColorIgnoreCase(nameOrColor, nameOrColor);
    }

    public Collection<Student> getStudentsByFacultyId (long id) {
        logger.debug("Method called:getStudentsByFacultyId");
        Faculty faculty = findFaculty(id);
        return studentRepository.findAllByFaculty_Id(faculty.getId());
    }

    public String getLongestFacultyName() {
        return getAllFaculties().stream()
                .map(s->s.getName())
                .max(Comparator.comparing(String::length))
                .orElse("");
    }
}
