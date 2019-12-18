package com.example.memelord;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.memelord.Adapter.ComicAdapter;
import com.example.memelord.Model.AddComicModel;
import com.example.memelord.Model.AddDescriptionModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static com.example.memelord.Add_Comic.descriptionListt;
import static com.example.memelord.Add_Comic.scramble;
import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    public RecyclerView recyclerView;
    public ComicAdapter comicAdapter;

    Toolbar toolbar;

    FloatingActionButton add_comic;


    public List<AddComicModel> comics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        add_comic = findViewById(R.id.add_comic);



        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {


                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},23
                );
            }
        }



        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Meme Lord");





        add_comic.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                long time= System.currentTimeMillis();

                Random r = new Random();

                String word = "Comic"+"MemeLord"+time;

                String comicidd = scramble( r, word);

                Intent intent=new Intent(MainActivity.this,Add_Comic.class);

                intent.putExtra("comicid",comicidd);

                final String currentDateandTime = new SimpleDateFormat("EEE  MMM d, yyyy h:mm a").format(new Date());

               List <AddDescriptionModel>  descriptionListt;
               descriptionListt=new ArrayList<>();

                AddComicModel addComicModel = new AddComicModel(comicidd
                        ,"",descriptionListt,"",currentDateandTime);
                FirebaseDatabase.getInstance().getReference("Comics")
                        .child(comicidd)
                        .setValue(addComicModel);


                startActivity(intent);


            }
        });









        recyclerView=findViewById(R.id.recyclerviewcomics);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        comics = new ArrayList<>();

        readcomics();










    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);


        SearchManager searchManager =
                (SearchManager) getSystemService(this.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);






        return true;
    }





    private void readcomics(){


        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Comics");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                comics.clear();



                for(DataSnapshot snapshot: dataSnapshot.getChildren()) {

                    AddComicModel addComicModel = snapshot.getValue(AddComicModel.class);


                    assert addComicModel != null;

                    assert addComicModel.getComicmemelord() !=null;

                    if (!addComicModel.getComicmemelord().equals("")) {


                      comics.add(addComicModel);



                       }else{

                        /*DatabaseReference referenceee= FirebaseDatabase.getInstance()
                                .getReference("Comics").child(addComicModel.getComicid());

                        referenceee.removeValue();*/


                    }


                }


                comicAdapter = new ComicAdapter(MainActivity.this,comics);
                recyclerView.setAdapter(comicAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public static boolean isProbablyArabic(String s) {
        for (int i = 0; i < s.length();) {
            int c = s.codePointAt(i);
            if (c >= 0x0600 && c <= 0x06E0)
                return true;
            i += Character.charCount(c);
        }
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    @Override
    public boolean onQueryTextChange(String newText) {


        if(isProbablyArabic(newText)) {
            try {
                newText = newText.toLowerCase();


                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Comics");

                final String finalNewText = newText;
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        comics.clear();

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            AddComicModel addComicModel = snapshot.getValue(AddComicModel.class);

                            assert addComicModel.getComicdescription().size() != 0;


                            if (addComicModel.getComicdescription().size() > 0) {

                                for (int i = 0; i < addComicModel.getComicdescription().size(); i++) {

                                    if (addComicModel.getComicdescription().get(i).getDescription().toLowerCase().contains(finalNewText)

                                            && !addComicModel.getComicmemelord().equals("") &&
                                            !addComicModel.getComicphoto().equals("")
                                    ) {


                                        comics.add(addComicModel);

                                        break;

                                    }

                                }
                            }

                        }


                        comicAdapter = new ComicAdapter(MainActivity.this, comics);
                        recyclerView.setAdapter(comicAdapter);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            /*

            DatabaseReference referencee = FirebaseDatabase.getInstance().getReference("Comics");

            referencee.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        AddComicModel addComicModel = snapshot.getValue(AddComicModel.class);


                        assert addComicModel.getComicmemelord() != null;

                        assert addComicModel.getComicphoto() !=null;

                        if (addComicModel.getComicmemelord().equals("") || addComicModel.getComicphoto().equals("")) {



                            DatabaseReference referenceee = FirebaseDatabase.getInstance()
                                    .getReference("Comics").child(addComicModel.getComicid());

                            referenceee.removeValue();


                        }
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            */

            } catch (Exception e) {


            }
        }




        if(!isProbablyArabic(newText) && ! newText.isEmpty()){

            Toast.makeText(this, "Arabic, please", Toast.LENGTH_SHORT).show();

        }





        return true;



    }
}
