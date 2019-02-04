package com.example.thermonitor2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.view.ViewGroup;

// array of options---adaptor----listview
public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        populateListView();
    }

    private void populateListView() {
        //create list of items
        String [] myItems={"Android","iPhone","Windows","Blackburry","Linux"};
        //adapter
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.the_items,myItems);

        ListView list=(ListView) findViewById(R.id.ListViewMain);


        list.setAdapter(adapter);
        //move to another activity
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent= new Intent(view.getContext(),DeviceDetailActivity.class);
                startActivityForResult(intent,position);
            }
        });
    }

}
