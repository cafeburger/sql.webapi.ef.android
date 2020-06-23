package com.ss.recyclerviewdemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import github.nisrulz.recyclerviewhelper.RVHAdapter;
import github.nisrulz.recyclerviewhelper.RVHViewHolder;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ItemViewHolder>
    implements RVHAdapter {

    private final List<String> dataList;

    public MyAdapter(List<String> dataList) {
        this.dataList = dataList;
    }

    @Override
    public int getItemCount() {


        return dataList.size();
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.txt.setText(dataList.get(position));
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view =
            LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_list_item, parent, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onItemDismiss(int position, int direction) {
        remove(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        swap(fromPosition, toPosition);
        return false;
    }

    private void remove(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
    }

    private void swap(int firstPosition, int secondPosition) {
        Collections.swap(dataList, firstPosition, secondPosition);
        notifyItemMoved(firstPosition, secondPosition);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements RVHViewHolder {

        final TextView txt;

        public ItemViewHolder(View itemView) {
            super(itemView);
            txt = itemView.findViewById(R.id.txt);
        }

        @Override
        public void onItemClear() {
            System.out.println("Item is unselected");
        }

        @Override
        public void onItemSelected(int actionstate) {
            System.out.println("Item is selected");
        }
    }
}
