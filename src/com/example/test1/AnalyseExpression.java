package com.example.test1;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

import com.example.test1.Token.TypeToken;

import android.util.Log;

public class AnalyseExpression {

	private static final String TAG = "AnalyseExpression";
	private static final boolean active_log=true;
	
	public AnalyseExpression()
	{
		
	}
	
	public List<Token> decoupe(String expr){
		List<Token> res=null;
		boolean fin=false;
		int pos,nb,i;
		char c;
		Token tmp;
		if(active_log&&Log.isLoggable(TAG, Log.WARN))
		{
			Log.w(TAG, "analyse expression : '"+expr+"'");
		}
		
		if(expr!=null&&expr.trim().length()>0)
		{
			//res=new ArrayList<Token>();
			//res=new CopyOnWriteArrayList<Token>();
			res=new Vector<Token>();
			expr=expr.trim();
			pos=0;
			while(!fin&&pos<expr.length())
			{
				c=expr.charAt(pos);
				if(Character.isDigit(c))
				{
					nb=c-'0';
					i=pos+1;
					while(i<expr.length()&&Character.isDigit(expr.charAt(i)))
					{
						nb=nb*10+(expr.charAt(i)-'0');
						i++;
					}
					if(i<expr.length()&&expr.charAt(i)=='.')
					{
						double m;
						int n=1,nb2;
						m=nb;
						i++;
						while(i<expr.length()&&Character.isDigit(expr.charAt(i)))
						{
							nb2=expr.charAt(i)-'0';
							m=m+(nb2/Math.pow(10, n));
							i++;
							n++;
						}
						tmp=new Token(m);
					}
					else
					{
						tmp=new Token(nb);
					}
					res.add(tmp);
					pos=i;
				}
				else
				{
					tmp=new Token(c);
					res.add(tmp);
					pos++;
				}
			}
		}
		if(active_log&&Log.isLoggable(TAG, Log.WARN))
		{
			Log.w(TAG, "analyse expression resultat : '"+res+"'");
		}
		return res;
	}
	
	public Token evalue(List<Token> liste_token)
	{
		Token res=null;
		char []op1=new char[]{'*','/'};
		char []op2=new char[]{'+','-'};
		
		if(active_log&&Log.isLoggable(TAG, Log.WARN))
		{
			Log.w(TAG, "evaluation expression : "+copy(liste_token));
		}
		if(liste_token!=null&&!liste_token.isEmpty())
		{
			if(est_valide(liste_token))
			{
				simplifie(liste_token);
				//simplifie(liste_token,op1);
				//simplifie(liste_token,op2);
				
				if(liste_token.size()==1)
				{
					Token tmp = liste_token.get(0);
					if(tmp!=null&&tmp.getType_token()!=TypeToken.Operateur)
					{
						res=tmp;
					}
					else
					{
						if(active_log&&Log.isLoggable(TAG, Log.WARN))
						{
							Log.w(TAG, "Erreur token invalide:"+tmp);
						}
					}
				}
				else
				{
					if(active_log&&Log.isLoggable(TAG, Log.WARN))
					{
						Log.w(TAG, "Erreur liste <>1:"+copy(liste_token));
					}
				}
			}
			else
			{
				if(active_log&&Log.isLoggable(TAG, Log.WARN))
				{
					Log.w(TAG, "Erreur liste invalide:"+copy(liste_token));
				}
			}
		}
		else
		{
			if(active_log&&Log.isLoggable(TAG, Log.WARN))
			{
				Log.w(TAG, "Erreur:liste vide");
			}
		}
		if(active_log&&Log.isLoggable(TAG, Log.WARN))
		{
			Log.w(TAG, "resultat:"+res);
		}
		
		return res;
	}
	
	private String copy(List<Token> liste) {
		/*if(liste==null)
		{
			return null;
		}
		else
		{
			List<Token> res;
			res=new Vector<Token>(liste);
			return res.toString();
		}*/
		return ""+liste;
	}

