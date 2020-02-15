package in.srijanju.androidapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Date;
import java.util.Map;

import in.srijanju.androidapp.view.MainActivity;

public class SrijanMessagingService extends FirebaseMessagingService {

  @Override
  public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
	super.onMessageReceived(remoteMessage);

	Log.d("Received", remoteMessage.getData().toString());

	final RemoteMessage.Notification notification = remoteMessage.getNotification();
	// Check if message contains a notification payload.
	if (notification != null) {
	  createNotificationChannel();
	  getBitmapAsyncAndDoWork(String.valueOf(notification.getImageUrl()), notification, remoteMessage);
	}
  }

  private void getBitmapAsyncAndDoWork(final String imageUrl,
									   final RemoteMessage.Notification notification,
									   final RemoteMessage remoteMessage) {
	Log.d("image", imageUrl);
	if (imageUrl.equals("null")) {
	  displayImageNotification(null, notification, remoteMessage);
	  return;
	}
	Log.d("loading", imageUrl);
	final Bitmap[] bitmap = {null};
	Glide.with(getApplicationContext())
			.asBitmap()
			.load(imageUrl)
			.into(new CustomTarget<Bitmap>() {
			  @Override
			  public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
				bitmap[0] = resource;
				Log.d("loaded", imageUrl);
				displayImageNotification(bitmap[0], notification, remoteMessage);
			  }

			  @Override
			  public void onLoadCleared(@Nullable Drawable placeholder) {
			  }
			});
  }

  private void displayImageNotification(Bitmap largeImage,
										RemoteMessage.Notification notification, RemoteMessage remoteMessage) {
	Intent intent = new Intent(this, MainActivity.class);
	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
	// Check if message contains a data payload.
	if (remoteMessage.getData().size() > 0) {
	  Bundle extras = new Bundle();
	  Map<String, String> data = remoteMessage.getData();
	  for (String key : data.keySet()) {
		extras.putString(key, data.get(key));
	  }
	  intent.putExtras(extras);
	}
	PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

	NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "updates")
			.setSmallIcon(R.drawable.ic_launcher)
			.setContentTitle(!"".equals(notification.getTitle()) ? notification.getTitle() :
					"Srijan '20")
			.setContentText(notification.getBody())
			.setStyle(new NotificationCompat.BigPictureStyle()
					.bigLargeIcon(null)
					.bigPicture(largeImage)
					.setSummaryText(notification.getBody()))
			.setPriority(NotificationCompat.PRIORITY_DEFAULT)
			.setColor(getApplicationContext().getResources().getColor(R.color.colorAccent))
			.setLargeIcon(largeImage != null ? largeImage :
					BitmapFactory.decodeResource(getApplicationContext().getResources(),
							R.drawable.ic_launcher))
			.setContentIntent(pendingIntent)
			.setAutoCancel(true);

	NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
	notificationManager.notify((int) new Date().getTime(), builder.build());
  }

  private void createNotificationChannel() {
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
	  CharSequence name = "Updates";
	  String description = "Updates of Srijan '20";
	  int importance = NotificationManager.IMPORTANCE_DEFAULT;
	  NotificationChannel channel = new NotificationChannel("updates", name, importance);
	  channel.setDescription(description);
	  NotificationManager notificationManager = getSystemService(NotificationManager.class);
	  if (notificationManager != null)
		notificationManager.createNotificationChannel(channel);
	}
  }
}
