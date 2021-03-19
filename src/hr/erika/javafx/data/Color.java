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
public enum Color {
    YELLOW(1),
    GREEN(2),
    BLUE(3),
    RED(4);
    
    private int value;

     Color(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
