package dao;

import dao.impl.StudentDao;
import entity.Student;
import model.wrapper.ListWrapper;
import model.search.ParameterSearchStudent;
import model.search.base.BaseSort;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;


public class StudentDaoTest {
    private StudentDao studentDao = new StudentDao();

    @Test
    public void searchStudent() throws SQLException, ClassNotFoundException {
        ParameterSearchStudent parameterSearchStudent = new ParameterSearchStudent();
        List<BaseSort> baseSorts = new java.util.ArrayList<>();
        BaseSort baseSort1 = new BaseSort();
        baseSort1.setKey("id");
        baseSort1.setAsc(false);
        baseSorts.add(baseSort1);

        BaseSort baseSort2 = new BaseSort();
        baseSort2.setKey("name");
        baseSort2.setAsc(true);

        parameterSearchStudent.setSorts(baseSorts);

//        parameterSearchStudent.setStudentCode("S001");
        ListWrapper<Student> studentListWrapper = studentDao.searchStudents(parameterSearchStudent);
        int n = 0;
    }

    @Test
    public void createStudent() throws SQLException, ClassNotFoundException {
        Student student = new Student();
        student.setStudentCode("S005");
        student.setAddress("Hà Nội");
        student.setBirthday(new java.util.Date());
        student.setGrade("10B");
        student.setName("Nguyễn Văn A");
        student.setNotes("Ghi chú");
        Student studentCreate = studentDao.createStudent(student);
        int n = 0;
    }

    @Test
    public void updateStudent() throws SQLException, ClassNotFoundException {
        Student student = studentDao.getById(1);

        student.setName("Nguyễn Văn B");
        student.setNotes("Ghi chú 2");
        Student studentUpdate = studentDao.updateStudentById(1, student);
        int n = 0;
    }
}
