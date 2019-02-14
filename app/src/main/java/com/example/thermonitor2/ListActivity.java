package com.example.thermonitor2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.view.ViewGroup;

// array of options---adaptor----listview
public class ListActivity extends AppCompatActivity {
ListView mListView;
    String [] myItems={"Android","iPhone","Windows","Blackburry","Linux"};
    int [] images={R.drawable.android,R.drawable.apple,R.drawable.windows,R.drawable.blackberry,R.drawable.linux};

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        mListView=findViewById(R.id.ListViewMain);
        CustomerAdapter customerAdapter=new CustomerAdapter();
        mListView.setAdapter(customerAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent= new Intent(view.getContext(),DeviceDetailActivity.class);
                startActivityForResult(intent,position);

            }
        });
        //populateListView();
    }
    class CustomerAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view=getLayoutInflater().inflate(R.layout.items,null);
            ImageView imageView=(ImageView)view.findViewById(R.id.imageLogo);
            TextView textView=(TextView)view.findViewById(R.id.textName);
            imageView.setImageResource(images[position]);
            textView.setText(myItems[position]);

            return view;
        }
    }

    private void populateListView() {
        //create list of items
        String [] myItems={"Android","iPhone","Windows","Blackburry","Linux"};
        //int [] images={R.drawable.andy_lg,R.drawable.elibera_liblounge_115664,R.drawable.windows,R.drawable.blackberry,R.drawable.linux_PNG1};
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
