package controller.student_course;

import controller.BaseController;
import model.profile.StudentsCoursesProfile;
import model.search.ParameterSearchStudentCourse;
import model.wrapper.ListWrapper;
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


@WebServlet(name = "StudentCourseController", value = "/students-courses")
public class StudentCourseController extends BaseController {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ParameterSearchStudentCourse parameterSearchStudentCourse = new ParameterSearchStudentCourse();

            String studentId = req.getParameter("studentId");
            String courseId = req.getParameter("courseId");
            String pageSize = req.getParameter("pageSize");
            String pageNumber = req.getParameter("pageNumber");
            if (!StringUtils.isBlankOrNull(studentId)) {
                parameterSearchStudentCourse.setStudentId(Integer.parseInt(studentId));
            }
            if (!StringUtils.isBlankOrNull(courseId)) {
                parameterSearchStudentCourse.setCourseId(Integer.parseInt(courseId));
            }
            if (!StringUtils.isBlankOrNull(pageSize)) {
                parameterSearchStudentCourse.setPageSize(Integer.parseInt(pageSize));
            }
            if (!StringUtils.isBlankOrNull(pageNumber)) {
                parameterSearchStudentCourse.setPageNumber(Integer.parseInt(pageNumber));
            }

            ListWrapper<StudentsCoursesProfile> studentsCoursesProfileListWrapper =
                    studentsCoursesService.searchStudentsCoursesProfiles(parameterSearchStudentCourse);
            req.setAttribute("model", studentsCoursesProfileListWrapper);
            RequestDispatcher rs = req.getRequestDispatcher("/students-courses/students-courses.jsp");
            rs.forward(req, resp);
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
                studentsCoursesService.deleteStudentCourseById(Integer.parseInt(idSt));
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
