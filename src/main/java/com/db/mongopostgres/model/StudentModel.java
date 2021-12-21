package com.db.mongopostgres.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class StudentModel {
    private String firstName;
    private String lastName;
    private String email;
    private List<CourseModel> courses;
}
