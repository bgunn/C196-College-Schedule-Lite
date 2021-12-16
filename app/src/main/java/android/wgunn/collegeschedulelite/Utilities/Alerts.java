package android.wgunn.collegeschedulelite.Utilities;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.wgunn.collegeschedulelite.R;
import android.wgunn.collegeschedulelite.UI.AddEditAssessmentActivity;
import android.wgunn.collegeschedulelite.UI.CourseDetailActivity;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Alerts extends BroadcastReceiver {

    public void createAlert(Context context, int requestCode, long time, String type, int typeId, String title, String message) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, Alerts.class);
        intent.putExtra("type", type);
        intent.putExtra("typeId", typeId);
        intent.putExtra("title", title);
        intent.putExtra("message", message);
        intent.putExtra("requestCode", requestCode);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
    }

    public void cancelAlert(Context context, int requestCode) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, Alerts.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.cancel(pendingIntent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        String type = intent.getStringExtra("type");
        int typeId = intent.getIntExtra("typeId", 0);
        String title = intent.getStringExtra("title");
        String message = intent.getStringExtra("message");
        int requestCode = intent.getIntExtra("requestCode", 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("CSL", "CSLChannel", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("CSL notifications channel");
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        // Set the notification tap action
        Intent destination = null;

        switch (type) {

            case "course":
                destination = new Intent(context, CourseDetailActivity.class);
                destination.putExtra("courseId", typeId);
                break;

            case "assessment":
                destination = new Intent(context, AddEditAssessmentActivity.class);
                destination.putExtra("assessmentId", typeId);
                break;
        }

        destination.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivities(context, 0, new Intent[]{destination}, 0);

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "CSL")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(requestCode, builder.build());
    }
}
