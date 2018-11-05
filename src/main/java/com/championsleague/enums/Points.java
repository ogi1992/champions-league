package com.championsleague.enums;

public enum Points {
    WIN(3),
    LOSE(0),
    DRAW(1);

    private int points;

    Points(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }
}
