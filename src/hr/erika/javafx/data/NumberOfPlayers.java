/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.erika.javafx.data;

/**
 *
 * @author ER
 */
public enum NumberOfPlayers {
    TWO(2),
    THREE(3),
    FOUR(4);
    
    private int value;

    NumberOfPlayers(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
