package com.tech.sysmindassignment;


import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.tech.sysmindassignment.MarvelArchitecture.MarvelModelEntity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class MarvelItemRecyclerViewAdapter extends RecyclerView.Adapter<MarvelItemRecyclerViewAdapter.NoteHolder> {
  Context context;

  MarvelInterface marvelInterface;
List<MarvelModelEntity> marvelModelEntities=new ArrayList<>();

    public MarvelItemRecyclerViewAdapter(Context c, MarvelInterface marvelInterface){
        this.marvelInterface=marvelInterface;
 context=c;
    }
    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_marvel,viewGroup,false);


        return new NoteHolder(itemView,marvelInterface);
    }
public void setcourse(List<MarvelModelEntity> c){
        marvelModelEntities=c;
        notifyDataSetChanged();

}
    @Override
    public void onBindViewHolder(@NonNull NoteHolder noteHolder, final int i) {

   noteHolder.marvel_name_textview.setText(marvelModelEntities.get(i).getMarvel_name());
        noteHolder.marvel_desc_textview.setText(marvelModelEntities.get(i).getDescription());

        ContextWrapper cw = new ContextWrapper(context.getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        try {
            File f=new File(directory, marvelModelEntities.get(i).getMarvel_image()+".jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));

            noteHolder.marvelImageView.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
      //  noteHolder.marvel_name_textview.setText(marvelModelEntities.get(i).getCourse_name());
noteHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
    @Override
    public boolean onLongClick(View view) {
        marvelInterface.onlongPressed(marvelModelEntities.get(i));
        return false;
    }
});
        noteHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                marvelInterface.GotoDetail(marvelModelEntities.get(i));
            }
        });

    }


    @Override
    public int getItemCount() {
        return marvelModelEntities.size();
    }


    class NoteHolder extends RecyclerView.ViewHolder{
public ImageView marvelImageView;
private TextView marvel_name_textview,marvel_desc_textview;
private  MarvelInterface marvelInterface;


        public NoteHolder(@NonNull final View itemView, final MarvelInterface marvelInterface ) {
            super(itemView);
            this.marvelInterface = marvelInterface;
            marvel_desc_textview=itemView.findViewById(R.id.textview_desc);
            marvel_name_textview=itemView.findViewById(R.id.textView_name);
           marvelImageView=itemView.findViewById(R.id.userimage);

        }

}


}
