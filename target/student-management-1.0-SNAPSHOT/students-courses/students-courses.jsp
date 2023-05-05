<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp" %>
<html>
<head>
    <title>Danh sách học sinh</title>
    <%@include file="../resource/template/css/css.jsp" %>
</head>
<body>
<div class="container">
    <div>
        <h1>Danh sách học sinh</h1>
    </div>
    <div>
        <a href="courses">
            Danh sách môn học
        </a>
        <br/>
        <a href="students-courses-create?courseId=<%=request.getParameter("courseId")%>">
            Thêm học sinh
        </a>
    </div>
    <div>
        <form action="<c:url value="/students-courses"/>" id="frmStudentsCourses" method="get">
            <div>
                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col">Student Code</th>
                        <th scope="col">Student Name</th>
                        <th scope="col">Score</th>
                        <th scope="col">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${model.data}" var="studentCourse">
                        <tr>
                            <td>${studentCourse.studentCode}</td>
                            <td>${studentCourse.studentName}</td>
                            <td>${studentCourse.score}</td>
                            <td>
                                <a href="student-course-add-score?id=${studentCourse.id}&courseId=${studentCourse.courseId}&studentId=${studentCourse.studentId}">
                                    Add score
                                </a>
                                <br/>
                                <input type="button" value="Delete" onclick="deleteStudentCourse(${studentCourse.id})"/>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <input type="hidden" name="pageNumber" id="pageNumber"/>
                <input type="hidden" name="pageSize" id="pageSize"/>
                <input type="hidden" name="courseId" value="<%=request.getParameter("courseId")%>">
                <nav aria-label="Page navigation">
                    <ul class="pagination" id="pagination"></ul>
                </nav>
            </div>

        </form>

    </div>
</div>
<%@include file="../resource/template/js/js.jsp" %>
<script>
    function deleteStudentCourse(id) {
        let confirmed = confirm("Are you sure?");
        if (confirmed) {
            $(function () {
                $.ajax({
                    url: `<%=request.getContextPath()%>/students-courses`,
                    contentType: 'application/json',
                    type: 'DELETE',
                    data: {
                        id: id,
                    },
                    success: function (data) {
                        console.log(data);
                        $('#frmStudentsCourses').submit();
                    },
                });
            });

        }
    }
</script>
</body>
</html>
