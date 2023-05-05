package service.impl;

import dao.IStudentDao;
import dao.impl.StudentDao;
import entity.Student;
import model.wrapper.ListWrapper;
import model.search.ParameterSearchStudent;
import service.IStudentService;

import java.sql.SQLException;
import java.util.List;


public class StudentService implements IStudentService {
    private IStudentDao studentDao = new StudentDao();

    @Override
    public Student getById(Integer id) throws SQLException, ClassNotFoundException {
        return studentDao.getById(id);
    }

    @Override
    public Student createStudent(Student student) throws SQLException, ClassNotFoundException {
        return studentDao.createStudent(student);
    }

    @Override
    public Student updateStudentById(Integer id, Student student) throws SQLException, ClassNotFoundException {
        return studentDao.updateStudentById(id, student);
    }

    @Override
    public void deleteStudentById(Integer id) throws SQLException, ClassNotFoundException {
        studentDao.deleteStudentById(id);
    }

    @Override
    public ListWrapper<Student> searchStudents(ParameterSearchStudent parameterSearchStudent) throws SQLException, ClassNotFoundException {
        return studentDao.searchStudents(parameterSearchStudent);
    }

    @Override
    public List<Student> findAllStudentNotInStudentCourse(Integer courseId) throws SQLException, ClassNotFoundException {
        return studentDao.findAllStudentNotInStudentCourse(courseId);
    }
}
