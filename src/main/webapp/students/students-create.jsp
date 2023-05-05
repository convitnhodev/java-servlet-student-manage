<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@include file="/common/taglib.jsp" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tạo Học Sinh</title>
    <%@include file="../resource/template/css/css.jsp" %>
</head>
<body>
<div class="container">
    <div>
        <h1>Tạo Học Sinh</h1>
    </div>
    <div>
        <a href="students">Danh sách học sinh</a>
    </div>
    <div>
        <form accept-charset="UTF-8" action="<c:url value="/students-create"/>"
              id="frmStudentsCreate" method="post">
            <div>
                <input type="text" name="studentCode" placeholder="studentCode" id="studentCode"/>
            </div>
            <div>
                <input type="text" name="name" placeholder="name" id="name"/>
            </div>
            <div>
                <input type="text" name="grade" placeholder="grade" id="grade"/>
            </div>
            <div>
                <input type="date" name="birthday" placeholder="birthday" id="birthday"/>
            </div>
            <div>
                <input type="address" name="address" placeholder="address" id="address"/>
            </div>
            <div>
                <input type="text" name="notes" placeholder="notes" id="notes"/>
            </div>
            <div>
                <input type="submit" value="Tạo"/>
            </div>
        </form>
    </div>

</div>
<%@include file="../resource/template/js/js.jsp" %>
</body>
</html>
