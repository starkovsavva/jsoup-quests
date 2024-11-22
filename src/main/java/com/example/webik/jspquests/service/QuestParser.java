package com.example.webik.jspquests.service;

import com.example.webik.jspquests.models.Chapter;
import com.example.webik.jspquests.models.Quest;
import com.example.webik.jspquests.models.Question;

import java.io.*;
import java.util.*;

public class QuestParser {
    public Quest parseQuestFile(String filePath, int questId) throws IOException {
        Quest.QuestBuilder questBuilder = Quest.builder();
        List<Chapter> chapters = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            Chapter.ChapterBuilder currentChapterBuilder = null;
            List<Question> currentQuestions = new ArrayList<>();
            int chapterNumber = 0;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("QUEST:")) {
                    questBuilder.title(line.substring(6).trim());
                } else if (line.startsWith("DESCRIPTION:")) {
                    questBuilder.description(line.substring(12).trim());
                } else if (line.startsWith("PROLOGUE:")) {
                    questBuilder.prologue(line.substring(9).trim());
                } else if (line.startsWith("CHAPTER:")) {
                    // Завершить текущую главу
                    if (currentChapterBuilder != null) {
                        Chapter chapter = currentChapterBuilder
                                .questions(new ArrayList<>(currentQuestions))
                                .build();
                        chapters.add(chapter);
                        currentQuestions.clear();
                    }
                    chapterNumber++;
                    currentChapterBuilder = Chapter.builder()
                            .id(chapters.size() + 1)
                            .quest_id(questId)
                            .chapter_number(chapterNumber);
                } else if (line.startsWith("TITLE:")) {
                    if (currentChapterBuilder != null) {
                        currentChapterBuilder.title(line.substring(6).trim());
                    }
                } else if (line.startsWith("CONTENT:")) {
                    if (currentChapterBuilder != null) {
                        currentChapterBuilder.content(line.substring(8).trim());
                    }
                } else if (line.startsWith("IS_ENDING:")) {
                    if (currentChapterBuilder != null) {
                        currentChapterBuilder.isEnding(Boolean.parseBoolean(line.substring(10).trim()));
                    }
                } else if (line.startsWith("QUESTION:")) {
                    currentQuestions.add(Question.builder()
                            .question_text(line.substring(9).trim())
                            .build());
                }
            }

            // Добавить последнюю главу
            if (currentChapterBuilder != null) {
                Chapter chapter = currentChapterBuilder
                        .questions(new ArrayList<>(currentQuestions))
                        .build();
                chapters.add(chapter);
            }
        }

        return questBuilder
                .id(questId)
                .chapters(chapters)
                .build();
    }
}

