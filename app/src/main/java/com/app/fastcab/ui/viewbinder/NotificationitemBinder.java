package com.app.fastcab.ui.viewbinder;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;


import com.app.fastcab.R;
import com.app.fastcab.entities.NotificationEnt;
import com.app.fastcab.ui.viewbinders.abstracts.ViewBinder;
import com.app.fastcab.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;


public class NotificationitemBinder extends ViewBinder<NotificationEnt> {



    public NotificationitemBinder() {
        super(R.layout.notification_item);
    }


    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new NotificationitemBinder.ViewHolder(view);
    }

    @Override
    public void bindView(final NotificationEnt entity, int position, int grpPosition, View view, Activity activity) {

        NotificationitemBinder.ViewHolder viewHolder = (NotificationitemBinder.ViewHolder) view.getTag();

        viewHolder.iv_Notificationlogo.setImageResource(entity.getNotificationlogo());
        viewHolder.txt_Notification.setText(entity.getNotificationTxt());


    }

    public static class ViewHolder extends BaseViewHolder {

        private ImageView iv_Notificationlogo;
        private AnyTextView txt_Notification;

        public ViewHolder(View view) {

            iv_Notificationlogo=(ImageView)view.findViewById(R.id.iv_Notificationlogo);
            txt_Notification=(AnyTextView)view.findViewById(R.id.txt_Notification);

        }
    }
}
