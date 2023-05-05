package controller.course;

import controller.BaseController;
import entity.Course;
import entity.Student;
import utils.DateUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


@WebServlet(name = "UpdateCourseController", value = "/courses-update")
public class UpdateCourseController extends BaseController {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String id = req.getParameter("id");
            Course course = courseService.getById(Integer.parseInt(id));
            req.setAttribute("model", course);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/courses/courses-update.jsp");
            requestDispatcher.forward(req, resp);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setCharacterEncoding("UTF-8");
            resp.setCharacterEncoding("UTF-8");
            String id = req.getParameter("id");
            String courseCode = req.getParameter("courseCode");
            String name = req.getParameter("name");
            String lecture = req.getParameter("lecture");
            String year = req.getParameter("year");
            String notes = req.getParameter("notes");

            Course course = new Course();
            course.setName(name);
            course.setCourseCode(courseCode);
            course.setLecture(lecture);
            course.setYear(DateUtils.convertDateToStringUTC(year));
            course.setNotes(notes);

            courseService.updateCourseById(Integer.parseInt(id), course);
            resp.sendRedirect(req.getContextPath() + "/courses");
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
