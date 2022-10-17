package com.example.dolirapide2;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
classe qui se charge de prendre l'id de l'utilisateur et ses autres informations.
 */
public class UserId {
    private Login log;
    String token = null;
    private String idU;

    MainActivity app;
    public UserId(Login log) {
        this.log= log;
        app = log.getApp();
    }
    public void GetUserId() throws IOException {
        String mdp = log.getMdpTxt();
        String id = log.getIdTxt();
        String ip = log.getIpTxt();

        HttpURLConnection urlConnection = null;

        Object token = app.getToken();

        try {
            URL url = new URL("http://"+ip+"/dolibarr/api/index.php/users/login/"+id);
            //URL url = new URL("http://10.0.51.243/dolibarr/api/index.php/login?login=admin&password=123456789");
            urlConnection = (HttpURLConnection) url.openConnection();

            System.out.println("Début Thread : ");

            //paramètres de connexions
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(5000);
            urlConnection.setConnectTimeout(5000);

            urlConnection.setRequestProperty("DOLAPIKEY", (String) token);
            urlConnection.setRequestProperty("Accept", "application/json");


            BufferedReader buff = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String contenuIDU = "", ligne;
            ligne = buff.readLine();
            while (ligne!=null) { // lecture ligne par ligne
                contenuIDU += ligne + "\n";
                ligne = buff.readLine();
            }
            //System.out.println(contenu);
            //conversion JSON

            try {
                JSONObject jsonIDU = new JSONObject(contenuIDU);
                idU = jsonIDU.getString("id");
                System.out.println(jsonIDU);
                System.out.println(idU);
                app.setIdU(idU);


            } catch (JSONException err) {
                System.out.println("Exception : "+err.toString());
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
