package org.example.edutrackerteach.mapper;

import org.example.edutrackerteach.dto.student.StudentResponseViewAll;
import org.example.edutrackerteach.dto.student.StudentResponseViewOnePage;
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
        if(student.getPhone() != null)studentResponseViewAll.setPhone(student.getPhone());
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

    public StudentResponseViewOnePage toDtoForViewOnePage(Student student) {
        StudentResponseViewOnePage studentResponseViewOnePage = new StudentResponseViewOnePage();
        if(student.getId() != null)studentResponseViewOnePage.setId(student.getId());
        if(student.getLastName() != null)studentResponseViewOnePage.setLastName(student.getLastName());
        if(student.getName() != null)studentResponseViewOnePage.setName(student.getName());
        if(student.getMiddleName() != null)studentResponseViewOnePage.setMiddleName(student.getMiddleName());
        if(student.getPhone() != null)studentResponseViewOnePage.setPhone(student.getPhone());
        if(student.getEmail() != null)studentResponseViewOnePage.setEmail(student.getEmail());
        if(student.getTelegram() != null)studentResponseViewOnePage.setTelegram(student.getTelegram());
        if(student.getGroupName() != null)studentResponseViewOnePage.setGroupName(student.getGroupName());
        return studentResponseViewOnePage;
    }
}
