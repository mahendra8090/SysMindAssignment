package com.tech.sysmindassignment;

import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class DetailActivity extends AppCompatActivity {
    private TextView name,desc;
    private CircleImageView userimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initViews();
        setvalues();
    }

    private void setvalues() {
        name.setText(""+getIntent().getStringExtra("name"));
        desc.setText(""+getIntent().getStringExtra("desc"));
        setTitle(""+getIntent().getStringExtra("name"));
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        try {
            File f=new File(directory, getIntent().getStringExtra("userimage")+".jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));

           userimage.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    private void initViews() {

        name=findViewById(R.id.detail_name_textView);
        desc=findViewById(R.id.detail_textView_desc);
        userimage=findViewById(R.id.detail_userimage);

    }

}
