<%@page contentType="text/html" pageEncoding="utf-8"%>
<div class="widget">
    <h1 class="widget-header">Najnowsze tematy</h1>
    <div class="widget-content table">
        <c:forEach var="topic" items="${widgets.lastTopics}">
            <div class="table-row widget-row">
                <div class="table-cell widget-avatar">
                    <img class="circle" src="${topic.user.getAvatar(38)}" alt="${topic.user.nick}" />
                </div>
                <div class="table-cell truncate widget-topic">
                    <a href="${topic.link}">${topic.title}</a><br />
                    ${topic.user.formattedNick}
                </div>
            </div>
        </c:forEach>
    </div>
</div>