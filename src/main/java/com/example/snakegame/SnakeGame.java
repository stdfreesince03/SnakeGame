package com.example.snakegame;

import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class SnakeGame {
    private static final int TILE_HEIGHT = 20;
    private static final int TILE_WIDTH = 20;
    private static final int SCREEN_WIDTH = 500;
    private static final int SCREEN_HEIGHT = 500;
    private static final int TILES_PER_ROW = SCREEN_WIDTH/TILE_WIDTH;
    private static final int TILES_PER_COLUMN = SCREEN_HEIGHT/TILE_HEIGHT;

    private static final Random rand = new Random();


    private final Tile[][] tileGrid  = new Tile[TILES_PER_COLUMN][ TILES_PER_ROW] ;

    private final GridPane pane;

    private final Apple apple = new Apple(TILE_WIDTH,TILE_HEIGHT);

    private final LinkedList<Tile> snakeList = new LinkedList<>(List.of(
            new Tile(TILE_WIDTH,TILE_HEIGHT,6,0,true),
            new Tile(TILE_WIDTH,TILE_HEIGHT,5,0,false),
            new Tile(TILE_WIDTH,TILE_HEIGHT,4,0,false),
            new Tile(TILE_WIDTH,TILE_HEIGHT,3,0,false),
            new Tile(TILE_WIDTH,TILE_HEIGHT,2,0,false),
            new Tile(TILE_WIDTH,TILE_HEIGHT,1,0,false),
            new Tile(TILE_WIDTH,TILE_HEIGHT,0,0,false)
// tail
    ));

    private int score ;

    private Direction headDir = Direction.DOWN; //initially
    private Tile head = snakeList.getFirst();
    private Tile fooded;

    public SnakeGame() {
        this.pane = new GridPane();
        fooded = new Tile(TILE_WIDTH,TILE_HEIGHT,1 + rand.nextInt(TILES_PER_ROW-2),
                1+ rand.nextInt(TILE_HEIGHT-2));
        while(snakeList.contains(fooded)){
            fooded.setRow(1 + rand.nextInt(TILES_PER_ROW -2));
            fooded.setCol(1 + rand.nextInt(TILES_PER_COLUMN)-2);
        }
        score = 0;
        paintGrid();
        updateGrid();

    }

    private void paintGrid(){

        for(int row = 0;row<TILES_PER_COLUMN;row++){
            for(int col= 0;col<TILES_PER_ROW;col++){
                Tile tile = new Tile(TILE_WIDTH,TILE_HEIGHT,row,col);
                if(snakeList.contains(tile)){
                    tile = snakeList.get(snakeList.indexOf(tile));
                }
                tileGrid[row][col] = tile;
            }
        }
    }

    public final void reset(){
        for(Tile tile : snakeList){
            tileGrid[tile.getRow()][tile.getCol()] = new Tile(TILE_WIDTH,TILE_HEIGHT,tile.getRow(),tile.getCol());
        }
        snakeList.clear();
        snakeList.addAll(List.of(
                new Tile(TILE_WIDTH,TILE_HEIGHT,6,0,true),
                new Tile(TILE_WIDTH,TILE_HEIGHT,5,0,false),
                new Tile(TILE_WIDTH,TILE_HEIGHT,4,0,false),
                new Tile(TILE_WIDTH,TILE_HEIGHT,3,0,false),
                new Tile(TILE_WIDTH,TILE_HEIGHT,2,0,false),
                new Tile(TILE_WIDTH,TILE_HEIGHT,1,0,false),
                new Tile(TILE_WIDTH,TILE_HEIGHT,0,0,false)));
        head = snakeList.getFirst();

        tileGrid[fooded.getRow()][fooded.getCol()] =  new Tile(TILE_WIDTH,TILE_HEIGHT,fooded.getRow(),fooded.getCol());
        fooded.setRow(1 + rand.nextInt(TILES_PER_ROW -2));
        fooded.setCol(1 + rand.nextInt(TILES_PER_COLUMN)-2);
        this.headDir = Direction.DOWN;
        this.score = 0;

    }

    public final  void updateGrid(){
        pane.getChildren().removeAll(pane.getChildren());
        for(int row = 0;row<TILES_PER_COLUMN;row++){
            for(int col= 0;col<TILES_PER_ROW;col++){
                Tile temp = tileGrid[row][col];
                if(temp.equals(fooded)){
                    temp.setFill(apple.imgPatRet());
                }
                if(!pane.getChildren().contains(temp)){
                    pane.add(temp,temp.getCol(),temp.getRow());
                }
            }
        }
    }

    public final void move(Direction dir){

        Tile next = null;

        int rowHead = this.head.getRow();
        int colHead = this.head.getCol();


        if(dir.equals(Direction.DOWN)){
            next = tileGrid[rowHead+1][colHead];
        }else if(dir == Direction.RIGHT){
            next = tileGrid[rowHead][colHead+1];
        }else if(dir == Direction.LEFT){
            next = tileGrid[rowHead][colHead-1];
        }else if(dir == Direction.UP){
            next = tileGrid[rowHead-1][colHead];
        }
        setHeadDir(dir);

        for (Tile temp : snakeList) {
            boolean last = snakeList.getLast().equals(temp);
            if(last){
                next.fillColor(); // just in case food is eaten
            }
            temp.posChange(next);
            tileGrid[next.getRow()][next.getCol()] = next;
            tileGrid[temp.getRow()][temp.getCol()] = temp;

        }

        if(snakeList.contains(fooded)){
            this.score ++;
            while(snakeList.contains(fooded)){
                fooded.setRow(1 + rand.nextInt(TILES_PER_ROW -2));
                fooded.setCol(1 + rand.nextInt(TILES_PER_COLUMN-2));
            }
        }


    }

    public final boolean Opposite(Direction dir){
        return this.headDir.equals(Direction.DOWN) && dir.equals(Direction.UP) ||
                this.headDir.equals(Direction.UP) && dir.equals(Direction.DOWN) ||
                this.headDir.equals(Direction.LEFT) && dir.equals(Direction.RIGHT) ||
                this.headDir.equals(Direction.RIGHT) && dir.equals(Direction.LEFT);
    }

    public final boolean inWall(){
        int rowNext  = 0;
        int colNext = 0;

        int rowHead = this.head.getRow();
        int colHead = this.head.getCol();


        if(headDir.equals(Direction.DOWN)){
            rowNext = rowHead + 1;
            colNext = colHead;
        }else if(headDir == Direction.RIGHT){
            rowNext = rowHead ;
            colNext = colHead + 1;
        }else if(headDir == Direction.LEFT){
            rowNext = rowHead;
            colNext = colHead-1;
        }else if(headDir == Direction.UP){
            rowNext = rowHead -1;
            colNext = colHead;
        }


        return rowNext < TILES_PER_COLUMN && rowNext >= 0 && colNext < TILES_PER_ROW && colNext >= 0;
    }

    public final boolean hitTail(){
        int rowNext  = 0;
        int colNext = 0;

        int rowHead = this.head.getRow();
        int colHead = this.head.getCol();


        if(headDir.equals(Direction.DOWN)){
            rowNext = rowHead + 1;
            colNext = colHead;
        }else if(headDir == Direction.RIGHT){
            rowNext = rowHead ;
            colNext = colHead + 1;
        }else if(headDir == Direction.LEFT){
            rowNext = rowHead;
            colNext = colHead-1;
        }else if(headDir == Direction.UP){
            rowNext = rowHead -1;
            colNext = colHead;
        }


        return snakeList.contains(new Tile(TILE_WIDTH, TILE_HEIGHT, rowNext, colNext));

    }


    public void setHeadDir(Direction dir){
        this.headDir = dir;
    }

    public final Direction getHeadDir() {
        return headDir;
    }

    public final GridPane getPane() {
        return pane;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}