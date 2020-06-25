package com.example.demoapiandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.demoapiandroid.model.Customer;
import com.example.demoapiandroid.service.CustomerService;
import com.example.demoapiandroid.utility.CallbackHandler;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";
    private EditText edtCustomerID;
    private EditText edtCompanyName;
    private EditText edtContactName;
    private EditText edtContactTitle;
    private EditText edtAddress;
    private EditText edtCity;
    private EditText edtRegion;
    private EditText edtPostalCode;
    private EditText edtCountry;
    private EditText edtPhone;
    private EditText edtFax;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        edtCustomerID = findViewById(R.id.edtCustomerId);
        edtCompanyName= findViewById(R.id.edtCompanyName);
        edtContactName= findViewById(R.id.edtContactName);
        edtContactTitle= findViewById(R.id.edtContactTitle);
        edtAddress= findViewById(R.id.edtAddress);
        edtCity= findViewById(R.id.edtCity);
        edtRegion= findViewById(R.id.edtRegion);
        edtPostalCode= findViewById(R.id.edtPostalCode);
        edtCountry= findViewById(R.id.edtCountry);
        edtPhone= findViewById(R.id.edtPhone);
        edtFax= findViewById(R.id.edtFax);
        
        Intent detailIntent =  getIntent();

        edtCustomerID.setText(detailIntent.getCharSequenceExtra("CustomerID"));
        edtCustomerID.setEnabled(false);
        edtCompanyName.setText(detailIntent.getCharSequenceExtra("CompanyName"));
        edtContactName.setText(detailIntent.getCharSequenceExtra("ContactName"));
        edtContactTitle.setText(detailIntent.getCharSequenceExtra("ContactTitle"));
        edtAddress.setText(detailIntent.getCharSequenceExtra("Address"));
        edtCity.setText(detailIntent.getCharSequenceExtra("City"));
        edtRegion.setText(detailIntent.getCharSequenceExtra("Region"));
        edtPostalCode.setText(detailIntent.getCharSequenceExtra("PostalCode"));
        edtCountry.setText(detailIntent.getCharSequenceExtra("Country"));
        edtPhone.setText(detailIntent.getCharSequenceExtra("Phone"));
        edtFax.setText(detailIntent.getCharSequenceExtra("Fax"));

    }

    public void onUpdateButtonClicked(View view){
        Customer customer = new Customer();
        customer.setCustomerID(edtCustomerID.getText().toString());
        customer.setCompanyName(edtCompanyName.getText().toString());
        customer.setContactName(edtContactName.getText().toString());
        customer.setContactTitle(edtContactTitle.getText().toString());
        customer.setAddress(edtAddress.getText().toString());
        customer.setCity(edtCity.getText().toString());
        customer.setRegion(edtRegion.getText().toString());
        customer.setPostalCode(edtPostalCode.getText().toString());
        customer.setCountry(edtCountry.getText().toString());
        customer.setPhone(edtPhone.getText().toString());
        customer.setFax(edtFax.getText().toString());
        CustomerService customerService = new CustomerService(this);
        customerService.updateCustomer(customer, new CallbackHandler<Boolean>() {
            @Override
            public void onComplete(Boolean data) {
                Log.d(TAG, "Update ressult: "+data);
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onError(String errorMessage) {
                Log.e(TAG, errorMessage);
                setResult(2);
                finish();
            }
        });
    }

    public void onCancelButtonClicked(View view){
        setResult(RESULT_CANCELED);
        finish();
    }


}
