<%@ page import="com.example.webik.jspquests.models.Quest" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<div class="quests">
    <h2>Доступные квесты</h2>
    <ul class="quest-list">
        <%
            List<Quest> quests = (List<Quest>) request.getAttribute("quests");
            if (quests != null) {
                for (Quest quest : quests) {
        %>
        <li class="quest-item">
            <img src="<%= quest.getImagePath() %>" alt="<%= quest.getTitle() %> изображение" class="quest-image" />
            <div class="quest-details">
                <h3><%= quest.getTitle() %></h3>
                <p><%= quest.getDescription() %></p>
                <a href="quest.jsp" class="start-button">Начать квест</a>
            </div>
        </li>
        <%
                }
            }
        %>
    </ul>
</div>
<style>
    .quests {
        padding: 20px;
        background-color: #f9f9f9;
    }
    .quest-list {
        list-style: none;
        padding: 0;
    }
    .quest-item {
        display: flex;
        align-items: center;
        margin: 15px 0;
        padding: 10px;
        border: 1px solid #ddd;
        border-radius: 8px;
    }
    .quest-image {
        width: 100px;
        height: 100px;
        margin-right: 20px;
        border-radius: 8px;
    }
    .quest-details {
        flex-grow: 1;
    }
    .start-button {
        display: inline-block;
        padding: 10px 20px;
        background-color: #4CAF50;
        color: white;
        text-decoration: none;
        border-radius: 5px;
        transition: background-color 0.3s;
    }
    .start-button:hover {
        background-color: #45a049;
    }
</style>
