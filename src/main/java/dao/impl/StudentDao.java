package dao.impl;

import dao.IStudentDao;
import entity.Student;
import model.wrapper.ListWrapper;
import model.search.ParameterSearchStudent;
import utils.CollectionUtils;
import utils.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class StudentDao extends BaseDao implements IStudentDao {

    @Override
    public Student createStudent(Student student) throws SQLException, ClassNotFoundException {
        Connection connection = open();
        connection.setAutoCommit(false);
        String sql = "INSERT INTO student (student_code, name, grade,birthday,address,notes) VALUES (?, ?, ?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, student.getStudentCode());
        ps.setString(2, student.getName());
        ps.setString(3, student.getGrade());
        ps.setDate(4, new java.sql.Date(student.getBirthday().getTime()));
        ps.setString(5, student.getAddress());
        ps.setString(6, student.getNotes());
        ps.executeUpdate();
        connection.commit();
        connection.close();
        return getByStudentCode(student.getStudentCode());
    }

    @Override
    public Student getById(Integer id) throws SQLException, ClassNotFoundException {
        Connection connection = open();
        String sql = "SELECT * FROM student WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        Student student = null;
        while (rs.next()) {
            student = new Student();
            student.setId(rs.getInt("id"));
            student.setStudentCode(rs.getString("student_code"));
            student.setName(rs.getString("name"));
            student.setGrade(rs.getString("grade"));
            student.setBirthday(rs.getDate("birthday"));
            student.setAddress(rs.getString("address"));
            student.setNotes(rs.getString("notes"));
        }
        connection.close();
        return student;
    }

    @Override
    public Student getByStudentCode(String studentCode) throws SQLException, ClassNotFoundException {
        Connection connection = open();
        String sql = "SELECT * FROM student WHERE student_code = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, studentCode);
        ResultSet rs = ps.executeQuery();
        Student student = null;
        while (rs.next()) {
            student = new Student();
            student.setId(rs.getInt("id"));
            student.setStudentCode(rs.getString("student_code"));
            student.setName(rs.getString("name"));
            student.setGrade(rs.getString("grade"));
            student.setBirthday(rs.getDate("birthday"));
            student.setAddress(rs.getString("address"));
            student.setNotes(rs.getString("notes"));
        }
        connection.close();
        return student;
    }

    @Override
    public Student updateStudentById(Integer id, Student student) throws SQLException, ClassNotFoundException {
        Connection connection = open();
        connection.setAutoCommit(false);
        String sql = "UPDATE student SET student_code = ?, name = ?, grade = ?, birthday = ?, address = ?, notes = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, student.getStudentCode());
        ps.setString(2, student.getName());
        ps.setString(3, student.getGrade());
        ps.setDate(4, new java.sql.Date(student.getBirthday().getTime()));
        ps.setString(5, student.getAddress());
        ps.setString(6, student.getNotes());
        ps.setInt(7, id);
        ps.executeUpdate();
        connection.commit();
        connection.close();
        return getById(id);
    }

    @Override
    public void deleteStudentById(Integer id) throws SQLException, ClassNotFoundException {
        Connection connection = open();
        connection.setAutoCommit(false);
        String sql = "DELETE FROM student WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        connection.commit();
        connection.close();
    }

    @Override
    public List<Student> findAllByIds(List<Integer> ids) throws SQLException, ClassNotFoundException {
        if (CollectionUtils.isNullOrEmpty(ids)) {
            return new ArrayList<>();
        }
        Connection connection = open();
        String idsArray = StringUtils.join(ids, ",");
        //remove "," at the end of string
        idsArray = idsArray.substring(0, idsArray.length() - 1);
        String sql = "SELECT DISTINCT * FROM student WHERE id IN (" + idsArray + ")";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<Student> students = new ArrayList<>();
        while (rs.next()) {
            Student student = new Student();
            student.setId(rs.getInt("id"));
            student.setStudentCode(rs.getString("student_code"));
            student.setName(rs.getString("name"));
            student.setGrade(rs.getString("grade"));
            student.setBirthday(rs.getDate("birthday"));
            student.setAddress(rs.getString("address"));
            student.setNotes(rs.getString("notes"));
            students.add(student);
        }
        return students;
    }

    @Override
    public ListWrapper<Student> searchStudents(ParameterSearchStudent parameterSearchStudent) throws SQLException, ClassNotFoundException {
        Connection connection = open();
        String sqlQuery = buildQuerySearch(parameterSearchStudent, false);
        PreparedStatement ps = connection.prepareStatement(sqlQuery.toString());
        ResultSet rs = ps.executeQuery();
        ListWrapper<Student> studentListWrapper = new ListWrapper<>();
        List<Student> students = new ArrayList<>();
        while (rs.next()) {
            Student student = new Student();
            student.setId(rs.getInt("id"));
            student.setStudentCode(rs.getString("student_code"));
            student.setName(rs.getString("name"));
            student.setGrade(rs.getString("grade"));
            student.setBirthday(rs.getDate("birthday"));
            student.setAddress(rs.getString("address"));
            student.setNotes(rs.getString("notes"));
            students.add(student);
        }
        Long total = countStudents(parameterSearchStudent);

        studentListWrapper.setCount(total);
        studentListWrapper.setCurrentPage(Long.valueOf(parameterSearchStudent.getPageNumber()));
        studentListWrapper.setSizePage(Long.valueOf(parameterSearchStudent.getPageSize()));
        studentListWrapper.setTotalPage(total % parameterSearchStudent.getPageSize() == 0 ? total / parameterSearchStudent.getPageSize() : total / parameterSearchStudent.getPageSize() + 1);
        studentListWrapper.setData(students);
        connection.close();
        return studentListWrapper;
    }

    @Override
    public Long countStudents(ParameterSearchStudent parameterSearchStudent) throws SQLException, ClassNotFoundException {
        Connection connection = open();
        String sqlQuery = buildQuerySearch(parameterSearchStudent, true);
        PreparedStatement ps = connection.prepareStatement(sqlQuery.toString());
        ResultSet rs = ps.executeQuery();
        Long count = 0L;
        while (rs.next()) {
            count = rs.getLong(1);
        }
        connection.close();
        return count;
    }

    @Override
    public List<Student> findAllStudentNotInStudentCourse(Integer courseId) throws SQLException, ClassNotFoundException {
        Connection connection = open();
        String sql = "SELECT * FROM student WHERE id NOT IN (SELECT student_id FROM student_course WHERE course_id = ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, courseId);
        ResultSet rs = ps.executeQuery();
        List<Student> students = new ArrayList<>();
        while (rs.next()) {
            Student student = new Student();
            student.setId(rs.getInt("id"));
            student.setStudentCode(rs.getString("student_code"));
            student.setName(rs.getString("name"));
            student.setGrade(rs.getString("grade"));
            student.setBirthday(rs.getDate("birthday"));
            student.setAddress(rs.getString("address"));
            student.setNotes(rs.getString("notes"));
            students.add(student);
        }
        connection.close();
        return students;
    }

    private String buildQuerySearch(ParameterSearchStudent parameterSearchStudent, boolean count) {
        if (parameterSearchStudent == null) {
            parameterSearchStudent = new ParameterSearchStudent();
        }
        if (parameterSearchStudent.getPageNumber() == null) {
            parameterSearchStudent.setPageNumber(1);
        }
        if (parameterSearchStudent.getPageSize() == null) {
            parameterSearchStudent.setPageSize(20);
        }
        StringBuilder sql = new StringBuilder();
        if (count) {
            sql.append("SELECT COUNT(*) FROM student WHERE 1=1 ");
        } else {
            sql.append("SELECT * FROM student WHERE 1=1 ");
        }
        if (!StringUtils.isBlankOrNull(parameterSearchStudent.getStudentCode())) {
            sql.append(" AND student_code LIKE '%" + parameterSearchStudent.getStudentCode() + "%' ");
        }
        if (!StringUtils.isBlankOrNull(parameterSearchStudent.getName())) {
            sql.append(" AND name LIKE '%" + parameterSearchStudent.getName() + "%' ");
        }
        if (!StringUtils.isBlankOrNull(parameterSearchStudent.getGrade())) {
            sql.append(" AND grade LIKE '%" + parameterSearchStudent.getGrade() + "%' ");
        }

        if (!count) {
            if (!CollectionUtils.isNullOrEmpty(parameterSearchStudent.getSorts())) {
                sql.append(" ORDER BY ");
                for (int i = 0; i < parameterSearchStudent.getSorts().size(); i++) {
                    sql.append(parameterSearchStudent.getSorts().get(i).getKey());
                    sql.append(" ");
                    if (parameterSearchStudent.getSorts().get(i).getAsc()) {
                        sql.append("ASC, ");
                    } else {
                        sql.append("DESC, ");
                    }
                }
                //remove ", " last
                sql.delete(sql.length() - 2, sql.length());
            }
            if (parameterSearchStudent.getPageSize() != null && parameterSearchStudent.getPageNumber() != null) {
                sql.append(" LIMIT " + parameterSearchStudent.getPageSize() + " OFFSET " + parameterSearchStudent.getPageSize() * (parameterSearchStudent.getPageNumber() - 1));
            }

        }
        return sql.toString();
    }
}
