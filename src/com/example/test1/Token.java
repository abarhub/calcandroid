package com.example.test1;

public class Token {

	enum TypeToken{Operateur,NombreEntier,NombreReel};
	private TypeToken type_token;
	private char operateur;
	private int entier;
	private double reel;
	
	public Token(Token token) {
		type_token=token.type_token;
		operateur=token.operateur;
		entier=token.entier;
		reel=token.entier;
	}
	
	public Token(char operateur) {
		type_token=TypeToken.Operateur;
		this.operateur=operateur;
	}

	public Token(int entier) {
		type_token=TypeToken.NombreEntier;
		this.entier=entier;
	}

	public Token(double reel) {
		type_token=TypeToken.NombreReel;
		this.reel=reel;
	}
	
	public void setOperateur(char operateur){
		type_token=TypeToken.Operateur;
		this.operateur=operateur;
	}
	
	public void setEntier(int entier){
		type_token=TypeToken.NombreEntier;
		this.entier=entier;
	}
	
	public void setReel(double reel){
		type_token=TypeToken.NombreReel;
		this.reel=reel;
	}

	public TypeToken getType_token() {
		return type_token;
	}

	public char getOperateur() {
		return operateur;
	}

	public int getEntier() {
		return entier;
	}

	public double getReel() {
		return reel;
	}
	
	public String toString()
	{
		switch(type_token)
		{
		case Operateur:
			return "op:"+operateur;
		case NombreEntier:
			return "nument:"+entier;
		case NombreReel:
			return "numrel:"+reel;
		}
		return "Inconnu";
	}
}
