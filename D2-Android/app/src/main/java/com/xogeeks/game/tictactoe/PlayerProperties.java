package com.xogeeks.game.tictactoe;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.xogeeks.game.control.Controller;
/**
 * This class is to set properties of the player. User can change name if they want otherwise game will start with default names.
 * Player can select mark.
 * Player can select number of rounds they want to play.
 * @version 2.0
 */
public class PlayerProperties extends AppCompatActivity implements View.OnClickListener {
    public Button startGameButton,
                  exitButton,
                  helpButton;
    EditText player1Name;
    EditText player2Name;
    RadioGroup selectXorORadioButton,
               selectRoundNumber;
    RadioButton selectXorO,
                selectRound,
                selectOasRadioButton,
                selectXasRadioButton;
    ImageView player1MarkImageView,
              player2MarkImageView;
    CustomizeDialog customizeDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_properties);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        startNewGame();
    }

    /**
     * In this class user can either start new game or check for help or can exit the game.
     * @param v Checks which button user selects.
     */
    @Override
    public void onClick(View v){
        int roundNum =1;
        player1Name = (EditText)findViewById(R.id.firstPlayerEditText);
        player2Name = (EditText)findViewById(R.id.secondPlayerEditText);
        selectXorORadioButton = (RadioGroup)findViewById(R.id.selectXorORadioButton);
        selectRoundNumber = (RadioGroup) findViewById(R.id.selectRoundRadioButton);
        int selectedXorO,selectedRounds;
        String name1 = player1Name.getText().toString();
        String name2 = player2Name.getText().toString();
        selectedXorO = selectXorORadioButton.getCheckedRadioButtonId();
        selectedRounds = selectRoundNumber.getCheckedRadioButtonId();
        selectXorO = (RadioButton) findViewById(selectedXorO);
        selectRound = (RadioButton) findViewById(selectedRounds);
        String checkXorO = selectXorO.getText().toString();
        player1MarkImageView=(ImageView)findViewById(R.id.firstPlayerMarkImageView);
        player2MarkImageView=(ImageView)findViewById(R.id.secondPlayerMarkImageView);
        switch (v.getId()){
            case R.id.startGameButton:
                if(!name1.equals(name2)) {
                    String mark1 = "X";
                    String mark2 = "O";
                    if(checkXorO.equals("O")){
                        mark1 = "O";
                        mark2 = "X";
                    }
                    roundNum=Integer.parseInt(selectRound.getText().toString());
                    new Controller(name1,name2,mark1,mark2,roundNum);
                    //Toast.makeText(PlayerProperties.this,""+name1+name2+mark1+mark2+roundNum,Toast.LENGTH_SHORT).show();
                    Intent startGame = new Intent("android.intent.action.GAMEBOARD");
                    startGame.putExtra("rounds",roundNum);
                    startActivity(startGame);
                }
                else
                    Toast.makeText(PlayerProperties.this,"Please select unique names",Toast.LENGTH_LONG).show();
                break;
            case R.id.selectOasRadioButton:
                    player1MarkImageView.setImageResource(R.drawable.o);
                    player2MarkImageView.setImageResource(R.drawable.x);
                break;
            case R.id.selectXasRadioButton:
                    player1MarkImageView.setImageResource(R.drawable.x);
                    player2MarkImageView.setImageResource(R.drawable.o);
                break;
            case R.id.exitButton:
                customizeDialog = new CustomizeDialog(this,"exit");
                customizeDialog.setTitle("Exit Game");
                customizeDialog.setMessage("Are you Sure? \n you want to Exit from game ");
                customizeDialog.show();
                break;
            case R.id.helpButton:
                Intent help=new Intent("android.intent.action.HELP");
                startActivity(help);
                break;
        }

    }

    /**
     * This method is to initialize Gui contents.
     */
    public void startNewGame(){
        startGameButton=(Button)findViewById(R.id.startGameButton);
        startGameButton.setOnClickListener(this);
        selectOasRadioButton=(RadioButton)findViewById(R.id.selectOasRadioButton);
        selectXasRadioButton=(RadioButton)findViewById(R.id.selectXasRadioButton);
        exitButton=(Button)findViewById(R.id.exitButton);
        helpButton=(Button)findViewById(R.id.helpButton);
        selectOasRadioButton.setOnClickListener(this);
        selectXasRadioButton.setOnClickListener(this);
        exitButton.setOnClickListener(this);
        helpButton.setOnClickListener(this);
    }
}
