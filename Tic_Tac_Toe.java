import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.Scanner;


public class Tic_Tac_Toe {
	
	public class Piece{
		private int player;
		private int xpos;
		private int ypos;
		
		public Piece(int c){
			player=c;
		}
		
		public int getPlayer(){
			return player;
		}
		
		public int getXpos(){
			return xpos;
		}
		
		public int getYpos(){
			return ypos;
		}
	
		public void setX(int x){
			xpos=x;
		}
		
		public void setY(int y){
			ypos=y;
		}
	}
	
	private Piece[][]board;
	private int size;
	
	public Tic_Tac_Toe(int s){
		board=new Piece[s][s];
		size=s;
	}
	
	public Piece getPieceAt(int x,int y){
		return board[x][y];
	}
	
	public void playpiece(int p, int x, int y){
		Piece n=new Piece(p);
		n.setX(x);
		n.setY(y);
		board[x][y]=n;
		
	}
	
	private boolean checkhorzWin(Piece p){
		int x=p.getXpos();
		for(int i=0;i<size;i++){
			if(board[x][i]==null||board[x][i].getPlayer()!=p.getPlayer())
				return false;
		}
		return true;
	}
	
	private boolean checkvertWin(Piece p){
		int y=p.getYpos();
		for(int i=0;i<size;i++){
			if(board[i][y]==null||board[i][y].getPlayer()!=p.getPlayer())
				return false;
		}
		return true;
	}
	
	private boolean checkdiagWin(Piece p, int diag){
		

		if(diag==1){
			for(int i=0;i<size;i++){
				
				if(board[size-1-i][i]==null || board[size-1-i][i].getPlayer()!=p.getPlayer())
					return false;
			}
			
		
			return true;
		}
		else{
			for(int i=0;i<size;i++){
				if(board[i][i]==null || board[i][i].getPlayer()!=p.getPlayer())
					return false;
			}
			return true;
		}

	}
	
	public boolean checkWin(int x,int y){
		Piece p=getPieceAt(x,y);
		if(checkhorzWin(p) || checkvertWin(p))
			return true;
		if((x==size-1 && y==0) || (x==0 && y==size-1) || (x==1 && y==1)){
			return checkdiagWin(p,1);
		}
		if((x==0 && y==0) || (x==size-1 && y==size-1) || (x==1 && y==1))
			return checkdiagWin(p,2);
		
		return false;
		
	}
	
	
	
	public void showboard(){
		for(int i=0;i<size;i++){
			for(int j=0;j<size;j++){
				if(board[i][j]==null){
					System.out.print(" ");
				}
				else if(board[i][j].getPlayer()==1)
					System.out.print("X");
				else
					System.out.print("O");
				
				if(j<size-1)
					System.out.print("|");
			
				
			}
			if(i<size-1){
			System.out.println();
			System.out.println("------");
			}
		}
		System.out.println();
		System.out.println();
	}
	
	public boolean checkBounds(int x, int y){
		if(x<0 || x>=size || y<0 || y>=size)
			return false;
		return true;
	}
	
	public void resetBoard(){
		for(int i=0;i<size;i++){
			for(int j=0;j<size;j++){
				board[i][j]=null;
			}
		}
	}
	
	public void saveGame(int player, int count) throws IOException{
		File f=new File("Save-file.txt");
		FileWriter writer=new FileWriter (f);
		for(int i=0;i<size;i++){
			for(int j=0;j<size;j++){
				if(board[i][j]==null)
					writer.write("n");
				if(board[i][j].getPlayer()==1)
					writer.write("1");
				if(board[i][j].getPlayer()==2)
					writer.write("2");
			}
			writer.write("\n");
		}
		writer.write(player+" "+count);
		writer.flush();
		writer.close();
	}
	
	public void loadGame() throws FileNotFoundException{
		 FileReader fileReader = new FileReader("Save-file.txt");
		 BufferedReader bufferedReader = new BufferedReader(fileReader);
		 String s=null;
		 for(int i=0;i<=size;i++){
			 try {
				s=bufferedReader.readLine();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			 if(i!=3){
				 for(int j=0;j<size;j++){
				 	if(s.charAt(j)=='n')
					 	board[i][j]=null;
				 	else{
					 	Piece p=new Piece(Integer.parseInt(s.substring(j,j+1)));
					 	p.setX(i);
					 	p.setY(j);
					 	board[i][j]=p;
				 	}
			 	}
			 }
		 }
	}
	public static void main(String [] args){
		Tic_Tac_Toe game=new Tic_Tac_Toe(3);

		boolean winner=false;
		int player=1;
		Scanner reader=new Scanner(System.in);
		int count=0;
		String save;
		while(!winner && count<game.size*game.size){

			if(player==1){
				
				int px,py;
				System.out.println("Save game?(y or n)");
				save=reader.nextLine();
				if(save.equals("y")){
					
				}
				System.out.println("Where do you wanna move?");
				System.out.print("X:");
				px=reader.nextInt();
				System.out.print("Y:");
				py=reader.nextInt();
				if(game.checkBounds(px,py) && game.getPieceAt(px, py)==null){
					game.playpiece(1,px,py);
					game.showboard();
					player=2;
					count++;
					if(game.checkWin(px, py)){
						winner=true;
						player=1;
					}
				}
				else{
					System.out.println("Move not available");
				}
			}
			else if(player==2){
				
				Random rand=new Random();
				int xpos=rand.nextInt(3);
				int ypos=rand.nextInt(3);
				if(game.getPieceAt(xpos, ypos)==null){
					game.playpiece(2,xpos,ypos);
					
					game.showboard();
					player=1;
					count++;
					
					if(game.checkWin(xpos, ypos)){
						winner=true;
						player=2;
					}
				}
			}
		}
		if(winner){
			System.out.println("Player "+player+" Wins!!");
		}
		else{
			System.out.println("There is a tie");
		}
		
	}
}
