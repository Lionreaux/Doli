package com.example.dolirapide2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Image {
    private MainActivity app;

    String idNote;


    public Image(MainActivity app) {

    }
    public void imageBase(MainActivity app) {
        String ip = app.getIp();
        System.out.println(ip);
        HttpURLConnection urlConnection = null;

        Object token = app.getToken();
        try {
            URL url = new URL("http://"+ip+"/dolibarr/api/index.php/expensereports\n");
            urlConnection = (HttpURLConnection) url.openConnection();

            System.out.println("Début Thread : ");

            //paramètres de connexions
            urlConnection.setRequestMethod("POST");
            urlConnection.setConnectTimeout(5000);
            urlConnection.setConnectTimeout(5000);
            urlConnection.setDoOutput(true);



            urlConnection.setRequestProperty("DOLAPIKEY", (String) token);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");


            try (OutputStream hein = urlConnection.getOutputStream()) {
                byte[] input = "{ \"fk_user_author\":\"1\", \"date_debut\":1663999400, \"date_fin\":1663599900 }".getBytes("utf-8");
                hein.write(input, 0, input.length);
            }

            BufferedReader repReq = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String contenuReq = "", ligneReq;
            ligneReq = repReq.readLine();
            while (ligneReq!=null) { // lecture ligne par ligne
                contenuReq += ligneReq + "\n";
                ligneReq = repReq.readLine();
            }
            System.out.println(contenuReq);
            idNote=contenuReq;
            app.setIdNote(idNote);

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            urlConnection.disconnect();
        }
    }
}
