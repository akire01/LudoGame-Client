/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.erika.javafx.data;
import java.io.Serializable;
/**
 *
 * @author ER
 */
public class Player implements Serializable {

    String name;
    Color color;
    Pawn[] playersPawns = new Pawn[4];

    public Player() {
        
    }
    
    public Player(String name, Color color, Pawn[] playersPawns) {
        this.name = name;
        this.color = color;
        this.playersPawns = playersPawns;
    }

    public String getName() {
        return name;
    }

    public Pawn[] getPlayersPawns() {
        return playersPawns;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlayersPawns(Pawn[] playersPawns) {
        this.playersPawns = playersPawns;
    }

    @Override
    public String toString() {
        return  name;
    }

    public Color getColor() {
        return color;
    }
        
    public static String getPlayersNames(Player[] players){
        StringBuilder namesOfPlayers = new StringBuilder();
        for (Player player : players) {
            namesOfPlayers.append(player.toString()).append(", ");
        }
        return namesOfPlayers.toString();
    }
    
}
