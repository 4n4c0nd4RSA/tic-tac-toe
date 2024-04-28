public class Player{
	private String name = "";
	private int score = 0;
	private char symbol;
	
	public Player(){
		this("noname",'-');
	}
	
	public Player(String name, char symbol){
		setName(name);
		setSymbol(symbol);
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void setSymbol(char symbol){
		this.symbol = symbol;
	}
	
	public char getSymbol(){
		return symbol;
	}
	
	public void setScore(int score){
		this.score = score;
	}
	
	public int getScore(){
		return score;
	}
	
	public void increaseScore(){
		score++;
	}
	
	public void decreaseScore(){
		score--;
	}
	
	public String toString(){
		return "Name: "+getName()+" ("+getSymbol()+") \t Score: "+getScore();
	}
}
