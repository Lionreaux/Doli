package com.example.dolirapide2;

import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class consultation {
    private AppCompatActivity app;
    public consultation(AppCompatActivity app) {
        this.app = app;
        app.setContentView(R.layout.consultations);

        Button btRetour = app.findViewById(R.id.buttonRetour);
        btRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Entre(app);
            }
        });
    }
}
