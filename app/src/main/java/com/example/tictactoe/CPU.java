package com.example.tictactoe;

public class CPU {
    private Coordinate[][] playfield;

    public CPU(Coordinate[][] playfield) {
        this.playfield = playfield;
    }

    public Coordinate click(){
        Coordinate freeCord = searchFreeCoordinate();
        freeCord.setPlayerNumber(2);
        return freeCord;
    }

    public void readPlayfield(Coordinate[][] newPlayfield){
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                playfield[y][x] = newPlayfield[y][x];
            }
        }
    }
    public void printPlayfield(){
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                System.out.print("[" + playfield[y][x].getPlayerNumber() +"]   ");
            }
            System.out.println();
        }
    }

    public Coordinate searchFreeCoordinate(){
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                if (playfield[y][x].getPlayerNumber() == 0) return playfield[y][x];
            }
        }
        return null;
    }
}
