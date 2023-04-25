package com.example.dolirapide2;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PDFDownload {

    public static void main(String[] args) throws IOException {
        // URL du fichier PDF à télécharger
        String pdfUrl = "http://example.com/path/to/file.pdf";

        // Identifiants d'authentification pour l'API de Dolibarr
        String apiKey = "votre_api_key";
        String apiBaseUrl = "https://votre_instance_dolibarr.com/api/index.php/";

        // URL de l'API pour récupérer un token d'authentification
        String tokenUrl = apiBaseUrl + "authenticate";

        // Paramètres pour la requête POST d'authentification
        String authParams = "apikey=" + apiKey + "&login=votre_login&password=votre_mot_de_passe";

        // Connexion à l'API pour récupérer le token d'authentification
        URL url = new URL(tokenUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.getOutputStream().write(authParams.getBytes());

        // Lecture de la réponse JSON de l'API pour récupérer le token d'authentification
        InputStream input = conn.getInputStream();
        byte[] buffer = new byte[4096];
        int n = -1;
        StringBuilder responseBuilder = new StringBuilder();
        while ((n = input.read(buffer)) != -1) {
            responseBuilder.append(new String(buffer, 0, n));
        }
        String response = responseBuilder.toString();
        String authToken = response.split(":")[1].replace("\"", "").trim();

        // URL de l'API pour télécharger le fichier PDF
        String downloadUrl = apiBaseUrl + "pdf/document/" + authToken + "/" + pdfUrl;

        // Connexion à l'API pour télécharger le fichier PDF
        url = new URL(downloadUrl);
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        // Enregistrement du fichier PDF sur le disque dur
        input = conn.getInputStream();
        FileOutputStream output = new FileOutputStream("file.pdf");
        while ((n = input.read(buffer)) != -1) {
            output.write(buffer, 0, n);
        }
        output.close();
    }
}

