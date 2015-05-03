package com.example.messageandroidsmnall;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	Button sendSMS;
	EditText msgTxt, numTxt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		sendSMS = (Button) findViewById(R.id.sendBtn);
		msgTxt = (EditText) findViewById(R.id.message);
		numTxt = (EditText) findViewById(R.id.numberTxt);
		sendSMS.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String myMsg = msgTxt.getText().toString();
				String theNumber = numTxt.getText().toString();
				sendMsg(theNumber, "yo asshole!!!  " + myMsg + " Good Bye fucker!!!");
			}
		});
	}

	protected void sendMsg(String theNumber, String myMsg) {
		// TODO Auto-generated method stub
		String SENT = "Message Sent";
		String DELIVERED = "Message Delivered";
		// PendingIntent launches another activity and it performs actions
		// on behalf of our application
		PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(
				SENT), 0);
		PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
				new Intent(DELIVERED), 0);
		registerReceiver(new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
				switch (getResultCode()) {
				case Activity.RESULT_OK:
					Toast.makeText(MainActivity.this, "SMS Sent",
							Toast.LENGTH_LONG).show();/*
													 * yo can't write just
													 * "this" in above because
													 * it will refer to class
													 * broadcastreceiver
													 */
					break;
				case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
					Toast.makeText(MainActivity.this, "Generic failure",
							Toast.LENGTH_LONG).show();
					break;

				case SmsManager.RESULT_ERROR_NO_SERVICE:
					Toast.makeText(MainActivity.this, "No service",
							Toast.LENGTH_LONG).show();
					break;
				}
			}
		}, new IntentFilter(SENT));

		registerReceiver(new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
				switch (getResultCode()) {
				case Activity.RESULT_OK:
					Toast.makeText(MainActivity.this, "SMS delivered",
							Toast.LENGTH_LONG).show();
					break;
				case Activity.RESULT_CANCELED:
					Toast.makeText(MainActivity.this, "SMS not delivered",
							Toast.LENGTH_LONG).show();
					break;
				}
			}
		}, new IntentFilter(DELIVERED));
		SmsManager sms = SmsManager.getDefault();// select android.telephony not
													// gsm
		sms.sendTextMessage(theNumber, null, myMsg, sentPI, deliveredPI);
	}

}
