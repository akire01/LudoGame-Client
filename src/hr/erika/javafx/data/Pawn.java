/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.erika.javafx.data;

import hr.erika.javafx.utils.FindElementsPosition;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.scene.shape.Shape;

/**
 *
 * @author ER
 */
public class Pawn implements Serializable {

    transient AtomicInteger count = new AtomicInteger(0);
    transient Shape circle;
    Integer id;
    transient Color color;
    transient Shape startPosition;
    PawnStatus status;
    transient Shape squarePosition;

    private String shapeId;
    private int column;
    private int row;

    /**
     *
     * @param circle
     * @param color
     * @param startPosition
     * @param status
     */
    public Pawn(Shape circle, Color color, Shape startPosition, PawnStatus status, Shape squarePosition) {
        this.circle = circle;
        this.id = count.incrementAndGet();
        this.color = color;
        this.startPosition = startPosition;
        this.status = status;
        this.squarePosition = squarePosition;
        this.shapeId = circle.getId();
    }
    
    public Pawn(int id, PawnStatus status, String shapeId, int column, int row) {
        this.id = id;
        this.status = status;
        this.shapeId = shapeId;
        this.column = column;
        this.row = row;
    }

     public Pawn(PawnStatus status, String shapeId, int column, int row) {
        this.status = status;
        this.shapeId = shapeId;
        this.column = column;
        this.row = row;
    }
    
    public Integer getId() {
        return id;
    }

    public PawnStatus getStatus() {
        return status;
    }

    public Shape getStartPosition() {
        return startPosition;
    }

    public Shape getCircle() {
        return circle;
    }

    public void setCircle(Shape circle) {
        this.circle = circle;
    }

    public void setStatus(PawnStatus status) {
        this.status = status;
    }

    public Color getColor() {
        return color;
    }

    public Shape getSquarePosition() {
        return squarePosition;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public String getShapeId() {
        return shapeId;
    }

    public static void hidePawns(Pawn[] pawns) {
        for (Pawn pawn : pawns) {
            pawn.getCircle().setVisible(false);
        }
    }

    public static void unHidePawns(Pawn[] pawns) {
        for (Pawn pawn : pawns) {
            pawn.getCircle().setVisible(true);
        }
    }

    public static Optional<Pawn> checkExistingPawn(Shape destination, Pawn[] allpawns) {
        Optional<Pawn> existingPawn = Optional.empty();
        for (Pawn pawn : allpawns) {
            if (FindElementsPosition.getColumn(pawn.getCircle()) == FindElementsPosition.getColumn(destination)
                    && FindElementsPosition.getRow(pawn.getCircle()) == FindElementsPosition.getRow(destination)) {
                existingPawn = Optional.of(pawn);
                break;
            }
        }
        return existingPawn;
    }
    
    public void refreshRowAndColumn() {
        this.column = FindElementsPosition.getColumn(circle);
        this.row = FindElementsPosition.getRow(circle);
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.writeInt(id);
        oos.writeObject(status);
        oos.writeUTF(shapeId);
        oos.writeInt(FindElementsPosition.getColumn(circle));
        oos.writeInt(FindElementsPosition.getRow(circle));

    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        id = ois.readInt();
        status = (PawnStatus) ois.readObject();
        shapeId = ois.readUTF();
        column = ois.readInt();
        row = ois.readInt();

    }

    
}