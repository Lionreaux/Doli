package com.example.dolirapide2;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Api extends Thread {

    private Login log;

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
        String urlEd = log.getUrlTxt();
        System.out.println(id);
        URL url = new URL("http://10.0.51.243/dolibarr/api/index.php/login?login="+id+"&password="+mdp);
        //URL url = new URL("http://10.0.51.243/dolibarr/api/index.php/login?login=admin&password=123456789");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
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
                Object token = jsonToken.get("token");
                System.out.println(jsonToken.toString());
                System.out.println(token.toString());

            } catch (JSONException err) {
                System.out.println("Exception : "+err.toString());
            }

        } finally {
            urlConnection.disconnect();
        }
        URL urlLogin = new URL("http://10.0.51.243/dolibarr/api/index.php/expensereports/HTTP_DOLAPIKEY=33118fe8393af9343d072f7a8db4c84c4e456a8f\n" );
        HttpURLConnection urlLog = (HttpURLConnection) urlLogin.openConnection();
        try {
            urlLog.setRequestMethod("GET");
            urlLog.setConnectTimeout(5000);
            urlLog.setConnectTimeout(5000);
            urlLog.setDoOutput(true);
            BufferedReader buff = new BufferedReader(new InputStreamReader(urlLog.getInputStream()));
            String log = "", ligne;
            ligne = buff.readLine();
            while (ligne!=null) { // lecture ligne par ligne
                log += ligne + "\n";
                ligne = buff.readLine();
            }
            System.out.println(log);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            urlLog.disconnect();
        }
    }


}
