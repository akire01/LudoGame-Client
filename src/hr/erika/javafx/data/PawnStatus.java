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
public enum PawnStatus implements Serializable {
    SQUARE(1),
    OUT(2),
    HOME(3);
    
    private int value;

    PawnStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    
    public static PawnStatus forValue(int value) {
        switch(value) {
            case 1:
                return PawnStatus.SQUARE;
            case 2:
                return PawnStatus.OUT;
            case 3:
                return PawnStatus.HOME;
            default:
                return PawnStatus.SQUARE;
        }
    }
}
