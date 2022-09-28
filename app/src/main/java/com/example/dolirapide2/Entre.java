package com.example.dolirapide2;

import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class Entre {
    private MainActivity app;

    public Entre(MainActivity app) {
        this.app = app;
        app.setContentView(R.layout.activity_main);

        Button btLog = app.findViewById(R.id.button);
        btLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Login(app);
                List pages = app.getPages();
                pages.add("0");
            }
        });
        Button btConsult = app.findViewById(R.id.buttonCons);
        btConsult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new consultation(app);
                List pages = app.getPages();
                pages.add("0");
            }
        });


        //créer mes boutons

        //a faire pour chaque trucs
    }
}
