package com.example.dolirapide2;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;

import androidx.core.content.FileProvider;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

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
import java.nio.file.Files;
import java.nio.file.Paths;
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

            File downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

            System.out.println(downloadDir);

            String nomFichier = "/Note"+idNote+".pdf";

            // Créez un nouveau document PDF
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, Files.newOutputStream(Paths.get(downloadDir+nomFichier)));
            document.open();

            // Ajoutez le contenu du fichier PDF décodé au nouveau document PDF
            PdfReader reader = new PdfReader(contentBytes);
            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                PdfImportedPage page = writer.getImportedPage(reader, i);
                writer.getDirectContent().addTemplate(page, 0, 0);
            }

            // Fermez le nouveau document PDF et le lecteur PDF
            document.close();
            reader.close();

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
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
        return null;
    }



}
