<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@include file="/common/taglib.jsp" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cập nhật môn học</title>
    <%@include file="../resource/template/css/css.jsp" %>
</head>
<body>
<div class="container">
    <div>
        <h1>Cập nhật môn học</h1>
    </div>
    <div>
        <a href="courses">Danh sách môn học</a>
    </div>
    <div>
        <form accept-charset="UTF-8" action="<c:url value="/courses-update"/>"
              id="frmCoursesUpdate" method="post">
            <div>
                <input type="text" readonly name="id" value="${model.id}"
                       placeholder="id" id="id"/>
            </div>
            <div>
                <input type="text" name="courseCode" value="${model.courseCode}"
                       placeholder="courseCode" id="courseCode"/>
            </div>
            <div>
                <input type="text" name="name"
                       value="${model.name}"
                       placeholder="name" id="name"/>
            </div>
            <div>
                <input type="text" name="lecture"
                       value="${model.lecture}"
                       placeholder="lecture" id="lecture"/>
            </div>
            <div>
                <input type="date" name="year"
                       value="${model.year}"
                       placeholder="year" id="year"/>
            </div>
            <div>
                <input type="address" name="notes"
                       value="${model.notes}"
                       placeholder="notes" id="notes"/>
            </div>
            <div>
                <input type="submit" value="Cập nhật"/>
            </div>
        </form>
    </div>

</div>
<%@include file="../resource/template/js/js.jsp" %>
</body>
</html>
