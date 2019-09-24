package com.example.lionortiger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    enum Player{
        ONE, TWO, NOONE
    }

    Player currentPlayer = Player.ONE;

    Player[] playerChoices = new Player[9];
    int[][] winnerRowsColumns = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}};

    private boolean gameOver = false;

    private Button btnReset;

    private GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for(int i=0; i<playerChoices.length; i++){
            playerChoices[i] = Player.NOONE;
        }

        btnReset = findViewById(R.id.resetBtn);
        gridLayout = findViewById(R.id.gridLayout);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTheGame();
            }
        });

    }
    public void imageViewisTapped(View imageView) {

        ImageView tappedImageView = (ImageView) imageView;

        int tiTag = Integer.parseInt(imageView.getTag().toString());

        if (playerChoices[tiTag] == Player.NOONE && gameOver == false) {
            playerChoices[tiTag] = currentPlayer;
            tappedImageView.setTranslationX(-2000);
            if (currentPlayer == Player.ONE) {
                tappedImageView.setImageResource(R.drawable.lion);
                currentPlayer = Player.TWO;
            } else if (currentPlayer == Player.TWO) {
                tappedImageView.setImageResource(R.drawable.tiger);
                currentPlayer = Player.ONE;
            }
            tappedImageView.animate().translationXBy(2000).alpha(1).rotation(3600).setDuration(1000);

            Toast.makeText(this, tappedImageView.getTag().toString(), Toast.LENGTH_SHORT).show();

            for (int[] winnerColumns : winnerRowsColumns) {

                if (playerChoices[winnerColumns[0]] == playerChoices[winnerColumns[1]] &&
                        playerChoices[winnerColumns[1]] == playerChoices[winnerColumns[2]] &&
                        playerChoices[winnerColumns[0]] != Player.NOONE) {

                    btnReset.setVisibility(View.VISIBLE);
                    gameOver = true;
                    String winnerOfGame = new String();

                    if (currentPlayer == Player.ONE) {
                        winnerOfGame = "Player Two is Victorious!";
                    }
                    if (currentPlayer == Player.TWO) {
                        winnerOfGame = "Player One is Victorious!";
                    }
                    Toast.makeText(this, winnerOfGame, Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    // Reset game function
    private void resetTheGame(){
        currentPlayer = Player.ONE;
        gameOver = false;
        for(int i=0 ; i<playerChoices.length; i++){
            playerChoices[i] = Player.NOONE;
        }
        for(int i = 0; i< gridLayout.getChildCount(); i++){
            ImageView imageView = (ImageView) gridLayout.getChildAt(i);
            imageView.setImageDrawable(null);
            imageView.setAlpha(0.2f);
        }
        btnReset.setVisibility(View.GONE);
    }

}
