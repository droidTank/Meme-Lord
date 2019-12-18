package com.example.memelord.Adapter;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.memelord.Add_Comic;
import com.example.memelord.Model.AddComicModel;
import com.example.memelord.Model.AddDescriptionModel;
import com.example.memelord.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.Viewholder> {



    private Context context;
    private List<AddComicModel> comics;


    String url ;
    File file;
    String dirPath, fileName;

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


        holder.downloadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                // Initialization Of DownLoad Butto



                downloadFile( addComicModel.getComicphoto());




                //Toast.makeText(context, "ffffff", Toast.LENGTH_SHORT).show();


            }
        });


        Glide.with(context).load(addComicModel.getComicphoto()).into(holder.comicphoto);


        holder.comicmemelord.setText(addComicModel.getComicmemelord());

       // holder.comickeyword.setText(addComicModel.getComicdescription());

        holder.comictime.setText(addComicModel.getComictime());


    }



    public void downloadFile(String uRl) {

        final File imageRoot = new File(Environment.DIRECTORY_PICTURES.concat("/MemeLord"));

        if (!imageRoot.exists()) {

            imageRoot.mkdirs();

        }

        DownloadManager mgr = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        Uri downloadUri = Uri.parse(uRl);
        DownloadManager.Request request = new DownloadManager.Request(
                downloadUri);

        String extStorageDirectory = imageRoot.toString();

        request.setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_WIFI
                        | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false).setTitle("MemeLord")
                .setDescription(uRl)
                .setDestinationInExternalPublicDir(extStorageDirectory,"MemeLord");

        mgr.enqueue(request);

    }




    @Override
    public int getItemCount() {
        return comics.size();
    }



    public static class Viewholder extends RecyclerView.ViewHolder {



        protected ImageView comicphoto,downloadbtn;

        protected TextView comicmemelord;

        protected TextView comictime;

        public Viewholder(@NonNull View itemView) {
            super(itemView);




            comicphoto = (ImageView) itemView.findViewById(R.id.comicphoto);

            comicmemelord = (TextView) itemView.findViewById(R.id.comicmemelord);

            comictime = (TextView) itemView.findViewById(R.id.comictime);


            downloadbtn = (ImageView) itemView.findViewById(R.id.downloadbtn);


        }
    }
}
