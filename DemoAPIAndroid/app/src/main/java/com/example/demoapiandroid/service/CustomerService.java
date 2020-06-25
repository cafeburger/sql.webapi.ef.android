package com.example.demoapiandroid.service;

import android.content.Context;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.demoapiandroid.model.Customer;
import com.example.demoapiandroid.utility.CallbackHandler;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

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

    public void updateCustomer(Customer customer, final CallbackHandler<Boolean> handler){
        String url = "https://demoapi20200621194452.azurewebsites.net/api/customer/updatecustomer";
        try {
            JSONObject parameter = new JSONObject( new Gson().toJson(customer));
            JsonObjectRequest jsonRequest = new JsonObjectRequest(url, parameter, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    handler.onComplete(true);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    handler.onError(error.getMessage());
                }
            });
            queue.add(jsonRequest);
        } catch (JSONException e) {
            handler.onError(e.getMessage());
        }
    }
    public void removeCustomer(final String customerId, final CallbackHandler<Boolean> handler){
        String url = "https://demoapi20200621194452.azurewebsites.net/api/customer/removecustomer";
        url+="?customerId="+customerId;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                handler.onComplete(true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String responseBody = new String(error.networkResponse.data, "utf-8");
                    handler.onError(responseBody);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    handler.onError(e.getMessage());
                }

            }
        }) ;
        queue.add(stringRequest);
    }
}
