package com.example.webik.jspquests.models;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Answer {

    private Integer id;
    private Integer question_id;
    private String answer_text;
    private Integer nextChapterId;
    private Integer next_question_id;
    private String description;
}