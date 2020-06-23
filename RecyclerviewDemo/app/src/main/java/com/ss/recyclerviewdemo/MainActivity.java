package com.ss.recyclerviewdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import github.nisrulz.recyclerviewhelper.RVHItemClickListener;
import github.nisrulz.recyclerviewhelper.RVHItemDividerDecoration;
import github.nisrulz.recyclerviewhelper.RVHItemTouchHelperCallback;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView myrecyclerview = findViewById(R.id.rv_fruits);

        data = new ArrayList<>();
        data.add("Apple");
        data.add("Banana");
        data.add("Peach");
        data.add("Pineapple");
        data.add("Orange");
        data.add("Strawberry");
        data.add("Grapes");
        data.add("Apricot");
        data.add("Avocado");
        data.add("Raisin");
        data.add("Guava");
        data.add("Papaya");
        data.add("Pear");
        data.add("Blueberry");
        data.add("Lychee");
        data.add("Date");
        data.add("Fig");

        MyAdapter adapter = new MyAdapter(data);
        myrecyclerview.hasFixedSize();
        myrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        myrecyclerview.setAdapter(adapter);

        // Setup onItemTouchHandler
        ItemTouchHelper.Callback callback = new RVHItemTouchHelperCallback(adapter, true, true, true);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(myrecyclerview);

        // Set the divider
        myrecyclerview.addItemDecoration(
            new RVHItemDividerDecoration(this, LinearLayoutManager.VERTICAL));

        // Set On Click
        myrecyclerview.addOnItemTouchListener(
            new RVHItemClickListener(this, new RVHItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    String value = "Clicked Item " + data.get(position) + " at " + position;

                    Log.d("TAG", value);
                    Toast.makeText(MainActivity.this, value, Toast.LENGTH_SHORT).show();
                }
            }));

        // Show Toast
        Toast.makeText(MainActivity.this, "Swipe items left/right\nLong press and drag and drop",
            Toast.LENGTH_LONG).show();
    }
}