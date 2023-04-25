package com.example.dolirapide2;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import androidx.core.content.FileProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

public class TelechargerPDF extends AsyncTask<String, Void, File> {

    private MainActivity app;
    private String ip;
    private String token;

    private String contenu;

    public TelechargerPDF(MainActivity app) {
        this.app = app;
        ip = app.getIp();
        token = app.getToken();
    }

    @Override
    protected File doInBackground(String... params) {
        String idNote = params[0];
        try {
            URL url = new URL("http://" + ip + "/dolibarr/api/index.php/documents/download?modulepart=expensereport&original_file=(PROV" + idNote + ")%2F(PROV" + idNote+").pdf");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("DOLAPIKEY", token);

            InputStream inputStream = conn.getInputStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            byte[] pdfData = outputStream.toByteArray();
            String json = new String(pdfData, StandardCharsets.UTF_8);
            System.out.println(json);

            JSONObject jsonObj = new JSONObject(json);
            System.out.println(jsonObj);
            contenu = jsonObj.getString("content");
            System.out.println(contenu);

            byte[] contentBytes = Base64.getDecoder().decode(contenu);



/**
            // Convertir le contenu en base64 en bytes
            byte[] decodedPdfData = Base64.getDecoder().decode(pdfData);

            File cacheDir = app.getCacheDir();
            System.out.println(cacheDir);

            // Créer un fichier temporaire pour le PDF
            File pdfFile = File.createTempFile("temp", ".pdf", cacheDir);

            // Écrire les données téléchargées dans le fichier temporaire
            FileOutputStream outputStream2 = new FileOutputStream(pdfFile);
            outputStream2.write(decodedPdfData);
            outputStream2.close();

            return pdfFile;*/
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

/**
    @Override
    protected void onPostExecute(File pdfFile) {
        if (pdfFile != null) {
            // Ouvrir le fichier temporaire avec l'application de visualisation de PDF de l'utilisateur
            Uri pdfUri = FileProvider.getUriForFile(app, app.getPackageName() + ".provider", pdfFile);
            Intent target = new Intent(Intent.ACTION_VIEW);
            target.setDataAndType(pdfUri, "application/pdf");
            target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            target.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            Intent intent = Intent.createChooser(target, "Ouvrir le PDF avec :");
            try {
                app.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                // Si aucune application de visualisation de PDF n'est installée, afficher un message d'erreur
                e.printStackTrace();
            }
        }
    }*/
}
