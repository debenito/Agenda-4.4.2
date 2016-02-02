package com.example.benito.agenda_442;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

public class Listin extends ActionBarActivity {
    public Button makeCall;
    public Button endCall;
    public Intent callIntent;
    public TextView numTelf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listin);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         makeCall = (Button)findViewById(R.id.button1);
        endCall = (Button)findViewById(R.id.button2);

        numTelf = (TextView) findViewById(R.id.numTelf);
        numTelf.append(getPhoneNumber()+"serial number");

        makeCall.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                call();
            }
        });

        endCall.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                end();
            }
        });
    }
    /** Called when the activity is first created. */


    private void call() {
        try {
            callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:600965300"));
            startActivity(callIntent);
        } catch (ActivityNotFoundException activityException) {
            Log.e("dialing-example", "Call failed", activityException);
        }
    }

    private void end() {
        try{
            this.finish();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    private String getPhoneNumber(){
        TelephonyManager mTelephonyManager;
        mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        return mTelephonyManager.getSimSerialNumber();
    }
}
