package com.cafeburger.demoapiandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.cafeburger.demoapiandroid.model.Customer;
import com.cafeburger.demoapiandroid.service.CustomerService;
import com.cafeburger.demoapiandroid.utility.CallbackHandler;
import com.cafeburger.demoapiandroid.utility.CustomerAdapter;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listView = findViewById(R.id.listview);

        CustomerService customerService = new CustomerService(this);
        customerService.getAllCustomers(new CallbackHandler<Customer[]>() {
            @Override
            public void onComplete(Customer[] customer) {
                CustomerAdapter customerAdapter = new CustomerAdapter(MainActivity.this,customer);
                listView.setAdapter(customerAdapter);
            }

            @Override
            public void onError(String errorMessage) {
                Log.e(TAG, errorMessage);
            }
        });

    }
}
