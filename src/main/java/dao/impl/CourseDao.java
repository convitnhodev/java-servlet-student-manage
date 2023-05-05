package dao.impl;

import dao.ICourseDao;
import entity.Course;
import model.wrapper.ListWrapper;
import model.search.ParameterSearchCourse;
import utils.CollectionUtils;
import utils.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CourseDao extends BaseDao implements ICourseDao {

    @Override
    public Course createCourse(Course course) throws SQLException, ClassNotFoundException {
        Connection connection = open();
        connection.setAutoCommit(false);
        String sql = "INSERT INTO course (course_code, name, lecture,year,notes) VALUES (?,?, ?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, course.getCourseCode());
        ps.setString(2, course.getName());
        ps.setString(3, course.getLecture());
        ps.setDate(4, new java.sql.Date(course.getYear().getTime()));
        ps.setString(5, course.getNotes());
        ps.executeUpdate();
        connection.commit();
        connection.close();
        return getByCourseCode(course.getCourseCode());
    }

    @Override
    public Course getById(Integer id) throws SQLException, ClassNotFoundException {
        Connection connection = open();
        String sql = "SELECT * FROM course WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        Course course = null;
        while (rs.next()) {
            course = new Course();
            course.setId(rs.getInt("id"));
            course.setCourseCode(rs.getString("course_code"));
            course.setName(rs.getString("name"));
            course.setLecture(rs.getString("lecture"));
            course.setYear(rs.getDate("year"));
            course.setNotes(rs.getString("notes"));
        }
        return course;
    }

    @Override
    public Course getByCourseCode(String courseCode) throws SQLException, ClassNotFoundException {
        Connection connection = open();
        String sql = "SELECT * FROM course WHERE course_code = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, courseCode);
        ResultSet rs = ps.executeQuery();
        Course course = null;
        while (rs.next()) {
            course = new Course();
            course.setId(rs.getInt("id"));
            course.setCourseCode(rs.getString("course_code"));
            course.setName(rs.getString("name"));
            course.setLecture(rs.getString("lecture"));
            course.setYear(rs.getDate("year"));
            course.setNotes(rs.getString("notes"));
        }
        return course;
    }

    @Override
    public Course updateCourseById(Integer id, Course course) throws SQLException, ClassNotFoundException {
        Connection connection = open();
        connection.setAutoCommit(false);
        String sql = "UPDATE course SET course_code = ?, name = ?, lecture = ?, year = ?, notes = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, course.getCourseCode());
        ps.setString(2, course.getName());
        ps.setString(3, course.getLecture());
        ps.setDate(4, new java.sql.Date(course.getYear().getTime()));
        ps.setString(5, course.getNotes());
        ps.setInt(6, id);
        ps.executeUpdate();
        connection.commit();
        connection.close();
        return getById(id);
    }

    @Override
    public List<Course> findAllByIds(List<Integer> ids) throws SQLException, ClassNotFoundException {
        if (CollectionUtils.isNullOrEmpty(ids)) {
            return new ArrayList<>();
        }
        Connection connection = open();
        String idsArray = StringUtils.join(ids, ",");
        //remove "," at the end of string
        idsArray = idsArray.substring(0, idsArray.length() - 1);
        String sql = "SELECT DISTINCT * FROM course WHERE id IN (" + idsArray + ")";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<Course> courses = new ArrayList<>();
        while (rs.next()) {
            Course course = new Course();
            course.setId(rs.getInt("id"));
            course.setCourseCode(rs.getString("course_code"));
            course.setName(rs.getString("name"));
            course.setLecture(rs.getString("lecture"));
            course.setYear(rs.getDate("year"));
            course.setNotes(rs.getString("notes"));
            courses.add(course);
        }
        return courses;
    }

    @Override
    public void deleteCourseById(Integer id) throws SQLException, ClassNotFoundException {
        Connection connection = open();
        connection.setAutoCommit(false);
        String sql = "DELETE FROM course WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        connection.commit();
        connection.close();
    }

    @Override
    public ListWrapper<Course> searchCourses(ParameterSearchCourse parameterSearchCourse) throws SQLException, ClassNotFoundException {

        Connection connection = open();
        String sql = buildQuerySearch(parameterSearchCourse, false);
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ListWrapper<Course> courseListWrapper = new ListWrapper<>();
        List<Course> courses = new ArrayList<>();
        while (rs.next()) {
            Course course = new Course();
            course.setId(rs.getInt("id"));
            course.setCourseCode(rs.getString("course_code"));
            course.setName(rs.getString("name"));
            course.setLecture(rs.getString("lecture"));
            course.setYear(rs.getDate("year"));
            course.setNotes(rs.getString("notes"));
            courses.add(course);
        }
        Long total = countCourse(parameterSearchCourse);

        courseListWrapper.setCount(total);
        courseListWrapper.setCurrentPage(Long.valueOf(parameterSearchCourse.getPageNumber()));
        courseListWrapper.setSizePage(Long.valueOf(parameterSearchCourse.getPageSize()));
        courseListWrapper.setTotalPage(total % parameterSearchCourse.getPageSize() == 0 ? total / parameterSearchCourse.getPageSize() : total / parameterSearchCourse.getPageSize() + 1);
        courseListWrapper.setData(courses);
        connection.close();
        return courseListWrapper;
    }

    @Override
    public Long countCourse(ParameterSearchCourse parameterSearchCourse) throws SQLException, ClassNotFoundException {
        Connection connection = open();
        String sql = buildQuerySearch(parameterSearchCourse, true);
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        Long count = 0L;
        while (rs.next()) {
            count = rs.getLong(1);
        }
        return count;
    }

    private String buildQuerySearch(ParameterSearchCourse parameterSearchCourse, boolean count) {
        if (parameterSearchCourse == null) {
            parameterSearchCourse = new ParameterSearchCourse();
        }
        if (parameterSearchCourse.getPageNumber() == null) {
            parameterSearchCourse.setPageNumber(1);
        }
        if (parameterSearchCourse.getPageSize() == null) {
            parameterSearchCourse.setPageSize(20);
        }
        StringBuilder sql = new StringBuilder();
        if (count) {
            sql.append("SELECT COUNT(*) FROM course WHERE 1=1 ");
        } else {
            sql.append("SELECT * FROM course WHERE 1=1 ");
        }
        if (!StringUtils.isBlankOrNull(parameterSearchCourse.getCourseCode())) {
            sql.append(" AND course_code LIKE '%" + parameterSearchCourse.getCourseCode() + "%' ");
        }
        if (!StringUtils.isBlankOrNull(parameterSearchCourse.getCourseName())) {
            sql.append(" AND name LIKE '%" + parameterSearchCourse.getCourseName() + "%' ");
        }

        if (!count) {
            if (!CollectionUtils.isNullOrEmpty(parameterSearchCourse.getSorts())) {
                sql.append(" ORDER BY ");
                for (int i = 0; i < parameterSearchCourse.getSorts().size(); i++) {
                    sql.append(parameterSearchCourse.getSorts().get(i).getKey());
                    sql.append(" ");
                    if (parameterSearchCourse.getSorts().get(i).getAsc()) {
                        sql.append("ASC, ");
                    }
                }
                //remove ", " last
                sql.delete(sql.length() - 2, sql.length());
            }
            if (parameterSearchCourse.getPageSize() != null && parameterSearchCourse.getPageNumber() != null) {
                sql.append(" LIMIT " + parameterSearchCourse.getPageSize() + " OFFSET " + parameterSearchCourse.getPageSize() * (parameterSearchCourse.getPageNumber() - 1));
            }

        }
        return sql.toString();
    }
}
