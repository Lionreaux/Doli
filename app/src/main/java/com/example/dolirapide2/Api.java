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
    String token = null;

    MainActivity app;

    public Api(Login log) {
        this.log= log;
        app = log.getApp();
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
        System.out.println(mdp);
        System.out.println(ip);
        HttpURLConnection urlConnection = null;


        try {
            System.out.println("http://"+ip+"/dolibarr/api/index.php/login?login="+id+"&password="+mdp);
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
            //System.out.println(contenu);
            //conversion JSON

            try {
                JSONObject jsonToken = new JSONObject(contenu);
                jsonToken = (JSONObject) jsonToken.getJSONObject("success");
                token = jsonToken.getString("token");
                System.out.println(jsonToken);
                System.out.println(token);
                app.setToken(token);


            } catch (JSONException err) {
                System.out.println("Exception : "+err.toString());
            }
        } finally {
            urlConnection.disconnect();
        }
        

    }



    public Object getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
