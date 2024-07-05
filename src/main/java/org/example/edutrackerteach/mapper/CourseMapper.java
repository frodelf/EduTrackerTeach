package org.example.edutrackerteach.mapper;

import lombok.SneakyThrows;
import org.example.edutrackerteach.dto.course.CourseDtoForAdd;
import org.example.edutrackerteach.dto.course.CourseResponseViewAll;
import org.example.edutrackerteach.entity.Course;
import org.example.edutrackerteach.service.CourseService;
import org.example.edutrackerteach.service.MinioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.stream.Collectors;

public class CourseMapper {
    @SneakyThrows
    public CourseResponseViewAll toDtoForView(Course course, MinioService minioService) {
        CourseResponseViewAll courseResponseViewAll = new CourseResponseViewAll();
        courseResponseViewAll.setId(course.getId());
        courseResponseViewAll.setName(course.getName());
        courseResponseViewAll.setGoal(course.getGoal());
        if(course.getStudents()!=null)
            courseResponseViewAll.setNumberOfStudents(course.getStudents().size());
        if(course.getImage()!=null)
            courseResponseViewAll.setImage(minioService.getUrl(course.getImage()));
        return courseResponseViewAll;
    }
    public Page<CourseResponseViewAll> toDtoListForView(Page<Course> courses, MinioService minioService) {
        return new PageImpl<>(
                courses.getContent().stream()
                        .map(course -> toDtoForView(course, minioService))
                        .collect(Collectors.toList()),
                courses.getPageable(),
                courses.getTotalElements()
        );
    }
    public Course toEntityForAdd(CourseDtoForAdd requestAdd, CourseService courseService) {
        Course course = new Course();
        if(requestAdd.getId() != null) course = courseService.getById(requestAdd.getId());
        if(requestAdd.getName() != null) course.setName(requestAdd.getName());
        if(requestAdd.getMaximumMark() != null) course.setMaximumMark(requestAdd.getMaximumMark());
        if(requestAdd.getGoal() != null) course.setGoal(requestAdd.getGoal());
        return course;
    }
    public CourseDtoForAdd toDtoForAdd(Course course) {
        CourseDtoForAdd courseDtoForAdd = new CourseDtoForAdd();
        courseDtoForAdd.setId(course.getId());
        courseDtoForAdd.setName(course.getName());
        courseDtoForAdd.setMaximumMark(course.getMaximumMark());
        courseDtoForAdd.setGoal(course.getGoal());
        return courseDtoForAdd;
    }
}