package com.example.quizserver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class QuestionsProcessing {
    private static String questionsPath = "src/quizQuestions.txt";
    private static HashMap<String, String> questions = new HashMap<>();

    private static void processQuestions(){
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(questionsPath))){
            String line;
            while((line = bufferedReader.readLine()) != null){
                String[] questionAndAnswer = line.split("\\|");
                questions.put(questionAndAnswer[0], questionAndAnswer[1]);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static HashMap<String, String> getQuestions() {
        processQuestions();
        return questions;
    }
}
