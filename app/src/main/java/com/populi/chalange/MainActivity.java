package com.populi.chalange;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.populi.chalange.presenter.CountryPresenter;
import com.populi.chalange.rest.response.model.Country;
import com.populi.chalange.view.MvpView;

import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

public class MainActivity extends AppCompatActivity implements MvpView<Country> {

    private CountryPresenter presenter;
    private SectionedRecyclerViewAdapter sectionAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sectionAdapter = new SectionedRecyclerViewAdapter();
        // Add your Sections
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(sectionAdapter);


        if (presenter == null) {
            presenter = new CountryPresenter(this, this);
        }

        findViewById(R.id.fetchDataButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.getData();
            }
        });
    }

    @Override
    public void showLoadingView() {
        findViewById(R.id.progress).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetryView() {
        // no needed for now
    }

    @Override
    public void showRetry() {
        // no needed
    }

    @Override
    public void hideLoading() {
        findViewById(R.id.progress).setVisibility(View.GONE);
    }

    @Override
    public void renderWithData(List<Country> list) {
        for (Country c : list) {
            sectionAdapter.addSection(new MySection(c));
        }
        // Set up your RecyclerView with the SectionedRecyclerViewAdapter
        sectionAdapter.notifyDataSetChanged();
    }
}
