package ru.hogwarts.school;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
public class StudentControllerTests {


	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private FacultyRepository facultyRepository;

	private final Faker faker = new Faker();

	@AfterEach
	public void afterEach() {
		studentRepository.deleteAll();
		facultyRepository.deleteAll();
	}

	@Test
	public void createTest() {
		createStudent(generateStudent(createFaculty(generateFaculty())));
	}

	private Faculty createFaculty(Faculty faculty) {
		ResponseEntity<Faculty> facultyResponseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/faculty", faculty, Faculty.class);
		assertThat(facultyResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(facultyResponseEntity.getBody()).isNotNull();
		assertThat(facultyResponseEntity.getBody()).usingRecursiveComparison().ignoringFields("id").isEqualTo(faculty);
		assertThat(facultyResponseEntity.getBody().getId()).isNotNull();

		return facultyResponseEntity.getBody();
	}

	private Student createStudent(Student student) {
		ResponseEntity<Student> studentResponseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/student", student, Student.class);
		assertThat(studentResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(studentResponseEntity.getBody()).isNotNull();
		assertThat(studentResponseEntity.getBody()).usingRecursiveComparison().ignoringFields("id").isEqualTo(student);
		assertThat(studentResponseEntity.getBody().getId()).isNotNull();

		return studentResponseEntity.getBody();
	}

	@Test
	public void putTest() {
		Faculty faculty1 = createFaculty(generateFaculty());
		Faculty faculty2 = createFaculty(generateFaculty());
		Student student = createStudent(generateStudent(faculty1));

		ResponseEntity<Student> getForEntityResponse = testRestTemplate.getForEntity("http://localhost:" + port + "/student/" + student.getId(),Student.class);
		assertThat(getForEntityResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(getForEntityResponse.getBody()).isNotNull();
		assertThat(getForEntityResponse.getBody()).usingRecursiveComparison().isEqualTo(student);
		assertThat(getForEntityResponse.getBody().getFaculty()).usingRecursiveComparison().isEqualTo(faculty1);

		student.setFaculty(faculty2);

		ResponseEntity<Student> recordForEntityResponse = testRestTemplate.exchange("http://localhost:" + port + "/student/" + student.getId(), HttpMethod.PUT, new HttpEntity<>(student),Student.class);
		assertThat(getForEntityResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(recordForEntityResponse.getBody()).isNotNull();
		assertThat(recordForEntityResponse.getBody()).usingRecursiveComparison().isEqualTo(student);
		assertThat(recordForEntityResponse.getBody().getFaculty()).usingRecursiveComparison().isEqualTo(faculty2);

	}


	@Test
	public void findByAgeBetweenTest() {
		List<Faculty> faculties = Stream.generate(this::generateFaculty)
				.limit(5)
				.map(this::createFaculty)
				.toList();
		List<Student> students = Stream.generate(() -> generateStudent(faculties.get(faker.random().nextInt(faculties.size()))))
				.limit(15)
				.map(this::createStudent)
				.toList();

		int minAge = 12;
		int maxAge = 45;

		List<Student> expectedStudents = students.stream()
				.filter(studentRecord -> studentRecord.getAge() >=minAge && studentRecord.getAge() <= maxAge )
				.toList();

		ResponseEntity<List<Student>> getForEntityResponse = testRestTemplate.exchange("http://localhost:" + port + "/student?minAge={minAge}&maxAge={maxAge}", HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<List<Student>>() {
			@Override
			public boolean equals(Object other) {
				return super.equals(other);
			}
		}, minAge, maxAge);
		assertThat(getForEntityResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(getForEntityResponse.getBody()).hasSize(expectedStudents.size()).usingRecursiveFieldByFieldElementComparator().containsExactlyInAnyOrderElementsOf(students);


	}

	private Student generateStudent(Faculty faculty) {
		Student student = new Student();
		student.setName(faker.harryPotter().character());
		student.setAge(faker.random().nextInt(10,20));
		if (faculty != null) {
			student.setFaculty(faculty);
		}

		return student;
	}

	private Faculty generateFaculty() {
		Faculty faculty = new Faculty();
		faculty.setName(faker.harryPotter().house());
		faculty.setColor(faker.color().name());
		return faculty;
	}

}
