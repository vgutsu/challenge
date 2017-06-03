package com.populi.chalange;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

class MyHeaderViewHolder extends RecyclerView.ViewHolder {

    public final TextView tvItem;

    public MyHeaderViewHolder(View itemView) {
        super(itemView);

        tvItem = (TextView) itemView.findViewById(R.id.city);
    }
}