package com.example.webik.jspquests.service;

import com.example.webik.jspquests.models.Answer;
import com.example.webik.jspquests.models.Chapter;
import com.example.webik.jspquests.models.Quest;
import com.example.webik.jspquests.models.Question;

import java.io.*;
import java.util.*;

public class QuestParser {

    private final ChapterParser chapterParser = new ChapterParser ();
    private final String bug = "C:/Users/sdf/IdeaProjects/jsp-quests/src/main/java/com/example/webik/jspquests/util/quest1.txt";
    public Quest parseQuestFile(String filePath, int questId) throws IOException {
        Quest.QuestBuilder questBuilder = Quest.builder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("QUEST:")) {
                    questBuilder.title(line.substring(6).trim());
                } else if (line.startsWith("DESCRIPTION:")) {
                    questBuilder.description(line.substring(12).trim());
                } else if (line.startsWith("PROLOGUE:")) {
                    questBuilder.prologue(line.substring(9).trim());
                }
            }
        }

        return questBuilder.id(questId).build();
    }
}
