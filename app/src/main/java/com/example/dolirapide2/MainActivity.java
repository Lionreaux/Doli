package com.example.dolirapide2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import android.util.Base64;
import java.util.List;
/**
Cette classe est le commencement du programme c'est ici qu'il va créer mon unique activité que je vais ensuite envoyer et utiliser avec toutes mes classes.
On y a aussi le système que j'utilise pour avoir un retour en arriere quand on appuie sur la fleche du bas du téléphone que je fait marcher avec un tableau étant donné que je n'ai qu'une seule activité, ce qui n'est pas optimale pour du développement java android.
On peut aussi trouver ligne 74 et 82 les méthodes qui servent a ouvrir la gallerie du téléphonne et a encoder en base 64 notre image pour que l'on puisse l'envoyer dans une note de frais.
Cette classe comporte d'ailleurs beaucoup de variables alors qu'elle ne les utilise pas forcément car en les regroupant ici, je peux les utiliser n'importe où puisque je fait circuler mon activité partout.
 */
public class MainActivity extends AppCompatActivity {
    private List pages;
    private String dernierElement;
    private int taille;
    private static int PICK_IMAGE_REQUEST = 1;


    private String ip;
    private String token;
    private String idNote;
    private String idU;
    private String imageContenu;

    private Uri mImageUri;
    private ImageView Vue;

    public MainActivity() {
        pages = new ArrayList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Login(this);
        //new Entre(this);
        pages.add("0");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        taille = pages.size();
        if(taille > 0){
            dernierElement = (String) pages.get(taille-1);
            if (dernierElement.equals("0")) {
                new Entre(this);
            } else if (dernierElement.equals("1")) {
                new Entre(this);
            } else if (dernierElement.equals("2")){
                new consultation(this);
            }
            pages.remove(taille-1);
        }
        return true;
    }


    public void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        this.startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String encodedfile = null;


        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageUri = data.getData();
            System.out.println(mImageUri);
            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setImageURI(mImageUri);

            try {
                InputStream imageStream = this.getContentResolver().openInputStream(mImageUri);
                Bitmap bitMap = BitmapFactory.decodeStream(imageStream);

                ByteArrayOutputStream byteEnc = new ByteArrayOutputStream();
                bitMap.compress(Bitmap.CompressFormat.PNG,100,byteEnc);
                byte[] b = byteEnc.toByteArray();
                imageContenu = Base64.encodeToString(b, Base64.DEFAULT);
                imageContenu = imageContenu.replace("\n", "");
                System.out.println(imageContenu);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    public List getPages() {
        return pages;
    }

    public void setPages(List pages) {
        this.pages = pages;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIdNote() {
        return idNote;
    }

    public void setIdNote(String idNote) {
        this.idNote = idNote;
    }

    public String getIdU() {
        return idU;
    }

    public void setIdU(String idU) {
        this.idU = idU;
    }

    public String getImageContenu() {
        return imageContenu;
    }

    public void setImageContenu(String imageContenu) {
        this.imageContenu = imageContenu;
    }
}