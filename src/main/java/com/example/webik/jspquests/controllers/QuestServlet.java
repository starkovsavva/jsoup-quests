package com.example.webik.jspquests.controllers;

import com.example.webik.jspquests.models.Chapter;
import com.example.webik.jspquests.models.Quest;
import com.example.webik.jspquests.service.ChapterService;
import com.example.webik.jspquests.service.QuestParser;
import com.example.webik.jspquests.service.QuestService;
import com.example.webik.jspquests.service.QuestStorage;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/quest")
public class QuestServlet extends HttpServlet {
    private final QuestService questService = QuestService.getInstance();
    private final ChapterService chapterService = new ChapterService();

    private final String bug = "C:/Users/sdf/IdeaProjects/jsp-quests/src/main/java/com/example/webik/jspquests/util/quest1.txt";
    @Override
    public void init() throws ServletException {
        try {
            // Загрузка квеста из текстового файла
            Quest quest = questService.loadQuest(bug);

            // Загрузка глав (если они читаются отдельно)
            List<Chapter> chapters = loadChaptersFromFile(bug);
            chapterService.addChapters(quest.getId(), chapters);
        } catch (IOException e) {
            throw new ServletException("Failed to load quest", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int questId = 1; // Пример: один квест
        int chapterNumber = Integer.parseInt(req.getParameter("chapterId"));

        Chapter chapter = chapterService.getChapter(questId, chapterNumber);

        req.setAttribute("chapter", chapter);
        req.getRequestDispatcher("/chapter.jsp").forward(req, resp);
    }

    private List<Chapter> loadChaptersFromFile(String filePath) throws IOException {
        // Реализуйте метод для чтения глав из файла
        return new ArrayList<>();
    }
}
