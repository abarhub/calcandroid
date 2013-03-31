package com.example.test1;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

public class Outils {

	private static final String TAG = "Outils";
	
	public static void appelActivity(Activity activity,String nomClasse){
		Intent explicit=new Intent();
		String pkg="";
		Log.w(TAG, "appel activity:"+nomClasse);
		if(nomClasse.contains("."))
		{
			int pos;
			pos=nomClasse.lastIndexOf(".");
			if(pos>0)
			{
				pkg=nomClasse.substring(0, pos);
			}
		}
		Log.w(TAG, "appel activity:pkg="+pkg);
		explicit.setClassName(pkg, nomClasse);
		Log.w(TAG, "appel activity:appel");
		activity.startActivity(explicit);
	}
}
