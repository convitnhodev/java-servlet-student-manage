package controller;


import service.ICourseService;
import service.IStudentService;
import service.IStudentsCoursesService;
import service.impl.CourseService;
import service.impl.StudentService;
import service.impl.StudentsCoursesService;

import javax.servlet.http.HttpServlet;
import java.util.logging.Logger;


public class BaseController extends HttpServlet {
    protected IStudentService studentService = new StudentService();
    protected ICourseService courseService = new CourseService();
    protected IStudentsCoursesService studentsCoursesService = new StudentsCoursesService();
    protected Logger logger;

    public Logger getLogger() {
        if (logger == null) {
            logger = Logger.getLogger(this.getClass().getName());
        }
        return logger;
    }
}
