package com.example.memelord;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.HashMap;

public class Add_Comic extends AppCompatActivity {

    ImageView comicphoto;

    private static final int image_request = 1;
    private Uri imageuri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__comic);


        comicphoto=findViewById(R.id.comic_photo);


        comicphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        OpenImage();

            }
        });

    }


    private void OpenImage() {


        Intent galleryIntent=new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,image_request);


    }




}
