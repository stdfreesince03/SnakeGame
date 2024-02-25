package com.example.snakegame;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

public class Main  extends Application {

    private  static SnakeGame sGame = new SnakeGame();

    private static final long[] lastTime = {System.nanoTime()};

    private static final boolean[] hitTail = {false};

    private static Direction dirNewStatic = sGame.getHeadDir();

    private static Label Count;

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        Timer t = new Timer();
        Count = new Label("FUCK");

        ImageView imgView = new ImageView(new File("src/main/java/com/example/icon/Flag_Of_China.png").toURI().toString());
        imgView.setFitWidth(200);
        imgView.setFitHeight(200);

        Label l1 = new Label("Quit");
        l1.setTextFill(Color.BLACK); //set black since default CSS Style sets it to background color of the Menu
        l1.addEventFilter(MouseEvent.MOUSE_PRESSED, ev -> {
            if (ev.getButton() == MouseButton.PRIMARY) {
                try{
                    Platform.exit();
                    System.exit(0);
                }catch(Exception e ){
                    e.printStackTrace();
                }

            }
            ev.consume(); //optional
        });

        Label l2 = new Label("Restart");
        l2.setTextFill(Color.BLACK);
        l2.addEventFilter(MouseEvent.MOUSE_PRESSED, ev -> {
            if (ev.getButton() == MouseButton.PRIMARY) {
                try{
                    sGame.reset();
                    dirNewStatic = Direction.DOWN;
                    start(primaryStage);
                    t.timerReset();
                }catch(Exception e ){
                    e.printStackTrace();
                }

            }
            ev.consume();
        });


        final MenuItem menu1 = new MenuItem("",l1);
        final MenuItem menu2 = new MenuItem("",l2);
        MenuButton menuBar = new MenuButton("Options",null,menu1,menu2);

        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);
        Region region2 = new Region();
        region2.setPrefWidth(100);
        HBox.setHgrow(region2, Priority.ALWAYS);
        HBox hBox = new HBox(menuBar,region1,Count,region2);

        BorderPane root = new BorderPane();
        root.setTop(hBox);
        root.setCenter(sGame.getPane());

        Scene scene = new Scene(root);
        scene.setOnKeyPressed(this::takeInput);
        scene.getRoot().requestFocus();

        t.start();

        primaryStage.setResizable(false);
        primaryStage.getIcons().add(imgView.getImage());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Snake Game");
        primaryStage.show();


    }

    private class Timer extends AnimationTimer {

        @Override
        public void handle(long now) {

            double elapsedTime = (now - lastTime[0]) / 1000000000.0;
            if (elapsedTime >= 0.15) {
                lastTime[0] = System.nanoTime();
                if (sGame.hitTail()) {
                    hitTail[0] = true;
                }
                if(!sGame.Opposite(dirNewStatic)){
                    sGame.setHeadDir(dirNewStatic);
                    sGame.move(sGame.getHeadDir());
                }

            }
            sGame.updateGrid();
            Count.setText(String.valueOf(sGame.getScore()) );
            if (!sGame.inWall()) {
                stop();

            }
            if (hitTail[0]) {
                stop();

            }
        }

        void timerReset(){
            lastTime[0] = System.nanoTime();
            hitTail[0] = false;
        }
    }

    private void takeInput(KeyEvent event) {
        Direction dirNew = null;

        switch (event.getCode()) {
            case UP, W -> {
                dirNew = Direction.UP;
            }
            case DOWN, S -> {
                dirNew = Direction.DOWN;
            }
            case LEFT, A -> {
                dirNew = Direction.LEFT;
            }
            case RIGHT, D -> {
                dirNew = Direction.RIGHT;
            }
        }

        if (dirNew != null) {
            if (!sGame.Opposite(dirNew)) {
               dirNewStatic = dirNew;
            }
        }

    }


}
