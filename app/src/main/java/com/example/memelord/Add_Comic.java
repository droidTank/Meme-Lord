package com.example.memelord;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.memelord.Adapter.ComicAdapter;
import com.example.memelord.Adapter.DescriptionAdapter;
import com.example.memelord.Model.AddComicModel;
import com.example.memelord.Model.AddDescriptionModel;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Add_Comic extends AppCompatActivity {

    public static String comicid;
    protected ImageView comicphoto;
    protected EditText Comicdescription;
    protected EditText ComicMemelord;
    protected FloatingActionButton addGroup;
    protected FloatingActionButton adddescription;

    public RecyclerView recyclerView;

    public DescriptionAdapter descriptionAdapter;

    protected ImageView comicPhotoImageView;

    StorageReference storageReference;
    private static final int image_request = 1;
    private Uri imageuri;
    private StorageTask<UploadTask.TaskSnapshot> uploadtask;

    List <String> descriptionList;

    public static List <AddDescriptionModel>  descriptionListt;

    public static List <AddDescriptionModel>  descriptionListtt;




    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_add__comic);


        Intent intent = getIntent();

        comicid = intent.getStringExtra("comicid");

        final String currentDateandTime = new SimpleDateFormat("EEE  MMM d, yyyy h:mm a").format(new Date());

        descriptionList = new ArrayList<>();

        descriptionListt = new ArrayList<>();

        descriptionListtt = new ArrayList<>();


        AddDescriptionModel addDescriptionModelll=new AddDescriptionModel("");

        descriptionListt.add(addDescriptionModelll);

        AddComicModel addComicModel = new AddComicModel(comicid
                ,"",descriptionListt,"",currentDateandTime);
        FirebaseDatabase.getInstance().getReference("Comics")
                .child(comicid)
                .setValue(addComicModel);

        comicPhotoImageView = (ImageView) findViewById(R.id.comicPhotoImageView);

        Comicdescription = (EditText) findViewById(R.id.Comic_description);

        ComicMemelord = (EditText) findViewById(R.id.Comic_memelord);

        addGroup = (FloatingActionButton) findViewById(R.id.add_group);

        adddescription=(FloatingActionButton) findViewById(R.id.add_description);

        comicphoto = findViewById(R.id.comic_photo);


        adddescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ComicDecriptionstr= Comicdescription.getText().toString();


                if(ComicDecriptionstr.isEmpty()){

                    Comicdescription.setError("required");



                }else {

                    Comicdescription.setText("");

                    DatabaseReference reference=FirebaseDatabase.getInstance()
                            .getReference("Comics").child(comicid);

                    AddDescriptionModel addDescriptionModelll=new AddDescriptionModel(ComicDecriptionstr);

                    if(descriptionListt.get(0).equals("")){
                        descriptionListt.clear();
                    }

                    descriptionListt.add(addDescriptionModelll);

                    HashMap<String,Object> map = new HashMap<>();

                    map.put("comicdescription",descriptionListt);

                    reference.updateChildren(map);

                    recyclerView = findViewById(R.id.recyclerviewdescription);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(Add_Comic.this));

                    readdescription();

                }

            }
        });

        storageReference= FirebaseStorage.getInstance().getReference("uploads");


        comicphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OpenImage();

            }
        });


        addGroup.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                String ComicDecriptionstr= Comicdescription.getText().toString();
                String ComicMemeLordstr= ComicMemelord.getText().toString();

                if(descriptionListt.size()==0){

                    Comicdescription.setError("required");

                }
                else if(ComicMemeLordstr.isEmpty()){

                    ComicMemelord.setError("required");

                }

                else if(comicPhotoImageView.getDrawable()== null){

                    Toast.makeText(Add_Comic.this, "Comic photo is required", Toast.LENGTH_SHORT).show();

                }

                else{






                    DatabaseReference reference=FirebaseDatabase.getInstance()
                            .getReference("Comics").child(comicid);

                    descriptionList.add(ComicDecriptionstr);

                    HashMap<String,Object> map = new HashMap<>();

                    map.put("comicmemelord",ComicMemeLordstr);

                    map.put("comicdescription",descriptionListt);

                    reference.updateChildren(map);


                    Toast.makeText(Add_Comic.this, "Comic Added successfully, MemeLord", Toast.LENGTH_SHORT).show();


                    Intent intent = new Intent(Add_Comic.this, MainActivity.class);

                    startActivity(intent);

                    finish();


                }


            }
        });


    }


    private void OpenImage() {


        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, image_request);


    }

    private String getFileExtension(Uri uri){


        ContentResolver contentResolver= getApplicationContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));



    }


    private void uploadImage(){

        final ProgressDialog progressDialog = new ProgressDialog(Add_Comic.this);
        progressDialog.setMessage("Uploading");
        progressDialog.show();



        if(imageuri !=null){

            final StorageReference fileReference= storageReference.child(System.currentTimeMillis()
                    +"."+getFileExtension(imageuri));


            uploadtask=fileReference.putFile(imageuri);

            uploadtask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {

                @Override
                public Task <Uri> then(@NonNull Task <UploadTask.TaskSnapshot>task) throws Exception {


                    if(!task.isSuccessful()){

                        throw task.getException();

                    }


                    return fileReference.getDownloadUrl();


                }
            }).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {

                    if(task.isSuccessful()) {
                        Uri downloadUri = (Uri) task.getResult();

                        String auri= downloadUri.toString();



                        DatabaseReference reference=FirebaseDatabase.getInstance()
                                .getReference("Comics").child(comicid);



                        HashMap<String,Object> map = new HashMap<>();

                        map.put("comicphoto",auri);

                        reference.updateChildren(map);

                        progressDialog.dismiss();



                        storageReference= FirebaseStorage.getInstance().getReference("uploads");

                        DatabaseReference referencee= FirebaseDatabase.getInstance().getReference("Comics")
                                .child(comicid);

                        referencee.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                                AddComicModel addComicModel = dataSnapshot.getValue(AddComicModel.class);


                                Glide.with(getApplicationContext())
                                        .load(addComicModel.getComicphoto()).into(comicPhotoImageView);




                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }else{

                        Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();

                        progressDialog.dismiss();

                    }


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();




                }
            });


        }else{


            Toast.makeText(getApplicationContext(),"No image selected",Toast.LENGTH_SHORT).show();

        }





    }



    private void readdescription(){


        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Comics");


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                descriptionListtt.clear();



                for(DataSnapshot snapshot: dataSnapshot.getChildren()){

                    AddComicModel addComicModel = snapshot.getValue(AddComicModel.class);

                    if(addComicModel.getComicid().equals(comicid)) {


                            descriptionListtt.addAll(addComicModel.getComicdescription());

                        break;
                    }

                }


                descriptionAdapter = new DescriptionAdapter(Add_Comic.this,descriptionListt);
                recyclerView.setAdapter(descriptionAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if(requestCode==image_request && resultCode==RESULT_OK

                && data != null && data.getData() != null
        ) {

            imageuri = data.getData();

            if(uploadtask!=null &&  uploadtask.isInProgress()){



                Toast.makeText(getApplicationContext(),"Upload in progress",Toast.LENGTH_SHORT).show();



            }else{

                uploadImage();

            }





        }
    }


    public static String scramble(Random random, String inputString )
    {
        // Convert your string into a simple char array:
        char a[] = inputString.toCharArray();

        // Scramble the letters using the standard Fisher-Yates shuffle,
        for( int i=0 ; i<a.length ; i++ )
        {
            int j = random.nextInt(a.length);
            // Swap letters
            char temp = a[i]; a[i] = a[j];  a[j] = temp;
        }

        return new String( a );
    }





    

    
    
}
