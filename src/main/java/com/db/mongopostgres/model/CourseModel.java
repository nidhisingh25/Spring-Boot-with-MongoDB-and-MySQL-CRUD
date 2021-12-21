package com.db.mongopostgres.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CourseModel {
    private String name;
    private String desc;
    private String email;
}
