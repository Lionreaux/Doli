package com.example.dolirapide2;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class Api extends Thread {

    private Login log;
    HttpURLConnection urlConnection = null;
    Object token = null;

    public Api(Login log) {
        this.log= log;
    }

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

       String mdp = log.getMdpTxt();
        String id = log.getIdTxt();
        String ip = log.getIpTxt();
        System.out.println(id);



        try {
            URL url = new URL("http://"+ip+"/dolibarr/api/index.php/login?login="+id+"&password="+mdp);
            //URL url = new URL("http://10.0.51.243/dolibarr/api/index.php/login?login=admin&password=123456789");
            urlConnection = (HttpURLConnection) url.openConnection();

            System.out.println("Début Thread : ");

            //paramètres de connexions
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(5000);
            urlConnection.setConnectTimeout(5000);
            urlConnection.setDoOutput(true);

            BufferedReader buff = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String contenu = "", ligne;
            ligne = buff.readLine();
            while (ligne!=null) { // lecture ligne par ligne
                contenu += ligne + "\n";
                ligne = buff.readLine();
            }
            System.out.println(contenu);
            //conversion JSON

            try {
                JSONObject jsonToken = new JSONObject(contenu);
                jsonToken = (JSONObject) jsonToken.getJSONObject("success");
                token = jsonToken.get("token");
                System.out.println(jsonToken);
                System.out.println(token);


            } catch (JSONException err) {
                System.out.println("Exception : "+err.toString());
            }
        } finally {
            urlConnection.disconnect();
        }

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
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
        urlConnection.disconnect();
        }

    }




}
