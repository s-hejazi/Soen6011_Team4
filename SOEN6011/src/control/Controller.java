package control;
import javax.swing.SwingUtilities;

import model.Player;
import ui.GameBoard;
import ui.PlayerOptionMenu;
public class Controller {

	static Player player1;
	static Player player2;
	
	public Controller(String name1, String name2, String mark1, String mark2){
		player1 = new Player(name1, mark1);
		player2 = new Player(name2, mark2);
		new GameBoard().main();
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				new PlayerOptionMenu().setVisible(true);
			}
		});
	}
	//TODO: set direction
	public static String getPlayer1Name(){
		return player1.getName();
	}
	public static String getPlayer2Name(){
		return player2.getName();
	}
	public static String getPlayer1Mark(){
		return player1.getToken();
	}
	public static String getPlayer2Mark(){
		return player2.getToken();
	}
}
