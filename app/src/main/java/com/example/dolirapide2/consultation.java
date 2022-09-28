package com.example.dolirapide2;

import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class consultation {
    private MainActivity app;
    public consultation(MainActivity app) {
        this.app = app;
        app.setContentView(R.layout.consultations);

        Button btRetour = app.findViewById(R.id.buttonRetour);
        btRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Entre(app);
                List pages = app.getPages();
                pages.add("2");
            }
        });

            Button btCreer = app.findViewById(R.id.buttoncrea);
        btCreer.setOnClickListener(new View.OnClickListener()

            {
                public void onClick(View v) {
                new creation(app);
                List pages = app.getPages();
                pages.add("2");
                }

        });
    }
}
