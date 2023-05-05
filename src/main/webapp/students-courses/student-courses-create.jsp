<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp" %>
<html>
<head>
    <title>Thêm học sinh vào khóa học</title>
</head>
<body>
<div class="container">
    <div>
        <h1>Thêm học sinh vào khóa học</h1>
    </div>
    <div>
        <form action="<c:url value="/students-courses-create"/>" id="frmStudentsCourses" method="post">
            <select name="studentId" class="form-select form-select-sm" aria-label=".form-select-sm example">
                <option selected>Open this select menu</option>
                <c:forEach items="${model}" var="student">
                    <option value="${student.id}">${student.name}</option>
                </c:forEach>
            </select>
            <input type="submit" value="Thêm vào"/>
            <input type="hidden" name="courseId" value="<%=request.getParameter("courseId")%>"/>
        </form>
    </div>
</div>

</body>
</html>
