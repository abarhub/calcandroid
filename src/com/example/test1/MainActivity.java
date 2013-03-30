package com.example.test1;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	private static final String TAG = "MyActivityHello";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button bouton;
        bouton=(Button) findViewById(R.id.button1);
        if(bouton!=null)
        {
        	bouton.setOnClickListener(new Button.OnClickListener(){

				@Override
				public void onClick(View arg0) {
					Log.w(TAG, "Click bouton");
				}
        		
        	});
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
}
