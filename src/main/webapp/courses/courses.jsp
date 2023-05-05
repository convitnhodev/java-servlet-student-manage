<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Danh sách môn học</title>
</head>
<body>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@include file="/common/taglib.jsp" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student</title>
    <%@include file="../resource/template/css/css.jsp" %>
</head>
<body>
<div class="container">
    <div>
        <h1>Danh sách môn học</h1>
    </div>
    <div>
        <a href="courses-create">
            Tạo môn học
        </a>
    </div>
    <form action="<c:url value="/courses"/>" id="frmCourses" method="get">
        <div>
            <input id="courseCode" name="courseCode" placeholder="courseCode"/>
            <input id="courseName" name="courseName" placeholder="courseName"/>
            <input type="submit" value="Tìm kiếm"/>
            <input type="button" onclick="clearSearch()" value="Xóa tìm"/>
        </div>
        <div>
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">Course Code</th>
                    <th scope="col">Name</th>
                    <th scope="col">Lecture</th>
                    <th scope="col">Year</th>
                    <th scope="col">Notes</th>
                    <th scope="col">Hành động</th>
                    <th scope="col">Danh sách sinh viên</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${model.data}" var="course">
                    <tr>
                        <td>${course.courseCode}</td>
                        <td>${course.name}</td>
                        <td>${course.lecture}</td>
                        <td>${course.year}</td>
                        <td>${course.notes}</td>
                        <td>
                            <input type="button" value="Edit" onclick="editCourse(${course.id})"/>
                            <input type="button" value="Delete" onclick="deleteCourse(${course.id})"/>
                        </td>
                        <td>
                            <a href="students-courses?courseId=${course.id}">Danh sách sinh viên</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <input type="hidden" name="pageNumber" id="pageNumber"/>
            <input type="hidden" name="pageSize" id="pageSize"/>
            <nav aria-label="Page navigation">
                <ul class="pagination" id="pagination"></ul>
            </nav>
        </div>
    </form>

    <%@include file="../resource/template/js/js.jsp" %>
    <script>
        function clearSearch() {
            $('#courseCode').val('');
            $('#courseName').val('');
            $('#frmCourses').submit();
        }

        function editCourse(id) {
            window.location = `<%=request.getContextPath()%>/courses-update?id=` + id;
        }

        function deleteCourse(id) {
            let confirmed = confirm("Are you sure?");
            if (confirmed) {
                $(function () {
                    $.ajax({
                        url: `<%=request.getContextPath()%>/courses`,
                        contentType: 'application/json',
                        type: 'DELETE',
                        data: {
                            id: id,
                        },
                        success: function (data) {
                            console.log(data);
                            clearSearch();
                        },
                    });
                });

            }
        }
    </script>
    <script type="text/javascript">
        console.log(${model.currentPage});
        let currentPage = ${model.currentPage};
        let totalPage = ${model.totalPage};
        if (totalPage == 0) {
            totalPage = 1;
        }
        let visiblePages = 10;
        if (totalPage < visiblePages) {
            visiblePages = totalPage;
        }
        $(function () {
            window.pagObj = $('#pagination').twbsPagination({
                startPage: currentPage,
                totalPages: totalPage,
                visiblePages: visiblePages,
                onPageClick: function (event, page) {
                    if (${model.currentPage} !=
                    page
                )
                    {
                        $('#pageNumber').val(page);
                        $('#frmCourses').submit();
                    }
                }
            });
        });
    </script>
</div>
</body>

</html>


</body>
</html>
