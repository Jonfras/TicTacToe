package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    static final String SEPARATOR = "_";
    boolean isPlayer1 = true;
    int playerDecider = 1;
    int playcounter = 0;
    Coordinate[][] playfield = new Coordinate[3][3];
    CPU cpu = new CPU(playfield);
    boolean winner = false;
    Map<String, Button> buttons = new HashMap<>();
    List<String> log = new ArrayList<>();
    private boolean cpuActive;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                playfield[y][x] = new Coordinate(y, x);
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b0_0 = findViewById(R.id.b0_0);
        Button b0_1 = findViewById(R.id.b0_1);
        Button b0_2 = findViewById(R.id.b0_2);
        Button b1_0 = findViewById(R.id.b1_0);
        Button b1_1 = findViewById(R.id.b1_1);
        Button b1_2 = findViewById(R.id.b1_2);
        Button b2_0 = findViewById(R.id.b2_0);
        Button b2_1 = findViewById(R.id.b2_1);
        Button b2_2 = findViewById(R.id.b2_2);
        buttons.put("b0_0", b0_0);
        buttons.put("b0_1", b0_1);
        buttons.put("b0_2", b0_2);
        buttons.put("b1_0", b1_0);
        buttons.put("b1_1", b1_1);
        buttons.put("b1_2", b1_2);
        buttons.put("b2_0", b2_0);
        buttons.put("b2_1", b2_1);
        buttons.put("b2_2", b2_2);
    }

    public void onCpuActivate(View view) {
        cpuActive = !cpuActive;
        reset();

    }

    @SuppressLint("SetTextI18n")
    public void onClick(View button) {
        Button playerButton = (Button) findViewById(button.getId());
        Button cpuButton;
        String id = playerButton.getResources().getResourceEntryName(button.getId());

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
                if (cpuActive) { // if cpu is switched on
                    playcounter++;
                    cpu.readPlayfield(playfield);
                    c = cpu.click();/**
                     *
                     *
                     nextStep
                     *
                     */
                    String buttonName = "b" + c.getY() + "_" + c.getX();
                    cpuButton = buttons.get(buttonName);
                    setPlayfield(c);
                    cpuButton.setText("O");
                }
                else {

                }
                if (playcounter >= 5) checkWinner();
                isPlayer1 = true;
                playerDecider = 1;
            }
        }
    }

//    //@SuppressLint("ResourceType")
//    private int findButton(String buttonName) {
//        switch (buttonName){
//            case "b0_0": return 1000009;
//            case "b0_1": return 1000003;
//            case "b0_2": return 1000000;
//            case "b1_0": return 1000004;
//            case "b1_1": return 1000001;
//            case "b1_2": return 1000006;
//            case "b2_0": return 1000002;
//            case "b2_1": return 1000007;
//            case "b2_2": return 1000005;
//
//            default:
//                throw new IllegalStateException("Unexpected value: " + buttonName);
//        }
//    }

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
        logCord(c);
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
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }
    }

    public void logCord(Coordinate c) {
        String message = "Player " + c.getPlayerNumber() + " clicked on + [" + c.getY() + "][" + c.getX() + "]";
        System.out.println(message);
        log.add(message);
    }

    public void logState() {
        String message = "CPU running: " + cpuActive;
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

    public void reset() {
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                playfield[y][x] = new Coordinate(y, x);
            }
        }
        for (Button b :
                buttons.values()) {
            b.setText("-");
        }
        playerDecider = 1;
        isPlayer1 = true;
        log.clear();
        logState();
    }
}