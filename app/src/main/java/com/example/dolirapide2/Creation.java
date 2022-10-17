package com.example.dolirapide2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import java.util.List;

/**
Cette classe va s'occuper de la vue de ma page de création et va gérer ce qu'il se passe lors d'un appui sur un de ses boutons.

 */
public class Creation {
    private MainActivity app;
    private Login log;

    private Button btCrea;
    private ImageButton intentBtn;

    public Creation(MainActivity app) {
        this.app = app;
        this.app.setContentView(R.layout.creer);

        btCrea = (Button) app.findViewById(R.id.buttonCrea);
        btCrea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreationNote crea = new CreationNote(app);
                crea.start();


            }
        });

        ImageButton btImage = app.findViewById(R.id.intentBtn);
        btImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.openFileChooser();
            }
        });
    }
}
