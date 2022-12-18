package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;

    public FacultyService(FacultyRepository facultyRepository,StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }


    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) {
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty editFaculty(Faculty faculty) {
        Faculty findFaculty = findFaculty(faculty.getId());
        if (findFaculty == null) {
            throw new FacultyNotFoundException("Такой факультет не найден");
        }
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        facultyRepository.deleteById(id);
    }


    public Collection<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }


    public Collection<Faculty> findFacultyColor (String color) {
            return facultyRepository.findByColorIgnoreCase(color);
    }

    public Collection<Faculty> findFacultyByNameOrColor(String nameOrColor) {
        return facultyRepository.findFacultyByNameIgnoreCaseOrColorIgnoreCase(nameOrColor, nameOrColor);
    }

    public Collection<Student> getStudentsByFacultyId (long id) {
        Faculty faculty = findFaculty(id);
        return studentRepository.findAllByFaculty_Id(faculty.getId());
    }

}
