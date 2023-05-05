package controller.student_course;

import controller.BaseController;
import entity.Student;
import entity.StudentCourse;
import utils.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;



@WebServlet(name = "StudentCourseCreateController", value = "/students-courses-create")
public class CreateStudentCourseController extends BaseController {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String courseIdSt = req.getParameter("courseId");
            if (!StringUtils.isBlankOrNull(courseIdSt)) {
                int courseId = Integer.parseInt(courseIdSt);
                List<Student> students = studentService.findAllStudentNotInStudentCourse(courseId);
                req.setAttribute("model", students);
//                req.setAttribute("courseId", courseId);
            }
            RequestDispatcher rs = req.getRequestDispatcher("students-courses/student-courses-create.jsp");
            rs.forward(req, resp);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String studentIdSt = req.getParameter("studentId");
        String courseIdSt = req.getParameter("courseId");
        if (!StringUtils.isBlankOrNull(studentIdSt) && !StringUtils.isBlankOrNull(courseIdSt)) {
            int studentId = Integer.parseInt(studentIdSt);
            int courseId = Integer.parseInt(courseIdSt);
            try {
                StudentCourse studentCourse = new StudentCourse();
                studentCourse.setStudentId(studentId);
                studentCourse.setCourseId(courseId);
                studentsCoursesService.createStudentCourseWithOutScore(studentCourse);
                resp.sendRedirect(req.getContextPath() + "/students-courses?courseId=" + courseId);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}
