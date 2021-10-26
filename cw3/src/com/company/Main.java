package com.company;

public class Main {

	private static int THREADS_NUMB = 4;

    public static void main(String[] args) {

    	Buffer buffer = new Buffer();
    	Producer[] producers = new Producer[THREADS_NUMB];
    	Consumer[] consumers = new Consumer[THREADS_NUMB];

    	for(int i =0; i<THREADS_NUMB; i++){
    		producers[i] = new Producer(buffer);
    		producers[i].start();
    		consumers[i] = new Consumer(buffer);
    		consumers[i].start();
			try {
				producers[i].join();
				consumers[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

    }
}
