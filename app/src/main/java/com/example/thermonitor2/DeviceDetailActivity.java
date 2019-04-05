package com.example.thermonitor2;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class DeviceDetailActivity extends AppCompatActivity {
    TextView esp;
    FirebaseDatabase database;
    private DatabaseReference ref;

    private ListView mlistView;
    private ArrayList<String> reading=new ArrayList<>();
    private CustomerAdapter customerAdapter;
    private ArrayAdapter<String> adapter;
    ArrayList<String> listItems = new ArrayList<String>();
    ArrayList<String> listKeys = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_detail);
        esp=(TextView)findViewById(R.id.espname);
        esp.setText(getIntent().getStringExtra("espName"));
        final String name= (String) esp.getText().toString();

        database=FirebaseDatabase.getInstance();
        ref= FirebaseDatabase.getInstance().getReference();
        mlistView=(ListView)findViewById(R.id.ListReadings);
        reading=new ArrayList<>();
        adapter=new ArrayAdapter<String>(this,R.layout.readings,R.id.temprature,reading);

       customerAdapter=new CustomerAdapter();
       mlistView.setAdapter(customerAdapter);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dis : dataSnapshot.getChildren()){
                    if (dis.getKey().equals(name)){
                        reading.clear();
//                        ArrayList<String>data=(ArrayList<String>)dis.getValue();
//                        adapter.add(
//                                (String) dis.getValue());
//                        dis.getChildren().iterator().toString();
//
//                        listKeys.add(dataSnapshot.getKey());


                    //HashMap<String, Object> yourData = (HashMap<String, Object>) dis.getValue();
                    for(DataSnapshot inner:dis.getChildren()){
                        String value = (String) inner.getValue();
                        reading.add(value);
                    }

//                        HashMap<String, Object> Data = (HashMap<String, Object>) inner.getValue();
//                        Set keys=Data.keySet();
//                        for (Iterator i = keys.iterator(); i.hasNext(); ) {
//                            String key = (String) i.next();
//                            String value = (String) Data.get(key);
//                            reading.add(value);}
//                    }
//                    Set keys=yourData.keySet();
//                    for (Iterator i = keys.iterator(); i.hasNext(); ) {
//                        String key = (String) i.next();
//                        String value = (String) yourData.get(key);
//                    reading.add(value);}

                }}
                mlistView.setAdapter(customerAdapter);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    class CustomerAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return reading.size();
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
            if(reading!=null&&reading.size()!=0&&position<reading.size()){
                View view=getLayoutInflater().inflate(R.layout.readings,null);
                TextView temp=(TextView)view.findViewById(R.id.temprature);
                temp.setText(reading.get(position));
                if (position==reading.size()-1){
                    temp.setTextColor(Color.RED);
                }
                return view;
            }




          return null;  }

    }
}
