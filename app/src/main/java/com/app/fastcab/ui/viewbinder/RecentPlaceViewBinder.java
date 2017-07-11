package com.app.fastcab.ui.viewbinder;

import android.app.Activity;
import android.graphics.Typeface;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.fastcab.R;
import com.app.fastcab.entities.LocationEnt;
import com.app.fastcab.ui.viewbinders.abstracts.ViewBinder;

import java.util.StringTokenizer;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 7/11/2017.
 */

public class RecentPlaceViewBinder extends ViewBinder<LocationEnt> {
    public RecentPlaceViewBinder() {
        super(R.layout.row_item_history);
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(LocationEnt entity, int position, int grpPosition, View view, Activity activity) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        StringTokenizer tokens = new StringTokenizer(entity.getAddress(), ",");
        String first = tokens.nextToken();
        viewHolder.text1.setText(first);
       // viewHolder.text1.setAlpha(0.7f);
       // viewHolder.text1.setPaintFlags(Typeface.BOLD);
        viewHolder.text2.setText(entity.getAddress());
        viewHolder.text2.setPaintFlags((Typeface.BOLD));
        viewHolder.imgIcon.setImageDrawable(view.getContext().getResources().getDrawable(R.drawable.adress_recent));
    }

    public static class ViewHolder extends ViewBinder.BaseViewHolder {
        @BindView(R.id.img_icon)
        ImageView imgIcon;
        @BindView(R.id.text1)
        TextView text1;
        @BindView(R.id.text2)
        TextView text2;


        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}