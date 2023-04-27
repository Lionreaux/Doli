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

        Button btTelecharger1 = app.findViewById(R.id.buttonPDF1);
        btTelecharger1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                EditText note1 = app.findViewById(R.id.note1);
                String idNote = note1.getText().toString();
                //CreatePDF createPDF = new CreatePDF();
                //CreatePDF.main(null);
                TelechargerPDF telechargerPDF = new TelechargerPDF(app);
                telechargerPDF.execute(idNote);

            }
        });
        Button btTelecharger2 = app.findViewById(R.id.buttonPDF2);
        btTelecharger2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                EditText note1 = app.findViewById(R.id.note4);
                String idNote = note1.getText().toString();
                //CreatePDF createPDF = new CreatePDF();
                //CreatePDF.main(null);
                TelechargerPDF telechargerPDF = new TelechargerPDF(app);
                telechargerPDF.execute(idNote);

            }
        });

        Button btTelecharger3 = app.findViewById(R.id.buttonPDF3);
        btTelecharger3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                EditText note1 = app.findViewById(R.id.note7);
                String idNote = note1.getText().toString();
                //CreatePDF createPDF = new CreatePDF();
                //CreatePDF.main(null);
                TelechargerPDF telechargerPDF = new TelechargerPDF(app);
                telechargerPDF.execute(idNote);

            }
        });
        Button btTelecharger4 = app.findViewById(R.id.buttonPDF4);
        btTelecharger4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                EditText note1 = app.findViewById(R.id.note10);
                String idNote = note1.getText().toString();
                //CreatePDF createPDF = new CreatePDF();
                //CreatePDF.main(null);
                TelechargerPDF telechargerPDF = new TelechargerPDF(app);
                telechargerPDF.execute(idNote);

            }
        });
        Button btTelecharger5 = app.findViewById(R.id.buttonPDF5);
        btTelecharger5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                EditText note1 = app.findViewById(R.id.note13);
                String idNote = note1.getText().toString();
                //CreatePDF createPDF = new CreatePDF();
                //CreatePDF.main(null);
                TelechargerPDF telechargerPDF = new TelechargerPDF(app);
                telechargerPDF.execute(idNote);

            }
        });


    }
}
