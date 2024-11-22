package com.example.webik.jspquests.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class Chapter {

    private Integer id;
    private Integer quest_id;
    private Integer chapter_number;
    private String content;
    private String title;
    private boolean isEnding; // Флаг, обозначающий финальную главу
    private List<Question> questions;

    public boolean hasQuestions() {
        return questions != null && !questions.isEmpty();
    }
}