package com.example.dolirapide2;

import java.net.HttpURLConnection;

public class ListeNote extends Thread {

    private MainActivity app;
    private String ip;
    private Object token;




    public ListeNote(MainActivity app) {
        this.app = app;
        ip = app.getIp();
        token = app.getToken();
    }
    @Override
    public void run() {
        super.run();
        Liste();
    }

    public void Liste() {

        HttpURLConnection urlConnection = null;
    }
}
