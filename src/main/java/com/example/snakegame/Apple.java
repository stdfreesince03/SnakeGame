package com.example.snakegame;

import javafx.scene.image.*;
import javafx.scene.paint.ImagePattern;

import java.io.File;

public final class Apple extends Image {

    private static final String slink = "src/main/java/com/example/icon/apple.png";

    private final  ImagePattern imgPat;

    private int posx;
    private int posy;

    public Apple(double width, double height,int posx,int posy) {
        super(new File(slink).toURI().toString(), width, height, true,true,false);
        this.posx = posx;
        this.posy = posy;
        this.imgPat = new ImagePattern(this);
    }

    public Apple(double width, double height) {
        super(new File(slink).toURI().toString(), width, height, true,true,false);
        this.posx = 0;
        this.posy = 0;
        this.imgPat = new ImagePattern(this);
    }

    public ImagePattern imgPatRet(){
        return this.imgPat;
    }



}
