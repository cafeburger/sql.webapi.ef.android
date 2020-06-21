package com.cafeburger.demoapiandroid.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import com.cafeburger.demoapiandroid.R;
import com.cafeburger.demoapiandroid.model.Customer;

import java.util.zip.Inflater;

public class CustomerAdapter extends BaseAdapter {

    private Context context;
    private Customer[] customers;
    public CustomerAdapter(Context context, Customer[] customers){
        this.context = context;
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
        EditText edtCustomerID = convertView.findViewById(R.id.edtCustomerID);
        EditText edtCompanyName = convertView.findViewById(R.id.edtCompanyName);
        edtCustomerID.setText(customers[position].getCustomerID());
        edtCompanyName.setText(customers[position].getCompanyName());
        return convertView;
    }
}
