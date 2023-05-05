package dao;

import model.profile.StudentsCoursesProfile;
import model.search.ParameterSearchStudentCourse;
import model.wrapper.ListWrapper;
import org.junit.jupiter.api.Test;
import service.IStudentsCoursesService;
import service.impl.StudentsCoursesService;

import java.sql.SQLException;


public class StudentsCoursesTest {
    private IStudentsCoursesService studentsCoursesService = new StudentsCoursesService();

    @Test
    public void searchTest() throws SQLException, ClassNotFoundException {
        ParameterSearchStudentCourse parameterSearchStudentCourse = new ParameterSearchStudentCourse();
        parameterSearchStudentCourse.setCourseId(1);
        ListWrapper<StudentsCoursesProfile> studentsCoursesProfileListWrapper
                = studentsCoursesService.searchStudentsCoursesProfiles(parameterSearchStudentCourse);
        int n = 0;
    }
}
