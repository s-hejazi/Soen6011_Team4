package com.xogeeks.game.tictactoe;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Process;
import android.renderscript.Short4;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xogeeks.game.control.Controller;

import java.util.Arrays;
/**
 * This class to display GUI for 3*3 board where players can play game.
 * Other than game board players can see scores, current round and players turn with mark.
 * If player has selected 3/5 rounds mode, then after each round players are able to see start new round button.
 * After each round winner of that round is displayed and according to mode 3/5, best of Game will be displayed after finishing all rounds.
 * Winner will get some Gift!!!
 * @version 2.0
 */
public class GameBoard extends AppCompatActivity implements View.OnClickListener {

    static Context context;
    int setImageX,setImageO;
    MediaPlayer cheer=null;
    MediaPlayer crack=null;
    private static int buttonLength=9;
    public static Button gameBoardButton[]=new Button[buttonLength];
    CustomizeDialog customizeDialog = null;
    TextView player1ScoreTextView,
             player2ScoreTextView,
             tieScoreTextView,
             playersTurnTextView,
             changeRound,
             roundWinner;
    Button checkButton1,
            backButton,
            exitButton,
            helpButton,
            nextRound;
    ImageView xImageView;
    static int currentRound;
    static int checkPlayer=0;
    static String turn;
    static String name1, name2;
    static String mark;
    static String btnValue[] = new String[9];
    int totalRound;
    private static GameBoard gameBoard=null;
    GameBoard(){
        gameBoard=GameBoard.this;
    }

    /**
     * This is first method created after starting this activity.
     * Some contents are initialized in this method.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_board);
        //String name1=getIntent().getExtras().getString("name1");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setPlayers();
        gameBoard.currentRound=1;
        playersTurnTextView=(TextView)findViewById(R.id.playersTurnTextView);
        player1ScoreTextView=(TextView)findViewById(R.id.player1ScoreTextView);
        player2ScoreTextView=(TextView)findViewById(R.id.player2ScoreTextView);
        player1ScoreTextView.setText(name1+": 0");
        player2ScoreTextView.setText(name2+": 0");
        playersTurnTextView.setText(turn+"'s turn");
        xImageView=(ImageView)findViewById(R.id.xImageView);

        if(mark=="O")
            xImageView.setImageResource(R.drawable.o);
        nextRound=(Button)findViewById(R.id.startNextRoundButton);
        roundWinner=(TextView)findViewById(R.id.roundWinnerTextView);
        nextRound.setVisibility(View.INVISIBLE);
        roundWinner.setVisibility(View.INVISIBLE);
        cheer=MediaPlayer.create(this,R.raw.cheer);
        crack=MediaPlayer.create(this,R.raw.crakers);
        checkButton();
        for(int i = 0 ; i < 9 ; i++){
            gameBoardButton[i].setEnabled(true);
            btnValue[i]=null;
            checkPlayer=0;
        }
        setMarks();
    }
    /**
     * This method is initialise all buttons on this GUI and set action listeners.
     */
    public void checkButton() {

        gameBoardButton[0] = (Button) findViewById(R.id.boardButton1);
        gameBoardButton[1] = (Button) findViewById(R.id.boardButton2);
        gameBoardButton[2] = (Button) findViewById(R.id.boardButton3);
        gameBoardButton[3] = (Button) findViewById(R.id.boardButton4);
        gameBoardButton[4] = (Button) findViewById(R.id.boardButton5);
        gameBoardButton[5] = (Button) findViewById(R.id.boardButton6);
        gameBoardButton[6] = (Button) findViewById(R.id.boardButton7);
        gameBoardButton[7] = (Button) findViewById(R.id.boardButton8);
        gameBoardButton[8] = (Button) findViewById(R.id.boardButton9);

        for(int i=0;i<9;i++)
        gameBoardButton[i].setOnClickListener(this);

        tieScoreTextView=(TextView)findViewById(R.id.tiesScoreTextView);
        changeRound=(TextView)findViewById(R.id.roundNumberTextView);
        backButton=(Button)findViewById(R.id.backButton);
        exitButton=(Button)findViewById(R.id.exitButton);
        nextRound=(Button)findViewById(R.id.startNextRoundButton);
        helpButton=(Button)findViewById(R.id.helpButton);
        roundWinner=(TextView)findViewById(R.id.roundWinnerTextView);
        backButton.setOnClickListener(this);
        exitButton.setOnClickListener(this);
        nextRound.setOnClickListener(this);
        helpButton.setOnClickListener(this);
    }

