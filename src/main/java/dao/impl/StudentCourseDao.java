package dao.impl;

import dao.IStudentCourseDao;
import entity.StudentCourse;
import model.wrapper.ListWrapper;
import model.search.ParameterSearchStudentCourse;
import utils.CollectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class StudentCourseDao extends BaseDao implements IStudentCourseDao {

    @Override
    public ListWrapper<StudentCourse> searchStudentCourses(ParameterSearchStudentCourse parameterSearchStudentCourse) throws SQLException, ClassNotFoundException {

        Connection connection = open();
        String sqlQuery = buildQuerySearchStudentCourse(parameterSearchStudentCourse, false);
        PreparedStatement ps = connection.prepareStatement(sqlQuery);
        ResultSet rs = ps.executeQuery();
        ListWrapper<StudentCourse> listWrapper = new ListWrapper<>();
        List<StudentCourse> studentCourses = new ArrayList<>();
        while (rs.next()) {
            StudentCourse studentCourse = new StudentCourse();
            studentCourse.setId(rs.getInt("id"));
            studentCourse.setStudentId(rs.getInt("student_id"));
            studentCourse.setCourseId(rs.getInt("course_id"));
            studentCourse.setScore(rs.getDouble("score"));
            studentCourses.add(studentCourse);
        }
        Long total = countStudentCourses(parameterSearchStudentCourse);

        listWrapper.setCount(total);
        listWrapper.setCurrentPage(Long.valueOf(parameterSearchStudentCourse.getPageNumber()));
        listWrapper.setSizePage(Long.valueOf(parameterSearchStudentCourse.getPageSize()));
        listWrapper.setTotalPage(total % parameterSearchStudentCourse.getPageSize() == 0 ? total / parameterSearchStudentCourse.getPageSize() : total / parameterSearchStudentCourse.getPageSize() + 1);
        listWrapper.setData(studentCourses);
        connection.close();
        return listWrapper;
    }

    @Override
    public Long countStudentCourses(ParameterSearchStudentCourse parameterSearchStudentCourse) throws SQLException, ClassNotFoundException {
        Connection connection = open();
        String sqlQuery = buildQuerySearchStudentCourse(parameterSearchStudentCourse, true);
        PreparedStatement ps = connection.prepareStatement(sqlQuery);
        ResultSet rs = ps.executeQuery();
        Long total = 0L;
        while (rs.next()) {
            total = rs.getLong(1);
        }
        return total;
    }

    @Override
    public void deleteStudentCourseById(Integer id) throws SQLException, ClassNotFoundException {
        Connection connection = open();
        String sqlQuery = "DELETE FROM student_course WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sqlQuery);
        ps.setInt(1, id);
        ps.executeUpdate();
        connection.close();
    }

    @Override
    public StudentCourse createStudentCourseWithOutScore(StudentCourse studentCourse) throws SQLException, ClassNotFoundException {
        Connection connection = open();
        String sqlQuery = "INSERT INTO student_course (student_id, course_id) VALUES (?, ?)";
        PreparedStatement ps = connection.prepareStatement(sqlQuery);
        ps.setInt(1, studentCourse.getStudentId());
        ps.setInt(2, studentCourse.getCourseId());
        ps.executeUpdate();
        connection.close();
        return studentCourse;
    }

    @Override
    public void addScore(Integer studentId, Integer courseId, Integer score) throws SQLException, ClassNotFoundException {
        Connection connection = open();
        String sqlQuery = "UPDATE student_course SET score = ? WHERE student_id = ? AND course_id = ?";
        PreparedStatement ps = connection.prepareStatement(sqlQuery);
        ps.setInt(1, score);
        ps.setInt(2, studentId);
        ps.setInt(3, courseId);
        ps.executeUpdate();
        connection.close();
    }

    private String buildQuerySearchStudentCourse(ParameterSearchStudentCourse parameterSearchStudentCourse, boolean count) {
        if (parameterSearchStudentCourse == null) {
            parameterSearchStudentCourse = new ParameterSearchStudentCourse();
        }
        if (parameterSearchStudentCourse.getPageNumber() == null) {
            parameterSearchStudentCourse.setPageNumber(1);
        }
        if (parameterSearchStudentCourse.getPageSize() == null) {
            parameterSearchStudentCourse.setPageSize(20);
        }
        StringBuilder sql = new StringBuilder();
        if (count) {
            sql.append("SELECT COUNT(*) FROM student_course");
        } else {
            sql.append("SELECT * FROM student_course");
        }
        sql.append(" WHERE 1 = 1");
        if (parameterSearchStudentCourse.getStudentId() != null) {
            sql.append(" AND student_id = ").append(parameterSearchStudentCourse.getStudentId());
        }
        if (parameterSearchStudentCourse.getCourseId() != null) {
            sql.append(" AND course_id = ").append(parameterSearchStudentCourse.getCourseId());
        }

        if (!count) {
            if (!CollectionUtils.isNullOrEmpty(parameterSearchStudentCourse.getSorts())) {
                sql.append(" ORDER BY ");
                for (int i = 0; i < parameterSearchStudentCourse.getSorts().size(); i++) {
                    sql.append(parameterSearchStudentCourse.getSorts().get(i).getKey());
                    sql.append(" ");
                    if (parameterSearchStudentCourse.getSorts().get(i).getAsc()) {
                        sql.append("ASC, ");
                    } else {
                        sql.append("DESC, ");
                    }
                }
                //remove ", " last
                sql.delete(sql.length() - 2, sql.length());
            }
            if (parameterSearchStudentCourse.getPageSize() != null && parameterSearchStudentCourse.getPageNumber() != null) {
                sql.append(" LIMIT " + parameterSearchStudentCourse.getPageSize() + " OFFSET " + parameterSearchStudentCourse.getPageSize() * (parameterSearchStudentCourse.getPageNumber() - 1));
            }

        }
        return sql.toString();
    }
}
