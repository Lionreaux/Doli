package com.example.dolirapide2;

import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;

public class CreationNote extends Thread{
    private MainActivity app;
    private Api api;

    private String ip;
    private Object token;

    String idNote;
    String montant;
    String notePu;
    private String idU;

    public CreationNote(MainActivity app){
        this.app = app;
        ip = app.getIp();
        token = app.getToken();
    }
    @Override
    public void run() {
        super.run();
        Creation();
        modifNote();
    }


    public void Creation() {
        String ip = app.getIp();
        System.out.println(ip);

        idU = app.getIdU();

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
                byte[] input = ("{ \"fk_user_author\":\""+idU+"\", \"date_debut\":1663999400, \"date_fin\":1663599900 }").getBytes("utf-8");
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
    public void modifNote() {
        EditText montantE = app.findViewById(R.id.montant);
        montant = montantE.getText().toString();

        EditText noteE = app.findViewById(R.id.note);
        notePu = noteE.getText().toString();

        LocalDateTime DateMtn = LocalDateTime.now();

        LocalDateTime DateButoir ;



        idNote = app.getIdNote();
        HttpURLConnection urlConnection = null;
        Object token = app.getToken();
        try {
            URL url = new URL("http://"+ip+"/dolibarr/api/index.php/expensereports/"+idNote+"\n");
            urlConnection = (HttpURLConnection) url.openConnection();

            System.out.println("Début Thread : ");

            //paramètres de connexions
            urlConnection.setRequestMethod("PUT");
            urlConnection.setConnectTimeout(5000);
            urlConnection.setConnectTimeout(5000);
            urlConnection.setDoOutput(true);



            urlConnection.setRequestProperty("DOLAPIKEY", (String) token);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");

            try (OutputStream hein = urlConnection.getOutputStream()) {
                byte[] input = ("{\"total_ttc\":\""+montant+"\",\"note_public\":\""+notePu+"\" }").getBytes("utf-8");
                hein.write(input, 0, input.length);
            }

            BufferedReader repReq = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String contenuReq = "", ligneReq;
            ligneReq = repReq.readLine();
            while (ligneReq!=null) { // lecture ligne par ligne
                contenuReq += ligneReq + "\n";
                ligneReq = repReq.readLine();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            urlConnection.disconnect();
        }
    }

    public String getIdNote() {
        return idNote;
    }

    public void setIdNote(String idNote) {
        this.idNote = idNote;
    }
}
