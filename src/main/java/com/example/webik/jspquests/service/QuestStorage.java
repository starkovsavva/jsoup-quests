package com.example.webik.jspquests.service;

import com.example.webik.jspquests.models.Answer;
import com.example.webik.jspquests.models.Chapter;
import com.example.webik.jspquests.models.Question;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestStorage {

    private static final Map<Integer, Chapter> chapters = new HashMap<>();

    static {
        // Пример квеста
        chapters.put(1, Chapter.builder()
                .id(1)
                .title("Начало квеста")
                .content("Вы стоите на развилке. Куда пойдете?")
                .questions(List.of(
                        Question.builder()
                                .id(1)
                                .question_text("Куда вы пойдете?")
                                .answers(List.of(
                                        Answer.builder()
                                                .id(1)
                                                .question_id(1)
                                                .answer_text("Направо")
                                                .next_question_id(2)
                                                .description("Вы направились в лес.")
                                                .build(),
                                        Answer.builder()
                                                .id(2)
                                                .question_id(1)
                                                .answer_text("Налево")
                                                .next_question_id(3)
                                                .description("Вы пошли к реке.")
                                                .build()
                                ))
                                .build()
                ))
                .build());

        chapters.put(2, Chapter.builder()
                .id(2)
                .title("Лес")
                .content("Вы в лесу. Здесь темно и страшно.")
                .questions(Collections.emptyList()) // Нет вопросов, это конец
                .build());

        chapters.put(3, Chapter.builder()
                .id(3)
                .title("Река")
                .content("Вы подошли к реке. Здесь спокойно.")
                .questions(Collections.emptyList()) // Нет вопросов, это конец
                .build());
    }

    public static Chapter getChapterById(int id) {
        return chapters.get(id);
    }
}
