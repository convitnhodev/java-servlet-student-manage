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
        <h1>Danh sách học sinh</h1>
    </div>
    <div>
        <a href="students-create">
            Tạo học sinh
        </a>
    </div>
    <form action="<c:url value="/students"/>" id="frmStudents" method="get">
        <div>
            <input id="studentCode" name="studentCode" placeholder="studentCode"/>
            <input id="name" name="name" placeholder="name"/>
            <input id="grade" name="grade" placeholder="grade"/>
            <input type="submit" value="Tìm kiếm"/>
            <input type="button" onclick="clearSearch()" value="Xóa tìm"/>
        </div>
        <div>
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">Student Code</th>
                    <th scope="col">Name</th>
                    <th scope="col">Grade</th>
                    <th scope="col">Birthday</th>
                    <th scope="col">Address</th>
                    <th scope="col">Notes</th>
                    <th scope="col">Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${model.data}" var="student">
                    <tr>
                        <td>${student.studentCode}</td>
                        <td>${student.name}</td>
                        <td>${student.grade}</td>
                        <td>${student.birthday}</td>
                        <td>${student.address}</td>
                        <td>${student.notes}</td>
                        <td>

                            <input type="button" value="Edit" onclick="editStudent(${student.id})"/>
                            <input type="button" value="Delete" onclick="deleteStudent(${student.id})"/>
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
            $('#studentCode').val('');
            $('#name').val('');
            $('#grade').val('');
            $('#frmStudents').submit();
        }

        function editStudent(id) {
            window.location = `<%=request.getContextPath()%>/students-update?id=`+id;
        }

        function deleteStudent(id) {
            let confirmed = confirm("Are you sure?");
            if (confirmed) {
                $(function () {
                    $.ajax({
                        url: `<%=request.getContextPath()%>/students`,
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
                        $('#frmStudents').submit();
                    }
                }
            });
        });
    </script>
</div>
</body>

</html>
