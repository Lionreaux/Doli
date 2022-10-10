package com.example.dolirapide2;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;


public class Login {
    String ipTxt;
    String idTxt;
    String mdpTxt;

    private MainActivity app;
    public Login(MainActivity app) {
        this.app = app;
        List l = app.getPages();
        app.setContentView(R.layout.pagelogin);

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
        ipTxt = url.getText().toString();

        EditText id = app.findViewById(R.id.ChampId);
        idTxt = id.getText().toString();

        EditText mdp = app.findViewById(R.id.ChampMdp);
        mdpTxt = mdp.getText().toString();

        TextView status = app.findViewById(R.id.essaie);
        if (idTxt.equals("admin") && mdpTxt.equals("123456789")) {
            status.setText("Succès");
            List pages = app.getPages();
            pages.add("1");
            app.setIp(ipTxt);
            new Entre(app);
        }
        else {
            status.setText("Erreur : identifiant non valide");
        }
    }

    public String getIpTxt() {
        return ipTxt;
    }

    public void setIpTxt(String ipTxt) {
        this.ipTxt = ipTxt;
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

    public MainActivity getApp() {
        return app;
    }
}
