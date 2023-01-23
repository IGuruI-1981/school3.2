package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.Stream;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {

    @Value("${students.avatar.dir.path}")
    private String avatarsDir;


    private final StudentService studentService;
    private final AvatarRepository avatarRepository;

    Logger logger = LoggerFactory.getLogger(AvatarService.class);

    public AvatarService(StudentService studentService, AvatarRepository avatarRepository) {
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
    }

    public void uploadAvatar(Long studentId, MultipartFile file) throws IOException {
        logger.debug("Method called:uploadAvatar");
        Student student = studentService.findStudent(studentId);

        Path filePath = Path.of(avatarsDir, student + "." + getExtensions(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try(InputStream is = file.getInputStream();
            OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
            BufferedInputStream bis = new BufferedInputStream(is, 1024);
            BufferedOutputStream bos = new BufferedOutputStream(os,1024)
        ){
            bis.transferTo(bos);
        }

        Avatar avatar = findAvatar(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(file.getBytes());

        avatarRepository.save(avatar);
    }
    private String getExtensions(String fileName) {
        logger.debug("Method called:getExtensions");
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public Avatar findAvatar(Long studentId) {
        logger.debug("Method called:findAvatar");
        return avatarRepository.findByStudentId(studentId).orElse(new Avatar());
    }

    public List<Avatar> getAllAvatars(Integer pageSize, Integer pageNumber) {
        logger.debug("Method called:getAllAvatars");
        PageRequest pageRequest = PageRequest.of(pageNumber-1,pageSize);
        return avatarRepository.findAll(pageRequest).getContent();
    }

    public String getIntegerValue() throws InterruptedException {
        logger.debug("Method called:getIntegerValue");
        Instant start = Instant.now();
       int sum = Stream.iterate(1, a -> a +1)
               .limit(1_000_000_0)
               .reduce(0, (a, b) -> a + b );
        Thread.sleep(1000);
        Instant finish = Instant.now();
        long elapsed = Duration.between(start, finish).toMillis();

        Instant startParallel = Instant.now();
        int sumParallel = Stream.iterate(1, a -> a +1)
                .limit(1_000_000_0)
                .parallel()
                .reduce(0, (a, b) -> a + b );
        Thread.sleep(1000);
        Instant finishParallel = Instant.now();
        long elapsedParallel = Duration.between(startParallel, finishParallel).toMillis();
        String result = "Результат выполнения SUM = " + sum + ", Время выполнения - " + elapsed + "мс.  " +
                        "Результат выполнения SUM_Parllel= " + sumParallel + ", Время выполнения - " + elapsedParallel + "мс.";

       return result;
    }


}
