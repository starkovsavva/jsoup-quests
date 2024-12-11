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
import java.util.Map;

@WebServlet("/quest")
public class StartQuestServlet extends HttpServlet {
    private final ChapterService chapterService = new ChapterService();
    private final QuestService questService = new QuestService();

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
        String questionIdParam = request.getParameter("questionId");
        int chapterId = chapterIdParam != null ? Integer.parseInt(chapterIdParam) : 1;
        int questionId = questionIdParam != null ? Integer.parseInt(questionIdParam): 1;


        Chapter chapterreq = null;
        // Загрузка глав
        Map<Integer, Chapter> chapterToSend = chapterService.getChapterAndQuestById(chapterId, questionId); // QuestId = 1
        for (Map.Entry<Integer,Chapter> entry : chapterToSend.entrySet()){

            questionId = entry.getKey();
            chapterreq = entry.getValue();
        }

        if (chapterToSend == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Chapter not found");
            return;
        }
        chapterService.setCurrentChapter(chapterreq);
        // Установка атрибутов для JSP
        request.setAttribute("chapter", chapterreq);
        request.setAttribute("chapterId",chapterreq.getId());
        request.setAttribute("questionId",questionId);


        // Переход к JSP
        request.getRequestDispatcher("/quest.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Получение параметра
        String chapterIdParam = request.getParameter("chapterId");
        String questionIdParam = request.getParameter("questionId");

        if (questionIdParam == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No answer selected");
            return;
        }

        Chapter chapterToSend = null;
        Integer questionIdToSend = null;
        try {
            int chapterId = Integer.parseInt(chapterIdParam);
            int questionId = Integer.parseInt(questionIdParam);
            // Здесь обработка следующей главы


            Map<Integer,Chapter> chapterMap = chapterService.getChapterAndQuestById(chapterId,questionId);
            for (Map.Entry<Integer,Chapter> entry : chapterMap.entrySet()){
                chapterToSend = entry.getValue();
                questionIdToSend = entry.getKey();
            }

            // Установить следующую главу в атрибутах
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid answer ID");
        }
        if(chapterToSend != null  && questionIdToSend != null){
            chapterService.setCurrentChapter(chapterToSend);
            request.setAttribute("chapter", chapterToSend);
            request.setAttribute("questionId", questionIdToSend);
            request.getRequestDispatcher("/quest.jsp").forward(request, response);

        }
        else{
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Chapter!");
        }

    }

}
