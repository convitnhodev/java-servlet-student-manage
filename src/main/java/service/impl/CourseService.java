package service.impl;

import dao.ICourseDao;
import dao.impl.CourseDao;
import entity.Course;
import model.wrapper.ListWrapper;
import model.search.ParameterSearchCourse;
import service.ICourseService;

import java.sql.SQLException;


public class CourseService implements ICourseService {
    private ICourseDao courseDao = new CourseDao();

    @Override
    public Course createCourse(Course course) throws SQLException, ClassNotFoundException {
        return courseDao.createCourse(course);
    }

    @Override
    public Course getById(Integer id) throws SQLException, ClassNotFoundException {
        return courseDao.getById(id);
    }

    @Override
    public Course updateCourseById(Integer id, Course course) throws SQLException, ClassNotFoundException {
        return courseDao.updateCourseById(id, course);
    }

    @Override
    public void deleteCourseById(Integer id) throws SQLException, ClassNotFoundException {
        courseDao.deleteCourseById(id);
    }

    @Override
    public ListWrapper<Course> searchCourses(ParameterSearchCourse parameterSearchCourse) throws SQLException, ClassNotFoundException {
        return courseDao.searchCourses(parameterSearchCourse);
    }
}
