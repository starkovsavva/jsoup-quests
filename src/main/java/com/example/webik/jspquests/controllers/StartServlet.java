package com.example.webik.jspquests.controllers;

import com.example.webik.jspquests.models.Quest;

import com.example.webik.jspquests.service.QuestService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/start")
public class StartServlet extends HttpServlet {

    QuestService questService = new QuestService();
    List<Quest> quests = new ArrayList<>();
    @Override
    public void init(){
        quests = questService.loadAllQuests("C:/Users//sdf/IdeaProjects/jsp-quests/src/main/java/com/example/webik/jspquests/util");
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Генерация списка квестов (в реальном приложении это может быть запрос из базы данных)
        // Передача списка квестов в JSP
        request.setAttribute("quests", quests);
//        request.setAttribute("chapter", currentChapter);

        // Переход к JSP для отображения списка
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}

