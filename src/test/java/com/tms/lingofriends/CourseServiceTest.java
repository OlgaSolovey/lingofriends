package com.tms.lingofriends;

import com.tms.lingofriends.mapper.CourseToCourseResponseMapper;
import com.tms.lingofriends.model.Course;
import com.tms.lingofriends.model.response.CourseResponse;
import com.tms.lingofriends.repository.CourseRepository;
import com.tms.lingofriends.security.Authorization;
import com.tms.lingofriends.service.CourseService;
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
public class CourseServiceTest {

    @InjectMocks
    private CourseService courseService;
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private CourseToCourseResponseMapper courseToCourseResponseMapper;
    @Mock
    private Authorization authorization;

    private int id;
    private Course course;
    private com.tms.lingofriends.model.response.CourseResponse courseResponse;
    private final List<Course> courses = new ArrayList<>();
    private final List<CourseResponse> courseResponses = new ArrayList<>();
    private final Timestamp time = new Timestamp(System.currentTimeMillis());

    @BeforeEach
    public void init() {
        id = 1;
        course = new Course();
        course.setId(1);
        course.setUserId(1);
        course.setTitle("TitleTest");
        course.setLanguageName("LanguageNameTest");
        course.setDescription("DescriptionTest");
        course.setCreated(time);
        course.setChanged(time);
        course.setDeleted(false);
        course.setUserLogin("UserLoginTest");
        courses.add(course);
        courseResponse = new CourseResponse();
        courseResponses.add(courseResponse);
    }

    @Test
    public void getAllCourseResponseTest() {
        when(courseRepository.findAll()).thenReturn(courses);
        when(courseToCourseResponseMapper.courseToResponse(course)).thenReturn(courseResponse);
        assertEquals(courseResponses, courseService.getAllCoursesResponse());
    }

    @Test
    public void getCourseResponseByIdTest() {
        when(courseRepository.findById(id)).thenReturn(Optional.of(course));
        when(courseToCourseResponseMapper.courseToResponse(course)).thenReturn(courseResponse);
        CourseResponse returned = courseService.getCourseResponseById(id);
        verify(courseRepository).findById(id);
        verify(courseToCourseResponseMapper).courseToResponse(course);
        assertEquals(courseResponse, returned);
    }

    @Test
    public void findCourseResponseByLanguageNameTest() {
        when(courseRepository.findCourseByLanguageName(course.getLanguageName())).thenReturn(courses);
        when(courseToCourseResponseMapper.courseToResponse(course)).thenReturn(courseResponse);
        assertEquals(courseResponses, courseService.findCourseResponseByLanguageName(course.getLanguageName()));
    }
    @Test
    public void findCourseResponseByUserIdTest() {
        when(courseRepository.findCourseByUserId(course.getUserId())).thenReturn(courses);
        when(courseToCourseResponseMapper.courseToResponse(course)).thenReturn(courseResponse);
        assertEquals(courseResponses, courseService.findCourseResponseByUserId(course.getUserId()));
    }

    @Test
    public void createCourseTest() {
        course.setCreated(time);
        course.setChanged(time);
        courseService.createCourse(course);
        verify(courseRepository).save(course);
    }



    @Test
    public void updateCourseTest() {
        when(courseRepository.findById(id)).thenReturn(Optional.of(course));
        when(authorization.authorization(course.getUserLogin())).thenReturn(true);
        courseService.updateCourse(course);
        verify(courseRepository).saveAndFlush(course);
    }

    @Test
    public void deleteCourseByIdTest() {
        when(courseRepository.findById(id)).thenReturn(Optional.of(course));
        when(authorization.authorization(course.getUserLogin())).thenReturn(true);
        courseService.deleteCourseById(id);
        verify(courseRepository).deleteCourseById(id);
    }
}