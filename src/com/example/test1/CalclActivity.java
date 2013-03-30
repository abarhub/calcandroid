package com.example.test1;

import java.util.LinkedHashSet;
import java.util.Set;

import com.example.test1.Token.TypeToken;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CalclActivity extends Activity {

	private static final String TAG = "MyActivityHello";
	
	public enum EtatSaisie {Debut,Nombre,Operateur,Resultat,Erreur};
	private String texte_courant="0";
	private String calcul_courant="";
	private EtatSaisie etat_saisie;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calcl);
		
		init_boutons();
	}

	private void init_boutons() {
		Set<Integer> set;
		int i;
		set=new LinkedHashSet<Integer>();
		set.add(R.id.button1);
		set.add(R.id.button2);
		set.add(R.id.button3);
		set.add(R.id.button4);
		set.add(R.id.button5);
		set.add(R.id.button6);
		set.add(R.id.button7);
		set.add(R.id.button8);
		set.add(R.id.button9);
		//for(int i=1;i<=9;i++)
		i=1;
		for(int n:set)
		{
			final int no=i;
			final Button button = (Button) findViewById(n);
	        button.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	                ajoute(no);
	            }
	        });
	        i++;
		}
		{
			final Button button = (Button) findViewById(R.id.button13);
	        button.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	                ajoute('+');
	            }
	        });
		}
		{
			final Button button = (Button) findViewById(R.id.button14);
	        button.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	                ajoute('-');
	            }
	        });
		}
		{
			final Button button = (Button) findViewById(R.id.button15);
	        button.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	                ajoute('*');
	            }
	        });
		}
		{
			final Button button = (Button) findViewById(R.id.button16);
	        button.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	                ajoute('/');
	            }
	        });
		}
		{
			final Button button = (Button) findViewById(R.id.button11);
	        button.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	                ajoute('.');
	            }
	        });
		}
		{
			final Button button = (Button) findViewById(R.id.button12);
	        button.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	                ajoute('=');
	            }
	        });
		}
		{
			final Button button = (Button) findViewById(R.id.Button01);
	        button.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	                ajoute('X');
	            }
	        });
		}
		{
			final Button button = (Button) findViewById(R.id.Button02);
	        button.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	                ajoute('C');
	            }
	        });
		}
		{
			final Button button = (Button) findViewById(R.id.Button03);
	        button.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	                ajoute('.');
	            }
	        });
		}
		{
			final Button button = (Button) findViewById(R.id.Button04);
	        button.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	                ajoute(')');
	            }
	        });
		}
		{
			final Button button = (Button) findViewById(R.id.Button05);
	        button.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	                ajoute('(');
	            }
	        });
		}
		final TextView result=(TextView) findViewById(R.id.textView2);
		final TextView result2=(TextView) findViewById(R.id.textView1);
		etat_saisie=EtatSaisie.Debut;
		texte_courant="0";
		calcul_courant="";
		result.setText(calcul_courant);
		result2.setText(texte_courant);
	}
	
	private void ajoute(int i) {
		final TextView result=(TextView) findViewById(R.id.textView1);
		Log.w(TAG, "ajout "+i+" ...");
		//if(texte.equals("0"))
			//texte="";
		if(etat_saisie==EtatSaisie.Debut||etat_saisie==EtatSaisie.Resultat||etat_saisie==EtatSaisie.Erreur)
		{
			texte_courant=""+i;
			etat_saisie=EtatSaisie.Nombre;
		}
		else if(etat_saisie==EtatSaisie.Nombre)
		{
			texte_courant+=""+i;
			etat_saisie=EtatSaisie.Nombre;
		}
		else
		{// opérateur
			texte_courant=""+i;
			etat_saisie=EtatSaisie.Nombre;
		}
		//valeur_courante=valeur_courante*10.0+i;
		result.setText(texte_courant);
		Log.w(TAG, "ajout "+i+" fait");
	}
	
	private void ajoute(char c) {
		final TextView result=(TextView) findViewById(R.id.textView2);
		final TextView result2=(TextView) findViewById(R.id.textView1);

		Log.w(TAG, "ajout "+c+" ...");
		if(c=='C')
		{// CE
			texte_courant="0";
			etat_saisie=EtatSaisie.Debut;
		}
		else if(c=='X')
		{// C
			calcul_courant="";
			texte_courant="0";
			etat_saisie=EtatSaisie.Debut;
		}
		else if(c=='.')
		{// C
			if(etat_saisie==EtatSaisie.Nombre)
			{
				texte_courant+=".";
				etat_saisie=EtatSaisie.Nombre;
			}
			else
			{
				texte_courant="0.";
				etat_saisie=EtatSaisie.Nombre;
			}
		}
		else if(c=='=')
		{// egal
			Token tmp;
			AnalyseExpression exp;
			//m = calcul_expr();
			//valeur_precedante=m;
			//texte=""+valeur_precedante;
			if(etat_saisie==EtatSaisie.Nombre)
			{
				calcul_courant+=texte_courant;
			}
			exp=new AnalyseExpression();
			tmp=exp.evalue(calcul_courant);
			if(tmp!=null&&tmp.getType_token()==TypeToken.NombreEntier)
			{
				texte_courant=""+tmp.getEntier();
				etat_saisie=EtatSaisie.Resultat;
			}
			else if(tmp!=null&&tmp.getType_token()==TypeToken.NombreReel)
			{
				texte_courant=""+tmp.getReel();
				etat_saisie=EtatSaisie.Resultat;
			}
			else
			{// erreur
				texte_courant="Err";
				etat_saisie=EtatSaisie.Erreur;
			}
		}
		else if(c=='('||c==')')
		{// parenthese ouvrante ou fermante
			if(etat_saisie==EtatSaisie.Nombre)
			{
				calcul_courant+=texte_courant+c;
			}
			else
			{
				calcul_courant+=c;
			}
			texte_courant="0";
			etat_saisie=EtatSaisie.Operateur;
		}
		else
		{// operateur
			if(etat_saisie==EtatSaisie.Resultat||etat_saisie==EtatSaisie.Erreur||etat_saisie==EtatSaisie.Operateur)
			{
				calcul_courant+=c;
			}
			else
			{
				calcul_courant+=texte_courant+c;
			}
			texte_courant="0";
			etat_saisie=EtatSaisie.Operateur;
		}
		//result.setText(texte);
		result.setText(calcul_courant);
		result2.setText(texte_courant);
		Log.w(TAG, "ajout "+c+" fait");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_calcl, menu);
		return true;
	}

}
