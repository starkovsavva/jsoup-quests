<%@ page contentType="text/html;charset=UTF-8" %>
<div class="header">
    <div class="logo">
        <img src="path/to/logo.png" alt="Логотип" />
    </div>
    <div class="account-info">
        <a href="login.jsp">Вход в аккаунт</a> |
        <span>Пользователей: <%= request.getAttribute("userCount") %></span>
    </div>
</div>
<style>
    .header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 20px;
        background-color: #4CAF50;
        color: white;
    }
    .logo img {
        max-height: 50px;
    }
    .account-info {
        font-size: 1.2em;
    }
</style>
