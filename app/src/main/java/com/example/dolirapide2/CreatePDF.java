package com.example.dolirapide2;

import android.os.Environment;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CreatePDF {
    public static void main(String[] args) {
        try {
            File downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

            System.out.println(downloadDir);

            String nomFichier = "/exemple.pdf";

            String chemin = downloadDir + nomFichier;

            System.out.println(Paths.get(chemin));
            if (Files.exists(Paths.get(downloadDir + nomFichier))) {
                // Ouvrir le fichier PDF
                PdfReader reader = new PdfReader(downloadDir + nomFichier);
                // Créer un écrivain pour écrire dans le document existant
                PdfStamper stamper = new PdfStamper(reader, Files.newOutputStream(Paths.get(downloadDir + nomFichier)));
                // Remplacer le contenu du document existant par un nouveau texte
                stamper.getOverContent(1).showText("Voici le nouveau texte inséré !");
                // Fermer l'écrivain
                stamper.close();
                // Fermer le lecteur
                reader.close();
            }
            else {
                // Création du document PDF
                Document document = new Document();
                // Création d'un écrivain pour écrire dans le document
                PdfWriter.getInstance(document, Files.newOutputStream(Paths.get(downloadDir +nomFichier)));
                // Ouverture du document
                document.open();
                // Ajout d'un paragraphe au document
                document.add(new Paragraph("Bonjour, ceci est un exemple de document PDF généré avec iTexttttt !"));
                // Fermeture du document
                document.close();
                System.out.println("Le document a été créé avec succès !");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
