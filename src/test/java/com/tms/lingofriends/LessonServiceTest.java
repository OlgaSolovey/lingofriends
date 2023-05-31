package com.tms.lingofriends;

import com.tms.lingofriends.mapper.LessonToLessonResponseMapper;
import com.tms.lingofriends.model.Lesson;
import com.tms.lingofriends.model.response.LessonResponse;
import com.tms.lingofriends.repository.LessonRepository;
import com.tms.lingofriends.security.Authorization;
import com.tms.lingofriends.service.LessonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindingResult;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LessonServiceTest {

    @InjectMocks
    private LessonService lessonService;
    @Mock
    private LessonRepository lessonRepository;
    @Mock
    private LessonToLessonResponseMapper lessonToLessonResponseMapper;
    @Mock
    private BindingResult bindingResult;
    @Mock
    private Authorization authorization;
    private int id;
    private Lesson lesson;
    private LessonResponse lessonResponse;
    private final List<Lesson> lessons = new ArrayList<>();
    private final List<LessonResponse> lessonResponses = new ArrayList<>();
    private final Timestamp time = new Timestamp(System.currentTimeMillis());


    @BeforeEach
    public void init() {
        id = 1;
        lesson = new Lesson();
        lesson.setId(1);
        lesson.setTitle("TitleTest");
        lesson.setDescription("DescriptionTest");
        lesson.setTheory("TheoryTest");
        lesson.setUserId(1L);
        lesson.setCourseId(1L);
        lesson.setLanguageName("LanguageNameTest");
        lesson.setCreated(time);
        lesson.setChanged(time);
        lesson.setDeleted(false);
        lesson.setUserLogin("UserLoginTest");
        lessons.add(lesson);
        lessonResponse = new LessonResponse();
        lessonResponses.add(lessonResponse);
    }

    @Test
    public void getAllCourseResponseTest() {
        when(lessonRepository.findAll()).thenReturn(lessons);
        when(lessonToLessonResponseMapper.lessonToResponse(lesson)).thenReturn(lessonResponse);
        assertEquals(lessonResponses, lessonService.getAllLessonResponse());
    }

    @Test
    public void getLessonResponseByIdTest() {
        when(lessonRepository.findById(id)).thenReturn(Optional.of(lesson));
        when(lessonToLessonResponseMapper.lessonToResponse(lesson)).thenReturn(lessonResponse);
        LessonResponse returned = lessonService.getLessonResponseById(id);
        verify(lessonRepository).findById(id);
        verify(lessonToLessonResponseMapper).lessonToResponse(lesson);
        assertEquals(lessonResponse, returned);
    }
    @Test
    public void findLessonResponseByUserIdTest() {
        when(lessonRepository.findLessonByUserId(Math.toIntExact(lesson.getUserId()))).thenReturn(lessons);
        when(lessonToLessonResponseMapper.lessonToResponse(lesson)).thenReturn(lessonResponse);
        assertEquals(lessonResponses, lessonService.findLessonResponseByUserId(Math.toIntExact(lesson.getUserId())));
    }
    @Test
    public void findLessonResponseByCourseIdTest() {
        when(lessonRepository.findLessonByCourseId(Math.toIntExact(lesson.getCourseId()))).thenReturn(lessons);
        when(lessonToLessonResponseMapper.lessonToResponse(lesson)).thenReturn(lessonResponse);
        assertEquals(lessonResponses, lessonService.findLessonResponseByCourseId(Math.toIntExact(lesson.getUserId())));
    }

    @Test
    public void createLessonTest() {
        lesson.setCreated(time);
        lesson.setChanged(time);
        lessonService.createLesson(lesson);
        verify(lessonRepository).save(lesson);
    }



    @Test
    public void updateLessonTest() {
        when(lessonRepository.findById(id)).thenReturn(Optional.of(lesson));
        when(authorization.authorization(lesson.getUserLogin())).thenReturn(true);
        lessonService.updateLesson(lesson);
        verify(lessonRepository).saveAndFlush(lesson);
    }

    @Test
    public void deleteLessonByIdTest() {
        when(lessonRepository.findById(id)).thenReturn(Optional.of(lesson));
        when(authorization.authorization(lesson.getUserLogin())).thenReturn(true);
        lessonService.deleteLessonById(id);
        verify(lessonRepository).deleteLessonById(id);
    }

}