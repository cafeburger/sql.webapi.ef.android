package com.example.demoapiandroid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.demoapiandroid.model.Customer;
import com.example.demoapiandroid.service.CustomerService;
import com.example.demoapiandroid.utility.CallbackHandler;
import com.example.demoapiandroid.utility.CustomerAdapter;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Customer[] customers;
    private CustomerService customerService ;
    private CustomerAdapter customerAdapter;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        customerService = new CustomerService(this);
         listView = findViewById(R.id.listview);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent detailIntent = new Intent(MainActivity.this,DetailActivity.class);

                detailIntent.putExtra("CustomerID", customers[i].getCustomerID());
                detailIntent.putExtra("CompanyName", customers[i].getCompanyName());
                detailIntent.putExtra("ContactName", customers[i].getContactName());
                detailIntent.putExtra("ContactTitle", customers[i].getContactTitle());
                detailIntent.putExtra("Address", customers[i].getAddress());
                detailIntent.putExtra("City", customers[i].getCity());
                detailIntent.putExtra("Region", customers[i].getRegion());
                detailIntent.putExtra("PostalCode", customers[i].getPostalCode());
                detailIntent.putExtra("Country", customers[i].getCountry());
                detailIntent.putExtra("Phone", customers[i].getPhone());
                detailIntent.putExtra("Fax", customers[i].getFax());

                //startActivity(detailIntent);
                startActivityForResult(detailIntent,i);

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                customerService.removeCustomer(customers[i].getCustomerID(), new CallbackHandler<Boolean>() {
                    @Override
                    public void onComplete(Boolean data) {
                        getAllCustomers();
                        Toast.makeText(MainActivity.this,"刪除成功!",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Toast.makeText(MainActivity.this,"刪除失敗: "+errorMessage,Toast.LENGTH_LONG).show();
                    }
                });

                return true;
            }
        });

      getAllCustomers();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            Toast.makeText(this,"更新成功!",Toast.LENGTH_LONG).show();
        }else if(resultCode == 2){
            Toast.makeText(this,"更新失敗!",Toast.LENGTH_LONG).show();
        }else if(resultCode == RESULT_CANCELED){
            Toast.makeText(this,"更新取消!",Toast.LENGTH_LONG).show();
        }

        customerService.getAllCustomers(new CallbackHandler<Customer[]>() {
            @Override
            public void onComplete(Customer[] customers) {
                MainActivity.this.customers = customers;
                customerAdapter.setCustomers(customers);
                customerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String errorMessage) {

                Log.e(TAG, errorMessage);
            }
        });


    }
    private void getAllCustomers(){
        customerService.getAllCustomers(new CallbackHandler<Customer[]>() {
            @Override
            public void onComplete(Customer[] customers) {
                MainActivity.this.customers = customers;

                customerAdapter = new CustomerAdapter(MainActivity.this, customers);
                listView.setAdapter(customerAdapter);
            }

            @Override
            public void onError(String errorMessage) {

                Log.e(TAG, errorMessage);
            }
        });
    }
}
