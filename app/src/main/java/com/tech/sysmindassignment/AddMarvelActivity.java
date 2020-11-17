package com.tech.sysmindassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tech.sysmindassignment.MarvelArchitecture.MarvelModelEntity;
import com.tech.sysmindassignment.MarvelArchitecture.MarvelViewModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AddMarvelActivity extends AppCompatActivity {
private TextView changeimage_textview;
private EditText name,desc;
private CircleImageView userimage;
private Button add_button;
private MarvelViewModel marvelViewModel;
private Uri imageuri=null;
private Long time;
    FileOutputStream outputstream;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_marvel);
        setTitle("Add New");
        initViews();
        init();
        onclicks();
    }

    private void init() {
        marvelViewModel= ViewModelProviders.of(this).get(MarvelViewModel.class);
    }

    private void onclicks() {
        userimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
addImage();
            }
        });
        changeimage_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
addImage();
            }
        });
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adduser();
            }
        });
    }

    private void adduser() {
        String name_str=name.getText().toString().trim();
        String desc_str=desc.getText().toString().trim();
        MarvelModelEntity marvelModelEntity=new MarvelModelEntity(name_str,desc_str,""+time);
        marvelViewModel.insert(marvelModelEntity);
        Toast.makeText(AddMarvelActivity.this, "Successfully Added", Toast.LENGTH_LONG).show();
        onBackPressed();


    }

    private void initViews() {
        changeimage_textview=findViewById(R.id.changeimage_textview);
        name=findViewById(R.id.add_editText_name);
        desc=findViewById(R.id.add_editText_desc);
        userimage=findViewById(R.id.add_userimage);
        add_button=findViewById(R.id.add_button);
    }
 private void addImage(){
      Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
      photoPickerIntent.setType("image/*");
      startActivityForResult(photoPickerIntent, 101);
  }
    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {

                final Uri imageUri = data.getData();
                imageuri = imageUri;

            try {
             InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                userimage.setImageBitmap(selectedImage);
                saveToInternalStorage(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }




        }else {
        }
    }
    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        time=System.currentTimeMillis();
        File mypath=new File(directory,time+".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }
}
