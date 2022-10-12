package com.example.dolirapide2;

import android.view.View;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ListeNote extends Thread {

    private MainActivity app;
    private String ip;
    private String token;
    private String idNote;
    private String idDeNote;
    private String id;
    private String prix;
    private String note;



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
            System.out.println("http://"+ip+"/dolibarr/api/index.php/expensereports?sortfield=t.rowid&sortorder=DESC&limit=5&user_ids="+id+"&DOLAPIKEY="+token+"");
            //URL url = new URL("http://10.0.51.243/dolibarr/api/index.php/login?login=admin&password=123456789");
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
            EditText[] listeR = {app.findViewById(R.id.Note1), app.findViewById(R.id.Note2), app.findViewById(R.id.Note3), app.findViewById(R.id.Note4), app.findViewById(R.id.Note5)};

            for (int i = 0; i<5; i++) {
                try {
                    contenu = contenu.replace("\n","");
                    JSONArray jsonToken = new JSONArray(contenu);
                    JSONObject jsonObject = jsonToken.getJSONObject(i);
                    idDeNote = jsonObject.getString("id");
                    note = jsonObject.getString("note_public");
                    prix = jsonObject.getString("total_ttc");
                    System.out.println("Id de la note : "+idDeNote);
                    System.out.println("Description de la note : "+note);
                    System.out.println("Prix ttc de la note : "+prix+"€");
                    String recap = ("Id de la note : "+idDeNote+" | Description de la note : "+note+" | Prix ttc de la note : "+prix+"€");
                    app.setToken(idDeNote);


                    EditText champ = listeR[i];
                    champ.setText(recap);
                    System.out.println(champ);


                } catch (Exception err) {
                    System.out.println("Exception : "+err.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
