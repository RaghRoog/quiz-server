package com.example.quizserver;

import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class QuizLogic {
    private static Random random = new Random();

    private static HashMap<String, String> questionsAndAnswers = QuestionsProcessing.getQuestions();
    private static ArrayList<String> questions = new ArrayList<>(questionsAndAnswers.keySet());
    private static String currentQuestion;

    public static void drawQuestion(TextArea textArea){
        int randIndex = random.nextInt(questions.size());
        currentQuestion = questions.get(randIndex);
        questions.remove(randIndex);
        textArea.appendText(currentQuestion + "\n");
    }

    public static boolean checkAnswer(String dataFromClient, TextArea textArea){
        String correctAnswer = questionsAndAnswers.get(currentQuestion);
        String[] splitedData = dataFromClient.split("\\|"); //0 - nick, 1 - answer
        if(splitedData[1].toLowerCase().equals(correctAnswer.toLowerCase())){
            textArea.appendText(splitedData[0] + " odpowiedział poprawnie!\n");
            return true;
        }else{
            textArea.appendText("Dotarła niepoprawna odpowiedź.\n");
            return false;
        }
    }

    public static boolean isQuestionsEmpty(){
        return questions.isEmpty();
    }
}
