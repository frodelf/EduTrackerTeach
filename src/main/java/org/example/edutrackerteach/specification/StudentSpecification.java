package org.example.edutrackerteach.specification;

import jakarta.persistence.criteria.*;
import org.example.edutrackerteach.dto.student.StudentRequestFilter;
import org.example.edutrackerteach.entity.Course;
import org.example.edutrackerteach.entity.Student;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class StudentSpecification implements Specification<Student> {
    private StudentRequestFilter studentRequestFilter;
    private List<Course> courses;

    public StudentSpecification(StudentRequestFilter studentRequestFilter) {
        this.studentRequestFilter = studentRequestFilter;
    }

    public StudentSpecification(StudentRequestFilter studentRequestFilter, List<Course> courseList) {
        this.studentRequestFilter = studentRequestFilter;
        this.courses = courseList;
    }

    @Override
    public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (!studentRequestFilter.getGroup().isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("groupName"), "%" + studentRequestFilter.getGroup() + "%"));
        }
        if (!studentRequestFilter.getFullName().isEmpty()) {
            predicates.add(
                    criteriaBuilder.like(
                            criteriaBuilder.concat(
                                    criteriaBuilder.concat(
                                            criteriaBuilder.concat(
                                                    criteriaBuilder.lower(root.get("lastName")),
                                                    " "
                                            ),
                                            criteriaBuilder.lower(root.get("name"))
                                    ),
                                    criteriaBuilder.concat(
                                            " ",
                                            criteriaBuilder.lower(root.get("middleName"))
                                    )
                            ),
                            "%" + studentRequestFilter.getFullName().toLowerCase() + "%"
                    )
            );
        }
        if (!studentRequestFilter.getTelegram().isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("telegram"), "%" + studentRequestFilter.getTelegram() + "%"));
        }
        if (!studentRequestFilter.getPhone().isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("phone"), "%" + studentRequestFilter.getPhone() + "%"));
        }
        if (studentRequestFilter.getCourse() != null) {
            Join<Student, Course> courseJoin = root.join("courses", JoinType.INNER);
            predicates.add(criteriaBuilder.equal(courseJoin.get("id"), studentRequestFilter.getCourse()));
        } else {
            Join<Student, Course> coursesJoin = root.join("courses");
            predicates.add(coursesJoin.in(courses));
        }
        query.distinct(true);
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
