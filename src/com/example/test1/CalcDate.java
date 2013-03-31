package com.example.test1;

import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CalcDate extends Activity {

	private static final String TAG = "CalcDate";
	private static final int MY_DATE_DIALOG_ID = 3;
	
	private Date date_debut;
	private int nb_jours;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calc_date);
		
		initialisation();
		calcul_affichage();
		
		final Button button = (Button) findViewById(R.id.choixDateDebut);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	onDateDialogButtonClick(v);
            }
        });
        
        final Button button2 = (Button) findViewById(R.id.calcul);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	calcul_affichage();
            }
        });
	}

	private void initialisation() {
		date_debut=new Date();
		nb_jours=1;
	}
	
	private void calcul_affichage(){
		EditText button = (EditText) findViewById(R.id.choixDateValeur);
		CharSequence s;
		s=DateFormat.format("dd/MM/yyyy", date_debut);
		button.setText(s);
		
		EditText edit=(EditText) findViewById(R.id.choixjours);
		//edit.setText(""+nb_jours);
		int nb_jours=0;
		s=edit.getText();
		if(s!=null&&s.length()>0)
		{
			int nb=0;
			try{
				nb=Integer.parseInt(s.toString());
				nb_jours=nb;
			}catch(Exception e){
				// format incorrecte;
			}
		}
		
		TextView res=(TextView) findViewById(R.id.resultat);
		Calendar cal;
		Date d;
		cal=Calendar.getInstance();
		cal.setTime(date_debut);
		cal.add(Calendar.DAY_OF_YEAR, nb_jours);
		d=cal.getTime();
		s=DateFormat.format("dd/MM/yyyy", d);
		res.setText(s);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_calc_date, menu);
		return true;
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch(id)
		{
		case MY_DATE_DIALOG_ID:
		    DatePickerDialog dateDlg = new DatePickerDialog(this,
		         new DatePickerDialog.OnDateSetListener() {
		         public void onDateSet(DatePicker view, int year,
		                                             int monthOfYear, int dayOfMonth)
		         {
		                    Time chosenDate = new Time();
		                    chosenDate.set(dayOfMonth, monthOfYear, year);
		                    long dtDob = chosenDate.toMillis(true);
		                    CharSequence strDate = DateFormat.format("dd/MM/yyyy", dtDob);
		                    /*Toast.makeText(SuperSimpleDialogsActivity.this,
		                         "Date picked: " + strDate, Toast.LENGTH_SHORT).show();*/
		                    Log.w(TAG, "choix date:"+strDate);
		                    date_debut=new Date(dtDob);
		                    calcul_affichage();
		                    
		        }}, date_debut.getYear(),date_debut.getMonth(),date_debut.getDate());
		      dateDlg.setMessage("Date ?");
		      return dateDlg;
		}
		return super.onCreateDialog(id);
	}
	
	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		switch(id)
		{
		case MY_DATE_DIALOG_ID:
		     DatePickerDialog dateDlg = (DatePickerDialog) dialog;
		     int iDay,iMonth,iYear;
		     /*Calendar cal = Calendar.getInstance();
		     iDay = cal.get(Calendar.DAY_OF_MONTH);
		     iMonth = cal.get(Calendar.MONTH);
		     iYear = cal.get(Calendar.YEAR);*/
		     iDay=date_debut.getDay();
		     iMonth=date_debut.getMonth();
		     iYear=date_debut.getYear();
		     dateDlg.updateDate(iYear, iMonth, iDay);
		     break;
		}
		super.onPrepareDialog(id, dialog);
	}
	
	public void onDateDialogButtonClick(View v) {
	     showDialog(MY_DATE_DIALOG_ID);
	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.w(TAG, "menu calcdate");
		switch(item.getItemId())
		{
		case R.id.diffdate:
			Log.w(TAG, "appel diff date");
			return true;
		case R.id.calcnombre:
			Log.w(TAG, "appel calc nombre");
			Outils.appelActivity(this,"com.example.test1.CalclActivity");
			return true;
		}
		return false;
	}

}
