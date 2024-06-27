package org.example.edutrackerteach.mapper;

import org.example.edutrackerteach.dto.student.StudentResponseViewAll;
import org.example.edutrackerteach.entity.Course;
import org.example.edutrackerteach.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.HashMap;
import java.util.stream.Collectors;

public class StudentMapper {
    public StudentResponseViewAll toDtoForViewAll(Student student) {
        StudentResponseViewAll studentResponseViewAll = new StudentResponseViewAll();
        if(student.getId() != null)studentResponseViewAll.setId(student.getId());
        if(student.getGroupName() != null)studentResponseViewAll.setGroup(student.getGroupName());
        if(student.getLastName() != null)studentResponseViewAll.setLastName(student.getLastName());
        if(student.getName() != null)studentResponseViewAll.setName(student.getName());
        if(student.getMiddleName() != null)studentResponseViewAll.setMiddleName(student.getMiddleName());
        if(student.getTelegram() != null)studentResponseViewAll.setTelegram(student.getTelegram());
        if(student.getTelegram() != null)studentResponseViewAll.setTelegram(student.getTelegram());
        studentResponseViewAll.setCourses(new HashMap<>());
        if(student.getCourses()!=null && !student.getCourses().isEmpty()){
            for (Course course : student.getCourses()) {
                studentResponseViewAll.getCourses().put(course.getId().toString(), course.getName());
            }
        }
        return studentResponseViewAll;
    }
    public Page<StudentResponseViewAll> toDtoListForViewAll(Page<Student> students) {
        return new PageImpl<>(students.getContent().stream()
                .map(this::toDtoForViewAll)
                .collect(Collectors.toList()), students.getPageable(), students.getTotalElements());
    }
}
