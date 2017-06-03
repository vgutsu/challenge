package com.populi.chalange;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.populi.chalange.rest.response.model.City;
import com.populi.chalange.rest.response.model.Country;
import com.populi.chalange.rest.response.model.Tour;

import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

class MySection extends StatelessSection {


    private final List<City> cities;
    private final String coutryName;

    public MySection(Country country) {
        // call constructor with layout resources for this Section header and items 
        super(R.layout.section_header, R.layout.section_item);

        this.cities=country.getCities();
        this.coutryName= country.getName();
    }

    @Override
    public int getContentItemsTotal() {
        return cities.size(); // number of items of this section
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        // return a custom instance of ViewHolder for the items of this section
        return new MyItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyItemViewHolder itemHolder = (MyItemViewHolder) holder;
        // bind your view here
        itemHolder.tvItem.setText(cities.get(position).getName());
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new MyHeaderViewHolder(view);
    }
    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        MyHeaderViewHolder headerHolder = (MyHeaderViewHolder) holder;
        // bind your header view here
        headerHolder.tvItem.setText(coutryName);
    }

}