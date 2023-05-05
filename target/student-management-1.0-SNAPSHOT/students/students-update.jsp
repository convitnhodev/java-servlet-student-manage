<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@include file="/common/taglib.jsp" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cập nhật Học Sinh</title>
    <%@include file="../resource/template/css/css.jsp" %>
</head>
<body>
<div class="container">
    <div>
        <h1>Cập nhật Học Sinh</h1>
    </div>
    <div>
        <a href="students">Danh sách học sinh</a>
    </div>
    <div>
        <form accept-charset="UTF-8" action="<c:url value="/students-update"/>"
              id="frmStudentsUpdate" method="post">
            <div>
                <input readonly name="id" value="${model.student.id}"/>
            </div>
            <div>
                <input type="text" name="studentCode"
                       value="${model.student.studentCode}"
                       placeholder="studentCode" id="studentCode"/>
            </div>
            <div>
                <input type="text" name="name"
                       value="${model.student.name}"
                       placeholder="name" id="name"/>
            </div>
            <div>
                <input type="text"
                       value="${model.student.grade}"
                       name="grade" placeholder="grade" id="grade"/>
            </div>
            <div>
                <input type="date"
                       value="${model.student.birthday}"
                       name="birthday" placeholder="birthday" id="birthday"/>
            </div>
            <div>
                <input type="address"
                       value="${model.student.address}"
                       name="address" placeholder="address" id="address"/>
            </div>
            <div>
                <input type="text"
                       value="${model.student.notes}"
                       name="notes" placeholder="notes" id="notes"/>
            </div>
            <div>
                <input type="submit" value="Cập nhật"/>
            </div>
        </form>
    </div>
    <div>
        <table class="table">
            <thead>
            <tr>
                <th scope="col">Course Code</th>
                <th scope="col">Course Name</th>
                <th scope="col">Score</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${model.studentsCoursesProfileListWrapper.data}" var="studentCourse">
                <tr>
                    <td>${studentCourse.courseCode}</td>
                    <td>${studentCourse.courseName}</td>
                    <td>${studentCourse.score}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@include file="../resource/template/js/js.jsp" %>
</body>
</html>
