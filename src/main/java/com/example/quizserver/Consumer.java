package com.example.quizserver;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable{
    private TextArea textArea;
    private BlockingQueue<String> queue;
    private ServerSocket serverSocket;

    public Consumer(TextArea textArea, BlockingQueue<String> queue, ServerSocket serverSocket){
        this.textArea = textArea;
        this.queue = queue;
        this.serverSocket = serverSocket;
    }

    @Override
    public void run(){
        try{
            while(true){
                String answer = queue.take();
                if(QuizLogic.checkAnswer(answer, textArea)){
                    if(!QuizLogic.isQuestionsEmpty()){
                        QuizLogic.drawQuestion(textArea);
                    }else{
                        textArea.appendText("Udzielono odpowiedzi na wszystkie pytania. Koniec Quizu.");
                        serverSocket.close();
                    }
                }
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