    /**
     * This method is used to get player properties such as name, mark and total rounds.
     */
    public void setPlayers(){
        mark = Controller.getCurrentPlayerMark();
        turn = Controller.getCurrentPlayerName();
        name1 = Controller.getPlayer1Name();
        name2 = Controller.getPlayer2Name();
        totalRound = Controller.getTotalRound();
    }

    /**
     * This method sets mark X or O for players.
     */
    public void setMarks(){
        if(mark==("X")) {
            setImageX = R.drawable.xnew;
            setImageO = R.drawable.onew;
        }
        else {
            setImageX = R.drawable.onew;
            setImageO = R.drawable.xnew;
        }
    }

    /**
     * This method used for changing players turn after player places mark on board.
     */
    private static void changePlayerTurn() {
        Controller.changeTurn();

        turn = Controller.getCurrentPlayerName();
        mark = Controller.getCurrentPlayerMark();
    }

    /**
     * This method is displays Gift for player.
     * @param title
     * @param message
     */
    public void showGifts(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final AlertDialog dialog = builder.create();
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.go_pro_dialog_layout, null);
        dialog.setView(dialogLayout);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.show();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface d) {
               ImageView image = (ImageView) dialog.findViewById(R.id.goProDialogImage);
                Bitmap icon = BitmapFactory.decodeResource(context.getResources(),R.drawable.fireworks);
                float imageWidthInPX = (float)image.getWidth();

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Math.round(imageWidthInPX),
                        Math.round(imageWidthInPX * (float)icon.getHeight() / (float)icon.getWidth()));
                image.setLayoutParams(layoutParams);
            }
        });
    }

    /**
     * This method is used to display Final winner of the Game.
     * @param title For message box
     * @param message Winner name.
     */
    public void showWinner(String title, String message){

        android.app.AlertDialog.Builder alertadd = new android.app.AlertDialog.Builder(this);
        alertadd.setTitle(title);
        alertadd.setMessage(message);
        LayoutInflater factory = LayoutInflater.from(this);
        final View view = factory.inflate(R.layout.dialog_main, null);
        pl.droidsonroids.gif.GifTextView image= (pl.droidsonroids.gif.GifTextView) view.findViewById(R.id.imageView);
        alertadd.setView(view);
        alertadd.setNeutralButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dlg, int sumthin) {
                gameBoard.crack.stop();
                showGifts("Congratulations","Enjoy your Gift!!! ");
            }
        });
        alertadd.show();
    }

    /**
     * This method displays winner of round.
     * @param line To highlight wining position on 3*3 board.
     * @param token Mark is either X or O.
     */
    public static void roundWon(int[] line,String token){

        for (int j = 0; j < 3 ; j++) {
            if(token.equals("X"))
                gameBoardButton[line[j]].setBackgroundResource(R.drawable.xwin);
            else
                gameBoardButton[line[j]].setBackgroundResource(R.drawable.owin);
        }
        gameBoard.cheer.start();
        for(int i = 0; i < 9 ; i++)
            gameBoardButton[i].setEnabled(false);
        gameBoard.roundWinner.setText(turn+" wins this round");
        gameBoard.roundWinner.setVisibility(View.VISIBLE);
        gameBoard.nextRound.setVisibility(View.VISIBLE);
    }

    /**
     * Displayed when round is tie.
     */
    public static void roundTie(){
            // gameBoard.showAlert("Result","It's a Tie!!!");
             gameBoard.roundWinner.setText(" It's a Tie!!!");
             gameBoard.roundWinner.setVisibility(View.VISIBLE);
             gameBoard.nextRound.setVisibility(View.VISIBLE);
    }

    /**
     * Displayed when player wins the Game.
     * @param result Final result of all rounds.
     */
    public static void gameWon(String result){
        gameBoard.crack.start();
        gameBoard.showWinner("Game Winner",result);
        gameBoard.nextRound.setVisibility(View.INVISIBLE);
    }

    /**
     * Reset Board while starting new game.
     */
    private static void resetBoard(){
        for(int i=0; i < 9; i++) {
            gameBoardButton[i].setEnabled(true);
            gameBoardButton[i].setBackgroundResource(R.drawable.xoplain);
        }
        Controller.resetTurn();
        turn = Controller.getCurrentPlayerName();
        mark = Controller.getCurrentPlayerMark();
        gameBoard.playersTurnTextView.setText(turn+"'s turn");
        if(mark=="O")
            gameBoard.xImageView.setImageResource(R.drawable.o);
        else
            gameBoard.xImageView.setImageResource(R.drawable.x);
    }

    /**
     * This method starts new round.
     */
    private void startNextRound(){
        changePlayerTurn();
        resetBoard();
        Arrays.fill(btnValue, null);
        checkPlayer=0;
        currentRound++;
        changeRound.setText("Round : "+currentRound);
        nextRound.setVisibility(View.INVISIBLE);
        roundWinner.setVisibility(View.INVISIBLE);
    }

    /**
     * This method updates score on score board.
     * @param score1 Player 1 score.
     * @param score2 Player 2 score.
     * @param score3 Tie score.
     */
    public static void updateScoreboard(int score1, int score2, int score3){

        gameBoard.player1ScoreTextView.setText(name1+": "+score1);
        gameBoard.player2ScoreTextView.setText(name2+": "+score2);
        gameBoard.tieScoreTextView.setText("Ties: "+score3);
    }
    @Override
    public void onBackPressed()
    {
        customizeDialog = new CustomizeDialog(this,"back");
        customizeDialog.setTitle("Return to Menu");
        customizeDialog.setMessage("Are you Sure? \n you want to end this round");
        customizeDialog.show();
    }

    /**
     * This method is Onclick listener. Player can click on 3*3 board, Help button, Back to main menu or Exit button.
     * @param v Dependent on players click event actions are performed.
     */
    @Override
    public void onClick(View v) {
       // setMarks();
        String token;
        checkButton1=(Button)findViewById(v.getId());
        //if(checkButton1.isClickable()) {
        for (int i = 0; i < 9; i++) {
            if (checkButton1 == gameBoardButton[i] && checkPlayer < 9 && btnValue[i] == null) {
                if(checkPlayer %2 == 0){
                    gameBoardButton[i].setBackgroundResource(setImageX);
                    gameBoardButton[i].setEnabled(false);
                    btnValue[i] = mark;
                }
                else{
                    gameBoardButton[i].setBackgroundResource(setImageO);
                    gameBoardButton[i].setEnabled(false);
                    btnValue[i] = mark;
                }
                Controller.checkStatus(btnValue, mark, checkPlayer);
                changePlayerTurn();
                if (mark == "X") {
                    xImageView.setImageResource(R.drawable.x);
                } else {
                    xImageView.setImageResource(R.drawable.o);
                }
                playersTurnTextView.setText(turn + "'s turn");
                checkPlayer++;
            } else
                Toast.makeText(this, "No", Toast.LENGTH_LONG);
        }

        if(v.getId()==R.id.startNextRoundButton){
            startNextRound();
        }
        if(v.getId()==R.id.backButton){
            customizeDialog = new CustomizeDialog(this,"back");
            customizeDialog.setTitle("Return to Menu");
            customizeDialog.setMessage("Are you Sure? \n you want to end this round");
            customizeDialog.show();
        }
        if(v.getId()==R.id.exitButton){
            customizeDialog = new CustomizeDialog(this,"exit");
            customizeDialog.setTitle("Exit Game");
            customizeDialog.setMessage("Are you Sure? \n you want to Exit from game ");
            customizeDialog.show();
       }
    }


}
