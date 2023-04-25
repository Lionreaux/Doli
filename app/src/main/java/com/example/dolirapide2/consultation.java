package com.example.dolirapide2;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;
/**
Cette classe va s'occuper de la vue de ma page de consultation et va gérer ce qu'il se passe lors d'un appui sur un de ses boutons.
 */
public class consultation {
    private MainActivity app;
    public consultation(MainActivity app) {
        this.app = app;
        app.setContentView(R.layout.consultations);


        ListeNote liste = new ListeNote(app);
        liste.start();


        Button btCreer = app.findViewById(R.id.buttoncrea);
        btCreer.setOnClickListener(new View.OnClickListener()

            {
                public void onClick(View v) {
                new Creation(app);
                List pages = app.getPages();
                pages.add("2");
                }



        });

        Button btTelecharger = app.findViewById(R.id.buttonPDF1);
        btTelecharger.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                EditText note1 = app.findViewById(R.id.note1);
                String idNote = note1.getText().toString();// Remplacez "1" par l'ID de la note que vous voulez télécharger
                TelechargerPDF telechargerPDF = new TelechargerPDF(app);
                telechargerPDF.execute(idNote);

            }
        });


    }
}
