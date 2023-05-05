package service;

import entity.Course;
import model.wrapper.ListWrapper;
import model.search.ParameterSearchCourse;

import java.sql.SQLException;


public interface ICourseService {
    Course createCourse(Course course) throws SQLException, ClassNotFoundException;
    Course getById(Integer id) throws SQLException, ClassNotFoundException;
    Course updateCourseById(Integer id, Course course) throws SQLException, ClassNotFoundException;
    void deleteCourseById(Integer id) throws SQLException, ClassNotFoundException;
    ListWrapper<Course> searchCourses(ParameterSearchCourse parameterSearchCourse) throws SQLException, ClassNotFoundException;
}
