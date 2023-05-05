package controller.student;

import controller.BaseController;
import entity.Student;
import service.IStudentService;
import service.impl.StudentService;
import utils.DateUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


@WebServlet(name = "CreateStudentController", value = "/students-create")
public class CreateStudentController extends BaseController {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/students/students-create.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setCharacterEncoding("UTF-8");
            resp.setCharacterEncoding("UTF-8");
            String studentCode = req.getParameter("studentCode");
            String name = req.getParameter("name");
            String grade = req.getParameter("grade");
            String birthdaySt = req.getParameter("birthday");
            String address = req.getParameter("address");
            String notes = req.getParameter("notes");
            Student student = new Student();
            student.setName(name);
            student.setStudentCode(studentCode);
            student.setGrade(grade);
            student.setBirthday(DateUtils.convertDateToStringUTC(birthdaySt));
            student.setAddress(address);
            student.setNotes(notes);
            studentService.createStudent(student);
            resp.sendRedirect(req.getContextPath() + "/students");
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
