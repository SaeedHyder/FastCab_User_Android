package com.app.fastcab.entities;

/**
 * Created by saeedhyder on 6/21/2017.
 */

public class NotificationEnt {

    String Notificationlogo;
    String NotificationTxt;

    public NotificationEnt(String Notificationlogo,String NotificationTxt)
    {
        setNotificationlogo(Notificationlogo);
        setNotificationTxt(NotificationTxt);

    }

    public String getNotificationlogo() {
        return Notificationlogo;
    }

    public void setNotificationlogo(String notificationlogo) {
        Notificationlogo = notificationlogo;
    }

    public String getNotificationTxt() {
        return NotificationTxt;
    }

    public void setNotificationTxt(String notificationTxt) {
        NotificationTxt = notificationTxt;
    }
}
