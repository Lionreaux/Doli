package com.example.dolirapide2;

import android.view.View;
import android.widget.Button;

import java.util.List;

public class Creation {
    private MainActivity app;
    private Login log;

    private Button btCrea;

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
    }
}
