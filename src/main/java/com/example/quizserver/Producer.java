package com.example.quizserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable{
    private ServerSocket serverSocket;
    private BlockingQueue<String> queue;

    public Producer(ServerSocket serverSocket, BlockingQueue<String> queue){
        this.serverSocket = serverSocket;
        this.queue = queue;
    }

    @Override
    public void run(){
        try{
            while(true){
                Socket clienSocket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader((clienSocket.getInputStream())));
                String messageFromClient;
                while((messageFromClient = in.readLine()) != null){
                    queue.put(messageFromClient);
                }
                in.close();
                clienSocket.close();
            }
        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
    }
}
