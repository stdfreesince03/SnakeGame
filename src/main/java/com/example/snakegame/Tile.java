package com.example.snakegame;


import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle {

    private int row;
    private int col;
    private boolean isHead;

    private boolean isSnake;


    public Tile(double width, double height, int idX, int idY) {
        super(width, height,Color.rgb(42,43,41) );
        this.row = idX;
        this.col = idY;
        this.isHead = false;
        this.isSnake = false;
    }
    public Tile(double width, double height, int idX, int idY, boolean isHead) {

        super(width, height,Color.rgb(44,195,148));
        this.row = idX;
        this.col = idY;
        this.isHead = isHead;
        if(isHead){
            this.fillProperty().set(Color.rgb(255,255,255));
        }
        this.isSnake = true;


    }


    public final void fillColor(){
        if(isHead){
            this.fillProperty().set(Color.rgb(255,255,255));
        }else if(isSnake){
            this.fillProperty().set(Color.rgb(44,195,148));
        }else{
            this.fillProperty().set(Color.rgb(42,43,41));
        }
    }

    public final void posChange(Tile next){
        if(next != null){
            int tempRow = this.row;
            int tempCol = this.col;

            this.row = next.row;
            this.col = next.col;
            next.row = tempRow;
            next.col = tempCol;
        }

    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public boolean isHead() {
        return isHead;
    }

    public void setHead(boolean head) {
        isHead = head;
    }

    public boolean isSnake() {
        return isSnake;
    }

    public void setSnake(boolean snake) {
        isSnake = snake;
    }




    @Override
    public String toString() {
        return "Tile{" +
                "row=" + row +
                ", col=" + col +
                ", isHead=" + isHead +
                ", isSnake=" + isSnake +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tile tile = (Tile) o;

        return row==tile.row && col==tile.col;
    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + col;
        return result;
    }
}
