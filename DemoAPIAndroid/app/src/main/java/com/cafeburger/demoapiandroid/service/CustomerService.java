package com.cafeburger.demoapiandroid.service;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cafeburger.demoapiandroid.model.Customer;
import com.cafeburger.demoapiandroid.utility.CallbackHandler;
import com.google.gson.Gson;

public class CustomerService {
    private final static String TAG = "CustomerService";
    private RequestQueue queue;

    public CustomerService(Context context) {
        queue = Volley.newRequestQueue(context);
    }

    public void getAllCustomers(final CallbackHandler<Customer[]> handler) {
        String url = "https://demoapi20200621194452.azurewebsites.net/api/customer/getallcustomers";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Customer[] customers = new Gson().fromJson(response, Customer[].class);
                handler.onComplete(customers);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handler.onError(error.getMessage());
            }
        });
        queue.add(stringRequest);

    }

}
