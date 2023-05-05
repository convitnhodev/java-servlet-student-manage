package service;

import entity.StudentCourse;
import model.profile.StudentsCoursesProfile;
import model.search.ParameterSearchStudentCourse;
import model.wrapper.ListWrapper;

import java.sql.SQLException;


public interface IStudentsCoursesService {
    ListWrapper<StudentsCoursesProfile> searchStudentsCoursesProfiles(ParameterSearchStudentCourse parameterSearchStudentCourse) throws SQLException, ClassNotFoundException;

    void deleteStudentCourseById(Integer id) throws SQLException, ClassNotFoundException;

    StudentCourse createStudentCourseWithOutScore(StudentCourse studentCourse) throws SQLException, ClassNotFoundException;

    void addScore(Integer studentId, Integer courseId, Integer score) throws SQLException, ClassNotFoundException;
}
