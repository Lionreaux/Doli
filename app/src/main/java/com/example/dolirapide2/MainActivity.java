package com.example.dolirapide2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List pages;
    private String dernierElement;
    private int taille;

    public MainActivity() {
        pages = new ArrayList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Entre(this);
        pages.add("0");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        taille = pages.size() - 1;
        dernierElement = (String) pages.get(taille);
        if (dernierElement.equals("0")) {
            new Entre(this);
        }
        else if (dernierElement.equals("1")) {
            new Entre(this);
        }
        else if (dernierElement.equals("2")){
            new consultation(this);
        }
        pages.remove(taille);
        return true;
    }

    public List getPages() {
        return pages;
    }

    public void setPages(List pages) {
        this.pages = pages;
    }
}