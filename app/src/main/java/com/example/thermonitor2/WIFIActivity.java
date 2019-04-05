package com.example.thermonitor2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class WIFIActivity extends AppCompatActivity {
    private WifiManager wifiManager;
    private ListView listView;
    private Button buttonScan;
    private int size=0;
    private List<ScanResult> results;
    private ArrayList<String>Mac=new ArrayList<>();
    private ArrayList<String>ssid=new ArrayList<>();
    private ArrayList<Integer>images=new  ArrayList<>();
    private ArrayAdapter adapter;
    private CustomerAdapter customerAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        buttonScan=findViewById(R.id.scanBtn);
        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

scanWifi();
            }
        });
        listView=findViewById(R.id.WifiList);
        wifiManager=(WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if(!wifiManager.isWifiEnabled()){
            Toast.makeText(getApplicationContext(),"WiFi is disabled, we need to enable it ",Toast.LENGTH_LONG).show();
            wifiManager.setWifiEnabled(true);

        }
         customerAdapter=new CustomerAdapter();
        listView.setAdapter(customerAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view.findViewById(R.id.ssid);
                String text = tv.getText().toString();
               String name= String.valueOf(listView.getItemIdAtPosition(position));


                Intent intent= new Intent(view.getContext(),DeviceDetailActivity.class);
                intent.putExtra("espName",text );
                startActivityForResult(intent,position);


            }
        });
        scanWifi();








    }
    private void scanWifi(){

        Mac.clear();
        images.clear();
        ssid.clear();
        registerReceiver(wifiReceiver,new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifiManager.startScan();
        Toast.makeText(this,"scanning Wifi...",Toast.LENGTH_SHORT).show();

    }
    BroadcastReceiver wifiReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            results=wifiManager.getScanResults();
            unregisterReceiver(this);
            for( ScanResult scanResult:results){
                //86:F3:EB:0C:84:B8
                //86:F3:EB:0C:83:8A
                // if (scanResult.BSSID.equals("86:F3:EB:0C:84:B8")||scanResult.BSSID.equals("86:F3:EB:0C:83:8A"))
                if (scanResult.SSID.equals("ESP1")||scanResult.SSID.equals("ESP2"))
                ssid.add(scanResult.SSID);
                images.add(R.drawable.esp);
                Mac.add(scanResult.BSSID);
                customerAdapter.notifyDataSetChanged();
            }

        }
    };
    class CustomerAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return images.size();
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
            if(ssid!=null&&ssid.size()!=0&&position<ssid.size()){
            View view=getLayoutInflater().inflate(R.layout.row,null);
            ImageView imageView=(ImageView)view.findViewById(R.id.espLogo);
            TextView MacView=(TextView)view.findViewById(R.id.macAdress);
            TextView ssidView=(TextView)view.findViewById(R.id.ssid);
            imageView.setImageResource(images.get(position));
            MacView.setText(Mac.get(position));
            ssidView.setText(ssid.get(position));

            return view;
        }
        return null;}

    }
}
