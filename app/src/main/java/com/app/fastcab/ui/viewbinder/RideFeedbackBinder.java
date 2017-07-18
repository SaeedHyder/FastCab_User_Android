package com.app.fastcab.ui.viewbinder;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.app.fastcab.R;
import com.app.fastcab.activities.DockActivity;
import com.app.fastcab.entities.RideFeedbackEnt;
import com.app.fastcab.ui.viewbinders.abstracts.ViewBinder;
import com.app.fastcab.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.app.fastcab.R.id.drivingTick;

/**
 * Created by saeedhyder on 7/18/2017.
 */

public class RideFeedbackBinder extends ViewBinder<RideFeedbackEnt> {

    ImageLoader imageLoader;
    Boolean reasonsBoolean=false;
    DockActivity context;

    public RideFeedbackBinder(DockActivity context) {
        super(R.layout.row_item_ride_feedback);
        imageLoader = ImageLoader.getInstance();
        this.context=context;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(RideFeedbackEnt entity, int position, int grpPosition, View view, Activity activity) {

        final ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.txtReason.setText(entity.getReasons());

        viewHolder.txtReason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!reasonsBoolean) {
                    viewHolder.txtReason.setBackground(context.getResources().getDrawable(R.drawable.blue_rect));
                    viewHolder.drivingTick.setVisibility(View.VISIBLE);
                    viewHolder.txtReason.setTextColor(context.getResources().getColor(R.color.white));
                    viewHolder.txtReason.setAlpha(1);
                    reasonsBoolean = true;
                } else {
                    viewHolder.txtReason.setBackground(context.getResources().getDrawable(R.drawable.black_border));
                    viewHolder.drivingTick.setVisibility(View.GONE);
                    viewHolder.txtReason.setTextColor(context.getResources().getColor(R.color.black));
                    viewHolder.txtReason.setAlpha((float) 0.6);
                    reasonsBoolean = false;
                }
            }
        });


    }


    static class ViewHolder extends BaseViewHolder{
        @BindView(R.id.txt_reason)
        AnyTextView txtReason;
        @BindView(R.id.drivingTick)
        ImageView drivingTick;
        @BindView(R.id.ll_reason)
        RelativeLayout llReason;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
