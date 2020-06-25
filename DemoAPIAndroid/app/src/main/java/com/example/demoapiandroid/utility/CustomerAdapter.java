package com.example.demoapiandroid.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.demoapiandroid.R;
import com.example.demoapiandroid.model.Customer;


public class CustomerAdapter extends BaseAdapter {

    private Context context;
    private Customer[] customers;
    public CustomerAdapter(Context context, Customer[] customers){
        this.context = context;
        this.customers = customers;
    }
    public void setCustomers(Customer[] customers){
        this.customers = customers;
    }
    @Override
    public int getCount() {
        return customers.length;
    }

    @Override
    public Object getItem(int position) {
        return customers[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_item,parent,false);
        }
        TextView edtCustomerID = convertView.findViewById(R.id.edtCustomerID);
        TextView edtCompanyName = convertView.findViewById(R.id.edtCompanyName);
        edtCustomerID.setText(customers[position].getCustomerID());
        edtCompanyName.setText(customers[position].getCompanyName());
        return convertView;
    }
}