	private void simplifie(List<Token> liste_token) {
		Token t;
		int debut,fin,j;
		char []op1=new char[]{'*','/'};
		char []op2=new char[]{'+','-'};
		if(active_log&&Log.isLoggable(TAG, Log.WARN))
		{
			Log.w(TAG, "Simplification parenthese debut:"+copy(liste_token));
		}
		for(int i=liste_token.size()-1;i>=0;i--)
		{
			t=liste_token.get(i);
			if(est_operateur(t)&&t.getOperateur()=='(')
			{
				debut=i;
				fin=-1;
				if(active_log&&Log.isLoggable(TAG, Log.WARN))
				{
					Log.w(TAG, "simplification parenthese:recherche parenthese fermante "+i);
				}
				for(j=i+1;j<liste_token.size();j++)
				{
					if(est_operateur(liste_token.get(j))&&liste_token.get(j).getOperateur()==')')
					{
						fin=j;
						if(active_log&&Log.isLoggable(TAG, Log.WARN))
						{
							Log.w(TAG, "simplification parenthese:recherche parenthese fermante trouve "+j);
						}
						break;
					}
				}
				if(fin>-1)
				{
					List<Token> tmp;
					//tmp=liste_token.subList(debut+1, fin);
					// suppression des parentheses
					tmp=new ArrayList<Token>(liste_token.subList(debut+1, fin));
					//liste_token.remove(fin);
					//liste_token.remove(debut);
					if(active_log&&Log.isLoggable(TAG, Log.WARN))
					{
						Log.w(TAG, "simplification parenthese:simplification de "+copy(tmp));
					}
					simplifie(tmp,op1);
					simplifie(tmp,op2);
					if(active_log&&Log.isLoggable(TAG, Log.WARN))
					{
						Log.w(TAG, "simplification parenthese:resultat simplification "+copy(tmp));
					}
					if(tmp.size()==1)
					{
						for(int no=fin;no>debut;no--)
							liste_token.remove(no);
						liste_token.set(debut,tmp.get(0));
						//liste_token.remove(fin);
						//liste_token.remove(debut);
						if(active_log&&Log.isLoggable(TAG, Log.WARN))
						{
							Log.w(TAG, "simplification parenthese:resultat simplification (sans parenthese) "+copy(tmp));
						}
					}
					else
					{
						if(active_log&&Log.isLoggable(TAG, Log.WARN))
						{
							Log.w(TAG, "Erreur simplification parenthese:"+copy(liste_token)+","+debut+","+fin+","+copy(tmp));
						}
						return;
					}
				}
				else
				{
					if(active_log&&Log.isLoggable(TAG, Log.WARN))
					{
						Log.w(TAG, "Erreur simplification parenthese fermante non trouve:"+copy(liste_token)+","+t+","+i);
					}
					return;
				}
			}
		}
		if(active_log&&Log.isLoggable(TAG, Log.WARN))
		{
			Log.w(TAG, "Simplification parenthese fin:"+copy(liste_token));
		}
		simplifie(liste_token,op1);
		simplifie(liste_token,op2);
		if(active_log&&Log.isLoggable(TAG, Log.WARN))
		{
			Log.w(TAG, "Simplification parenthese fin2:"+copy(liste_token));
		}
	}

	private boolean est_valide(List<Token> liste_token) {
		boolean precedent_operateur,precedant_nombre,
			debut,est_nombre,est_operateur,
			est_pouvrante,est_pfermante,
			precedant_pouvrante,precedant_pfermante;
		int nb_parenthese_ouvrante,nb_parenthese_fermante;
		if(liste_token==null||liste_token.isEmpty())
		{
			return false;
		}
		if(liste_token.size()==1)
		{
			return est_nombre(liste_token.get(0));
		}
		debut=true;
		precedant_nombre=false;
		precedent_operateur=false;
		nb_parenthese_ouvrante=0;
		nb_parenthese_fermante=0;
		precedant_pouvrante=false;
		precedant_pfermante=false;
		for(Token t:liste_token)
		{
			est_nombre=est_nombre(t);
			est_operateur=est_operateur(t);
			est_pouvrante=est_operateur&&t.getOperateur()=='(';
			est_pfermante=est_operateur&&t.getOperateur()==')';
			if(!debut)
			{
				if(est_nombre&&precedant_nombre)
					return false;
				if(est_operateur&&precedent_operateur)
				{
					if(est_pouvrante)
					{
						if(precedant_pfermante)
							return false;
					}
					else if(est_pfermante)
					{
						if(!precedant_pfermante)
							return false;
					}
					else
					{
						if(!precedant_pfermante)
							return false;
					}
				}
			}
			if(est_operateur)
			{
				if(t.getOperateur()=='(')
					nb_parenthese_ouvrante++;
				if(t.getOperateur()==')')
					nb_parenthese_fermante++;
			}
			if(nb_parenthese_ouvrante<nb_parenthese_fermante)
				return false;
			precedant_nombre=est_nombre;
			precedent_operateur=est_operateur;
			precedant_pouvrante=est_pouvrante;
			precedant_pfermante=est_pfermante;
			debut=false;
		}
		if(nb_parenthese_ouvrante!=nb_parenthese_fermante)
			return false;
		return true;
	}

