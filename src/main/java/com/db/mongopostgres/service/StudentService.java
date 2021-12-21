package com.db.mongopostgres.service;

import com.db.mongopostgres.document.Course;
import com.db.mongopostgres.entity.Student;
import com.db.mongopostgres.model.CourseModel;
import com.db.mongopostgres.model.StudentModel;
import com.db.mongopostgres.repository.CourseRepository;
import com.db.mongopostgres.repository.StudentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class StudentService {
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    StudentRepository studentRepository;

    @Transactional
    public String createResource(StudentModel studentModel)
    {
        if(!studentRepository.existsByEmail(studentModel.getEmail()))
        {
            Student student=new Student();
            BeanUtils.copyProperties(studentModel,student);
            try{
                studentRepository.save(student);
                studentModel.getCourses().stream().forEach(
                        c->{
                            Course course=new Course();
                            c.setEmail(studentModel.getEmail());
                            BeanUtils.copyProperties(c,course);
                            try{
                                courseRepository.save(course);
                            }catch(Exception e)
                            {
                                throw e;
                            }

                        }
                );
            }catch (Exception e)
            {
                throw e;
            }
            return "Resource added succesfully";
        }else
            return "Duplicate records";
    }

    @Transactional
    public List<StudentModel> readResource()
    {
        List<StudentModel> students=new ArrayList<>();
        List<Student> studentList=new ArrayList<>();
        try{
            studentList=studentRepository.findAll();
        }catch(Exception e)
        {
            throw e;
        }

        if(studentList.size()>0)
        {
            studentList.stream().forEach(s->
            {
                StudentModel studentModel=new StudentModel();
                BeanUtils.copyProperties(s,studentModel);
                List<CourseModel> courses=new ArrayList<>();
                List<Course> courseList=new ArrayList<>();
                try{
                    courseList=courseRepository.findCourseByEmail(studentModel.getEmail());
                }catch(Exception e)
                {
                    throw e;
                }
                if(courseList.size()>0)
                {
                    courseList.stream().forEach(c->{
                        CourseModel courseModel=new CourseModel();
                        BeanUtils.copyProperties(c,courseModel);
                        courses.add(courseModel);
                    });
                }

            studentModel.setCourses(courses);
                students.add(studentModel);
            });

        }
        return students;
    }

    @Transactional
    public String updateResource(StudentModel studentModel)
    {
       if(studentRepository.existsByEmail(studentModel.getEmail()))
       {
           Student student=studentRepository.findByEmail(studentModel.getEmail()).get(0);
           BeanUtils.copyProperties(studentModel,student);
           try{
               studentRepository.save(student);//updateRepository
               List<Course> course= courseRepository.findCourseByEmail(studentModel.getEmail());
               for(int i=0;i<studentModel.getCourses().size();i++)
               {
                     BeanUtils.copyProperties(studentModel.getCourses().get(i),course.get(i));
               }

               course.stream().forEach(c->{
                   Course course1=courseRepository.findById(c.getId()).get();
                   BeanUtils.copyProperties(c,course1);
                   course1.setEmail(studentModel.getEmail());
                   courseRepository.save(course1);
               });
           }catch (Exception e)
           {
               throw e;
           }


           return "Resource updated successfully";
       }else
       {
           return "Resource does not exist";
       }
    }

    @Transactional
    public String deleteResource(StudentModel studentModel)
    {
        if(studentRepository.existsByEmail(studentModel.getEmail()))
        {
            studentRepository.deleteByEmail(studentModel.getEmail());
            courseRepository.deleteByEmail(studentModel.getEmail());
            return "Resource deleted successfully";
        }else
        {
            return "Resource does not exist";
        }
    }
}
