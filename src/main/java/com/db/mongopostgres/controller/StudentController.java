package com.db.mongopostgres.controller;

import com.db.mongopostgres.model.StudentModel;
import com.db.mongopostgres.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {
    @Autowired
    StudentService service;

    @RequestMapping(value = "info", method = RequestMethod.GET)
    public String info(){
        return " The application is up";
    }

    @RequestMapping(value = "create",method = RequestMethod.POST)
    public String createStudent(@RequestBody StudentModel studentModel)
    {
        return service.createResource(studentModel);
    }

    @RequestMapping(value = "read",method = RequestMethod.GET)
    public List<StudentModel> read()
    {
        return service.readResource();
    }


    @RequestMapping(value = "update",method = RequestMethod.PUT)
    public String update(@RequestBody StudentModel studentModel)
    {
        return service.updateResource(studentModel);
    }

    @RequestMapping(value = "remove",method = RequestMethod.DELETE)
    public String deleteresouce(@RequestBody StudentModel studentModel)
    {
        return service.deleteResource(studentModel);
    }
}
