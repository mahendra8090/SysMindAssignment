package com.tech.sysmindassignment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tech.sysmindassignment.MarvelArchitecture.MarvelModelEntity;
import com.tech.sysmindassignment.MarvelArchitecture.MarvelViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MarvelInterface{
    private MarvelInterface marvelInterface;
    private RecyclerView marvel_recyclerview;
    private MarvelViewModel marvelViewModel;
    private MarvelItemRecyclerViewAdapter marvelAdapter;
    FloatingActionButton floatingActionButton;
    private  MarvelModelEntity longPressed_model;
    private AlertDialog dialog;
    TextView textView;
    private List<MarvelModelEntity> all_marvelModelEntities=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Marvel");
        initviews();

        onclicks();
        init();
        longPressbuilder();
        setupRecyclerView();
        getData();
    }

    private void onclicks() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
startActivity(new Intent(MainActivity.this,AddMarvelActivity.class));
            }
        });
    }
    private void longPressbuilder() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        // add a list
        String[] animals = {"Delete","Edit"};

        builder.setItems(animals, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                    {
                        delete(longPressed_model);
                        dialog.dismiss();
                        break;

                    }
                    case 1:
                    {
                        edit(longPressed_model);
                        dialog.dismiss();
                        break;

                    }



                }
            }
        });
        dialog = builder.create();

    }
    private void delete(MarvelModelEntity marvelModelEntity){
        marvelViewModel.delete(marvelModelEntity);
        Toast.makeText(MainActivity.this, "Successfully Deleted", Toast.LENGTH_LONG).show();
    }
    private void edit(MarvelModelEntity marvelModelEntity){
        Intent i=new Intent(MainActivity.this, EditActivity.class);
        i.putExtra("name",marvelModelEntity.getMarvel_name());
        i.putExtra("desc",marvelModelEntity.getDescription());
        i.putExtra("id",marvelModelEntity.getId());
        i.putExtra("userimage",marvelModelEntity.getMarvel_image());
        startActivity(i);
     //   marvelViewModel.update(marvelModelEntity);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_menu:
               marvelViewModel.deleteAllMarvels();
                return true;



            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
    private void getData() {
        marvelViewModel.getAllMarvels().observe(this, new Observer<List<MarvelModelEntity>>() {
            @Override
            public void onChanged(@Nullable List<MarvelModelEntity> marvelModelEntities) {
                marvelAdapter.setcourse(marvelModelEntities);
                all_marvelModelEntities.clear();
                all_marvelModelEntities=marvelModelEntities;
                if(marvelModelEntities.size()==0){
                    textView.setVisibility(View.VISIBLE);
                }else {
                    textView.setVisibility(View.GONE);
                }
            }
        });
    }

    private void init() {
        marvelViewModel= ViewModelProviders.of(this).get(MarvelViewModel.class);
        marvelInterface=this;
        marvelAdapter=new MarvelItemRecyclerViewAdapter(MainActivity.this,marvelInterface);
        Uri uri=null;
  longPressed_model=new MarvelModelEntity("","",""+uri);

    }

    private void setupRecyclerView() {
        marvel_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        marvel_recyclerview.setAdapter(marvelAdapter);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,  ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //awesome code when user grabs recycler card to reorder

           return false;
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                //awesome code to run when user drops card and completes reorder
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //awesome code when swiping right to remove recycler card and delete SQLite data

                if (direction == ItemTouchHelper.RIGHT) {
//whatever code you want the swipe to perform

                   marvelViewModel.delete(all_marvelModelEntities.get( viewHolder.getAdapterPosition()));
                    Toast.makeText(MainActivity.this, "Successfully Deleted", Toast.LENGTH_LONG).show();
                }


                if (direction == ItemTouchHelper.LEFT) {
//whatever code you want the swipe to perform

                   edit( all_marvelModelEntities.get( viewHolder.getAdapterPosition()));
                }
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(marvel_recyclerview);
    }

    private void initviews() {
        marvel_recyclerview=findViewById(R.id.marvel_recycler);
        floatingActionButton=findViewById(R.id.floatingActionButton);
        textView=findViewById(R.id.textView);
        
    }

    @Override
    public void onlongPressed(MarvelModelEntity marvelModelEntity) {
        longPressed_model.setMarvel_name(marvelModelEntity.getMarvel_name());
        longPressed_model.setDescription(marvelModelEntity.getDescription());
        longPressed_model.setId(marvelModelEntity.getId());
        longPressed_model.setMarvel_image(marvelModelEntity.getMarvel_image());
        dialog.show();
    }

    @Override
    public void GotoDetail(MarvelModelEntity marvelModelEntity) {
        Intent i=new Intent(MainActivity.this, DetailActivity.class);
        i.putExtra("name",marvelModelEntity.getMarvel_name());
        i.putExtra("desc",marvelModelEntity.getDescription());
        i.putExtra("id",marvelModelEntity.getId());
        i.putExtra("userimage",marvelModelEntity.getMarvel_image());
        startActivity(i);
    }
}
