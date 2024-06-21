package org.example.edutrackerteach.mapper;

import lombok.SneakyThrows;
import org.example.edutrackerteach.dto.course.CourseResponseViewAll;
import org.example.edutrackerteach.entity.Course;
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
}