	public Token evalue(String expr)
	{
		Token res=null;
		List<Token> liste_token;
		
		if(active_log&&Log.isLoggable(TAG, Log.WARN))
		{
			Log.w(TAG, "evaluation expression :"+expr);
		}
		liste_token=decoupe(expr);
		if(liste_token!=null&&!liste_token.isEmpty())
		{
			res=evalue(liste_token);
		}
		if(active_log&&Log.isLoggable(TAG, Log.WARN))
		{
			Log.w(TAG, "evaluation expression resultat:"+res);
		}
		
		return res;
	}

	private void simplifie(List<Token> liste_token, char[] op) {
		char c;
		if(liste_token!=null&&!liste_token.isEmpty()&&op!=null&&op.length>0)
		{
			if(active_log&&Log.isLoggable(TAG, Log.WARN))
			{
				Log.w(TAG, "simplifie:"+copy(liste_token)+",op="+op[0]);
			}
			for(int i=0;i<liste_token.size();i++)
			{
				if(est_operateur(liste_token.get(i)))
				{
					c=liste_token.get(i).getOperateur();
					for(char c2:op)
					{
						if(c2==c)
						{
							if(i>0&&i+1<liste_token.size()
								&&est_nombre(liste_token.get(i-1))
								&&est_nombre(liste_token.get(i+1)))
							{
								Token t1,t2;
								t1=liste_token.get(i-1);
								t2=liste_token.get(i+1);
								if(t1.getType_token()==TypeToken.NombreEntier&&t2.getType_token()==TypeToken.NombreEntier)
								{
									int n=0;
									switch(c)
									{
									case '*':
										n=t1.getEntier()*t2.getEntier();
										break;
									case '/':
										if(t2.getEntier()==0)
											n=0;
										else
											n=t1.getEntier()/t2.getEntier();
										break;
									case '+':
										n=t1.getEntier()+t2.getEntier();
										break;
									case '-':
										n=t1.getEntier()-t2.getEntier();
										break;
									}
									t1.setEntier(n);
								}
								else
								{
									double n1,n2,n=0.0;
									if(t1.getType_token()==TypeToken.NombreEntier)
									{
										n1=t1.getEntier();
									}
									else
									{
										n1=t1.getReel();
									}
									if(t2.getType_token()==TypeToken.NombreEntier)
									{
										n2=t2.getEntier();
									}
									else
									{
										n2=t2.getReel();
									}
									switch(c)
									{
									case '*':
										n=n1*n2;
										break;
									case '/':
										if(Math.abs(n2)<=0.00001)
											n=0.0;
										else
											n=n1/n2;
										break;
									case '+':
										n=n1+n2;
										break;
									case '-':
										n=n1-n2;
										break;
									}
									t1.setReel(n);
								}
								liste_token.remove(i+1);
								liste_token.remove(i);
								i-=2;
							}
							break;
						}
					}
				}
			}
			if(active_log&&Log.isLoggable(TAG, Log.WARN))
			{
				Log.w(TAG, "simplifie resultat:"+copy(liste_token)+",op="+op[0]);
			}
		}
	}
	
	private boolean est_operateur(Token t)
	{
		if(t!=null&&t.getType_token()==TypeToken.Operateur)
			return true;
		return false;
	}
	
	private boolean est_nombre(Token t)
	{
		if(t!=null&&(t.getType_token()==TypeToken.NombreEntier||t.getType_token()==TypeToken.NombreReel))
			return true;
		return false;
	}
}
