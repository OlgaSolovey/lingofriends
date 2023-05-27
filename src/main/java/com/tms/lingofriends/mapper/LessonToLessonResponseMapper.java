package com.tms.lingofriends.mapper;

import com.tms.lingofriends.model.Lesson;
import com.tms.lingofriends.model.response.LessonResponse;
import org.springframework.stereotype.Component;

@Component
public class LessonToLessonResponseMapper {
    public LessonResponse lessonToResponse(Lesson lesson) {
        LessonResponse lessonResponse = new LessonResponse();
        lessonResponse.setTitle(lesson.getTitle());
        lessonResponse.setDescription(lesson.getDescription());
        lessonResponse.setTheory(lesson.getTheory());
        return lessonResponse;
    }
}