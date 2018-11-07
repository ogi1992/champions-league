package com.championsleague.to;

import com.championsleague.entities.Team;

import java.io.Serializable;

public class TeamTO implements Serializable, Comparable<TeamTO> {

    private int rank;

    private String team;

    private int playedGames;

    private int points;

    private int goals;

    private int goalsAgainst;

    private int goalDifference;

    private int win;

    private int lose;

    private int draw;

    public TeamTO() {

    }

    public TeamTO(Team team) {
        this.team = team.getName();
        this.playedGames = team.getPlayedGames();
        this.points = team.getPoints();
        this.goals = team.getGoals();
        this.goalsAgainst = team.getGoalsAgainst();
        this.goalDifference = team.getGoalDifference();
        this.win = team.getWin();
        this.lose = team.getLose();
        this.draw = team.getDraw();
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public int getPlayedGames() {
        return playedGames;
    }

    public void setPlayedGames(int playedGames) {
        this.playedGames = playedGames;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public int getGoalsAgainst() {
        return goalsAgainst;
    }

    public void setGoalsAgainst(int goalsAgainst) {
        this.goalsAgainst = goalsAgainst;
    }

    public int getGoalDifference() {
        return goalDifference;
    }

    public void setGoalDifference(int goalDifference) {
        this.goalDifference = goalDifference;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getLose() {
        return lose;
    }

    public void setLose(int lose) {
        this.lose = lose;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    @Override
    public int compareTo(TeamTO o) {
        int compare = Integer.compare(o.getPoints(), getPoints());
        if (compare == 0) {
            compare = Integer.compare(o.getGoals(), getGoals());
            if (compare == 0) {
                compare = Integer.compare(o.getGoalDifference(), getGoalDifference());
            }
        }
        return compare;
    }
}
