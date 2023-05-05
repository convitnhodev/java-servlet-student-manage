package controller.student;

import controller.BaseController;
import entity.Student;


import model.wrapper.ListWrapper;
import model.search.ParameterSearchStudent;
import utils.StringUtils;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


@WebServlet(name = "StudentController", value = "/students")
public class StudentController extends BaseController {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ParameterSearchStudent parameterSearchStudent = new ParameterSearchStudent();
            String name = req.getParameter("name");
            String studentCode = req.getParameter("studentCode");
            String grade = req.getParameter("grade");
            String pageSize = req.getParameter("pageSize");
            String pageNumber = req.getParameter("pageNumber");
            parameterSearchStudent.setName(name);
            parameterSearchStudent.setStudentCode(studentCode);
            if (!StringUtils.isBlankOrNull(pageSize)) {
                parameterSearchStudent.setPageNumber(Integer.parseInt(pageNumber));
            }
            if (!StringUtils.isBlankOrNull(pageNumber)) {
                parameterSearchStudent.setPageSize(Integer.parseInt(pageSize));
            }
            if (!StringUtils.isBlankOrNull(grade)) {
                parameterSearchStudent.setGrade(grade);
            }
            ListWrapper<Student> students = studentService.searchStudents(parameterSearchStudent);
            req.setAttribute("model", students);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/students/students.jsp");
            requestDispatcher.forward(req, resp);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        BufferedReader reader = req.getReader();
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        String data = stringBuilder.toString();
        Map<String, String> paramMap = new HashMap<>();
        for (String param : data.split("&")) {
            String[] keyValue = param.split("=");
            paramMap.put(keyValue[0], keyValue[1]);
        }
        String idSt = paramMap.get("id");
        if (!StringUtils.isBlankOrNull(idSt)) {
            try {
                studentService.deleteStudentById(Integer.parseInt(idSt));
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(200);
        resp.getWriter().write("{\"status\": \"success\"}");
    }
}
