package com.example.dolirapide2;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
Cette classe est celle qui comprends tout les traitements/requetes que je fait qui sont en rapport avec la liste des notes de frais.
Elle fonctionne grace a un thread qui est différent de celui de l'affichage qui me permet d'éxécuter mes requetes.
La méthode Liste va lister mes notes de frais en notant les parametres de ma note de frais.
 */

public class ListeNote extends Thread {

        private MainActivity app;
        private String ip;
        private String token;
    private String idNote;
    private String idDeNote;
    private String id;
    private String prix;
    private String note;
    private Integer j = 0;



    public ListeNote(MainActivity app) {
        this.app = app;
        ip = app.getIp();
        token = app.getToken();
        idNote = app.getIdNote();
        id = app.getIdU();
    }
    @Override
    public void run() {
        super.run();
        Liste();
    }

    public void Liste() {

        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL("http://"+ip+"/dolibarr/api/index.php/expensereports?sortfield=t.rowid&sortorder=DESC&limit=5&user_ids="+id+"&DOLAPIKEY="+token+"");
            urlConnection = (HttpURLConnection) url.openConnection();

            System.out.println("Début Thread : ");

            //paramètres de connexions
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(5000);
            urlConnection.setConnectTimeout(5000);

            BufferedReader buff = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String contenu = "", ligne;
            ligne = buff.readLine();
            while (ligne!=null) { // lecture ligne par ligne
                contenu += ligne + "\n";
                ligne = buff.readLine();
            }

            System.out.println(contenu);
            EditText[] listeR = {app.findViewById(R.id.note1),app.findViewById(R.id.note2),app.findViewById(R.id.note3), app.findViewById(R.id.note4), app.findViewById(R.id.note5), app.findViewById(R.id.note6), app.findViewById(R.id.note7), app.findViewById(R.id.note8), app.findViewById(R.id.note9), app.findViewById(R.id.note10), app.findViewById(R.id.note11), app.findViewById(R.id.note12), app.findViewById(R.id.note13), app.findViewById(R.id.note14), app.findViewById(R.id.note15)};

            for (int i = 0; i<15; i=i+3) {
                try {
                    if (note == null){note="Aucune description";}

                    contenu = contenu.replace("\n","");
                    JSONArray jsonToken = new JSONArray(contenu);
                    JSONObject jsonObject = jsonToken.getJSONObject(j);
                    idDeNote = jsonObject.getString("id");
                    note = jsonObject.getString("note_public");
                    prix = jsonObject.getString("total_ttc");
                    System.out.println("Id de la note : "+idDeNote);
                    System.out.println("Description de la note : "+note);
                    System.out.println("Prix ttc de la note : "+prix+"€");
                    String recap = ("Id de la note : "+idDeNote+" | Description de la note : "+note+" | Prix ttc de la note : "+prix+"€");

                    prix = prix + ":";
                    EditText champ;

                    champ = listeR[i];
                    champ.setText(idDeNote);
                    champ = listeR[i+1];
                    champ.setText(note);
                    champ = listeR[i+2];
                    champ.setText(prix.replace("000000:","€"));
                    System.out.println(champ);
                    j=j+1;


                } catch (Exception err) {
                    System.out.println("Exception : "+err.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
