<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/include/head.jsp" %>
        <script src="/ckeditor/ckeditor.js"></script>
    </head>
    <body>
        <%@ include file="/WEB-INF/include/menu.jsp" %>
        <br />
        <div id="main">
            <form method="post" action="/forum/${forum.url}/add">
                <div class="row">
                    <div class="input-field col s10">
                        <input placeholder="Tytuł" name="title" type="text">
                        <label>Tytuł</label>
                    </div>
                    <div class="input-field col s2">
                        <button type="submit" class="btn waves-effect waves-light">
                            Wyślij <i class="material-icons right">send</i> 
                        </button>
                    </div>
                </div>
                <textarea name="text" id="editor"></textarea>
                <script>CKEDITOR.replace('editor');</script>
            </form>
        </div>
    </body>
</html>
