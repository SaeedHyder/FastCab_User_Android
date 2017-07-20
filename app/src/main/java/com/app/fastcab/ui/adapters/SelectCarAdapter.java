package com.app.fastcab.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.app.fastcab.R;
import com.app.fastcab.activities.DockActivity;
import com.app.fastcab.entities.SelectCarEnt;
import com.app.fastcab.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.app.fastcab.R.id.imageView;

/**
 * Created by saeedhyder on 7/14/2017.
 */

public class SelectCarAdapter extends RecyclerView.Adapter<SelectCarAdapter.ViewHolder> {

    DockActivity context;
    private ImageLoader imageLoader;
    private ArrayList<SelectCarEnt> selectCarList;
    private int SelectedItemPosition = 0;

    public SelectCarAdapter(ArrayList<SelectCarEnt> selectCarList,DockActivity context) {
        this.selectCarList = selectCarList;
        this.context=context;
        imageLoader = ImageLoader.getInstance();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_selectcar, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        SelectCarEnt entity = selectCarList.get(position);
        Picasso.with(context).load(entity.getVehicleImageOne()).into(holder.ivCarImage);
       // imageLoader.displayImage(entity.getVehiclePictureOne(),holder.ivCarImage);
        holder.txtCarTypeName.setText(entity.getType());
        holder.txtCarTypeName.setTextColor(context.getResources().getColor(R.color.gray_dark));
        holder.ivCarImage.setBackground(context.getResources().getDrawable(R.drawable.circle_unactive));
        holder.ivCarImage.getLayoutParams().width = (int)context.getResources().getDimension(R.dimen.x40);
        holder.ivCarImage.getLayoutParams().height = (int)context.getResources().getDimension(R.dimen.x40);

        if (position == selectCarList.size()-1){
            holder.ivRightLine.setVisibility(View.GONE);
       }
        if (position == 0){
            holder.ivLeftLine.setVisibility(View.INVISIBLE);
        }
        if (position == SelectedItemPosition){
            holder.txtCarTypeName.setTextColor(context.getResources().getColor(R.color.button_color));
            holder.ivCarImage.setBackground(context.getResources().getDrawable(R.drawable.circle_blue));
            Picasso.with(context).load(entity.getVehicleImageTwo()).into(holder.ivCarImage);
            //imageLoader.displayImage(entity.getVehicleImageTwo(),holder.ivCarImage);
            holder.ivCarImage.getLayoutParams().width = (int)context.getResources().getDimension(R.dimen.x45);
            holder.ivCarImage.getLayoutParams().height = (int)context.getResources().getDimension(R.dimen.x45);

            /*RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    (int)context.getResources().getDimension(R.dimen.x45),
                    (int)context.getResources().getDimension(R.dimen.x45)
            );
            params.setMargins(0, 0, 0, (int)context.getResources().getDimension(R.dimen.x5_));
            holder.ivCarImage.setLayoutParams(params);*/

        }

            holder.ivCarImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int old = SelectedItemPosition;
                    SelectedItemPosition = position;
                    notifyItemChanged(position);
                    notifyItemChanged(old);
                }
            });



    }
    public SelectCarEnt getSelectedItemPosition(){
        return selectCarList.get(SelectedItemPosition);
    }

    @Override
    public int getItemCount() {
        return selectCarList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.iv_rightLine)
        ImageView ivRightLine;
        @BindView(R.id.iv_leftLine)
        ImageView ivLeftLine;
        @BindView(R.id.txt_carTypeName)
        AnyTextView txtCarTypeName;
        @BindView(R.id.iv_carImage)
        ImageView ivCarImage;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
