package com.example.memelord.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.memelord.Add_Comic;
import com.example.memelord.Model.AddComicModel;
import com.example.memelord.Model.AddDescriptionModel;
import com.example.memelord.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

public class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.Viewholder> {



    private Context context;
    private List<AddComicModel> comics;


    FirebaseUser firebaseUser;
    DatabaseReference reference;


    public ComicAdapter(Context context, List<AddComicModel> comics) {
        this.context = context;
        this.comics = comics;
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_comic_item, parent, false);
        return new Viewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {


        final AddComicModel addComicModel = comics.get(position);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });


        Glide.with(context).load(addComicModel.getComicphoto()).into(holder.comicphoto);


        holder.comicmemelord.setText(addComicModel.getComicmemelord());

       // holder.comickeyword.setText(addComicModel.getComicdescription());

        holder.comictime.setText(addComicModel.getComictime());


    }


    @Override
    public int getItemCount() {
        return comics.size();
    }



    public static class Viewholder extends RecyclerView.ViewHolder {



        protected ImageView comicphoto;

        protected TextView comicmemelord;

        protected TextView comictime;

        public Viewholder(@NonNull View itemView) {
            super(itemView);




            comicphoto = (ImageView) itemView.findViewById(R.id.comicphoto);

            comicmemelord = (TextView) itemView.findViewById(R.id.comicmemelord);

            comictime = (TextView) itemView.findViewById(R.id.comictime);


        }
    }
}
