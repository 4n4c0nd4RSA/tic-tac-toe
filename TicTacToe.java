import java.util.Scanner;
import java.util.ArrayList;
public class TicTacToe{
	private static Scanner input = new Scanner(System.in);
	private static Player[] player = new Player[2]; //not dynamic
	private static ArrayList<Player> dynamicPlayers = new ArrayList<Player>(); //dynamic
	private static GameBoard myBoard = new GameBoard();
	private static Player winner=null;
	private static int playerTurn=0;
	private static boolean draw = false;
	public static void main(String[] args){
		String move="";
		boolean legalMove = false;
		int xCoord=0;
		int yCoord=0;
		System.out.println("Player1 Name: ");
		String player1Name = input.next();
		System.out.println("");
		System.out.println("Player2 Name: ");
		String player2Name = input.next();
		System.out.println("");
		System.out.println("Board Size: ");
		int boardSize = input.nextInt();
		System.out.println("");
		player[0] = new Player(player1Name,'X');
		player[1] = new Player(player2Name,'O');
		myBoard = new GameBoard(boardSize);
		String playerAnswer = "";
		while(!playerAnswer.toLowerCase().contains("n")){
			playerAnswer = "";
			draw=false;
			winner=null;
			myBoard.clear();
			while(winner==null&&!draw){
				System.out.println("\n"+myBoard);
				while(!legalMove){
					System.out.println(player[playerTurn].getName()+"'s Turn ("+player[playerTurn].getSymbol()+") \nInput as co-ordiantes \"x,y\"\n");
					try{
						move = input.next();
						System.out.println("");
						xCoord = Integer.parseInt(move.substring(0,move.indexOf(',')))-1;
						yCoord = Integer.parseInt(move.substring(move.indexOf(',')+1))-1;
						if(myBoard.getSymbol(xCoord,yCoord)==' '&&xCoord<myBoard.getSize()&&yCoord<myBoard.getSize()){
							legalMove=true;
						}
						else{
							System.out.println("\n-----------------\nIllegal Move\n-----------------\n");
							System.out.println(myBoard);
						}
					}
					catch(Exception ex){
						System.out.println("\n-----------------\nIllegal Move\n-----------------\n");
						System.out.println(myBoard);
					}				
				}
				if(move.indexOf(',')>0){
					myBoard.addSymbol(player[playerTurn].getSymbol(),xCoord,yCoord);
					legalMove=false;		
				}
				winner = determineWinner();
				if(winner!=null){
					player[playerTurn].increaseScore();	
				}
				playerTurn++;
				if(playerTurn>=player.length){
					playerTurn=0;
				}
			}
			if(draw){
				System.out.println(myBoard+"\nDraw!\n\nPlay another round? (type \"yes\" or \"no\")");
			}
			else{
			System.out.println(myBoard+"\nWinner!\n"+winner+"\n\nPlay another round? (type \"yes\" or \"no\")");
			}
			playerAnswer = input.next();
			myBoard.clear();
		}
	}
	
	private static boolean checkHorizontal(int diagPos, char symbol){
		boolean output = true;
		char test = ' ';
		for(int i = 0; i < myBoard.getSize(); i++){
			test = myBoard.getSymbol(i,diagPos);
			if(test!=symbol){
				output = false;
				break;
			}
		}
		return output;
	}
	
	private static boolean checkVertical(int diagPos, char symbol){
		boolean output = true;
		char test = ' ';
		for(int i = 0; i < myBoard.getSize(); i++){
			test = myBoard.getSymbol(diagPos,i);
			if(test!=symbol){
				output = false;
				break;
			}
		}
		return output;
	}
	
	private static boolean checkDiagonal1(char symbol){
		boolean output = true;
		char test = ' ';
		for(int i = 0; i < myBoard.getSize(); i++){
			test = myBoard.getSymbol(i,i);
			if(test!=symbol){
				output = false;
				break;
			}
		}
		return output;
	}
	
	private static boolean checkDiagonal2(char symbol){
		boolean output = true;
		char test = ' ';
		for(int i = 0; i < myBoard.getSize(); i++){
			test = myBoard.getSymbol(myBoard.getSize()-1-i,i);
			if(test!=symbol){
				output = false;
				break;
			}
		}
		return output;
	}
		
	private static Player determineWinner(){
		Player output = null;
		char playerSymbol;
		draw = true;
		int boardSize = myBoard.getSize();
		boolean winning = false;
		playerSymbol = player[playerTurn].getSymbol();
		for(int j = 0; j < boardSize; j++){
			for(int k = 0; k < boardSize; k++){
				if(myBoard.getSymbol(j,k)==' '){
					draw = false;
				}
				if(myBoard.getSymbol(j,k)==playerSymbol){
					winning = checkHorizontal(j,playerSymbol);
					if(winning){
						output = player[playerTurn];
						draw = false;
						break;
					}				
					winning = checkVertical(j,playerSymbol);
					if(winning){
						output = player[playerTurn];
						draw = false;		
						break;
					}
					winning = checkDiagonal1(playerSymbol);
					if(winning){
						output = player[playerTurn];	
						draw = false;	
						break;
					}
					winning = checkDiagonal2(playerSymbol);
					if(winning){
						output = player[playerTurn];
						draw = false;			
						break;
					}
				}
			}
		}
	
		return output;
	}	
}
