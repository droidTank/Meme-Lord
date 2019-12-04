package com.example.memelord;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.example.memelord.Adapter.ComicAdapter;
import com.example.memelord.Model.AddComicModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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





        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Meme Lord");





        add_comic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long time= System.currentTimeMillis();

                Random r = new Random();

                String word = "Comic"+"MemeLord"+time;

                String comicidd = scramble( r, word);

                Intent intent=new Intent(MainActivity.this,Add_Comic.class);

                intent.putExtra("comicid",comicidd);

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



                for(DataSnapshot snapshot: dataSnapshot.getChildren()){

                    AddComicModel addComicModel = snapshot.getValue(AddComicModel.class);





                        comics.add(addComicModel);




                }


                comicAdapter = new ComicAdapter(MainActivity.this,comics);
                recyclerView.setAdapter(comicAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    @Override
    public boolean onQueryTextChange(String newText) {


        newText= newText.toLowerCase();


        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Comics");

        final String finalNewText = newText;
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                comics.clear();

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){

                    AddComicModel addComicModel = snapshot.getValue(AddComicModel.class);


                    if(addComicModel.getComickeyword().toLowerCase().contains(finalNewText)){


                        comics.add(addComicModel);


                    }

                }


                comicAdapter = new ComicAdapter(MainActivity.this,comics);
                recyclerView.setAdapter(comicAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        return true;
    }
}
