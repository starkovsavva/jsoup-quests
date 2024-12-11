package com.example.webik.jspquests.service;

import com.example.webik.jspquests.models.Answer;
import com.example.webik.jspquests.models.Chapter;
import com.example.webik.jspquests.models.Question;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChapterParser {
    public List<Chapter> parseChapters(String filePath, int questId) throws IOException {
        List<Chapter> chapters = new ArrayList<>();
        Chapter.ChapterBuilder currentChapterBuilder = null;
        List<Question> currentQuestions = new ArrayList<>();
        Question.QuestionBuilder currentQuestionBuilder = null;
        List<Answer> currentAnswers = new ArrayList<>();


        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;


            while ((line = reader.readLine()) != null) {
                line = line.trim();
                Integer currentChapterId = 0;
                if (line.startsWith("CHAPTER:")) {
                    // Завершить текущую главу
                    if (currentChapterBuilder != null) {
                        if (currentQuestionBuilder != null) {
                            currentQuestions.add(currentQuestionBuilder.answers(new ArrayList<>(currentAnswers)).build());
                        }
                        chapters.add(currentChapterBuilder.questions(new ArrayList<>(currentQuestions)).build());
                        currentQuestions.clear();
                    }
                    String[] chapterParam = line.substring(9).trim().split("\\:");
                    int chapterNumber = Integer.parseInt(chapterParam[0].trim());
                    currentChapterId++;
                    currentChapterBuilder = Chapter.builder()
                            .id(currentChapterId)
                            .quest_id(questId)
                            .chapter_number(chapterNumber);
                } else if (line.startsWith("TITLE:")) {
                    currentChapterBuilder.title(line.substring(6).trim());
                } else if (line.startsWith("CONTENT:")) {
                    currentChapterBuilder.content(line.substring(8).trim());
                } else if (line.startsWith("IS_ENDING:")) {
                    currentChapterBuilder.isEnding(Boolean.parseBoolean(line.substring(10).trim()));
                } else if (line.startsWith("QUESTION:")) {
                    if (currentQuestionBuilder != null) {
                        currentQuestions.add(currentQuestionBuilder.answers(new ArrayList<>(currentAnswers)).build());
                    }

                    // Разделить строку на две части: текст вопроса и идентификатор
                    String[] parts = line.substring(9).trim().split("\\|");
                    String questionText = parts[0].trim();
                    Integer chapterId = Integer.parseInt(parts[1].trim().split("\\.")[0]);
                    Integer questionId = Integer.parseInt(parts[1].trim().split("\\.")[1]);

                    currentQuestionBuilder = Question.builder()
                            .id(questionId)
                            .chapter_id(chapterId)
                            .question_text(questionText);
                    currentAnswers.clear();
                } else if (line.startsWith("ANSWER:")) {
                    String[] parts = line.substring(7).split("\\|");
                    if (parts.length == 3) {
                        currentAnswers.add(Answer.builder()
                                .answer_text(parts[0].trim())
                                .nextChapterId(Integer.parseInt(parts[1].trim().split("\\.")[0]))
                                .next_question_id(Integer.parseInt(parts[1].trim().split("\\.")[1]))
                                .description(parts[2].trim())
                                .build());
                    }
                }
            }

            // Добавить последнюю главу
            if (currentChapterBuilder != null) {
                if (currentQuestionBuilder != null) {
                    currentQuestions.add(currentQuestionBuilder.answers(new ArrayList<>(currentAnswers)).build());
                }
                chapters.add(currentChapterBuilder.questions(new ArrayList<>(currentQuestions)).build());
            }
        }

        return chapters;
    }
}
