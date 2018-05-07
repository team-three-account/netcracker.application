<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<body>
<head>
    <title>Add folder to note</title>
</head>
<form:form method="post" modelAttribute="newNoteIntoFolder">
    <div class="form-group">
        <label>Folders: </label>
        <form:select path="folder" class="form-control">
            <form:options items="${listFolders}" itemValue="folderId" itemLabel="name"/>
        </form:select>
        <form:errors path="folder" cssClass="error"/>
    </div>
    <button id="add" class="btn btn-success text-center">Add to folder</button>
</form:form>
</body>
</html>
