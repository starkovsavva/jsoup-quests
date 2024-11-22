<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Квест: ${chapter.title}</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            margin: 0;
            padding: 0;
            background: #f4f4f9;
            color: #333;
        }
        h1 {
            background: #333;
            color: #fff;
            margin: 0;
            padding: 15px 10px;
        }
        form {
            padding: 15px;
        }
        button {
            background: #28a745;
            color: #fff;
            border: none;
            padding: 10px 15px;
            cursor: pointer;
            border-radius: 5px;
        }
        button:hover {
            background: #218838;
        }
        .end-message {
            text-align: center;
            padding: 20px;
        }
    </style>
</head>
<body>
<h1>${chapter.title}</h1>
<p>${chapter.content}</p>

<c:choose>
    <c:when test="${not empty chapter.questions}">
        <form method="get" action="quest">
            <c:forEach var="question" items="${chapter.questions}">
                <h3>${question.text}</h3>
                <c:forEach var="answer" items="${question.answers}">
                    <label>
                        <input type="radio" name="chapterId" value="${answer.next_question_id}" required>
                            ${answer.answer_text} (${answer.description})
                    </label><br>
                </c:forEach>
            </c:forEach>
            <button type="submit">Далее</button>
        </form>
    </c:when>
    <c:otherwise>
        <div class="end-message">
            <p>Конец квеста. Спасибо за игру!</p>
        </div>
    </c:otherwise>
</c:choose>
</body>
</html>
