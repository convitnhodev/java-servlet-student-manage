package dao;

import entity.StudentCourse;
import model.wrapper.ListWrapper;
import model.search.ParameterSearchStudentCourse;

import java.sql.SQLException;


public interface IStudentCourseDao {
    ListWrapper<StudentCourse> searchStudentCourses(ParameterSearchStudentCourse parameterSearchStudentCourse) throws SQLException, ClassNotFoundException;
    Long countStudentCourses(ParameterSearchStudentCourse parameterSearchStudentCourse) throws SQLException, ClassNotFoundException;
    void deleteStudentCourseById(Integer id) throws SQLException, ClassNotFoundException;
    StudentCourse createStudentCourseWithOutScore(StudentCourse studentCourse) throws SQLException, ClassNotFoundException;

    void addScore(Integer studentId, Integer courseId, Integer score) throws SQLException, ClassNotFoundException;
}
