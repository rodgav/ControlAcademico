package com.nationfis.controlacademicononfc.Services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.nationfis.controlacademicononfc.Activitys.NavigationActivity;
import com.nationfis.controlacademicononfc.R;

import java.util.Random;

import static com.nationfis.controlacademicononfc.Activitys.NavigationActivity.NOTIFICACION;

/*
 * Created by GlobalTIC's on 30/01/2018.
 */

public class FireBaseServiceNotificacion extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String titulo = remoteMessage.getData().get("titulo");
        String fecha = remoteMessage.getData().get("fecha");
        String mensaje = remoteMessage.getData().get("mensaje");
        Notificacion(mensaje);
        ShowNotificacion(titulo,fecha,mensaje);
    }
    private void Notificacion(String mensaje){
        Intent intent = new Intent(NOTIFICACION);
        intent.putExtra("mensaje",mensaje);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }
    private void ShowNotificacion(String titulo, String fecha, String mensaje){
        Intent intent = new Intent(this, NavigationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        String NOTIFICATION_CHANNEL_ID = "canal01";
        Builder builder = new Builder(this, NOTIFICATION_CHANNEL_ID);
        builder.setAutoCancel(true);
        builder.setContentTitle("Asignatura: "+titulo);
        builder.setContentText("Se le registro: "+mensaje);
        builder.setSubText("fecha: "+fecha);
        builder.setSound(uri);
        //builder.setTicker("ticker");
        builder.setSmallIcon(R.drawable.icon);
        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert notificationManager != null;

        Random random  = new Random();

        notificationManager.notify(random.nextInt(),builder.build());

    }
}
