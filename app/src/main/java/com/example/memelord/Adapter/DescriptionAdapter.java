package com.example.memelord.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memelord.Add_Comic;
import com.example.memelord.Model.AddComicModel;
import com.example.memelord.Model.AddDescriptionModel;
import com.example.memelord.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

public class DescriptionAdapter extends RecyclerView.Adapter<DescriptionAdapter.Viewholder> {


    private Context context;
    private List<AddDescriptionModel> description;

    public DescriptionAdapter(Context context, List<AddDescriptionModel> description) {
        this.context = context;
        this.description = description;
    }

    @NonNull
    @Override
    public DescriptionAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.activity_add__description__item, parent, false);
        return new DescriptionAdapter.Viewholder(view);


    }


    @Override
    public void onBindViewHolder(@NonNull final DescriptionAdapter.Viewholder holder, final int position) {

        final AddDescriptionModel addDescriptionModel = description.get(position);




        holder.save_description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dstr= holder.Comicdescription.getText().toString();


                for(int i=0;i<Add_Comic.descriptionListt.size();i++){

                    if(i==position){

                        AddDescriptionModel addDescriptionModelll=new AddDescriptionModel(dstr);

                        Add_Comic.descriptionListt.set(i,addDescriptionModelll);


                        DatabaseReference reference=FirebaseDatabase.getInstance()
                                .getReference("Comics").child(Add_Comic.comicid);


                        HashMap<String,Object> map = new HashMap<>();

                        map.put("comicdescription",Add_Comic.descriptionListt);

                        reference.updateChildren(map);

                        Toast.makeText(context, "Updated successfully", Toast.LENGTH_SHORT).show();

                        break;

                    }
                }

            }
        });





        holder.delete_description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(Add_Comic.descriptionListt.size()==1){

                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();

                }

                else{

                for(int i=0;i<Add_Comic.descriptionListt.size();i++){

                    if(i==position){

                        Add_Comic.descriptionListt.remove(i);


                        DatabaseReference reference=FirebaseDatabase.getInstance()
                                .getReference("Comics").child(Add_Comic.comicid);


                        HashMap<String,Object> map = new HashMap<>();

                        map.put("comicdescription",Add_Comic.descriptionListt);

                        reference.updateChildren(map);

                        Toast.makeText(context, "Deleted successfully", Toast.LENGTH_SHORT).show();

                        break;

                    }
                }}


            }
        });






        holder.Comicdescription.setText(addDescriptionModel.getDescription());


    }



    @Override
    public int getItemCount() {
        return description.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        protected TextView Comicdescription;

        protected ImageView save_description,delete_description;

        public Viewholder(@NonNull View itemView) {
            super(itemView);


            Comicdescription = (EditText) itemView.findViewById(R.id.Comic_descriptionrc);

            save_description=itemView.findViewById(R.id.save_description);

            delete_description=itemView.findViewById(R.id.delete_description);


        }
    }
}
