package ru.hogwarts.school.repositories;

import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findByAge(int age);

    Collection<Student> findByAgeBetween(int minAge, int maxAge);

    Student findStudentById(Long id);

    Collection<Student> findAllByFaculty_Id(Long facultyId);

    @Query(value = "select count(*) from student",nativeQuery = true)
    Integer getCountStudent();
    @Query(value = "select avg(age) from student",nativeQuery = true)
    Double getAvaregeAgeAllStudent();
    @Query(value = "select * from student order by id desc limit 5",nativeQuery = true)
    List<Student> getLastFiveStudent();

}
