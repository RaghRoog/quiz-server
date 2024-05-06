package com.example.quizserver;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class QuizServer extends Application {
    Scene scene;
    TextArea textArea;
    ServerSocket serverSocket;
    private BlockingQueue<String> answerQueue = new LinkedBlockingQueue<>();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(QuizServer.class.getResource("server-view.fxml"));
        scene = new Scene(fxmlLoader.load(), 700, 350);
        stage.setTitle("Quiz server");
        stage.setScene(scene);
        stage.setOnCloseRequest(e -> {
            stopServer();
        });
        stage.show();

        textArea = (TextArea) scene.lookup("#textArea");
        serverSocket = createServerSocket(999);

        QuizLogic.drawQuestion(textArea);

        Producer producer = new Producer(serverSocket, answerQueue);
        Thread producerThread = new Thread(producer);
        producerThread.start();

        Consumer consumer = new Consumer(textArea, answerQueue, serverSocket);
        Thread consumerThread = new Thread(consumer);
        consumerThread.start();
    }

    private ServerSocket createServerSocket(int socket){
        try{
            return new ServerSocket(socket);
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }

    private void stopServer(){
        try{
            serverSocket.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}

