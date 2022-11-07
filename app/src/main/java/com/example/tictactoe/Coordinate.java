package com.example.tictactoe;

public class Coordinate {
    private int x;
    private int y;
    private int playerNumber;

    public Coordinate(int y, int x) {
        this.x = x;
        this.y = y;
        this.playerNumber = 0;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "y=" + x +
                ", x=" + y +
                ", playerNumber=" + playerNumber +
                '}';
    }

}
