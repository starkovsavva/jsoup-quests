package com.example.webik.jspquests.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class Question {

    private Integer id;
    private String question_text;
    private Integer chapter_id;
    private List<Answer> answers; // Список вариантов ответов
}