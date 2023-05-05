package controller.student_course;

import controller.BaseController;
import entity.Course;
import entity.Student;
import model.profile.AddScoreProfile;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


@WebServlet(name = "AddScoreStudentCourseController", urlPatterns = "/student-course-add-score")
public class AddScoreStudentCourseController extends BaseController {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String id = req.getParameter("id");
            String studentId = req.getParameter("studentId");
            String courseId = req.getParameter("courseId");
            Student student = studentService.getById(Integer.valueOf(studentId));
            Course course = courseService.getById(Integer.valueOf(courseId));
            AddScoreProfile model = new AddScoreProfile();
            model.setId(id);
            model.setStudent(student);
            model.setCourse(course);
            req.setAttribute("model", model);
            RequestDispatcher rs = req.getRequestDispatcher("students-courses/student-courses-add-score.jsp");
            rs.forward(req, resp);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String studentId = req.getParameter("studentId");
            String courseId = req.getParameter("courseId");
            String score = req.getParameter("score");
            studentsCoursesService.addScore(Integer.valueOf(studentId), Integer.valueOf(courseId), Integer.valueOf(score));
            resp.sendRedirect(req.getContextPath() + "/students-courses?courseId=" + courseId);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
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
