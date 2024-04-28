public class GameBoard{
	private char[][] board;
	
	public GameBoard(int size){
		if(size>0){
			board = new char[size][size];
		}
		else{
			board = new char[2][2];
		}
		for(int i = 0; i < board[0].length; i++){
			for(int j = 0; j < board.length;j++){
				board[j][i] = ' ';
			}
		}
	}
	
	public GameBoard(){
		this(3);
	}
	
	public void addSymbol(char symbol, int x,int y){
		if(x<board.length && y<board[0].length){
			board[x][y] = symbol;
		}
	}
	
	public void removeSymbol(int x, int y){
		if(x<board.length && y<board[0].length){
			board[x][y] = ' ';
		}
	}
	
	public char getSymbol(int x, int y){
		char output = ' ';
		if(x<board.length && y<board[0].length){
			output = board[x][y];
		}
		return output;
	}
	
	public int getSize(){
		return board.length;
	}
	
	public void clear(){
		for(int i = 0; i < board[0].length; i++){
			for(int j = 0; j < board.length;j++){
				board[j][i] = ' ';
			}
		}
	}
	
	public String toString(){
		String output = "";
		for(int i = 0; i < board[0].length; i++){
			output += "|";
			for(int j = 0; j < board.length;j++){
				output += board[j][i]+"|";
			}
			output +="\n";
		}
		
		return output;
	}
}
