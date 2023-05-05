package service.impl;

import dao.ICourseDao;
import dao.IStudentCourseDao;
import dao.IStudentDao;
import dao.impl.CourseDao;
import dao.impl.StudentCourseDao;
import dao.impl.StudentDao;
import entity.Course;
import entity.Student;
import entity.StudentCourse;
import model.profile.StudentsCoursesProfile;
import model.search.ParameterSearchStudentCourse;
import model.wrapper.ListWrapper;
import service.IStudentsCoursesService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class StudentsCoursesService implements IStudentsCoursesService {

    private IStudentCourseDao studentCourseDao = new StudentCourseDao();
    private ICourseDao courseDao = new CourseDao();
    private IStudentDao studentDao = new StudentDao();

    @Override
    public ListWrapper<StudentsCoursesProfile> searchStudentsCoursesProfiles(ParameterSearchStudentCourse parameterSearchStudentCourse) throws SQLException, ClassNotFoundException {
        ListWrapper<StudentCourse> studentCourseListWrapper = studentCourseDao.searchStudentCourses(parameterSearchStudentCourse);
        List<StudentCourse> studentCourses = studentCourseListWrapper.getData();
        List<Integer> studentIds = studentCourses.stream().map(StudentCourse::getStudentId).collect(Collectors.toList());
        List<Integer> courseIds = studentCourses.stream().map(StudentCourse::getCourseId).collect(Collectors.toList());
        List<Student> students = studentDao.findAllByIds(studentIds);
        Map<Integer, Student> studentMap = students.stream().collect(Collectors.toMap(Student::getId, student -> student));
        List<Course> courses = courseDao.findAllByIds(courseIds);
        Map<Integer, Course> courseMap = courses.stream().collect(Collectors.toMap(Course::getId, course -> course));
        ListWrapper<StudentsCoursesProfile> studentsCoursesProfileListWrapper = new ListWrapper<>();
        List<StudentsCoursesProfile> studentsCoursesProfiles = new ArrayList<>();
        for (StudentCourse studentCourse : studentCourses) {
            Student student = studentMap.get(studentCourse.getStudentId());
            Course course = courseMap.get(studentCourse.getCourseId());

            StudentsCoursesProfile studentsCoursesProfile = new StudentsCoursesProfile();
            studentsCoursesProfile.setId(studentCourse.getId());
            studentsCoursesProfile.setStudentId(student.getId());
            studentsCoursesProfile.setStudentName(student.getName());
            studentsCoursesProfile.setStudentCode(student.getStudentCode());
            studentsCoursesProfile.setAddress(student.getAddress());
            studentsCoursesProfile.setBirthday(student.getBirthday());
            studentsCoursesProfile.setStudentNotes(student.getNotes());
            studentsCoursesProfile.setGrade(student.getGrade());

            studentsCoursesProfile.setCourseId(course.getId());
            studentsCoursesProfile.setCourseName(course.getName());
            studentsCoursesProfile.setCourseCode(course.getCourseCode());
            studentsCoursesProfile.setLecture(course.getLecture());
            studentsCoursesProfile.setYear(course.getYear());
            studentsCoursesProfile.setCourseNotes(course.getNotes());

            studentsCoursesProfile.setScore(studentCourse.getScore());
            studentsCoursesProfiles.add(studentsCoursesProfile);
        }
        studentsCoursesProfileListWrapper.setCurrentPage(studentCourseListWrapper.getCurrentPage());
        studentsCoursesProfileListWrapper.setSizePage(studentCourseListWrapper.getSizePage());
        studentsCoursesProfileListWrapper.setTotalPage(studentCourseListWrapper.getTotalPage());
        studentsCoursesProfileListWrapper.setData(studentsCoursesProfiles);
        studentsCoursesProfileListWrapper.setCount(studentCourseListWrapper.getCount());
        return studentsCoursesProfileListWrapper;

    }

    @Override
    public void deleteStudentCourseById(Integer id) throws SQLException, ClassNotFoundException {
        studentCourseDao.deleteStudentCourseById(id);
    }

    @Override
    public StudentCourse createStudentCourseWithOutScore(StudentCourse studentCourse) throws SQLException, ClassNotFoundException {
        return studentCourseDao.createStudentCourseWithOutScore(studentCourse);
    }

    @Override
    public void addScore(Integer studentId, Integer courseId, Integer score) throws SQLException, ClassNotFoundException {
        studentCourseDao.addScore(studentId, courseId, score);
    }
}
