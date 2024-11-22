package com.example.webik.jspquests.service;

import com.example.webik.jspquests.models.Chapter;

import java.util.*;

public class ChapterService {
    private final Map<Integer, List<Chapter>> chaptersByQuestId = new HashMap<>();

    public void addChapters(int questId, List<Chapter> chapters) {
        chaptersByQuestId.put(questId, chapters);
    }

    public List<Chapter> getChaptersByQuestId(int questId) {
        return chaptersByQuestId.getOrDefault(questId, Collections.emptyList());
    }

    public Chapter getChapter(int questId, int chapterNumber) {
        return getChaptersByQuestId(questId).stream()
                .filter(chapter -> chapter.getChapter_number() == chapterNumber)
                .findFirst()
                .orElse(null);
    }
}
