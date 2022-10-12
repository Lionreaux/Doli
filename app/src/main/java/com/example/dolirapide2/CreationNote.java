package com.example.dolirapide2;

import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

/*
Cette classe est celle qui comprends tout les traitements/requetes que je fait qui sont en rapport avec la création d'une note de frais.
Elle fonctionne grace a un thread qui est différent de celui de l'affichage qui me permet d'éxécuter mes requetes.
La méthode Création va créer une note de frais basique avec les champs obligatoire et ainsi en récupérer l'id.
La méthode modifNote va elle modifier cette note de frais créée pour y rajouter un montant ttc et une note afin de décrire la nature de la note de frais.
La méthode EnvoiImage va elle s'occuper d'aller ouvrir notre gallerie en appelant la méthode de mon activité et va ensuite l'envoyer via une requete document vers la note choisie.
La méthode Date va elle me permettre de transformer mes dates du format "AAAA/MM/DD" vers un entier basé sur l'entier 0 = 1970/01/01 qui nous sera utile en paramètre.
 */
public class CreationNote extends Thread{
    private MainActivity app;
    private Api api;

    private String ip;
    private Object token;
    private long dateAuj;
    private String dateButStr;
    private Timestamp dateBut;
    private  String dateButoir;
    private String imageContenu;

    private String nomImage;

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
        //System.out.println(imageContenu);
        if (imageContenu != null) {EnvoiImage();}
        app.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new consultation(app);
            }
        });



    }


    public void Creation() {
        imageContenu = app.getImageContenu();
        String ip = app.getIp();
        //System.out.println(ip);

        idU = app.getIdU();

        HttpURLConnection urlConnection = null;
        Date();

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
                byte[] input = ("{ \"fk_user_author\":\""+idU+"\", \"date_debut\":"+dateAuj+", \"date_fin\":"+dateButoir+" }").getBytes("utf-8");
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
            //prendre l'id ici et le lié a ma page consultation pour qu'il en affiche les données correspondantes.

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




        idNote = app.getIdNote();
        idNote = idNote.replace("\n", "");
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
    public void Date() {
        dateAuj = Instant.now().getEpochSecond();

        EditText dateBu = app.findViewById(R.id.dateBut);
        dateButStr = dateBu.getText().toString().replace("/", "-") + " 00:00:01";
        dateBut = Timestamp.valueOf(dateButStr);
        dateButoir = String.valueOf(dateBut.getTime()/1000);

    }
    public void EnvoiImage(){
        EditText nomIm = app.findViewById(R.id.nomImage);
        nomImage = nomIm.getText().toString();


        HttpURLConnection urlConnection = null;
        Object token = app.getToken();

        imageContenu = app.getImageContenu();
        try {
            URL url = new URL("http://10.0.52.185/dolibarr/api/index.php/documents/upload");
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
                byte[] input = ("{\"filename\": \""+nomImage+".png\", \"modulepart\": \"expensereport\", \"ref\": \"(PROV"+idNote+")\", \"subdir\": \"\", \"filecontent\": \""+imageContenu+"\", \"fileencoding\": \"base64\", \"overwriteifexists\": \"0\"}").getBytes("utf-8");
                hein.write(input, 0, input.length);
            }
            BufferedReader repReq = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

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
