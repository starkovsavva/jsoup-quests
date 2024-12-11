package com.example.webik.jspquests.service;

import com.example.webik.jspquests.models.Chapter;
import com.example.webik.jspquests.models.Quest;
import com.example.webik.jspquests.models.Question;

import java.io.IOException;
import java.util.*;

public class ChapterService {
    private final Map<Integer, List<Chapter>> chaptersByQuestId = new HashMap<>();
    private final ChapterParser chapterParser = new ChapterParser();
    private Chapter currentChapter;

    public Chapter getCurrentChapter() {
        return currentChapter;
    }

    public void setCurrentChapter(Chapter chapter) {
        this.currentChapter = chapter;
    }

    public void addChapters(int questId, List<Chapter> chapters) {
        chaptersByQuestId.put(questId, chapters);
    }

    public List<Chapter> getChaptersByQuestId(int questId) {
        return chaptersByQuestId.getOrDefault(questId, Collections.emptyList());
    }

    public void loadChapters(int questId, String filePath) throws IOException {
        List<Chapter> chapters = chapterParser.parseChapters(filePath, questId);

        chaptersByQuestId.put(questId, chapters);

    }

    public Chapter getChapterById(int chapterId) {
        return chaptersByQuestId.values().stream()
                .flatMap(List::stream)
                .filter(chapter -> chapter.getId() == chapterId)
                .findFirst()
                .orElse(null);
    }

    public Map<Integer,Chapter> getChapterAndQuestById(int questId, int questionId) {
        // Получаем текущую главу
        Chapter questchapter = getChapterById(questId);
        Optional<Question> chapterOptional = questchapter.getQuestions().stream()
                .filter( question -> question.getId() == questionId)
                .findFirst();

        if(chapterOptional.isPresent()){
            Map<Integer,Chapter> result = new HashMap<>();
            result.put(questionId,questchapter);
            return result;
        }
        return null;
    }
    public Chapter getChapter(int questId, int chapterNumber) {
        return getChaptersByQuestId(questId).stream()
                .filter(chapter -> chapter.getChapter_number() == chapterNumber)
                .findFirst()
                .orElse(null);
    }

//    public Chapter getQuestionById(int nextQuestionId) {
//        return
//    }


}