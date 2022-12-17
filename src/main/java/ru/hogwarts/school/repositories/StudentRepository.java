package ru.hogwarts.school.repositories;

import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findByAge(Integer age);

    Collection<Student> findByAgeBetween(Integer minAge, Integer maxAge);

    Student findStudentById(Long id);

    Collection<Student> findStudentByFaculty_Id(Long facultyId);


}
