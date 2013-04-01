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

public class DiffCalc extends Activity {

	private static final String TAG = "DiffDate";
	private static final int MY_DATE_DIALOG_ID_DEBUT = 4;
	private static final int MY_DATE_DIALOG_ID_FIN = 5;
	
	private Date date_debut;
	private Date date_fin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_diff_calc);
		
		initialisation();
		calcul_affichage(false);
		
		final Button button = (Button) findViewById(R.id.choixDateDebutDiff);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	onDateDialogButtonClickDebut(v);
            }
        });

		final Button button3 = (Button) findViewById(R.id.choixDateFinDiff);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	onDateDialogButtonClickFin(v);
            }
        });
        
        final Button button2 = (Button) findViewById(R.id.calcdiff);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	calcul_affichage(true);
            }
        });
	}


	private void initialisation() {
		date_debut=new Date();
		date_fin=new Date();
	}
	
	private void calcul_affichage(boolean uniquement_calcul){
		CharSequence s;
		if(!uniquement_calcul)
		{
			EditText button = (EditText) findViewById(R.id.dateDebutDiff);
			s=DateFormat.format("dd/MM/yyyy", date_debut);
			button.setText(s);
	
			EditText button2 = (EditText) findViewById(R.id.dateFinDiff);
			s=DateFormat.format("dd/MM/yyyy", date_fin);
			button2.setText(s);
		}
				
		TextView res=(TextView) findViewById(R.id.resultatDiff);
		Calendar cal;
		Date d;
		long debut,fin,diff;
		int annee,mois,jours;
		debut=date_debut.getTime();
		fin=date_fin.getTime();
		if(fin-debut==0)
		{
			s="00/00/0000";
		}
		else
		{
			Date d3 = new Date(Math.abs(fin-debut));
			jours=d3.getDate();
			mois=d3.getMonth();
			annee=d3.getYear()-70;
			s=jours+"/"+Outils.complete(mois,2)+"/"+Outils.complete(annee,4);
			if(fin<debut)
			{
				s="-"+s;
			}
		}
		res.setText(s);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_diff_calc, menu);
		return true;
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch(id)
		{
		case MY_DATE_DIALOG_ID_DEBUT:
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
		                    Log.w(TAG, "choix date debut :"+strDate);
		                    date_debut=new Date(dtDob);
		                    calcul_affichage(false);
		                    
		        }}, date_debut.getYear(),date_debut.getMonth(),date_debut.getDate());
		      dateDlg.setMessage("Date Début ?");
		      return dateDlg;

		case MY_DATE_DIALOG_ID_FIN:
		    DatePickerDialog dateDlg2 = new DatePickerDialog(this,
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
		                    Log.w(TAG, "choix date fin :"+strDate);
		                    date_fin=new Date(dtDob);
		                    calcul_affichage(false);
		                    
		        }}, date_fin.getYear(),date_fin.getMonth(),date_fin.getDate());
		      dateDlg2.setMessage("Date Fin ?");
		      return dateDlg2;
		}
		return super.onCreateDialog(id);
	}
	
	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		switch(id)
		{
		case MY_DATE_DIALOG_ID_DEBUT:
		     DatePickerDialog dateDlg = (DatePickerDialog) dialog;
		     int iDay,iMonth,iYear;
		     iDay=date_debut.getDay();
		     iMonth=date_debut.getMonth();
		     iYear=date_debut.getYear();
		     dateDlg.updateDate(iYear, iMonth, iDay);
		     break;

		case MY_DATE_DIALOG_ID_FIN:
		     DatePickerDialog dateDlg2 = (DatePickerDialog) dialog;
		     int iDay2,iMonth2,iYear2;
		     iDay2=date_fin.getDay();
		     iMonth2=date_fin.getMonth();
		     iYear2=date_fin.getYear();
		     dateDlg2.updateDate(iYear2, iMonth2, iDay2);
		     break;
		}
		super.onPrepareDialog(id, dialog);
	}
	
	public void onDateDialogButtonClickDebut(View v) {
	     showDialog(MY_DATE_DIALOG_ID_DEBUT);
	}
	
	public void onDateDialogButtonClickFin(View v) {
	     showDialog(MY_DATE_DIALOG_ID_FIN);
	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.w(TAG, "menu diffdate");
		switch(item.getItemId())
		{
		case R.id.calcdate:
			Log.w(TAG, "appel calc date");
			Outils.appelActivity(this,"com.example.test1.CalcDate");
			return true;
		case R.id.calcnombre:
			Log.w(TAG, "appel calc nombre");
			Outils.appelActivity(this,"com.example.test1.CalclActivity");
			return true;
		}
		return false;
	}
}
