package wejump.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import wejump.server.repository.LessonRepository;
import wejump.server.service.LessonService;

@Configuration
public class SpringConfig {

    private final LessonRepository lessonRepository;

    public SpringConfig(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @Bean
    public LessonService lessonService() {
        return new LessonService(lessonRepository);
    }
}
