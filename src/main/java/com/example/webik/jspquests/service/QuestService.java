package com.example.webik.jspquests.service;

import com.example.webik.jspquests.models.Chapter;
import com.example.webik.jspquests.models.Quest;
import com.example.webik.jspquests.models.Question;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

import java.util.*;
import java.io.IOException;

public class QuestService {

    private static final QuestService INSTANCE = new QuestService();

    private final Map<Integer, Quest> quests = new HashMap<>();

    public QuestService() {}

    public static QuestService getInstance() {
        return INSTANCE;
    }

    public List<Quest> loadAllQuests(String folderPath) {
        File folder = new File(folderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            throw new IllegalArgumentException("Указанный путь не является папкой: " + folderPath);
        }

        File[] files = folder.listFiles((dir, name) -> name.endsWith(".txt")); // Считываем только .txt файлы
        if (files == null) {
            throw new IllegalStateException("Не удалось получить список файлов из папки: " + folderPath);
        }

        for (File file : files) {

            Quest quest = null; // Вызов существующей функции загрузки одного квеста
            try {
                quest = loadQuest(file.getAbsolutePath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (quest != null) {
                quests.put(quest.getId(), quest);
            }
        }

        return new ArrayList<>(quests.values()); // Возвращаем все квесты как список
    }
    // Метод для загрузки квеста из файла
    public Quest loadQuest(String filePath) throws IOException {
        // В данном случае мы читаем файл и возвращаем объект Quest
        ObjectMapper objectMapper = new ObjectMapper();
        QuestParser questFileReader = new QuestParser();

        Quest quest = questFileReader.parseQuestFile(filePath,1);
//        System.out.println(quest.getId());
//        System.out.println(quest.getDescription());
//        System.out.println(quest.getTitle());
//        System.out.println(quest.getImagePath());
//        System.out.println(quest.getPrologue());
//        System.out.println(questions.stream().findFirst().get().getId());
        // Добавляем квест в коллекцию
        addQuest(quest);

        return quest;
    }

    // Метод для добавления квеста
    public void addQuest(Quest quest) {
        if (quest != null) {
            quests.put(quest.getId(), quest);
        }
    }

    // Метод для получения квеста по ID
    public Quest getQuestById(int questId) {
        return quests.get(questId);
    }

    // Метод для получения всех квестов
    public List<Quest> getAllQuests() {
        return new ArrayList<>(quests.values());
    }
}
