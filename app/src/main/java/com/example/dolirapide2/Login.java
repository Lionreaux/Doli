package com.example.dolirapide2;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class Login {
    String urlTxt;
    String idTxt;
    String mdpTxt;

    private AppCompatActivity app;
    public Login(AppCompatActivity app) {
        this.app = app;
        app.setContentView(R.layout.pagelogin);


        Button btRetour = app.findViewById(R.id.buttonRetour);
        btRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Entre(app);
            }
        });
        Button btConn = app.findViewById(R.id.buttonConnect);
        btConn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check();
                Api api = new Api(Login.this);
                api.start();
            }
        });
    }
    public void check() {
        EditText url = app.findViewById(R.id.ChampUrl);
        urlTxt = url.getText().toString();

        EditText id = app.findViewById(R.id.ChampId);
        idTxt = id.getText().toString();

        EditText mdp = app.findViewById(R.id.ChampMdp);
        mdpTxt = mdp.getText().toString();

        TextView status = app.findViewById(R.id.essaie);
        if (urlTxt.equals("oui") && idTxt.equals("admin") && mdpTxt.equals("123456789")) {
            status.setText("Succès");
        }
        else {
            status.setText("Erreur : identifiant non valide");
        }
    }

    public String getUrlTxt() {
        return urlTxt;
    }

    public void setUrlTxt(String urlTxt) {
        this.urlTxt = urlTxt;
    }

    public String getIdTxt() {
        return idTxt;
    }

    public void setIdTxt(String idTxt) {
        this.idTxt = idTxt;
    }

    public  String getMdpTxt() {
        return mdpTxt;
    }

    public void setMdpTxt(String mdpTxt) {
        this.mdpTxt = mdpTxt;
    }
}
