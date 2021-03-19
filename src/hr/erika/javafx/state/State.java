/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.erika.javafx.state;

import hr.erika.javafx.data.Player;
import java.io.Serializable;

/**
 *
 * @author ER
 */
public class State implements Serializable {

    private Player[] players = new Player[]{};
    private int currentPlayer;
    private int turns;

    public State(Player[] players, int currentPlayer, int turns) {
        this.players = players;
        this.currentPlayer = currentPlayer;
        this.turns = turns;
    }

    public State() {
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public int getTurns() {
        return turns;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setTurns(int turns) {
        this.turns = turns;
    }
    
    
}
