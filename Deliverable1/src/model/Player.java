package model;
/**
 * This is a POJO class for players which includes following methods
 * getName();
 * getToken(); 
 * getScore();
 * resetScore();
 * @version 1.0
 * */
public class Player {
	protected String name;
	protected String token;
	protected int score = 0;

	/** 
	 * Constructor - which sets current player name and mark. 
	 * @param name Current player name 
	 * @param token Current player mark
	 * */
	public Player(String name, String token){
		this.name = name;
		this.token = token;
	}
	
	/**
	 *  Getter for property name.
	 * @return String Name of current player. 
	 * */
	public String getName(){ return name; }
	
	/**
	 *  Getter for property mark.
	 * @return String mark of current player. 
	 * */
	public String getToken(){ return token; }
	
	/** 
	 *  Getter for property score.
	 * @return Integer score of current player. 
	 * */
	public int getScore(){ return score; }
	
	/**
	 *  Setter for property score. 
	 * */
	public void resetScore(){ this.score = 0;}
}
