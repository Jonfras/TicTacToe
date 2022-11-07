package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    static final String SEPARATOR = "_";
    boolean isPlayer1 = true;
    int playerDecider = 1;
    int playcounter = 0;
    Coordinate[][] playfield = new Coordinate[3][3];
    CPU cpu = new CPU(playfield);
    boolean winner = false;

    List<String> log = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                playfield[y][x] = new Coordinate(y, x);
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @SuppressLint("SetTextI18n")
    public void onClick(View view) {
        Button playerButton = (Button) findViewById(view.getId());
        Button cpuButton;
        String id = playerButton.getResources().getResourceEntryName(view.getId());

        if (!playerButton.getText().equals("-")) {
            TextView errorText = findViewById(R.id.errorText);
            errorText.setText(id + " was already clicked");
        } else {
            playcounter++;
            Coordinate c = new Coordinate(toInt(id.charAt(1)), toInt(id.charAt(3)));
            if (isPlayer1) {
                playerButton.setText("X");
                c.setPlayerNumber(playerDecider);
                System.out.println("");
                setPlayfield(c);
                if (playcounter >= 5) checkWinner();
                isPlayer1 = false;
                playerDecider = 2;
                //player 1 is finished, player two/its cpu's turn
                cpu.readPlayfield(playfield);
                c = cpu.click();
                setPlayfield(c);
                String buttonName = "." + c.getY() + "_" + c.getX();
                System.out.println(Integer.parseInt(buttonName));
                cpuButton = (Button) findViewById(Integer.parseInt(buttonName));
                cpuButton.setText("O");
                //Cpu implementieren
                if (playcounter >= 5) checkWinner();
                isPlayer1 = true;
                playerDecider = 1;
            }
        }
    }

    private int toInt(char number) {
        switch (number) {
            case 48:
                return 0;
            case 49:
                return 1;
            case 50:
                return 2;
            default:
                return -1;
        }
    }

    public void setPlayfield(Coordinate c) {
        playfield[c.getY()][c.getX()] = c;
        log(c);
    }

    public void checkWinner() {
        List<Integer> stack = new ArrayList<>(3);
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                if (playfield[y][x].getPlayerNumber() == playerDecider)
                    stack.add(playfield[y][x].getPlayerNumber());
            }
            if (stack.size() == 3) {
                winner = true;
            } else stack = new ArrayList<>();
        }
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (playfield[y][x].getPlayerNumber() == playerDecider)
                    stack.add(playfield[y][x].getPlayerNumber());
            }
            if (stack.size() == 3) {
                winner = true;
            } else stack = new ArrayList<>();
        }
        if (playfield[1][1].getPlayerNumber() == playerDecider) {
            if (playfield[0][0].getPlayerNumber() == playerDecider && playfield[2][2].getPlayerNumber() == playerDecider)
                winner = true;

            else if (playfield[0][2].getPlayerNumber() == playerDecider && playfield[2][0].getPlayerNumber() == playerDecider)
                winner = true;
        }
        if (winner) {
            String message = "Winner is Player " + playerDecider;
            System.out.println();
            printPlayfield();

            /**
             * 
             * do muast weida doa jonas
             *
             */
            Toast.makeText(, message, Toast.LENGTH_LONG).show();

        }
    }

    public void log(Coordinate c) {
        String message = "Player " + c.getPlayerNumber() + " clicked on + [" + c.getY() + "][" + c.getX() + "]";
        System.out.println(message);
        log.add(message);
    }

    public void printPlayfield() {
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                System.out.print("[" + playfield[y][x].getPlayerNumber() + "]   ");
            }
            System.out.println();
        }
    }
}