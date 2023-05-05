package controller.course;

import controller.BaseController;
import entity.Course;
import model.wrapper.ListWrapper;
import model.search.ParameterSearchCourse;
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


@WebServlet(name = "CourseController", value = "/courses")
public class CourseController extends BaseController {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ParameterSearchCourse parameterSearchCourse = new ParameterSearchCourse();
            String courseCode = req.getParameter("courseCode");
            String courseName = req.getParameter("courseName");
            String pageSize = req.getParameter("pageSize");
            String pageNumber = req.getParameter("pageNumber");
            parameterSearchCourse.setCourseCode(courseCode);
            parameterSearchCourse.setCourseName(courseName);
            if (!StringUtils.isBlankOrNull(pageSize)) {
                parameterSearchCourse.setPageNumber(Integer.parseInt(pageNumber));
            }
            if (!StringUtils.isBlankOrNull(pageNumber)) {
                parameterSearchCourse.setPageSize(Integer.parseInt(pageSize));
            }
            ListWrapper<Course> courseListWrapper = courseService.searchCourses(parameterSearchCourse);
            req.setAttribute("model", courseListWrapper);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/courses/courses.jsp");
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
                courseService.deleteCourseById(Integer.parseInt(idSt));
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
