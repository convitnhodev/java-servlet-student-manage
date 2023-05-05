package model.profile;

import entity.Student;
import model.wrapper.ListWrapper;


public class EditStudentProfile {
    private Student student;
    private ListWrapper<StudentsCoursesProfile> studentsCoursesProfileListWrapper;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public ListWrapper<StudentsCoursesProfile> getStudentsCoursesProfileListWrapper() {
        return studentsCoursesProfileListWrapper;
    }

    public void setStudentsCoursesProfileListWrapper(ListWrapper<StudentsCoursesProfile> studentsCoursesProfileListWrapper) {
        this.studentsCoursesProfileListWrapper = studentsCoursesProfileListWrapper;
    }
}
