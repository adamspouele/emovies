package org.esiea.pouele_nemmene.emovies;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by adams on 29/01/18.
 */

public class MovieUpdate extends BroadcastReceiver {

    public static final String MOVIES_UPDATE = "org.esiea.pouele_nemmene.emovies.MOVIES_UPDATE";

    protected Activity activity = null;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public MovieUpdate(Activity _activity) {
        super();
        this.activity = _activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Received", this.MOVIES_UPDATE);

        this.sendNotification("Terminé", "Téléchargement des films terminé.");
    }

    @Override
    public IBinder peekService(Context myContext, Intent service) {
        return super.peekService(myContext, service);
    }

    public void sendNotification(String title, String message){
        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(this.activity);
        notifBuilder.setSmallIcon(R.drawable.logo)
                .setLargeIcon(BitmapFactory.decodeResource(this.activity.getResources(), R.drawable.logo))
                .setContentTitle(title)
                .setContentText(message);

        int mNotificationId = 2;
        NotificationManager notifyManager =
                (NotificationManager) this.activity.getSystemService(NOTIFICATION_SERVICE);
        notifyManager.notify(mNotificationId, notifBuilder.build());

        Toast.makeText(this.activity.getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}
