package com.company;

public class Buffer {

    private String message = "";

    public synchronized void put(String newMessage){
        if (!message.equals("")){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        message = newMessage;
        notifyAll();
    }
    public synchronized String take(){
        if(message.equals("")){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //System.out.println("taking " + message);
        String msCopy = message;
        message = "";
        notifyAll();
        return msCopy;
    }
}
