import java.util.Scanner;
import java.util.ArrayList;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.DataInputStream;
import java.io.OutputStreamWriter;

public class TicTacToeLAN{
	private static Scanner input = new Scanner(System.in);
	private static Player[] player = new Player[2];
	private static GameBoard myBoard = new GameBoard();
	private static Player winner=null;
	private static int playerTurn=0;
	private static boolean draw = false;
	private static ServerSocket myServerSocket;
	private static DataInputStream is;
	private static PrintStream os;
	private static int port;
	public static void main(String[] args){
		String move="";
		String networkMessage="";
		boolean legalMove = false;
		int xCoord=0;
		int yCoord=0;
		int boardSize=0;
		System.out.println("Player Name: ");
		String player1Name = input.next();
		String player2Name = "";
		System.out.println("");
		System.out.println("LAN Option (enter only number)\n1. Host\n2. Client");
		BufferedReader in = null;
		int selection = input.nextInt();
			System.out.println("");
		if(selection==1){
			System.out.println("Board Size: ");
			boardSize = input.nextInt();
			System.out.println("");
			System.out.println("Please enter port number: ");
			port = input.nextInt();
			System.out.println("");
			try{
				myServerSocket = new ServerSocket(port);
				System.out.println("Waiting for client...");
				try{
					Socket clientSocket = myServerSocket.accept();
					is = new DataInputStream(clientSocket.getInputStream());
					os = new PrintStream(clientSocket.getOutputStream());
					networkMessage = is.readLine();
					player2Name = networkMessage;	
					System.out.println(player2Name+" connected\n");				
				}
				catch(Exception ex){
					System.out.println(ex.toString());
				}	
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
						os.println(myBoard);
						while(!legalMove){
							System.out.println(player[playerTurn].getName()+"'s Turn ("+player[playerTurn].getSymbol()+") \nInput as co-ordiantes \"x,y\"\n");
							os.println(player[playerTurn].getName()+"'s Turn ("+player[playerTurn].getSymbol()+") \nInput as co-ordiantes \"x,y\"\n");
							try{
								if(playerTurn==0){
									move = input.next();
								}
								else{									
									os.println(".turn:"+playerTurn);
									move = is.readLine();
								}
								System.out.println("");
								os.println("");
								xCoord = Integer.parseInt(move.substring(0,move.indexOf(',')))-1;
								yCoord = Integer.parseInt(move.substring(move.indexOf(',')+1))-1;
								if(myBoard.getSymbol(xCoord,yCoord)==' '&&xCoord<myBoard.getSize()&&yCoord<myBoard.getSize()){
									legalMove=true;
								}
								else{
									System.out.println("\n-----------------\nIllegal Move: "+player[playerTurn].getName()+"\n-----------------\n");
									os.println("\n-----------------\nIllegal Move: "+player[playerTurn].getName()+"\n-----------------\n");
									System.out.println(myBoard);
									os.println(myBoard);
								}
							}
							catch(Exception ex){
								System.out.println("\n-----------------\nIllegal Move: "+player[playerTurn].getName()+"\n-----------------\n");
								os.println("\n-----------------\nIllegal Move: "+player[playerTurn].getName()+"\n-----------------\n");
								System.out.println(myBoard);
								os.println(myBoard);
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
						os.println(myBoard+"\nDraw!\n\nWaiting for server to restart the game");
					}
					else{
					System.out.println(myBoard+"\nWinner!\n"+winner+"\n\nPlay another round? (type \"yes\" or \"no\")");
					os.println(myBoard+"\nWinner!\n"+winner+"\n\nWaiting for server to restart the game");
					}
					playerAnswer = input.next();
					myBoard.clear();
				}
			}
			catch(Exception ex){
				System.out.println("Error creating server on port: "+port+"\n"+ex.toString());
			}
			finally{
				try{
					myServerSocket.close();
				}
				catch(Exception ex){
				}
			}
		}
		else if(selection==2){
			System.out.println("Server IP: ");
			String ip = input.next();
			System.out.println("");
			System.out.println("Server Port: ");
			port = input.nextInt();
			System.out.println("");
			MyClient clientService = new MyClient(ip,port);
			clientService.connect();
			try{
				clientService.startCient(player1Name);
			}
			catch(Exception ex){
				System.out.println(ex.toString());
			}
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
