package ui;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import control.Controller;

/**
 * This class to display GUI for 3*3 board, reset game and players turn. 
 * @version 1.0
 * @see ui
 */
public class GameBoard extends GUIParent implements ActionListener{
	
	private JPanel contentPane;
	JButton btnOnGameBoard[] = new JButton[9];
	
	JPanel gameBoardPannel = new JPanel(),
	scoreBoardPannel = new JPanel(),
	playerTurnPannel = new JPanel();
	JLabel lblPlayerMove = new JLabel("");
	URL turnImage;
	Color color1, color2;
	/*
	 * checkPlayer counts number of moves and is used to set turn
	 */
	int checkPlayer=0;
	String turn, name1, name2, mark1, mark2, mark, markColor;
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameBoard frame = new GameBoard();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Constructor to initialize Window, create the board
	 */
	private GameBoard() {
		initialize();
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		contentPane = new JPanel(){	 
		public void paintComponent (Graphics g)
		{
			super.paintComponent(g);
			 try {
				g.drawImage(ImageIO.read(getClass().getResource ("/bg2.jpg")), 0, 0, null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		};
		
		playerTurnPannel.setOpaque(false);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
			
		gameBoardPannel.setBounds(25, 25, 314, 191);
		contentPane.add(gameBoardPannel);
		gameBoardPannel.setLayout(new GridLayout(0, 3, 0, 0));
		//TODO: Remove/include in later deliverables
		JPanel resetPnl = new JPanel();
		JButton reset = new JButton("Reset Board");
		reset.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
					resetBoard();
			}
		});
		resetPnl.setBounds(363, 25, 125, 83);
		resetPnl.add(reset);
		resetPnl.setVisible(true);
		contentPane.add(resetPnl);
		resetPnl.setOpaque(false);
		
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				exitGame();
			}
		});
		
		addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent we){
				exitGame();
			}
		});
		
		setPlayers();
		turnImage = getClass().getResource("/"+mark+".png");
		ImageIcon imageIcon = new ImageIcon (new ImageIcon(turnImage).getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
		lblPlayerMove.setText(turn + "'s turn" );
		lblPlayerMove.setIcon(imageIcon);
		playerTurnPannel.setBounds(363, 160, 250, 95);
		playerTurnPannel.setLayout(null);
		lblPlayerMove.setBounds(10, 11, 250, 60);	
		playerTurnPannel.add(lblPlayerMove);
		contentPane.add(playerTurnPannel);

		/*
		 * create 3*3 board				
		 */
		for(int i = 0 ; i < 9 ; i++){
			btnOnGameBoard[i]=new JButton();		
			btnOnGameBoard[i].setFont(new Font("Tahoma", Font.BOLD, 40));
			btnOnGameBoard[i].addActionListener(this);
			btnOnGameBoard[i].setBackground(new Color(32,22,63));
			gameBoardPannel.add(btnOnGameBoard[i]);			
		}
		
	}
	/**
	 * actionPerformed for each board cell
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton checkClick=(JButton) e.getSource();
		if(checkClick.getText()== ""){
		for (int i = 0; i < 9 ; i++) {
			if(checkClick==btnOnGameBoard[i] && checkPlayer < 9 ){
				if(checkPlayer % 2 == 0){
					btnOnGameBoard[i].setForeground(color1);
					btnOnGameBoard[i].setText(mark1);
					
				}
				else{
					btnOnGameBoard[i].setForeground(color2);
					btnOnGameBoard[i].setText(mark2);
			
				}
				changePlayerTurn();
				checkPlayer++;
			}
		}
		}
		
	}
	
	/**
	 * This method set player names and marks 
	 */
	private void setPlayers(){
		name1 = Controller.getPlayer1Name();
		name2 = Controller.getPlayer2Name();
		mark1 = Controller.getPlayer1Mark();
		mark2 = Controller.getPlayer2Mark();
		color1 = new Color(247,247,242);
		color2 = new Color(246,31,74);
		if(mark1 == "O"){
			color1 = new Color(246,31,74);
			color2 = new Color(247,247,242);
		}
		turn = name1;
		mark = mark1;
	}
	
	/**
	 * Method to switch the turn of player. 
	 */
	private void changePlayerTurn() {
		if(checkPlayer % 2 == 0) {
		turn = name2;
		mark=mark2;
		} 
		else {
		turn = name1;
		mark=mark1;
		}
		turnImage = getClass().getResource("/"+mark+".png");
		ImageIcon imageIcon = new ImageIcon (new ImageIcon(turnImage).getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
		lblPlayerMove.setText(turn + "'s turn" );
		lblPlayerMove.setIcon(imageIcon);
	}
	/**
	 * Method to exit from game. Takes confirmation before exiting. 
	 */
	private void exitGame(){
		int res = JOptionPane.showConfirmDialog(null, "There is a game in progress. Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
		if (res == JOptionPane.YES_OPTION)
			System.exit(1);
	}
	/**
	 * This method resets the game board.  
	 */
	private void resetBoard(){
		for(int i = 0 ; i < 9 ; i++){
		btnOnGameBoard[i].setText("");
		btnOnGameBoard[i].setEnabled(true);
		btnOnGameBoard[i].setBackground(new Color(32,22,63));
		}
		turn = name1;
		mark = mark1;
		turnImage = getClass().getResource("/"+mark+".png");
		ImageIcon imageIcon = new ImageIcon (new ImageIcon(turnImage).getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
		lblPlayerMove.setText(turn + "'s turn" );
		lblPlayerMove.setIcon(imageIcon);
		checkPlayer = 0;
	}

}