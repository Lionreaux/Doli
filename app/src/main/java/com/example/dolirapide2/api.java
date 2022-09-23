package com.example.dolirapide2;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class api extends Thread {

    @Override
    public void run() {
        super.run();
        try {
            connexion();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void connexion() throws IOException {

        URL url = new URL("http://10.0.51.243/dolibarr/api/index.php/login?login=admin&password=123456789");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            System.out.println("OOEOEOEOEOE");

            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(5000);
            urlConnection.setConnectTimeout(5000);
            urlConnection.setDoOutput(true);
            BufferedReader buff = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String contenu = "", ligne;
            ligne = buff.readLine();
            while (ligne!=null) {
                contenu += ligne + "\n";
                ligne = buff.readLine();
            }
            System.out.println(contenu);


        } finally {
            urlConnection.disconnect();
        }
    }

}
