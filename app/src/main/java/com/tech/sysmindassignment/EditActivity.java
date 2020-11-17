package com.tech.sysmindassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tech.sysmindassignment.MarvelArchitecture.MarvelModelEntity;
import com.tech.sysmindassignment.MarvelArchitecture.MarvelViewModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class EditActivity extends AppCompatActivity {
    private TextView changeimage_textview;
    private EditText name,desc;
    private CircleImageView userimage;
    private Button edit_button;
    private MarvelViewModel marvelViewModel;
    private MarvelModelEntity marvelModelEntity;
    Uri uri=null;
    private Long time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        setTitle("Edit");
        initViews();
        init();
        getIntentData();
        onclicks();
    }

    private void getIntentData() {

        marvelModelEntity=new MarvelModelEntity(getIntent().getStringExtra("name"),getIntent().getStringExtra("desc"),""+  getIntent().getStringExtra("userimage"));
        marvelModelEntity.setId(getIntent().getIntExtra("id",0));
//        marvelModelEntity.setMarvel_name(getIntent().getStringExtra("name"));
//        marvelModelEntity.setMarvel_image(getIntent().getStringExtra("userimage"));
//        marvelModelEntity.setDescription(getIntent().getStringExtra("desc"));
        name.setText(""+getIntent().getStringExtra("name"));
        desc.setText(""+getIntent().getStringExtra("desc"));

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
        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edituser();
            }
        });
    }

    private void edituser() {
        String name_str=name.getText().toString().trim();
        String desc_str=desc.getText().toString().trim();
      marvelModelEntity.setDescription(""+desc_str);
      marvelModelEntity.setMarvel_name(""+name_str);
      if(time!=null){
          marvelModelEntity.setMarvel_image(""+time);
      }

        marvelViewModel.update(marvelModelEntity);
        Toast.makeText(EditActivity.this, "Successfully Edited", Toast.LENGTH_LONG).show();
        onBackPressed();


    }

    private void initViews() {
        changeimage_textview=findViewById(R.id.edit_changeimage_textview);
        name=findViewById(R.id.edit_editText_name);
        desc=findViewById(R.id.edit_editText_desc);
        userimage=findViewById(R.id.edit_user_image);
        edit_button=findViewById(R.id.edit_button);
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
            try {
                final Uri imageUri = data.getData();
                uri=imageUri;
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
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
