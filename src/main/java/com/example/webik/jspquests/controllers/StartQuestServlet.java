package com.example.webik.jspquests.controllers;

import com.example.webik.jspquests.models.Answer;
import com.example.webik.jspquests.models.Chapter;
import com.example.webik.jspquests.models.Quest;

import com.example.webik.jspquests.models.Question;
import com.example.webik.jspquests.service.ChapterParser;
import com.example.webik.jspquests.service.ChapterService;
import com.example.webik.jspquests.service.QuestService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@WebServlet("/quest")
public class StartQuestServlet extends HttpServlet {
    private final ChapterService chapterService = new ChapterService();

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            // Загрузка всех глав в ChapterService при инициализации
            chapterService.loadChapters(1, "C:/Users/sdf/IdeaProjects/jsp-quests/src/main/java/com/example/webik/jspquests/util/quest1.txt");
        } catch (IOException e) {
            throw new ServletException("Error loading quest data", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Получение параметров
        String chapterIdParam = request.getParameter("chapterId");
        String questid = request.getParameter("questId");
        int chapterId = chapterIdParam != null ? Integer.parseInt(chapterIdParam) : 1;

        // Загрузка глав
        List<Chapter> chapters = chapterService.getQuestById(chapterId,questid); // QuestId = 1
        Chapter currentChapter = chapters.stream()
                .filter(ch -> ch.getId() == chapterId)
                .findFirst()
                .orElse(null);


        if (currentChapter == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Chapter not found");
            return;
        }
        chapterService.setCurrentChapter(currentChapter);
        // Установка атрибутов для JSP
        request.setAttribute("chapter", currentChapter);
        request.setAttribute("chapterId",currentChapter.getId());


        // Переход к JSP
        request.getRequestDispatcher("/quest.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Получение параметра
        String nextQuestionIdParam = request.getParameter("next_question_id");
        System.out.println("Parameter next_question_id: " + request.getParameter("next_question_id"));

        if (nextQuestionIdParam == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No answer selected");
            return;
        }

        try {
            int nextQuestionId = Integer.parseInt(nextQuestionIdParam);
            // Здесь обработка следующей главы
            Question nextChapter = chapterService.getQuestById(nextQuestionId);
            if (nextChapter == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Next chapter not found");
                return;
            }

            // Установить следующую главу в атрибутах
            request.setAttribute("chapter", nextChapter);
            request.getRequestDispatcher("/quest.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid answer ID");
        }
    }

}
