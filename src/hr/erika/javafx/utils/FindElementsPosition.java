/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.erika.javafx.utils;

import javafx.scene.layout.GridPane;
import javafx.scene.shape.Shape;

/**
 *
 * @author ER
 */
public class FindElementsPosition {
    
    public static int getColumn(Shape shape){
         return GridPane.getColumnIndex(shape) == null ? 0 : GridPane.getColumnIndex(shape);
    }
    
    public static int getRow(Shape shape){
         return GridPane.getRowIndex(shape) == null ? 0 : GridPane.getRowIndex(shape);
    }
}
