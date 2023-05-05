<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp" %>
<html>
<head>
    <title>Thêm điểm cho học sinh</title>
    <%@include file="../resource/template/css/css.jsp" %>
</head>
<body>
<div class="container">
    <div>
        <h1>
            Thêm điểm cho học sinh
        </h1>
    </div>
    <div>
        <a href="students-courses?courseId=<%=request.getParameter("courseId")%>">
            Danh sách sinh viên trong môn học
        </a>
    </div>
    <div>
        <form action="student-course-add-score" method="post" id="frmStudentCourseAddScore">
            <div>
                <input type="hidden" readonly name="studentCourseId" value="${model.id}"
                       placeholder="studentCourseId" id="studentCourseId"/>
            </div>
            <div>
                <input type="hidden" readonly name="studentId" value="${model.student.id}"
                       placeholder="studentId" id="studentId"/>
            </div>
            <div>
                <input type="text" readonly name="studentCode" value="${model.student.studentCode}"
                       placeholder="studentCode" id="studentCode"/>
            </div>
            <div>
                <input type="text" readonly name="name"
                       value="${model.student.name}"
                       placeholder="name" id="name"/>
            </div>
            <div>
                <input type="hidden" readonly name="courseId"
                       value="${model.course.id}"
                       placeholder="courseId" id="courseId"/>
            </div>
            <div>
                <input type="text" name="courseName"
                       value="${model.course.name}"
                       placeholder="courseName" id="courseName"/>
            </div>
            <div>
                <input type="number" name="score" id="score"/>
            </div>
            <div>
                <input type="submit" value="Cập nhật"/>
            </div>
        </form>
    </div>
</div>
</body>
</html>
