package com.example.webik.jspquests.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class Quest {

    private Integer id;
    private List<Chapter> chapters;
    private String title;
    private String description;
    private String imagePath;
    private String prologue;
}