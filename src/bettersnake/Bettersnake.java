/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bettersnake;

import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.*;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author asus
 */
public class Bettersnake extends Application {

    double deltaX;
    double deltaY;
    double oldX;
    double oldY;
    double newX;
    double newY;

    public double rectangleSpeed = 100; // pixels per second

    public double maxX = 1200;
    public double maxY = 900;
    //Boolean erase = false;
    Boolean Ai = false;
    int lenght = 0, Score = 0;
    double g = 0, g2 = 0;
    int x = 100;
    int nn = 0;
    Media media;
    MediaPlayer mediaPlayer;
    String sound = new String("sound1");
    String wav = new String(".wav");
    int soundcounter;

    public void start(Stage primaryStage) throws Exception {
        AnchorPane ancPane = new AnchorPane();
        final Rectangle rect = new Rectangle();
        rect.setTranslateX(100);
        rect.setTranslateY(100);
        rect.setHeight(50);
        rect.setWidth(50);
        //rect.setFill(Color.DARKRED);
        // Rectangle2D r2= new Rectangle2D(0,0,1000,100);
        //Image image = new Image(new FileInputStream("C:\\Users\\asus\\Desktop\\Programming\\8081_earthmap4k.jpg"));
        //ImageView image = new ImageView();
        // Image imag = new Image(new FileInputStream("C:\\Users\\asus\\Desktop\\Programming\\8081_earthmap4k.jpg"));
        //imag.getHeight();
        // image.setImage(imag);
        ancPane.getChildren().add(rect);
        //image.setViewport(r2);//X(-1700);
        /* image.setScaleX(0.1);
        image.setY(-500);
        image.setScaleY(0.5);*/
        //ancPane.getChildren().add(image);
        Image snakehead = new Image(new FileInputStream("snakeheadgame.png"));
        Image map = new Image(new FileInputStream("map.jpg"));
        ImageView mapp=new ImageView(map);
        ImagePattern pattern = new ImagePattern(snakehead, 0, 0, rect.getWidth(), rect.getHeight(), false);
        //Setting the pattern
        rect.setFill(pattern);

        media = new Media(new File(sound + wav).toURI().toURL().toString());

        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setMute(true);
        mediaPlayer.play();
        //mediaPlayer.stop();
        //mediaPlayer.stop();
        //ancPane.getChildren().add();
        ancPane.getChildren().add(0,mapp);
        Scene scene = new Scene(ancPane, 400, 200, Color.WHITE);

        primaryStage.setScene(scene);
        primaryStage.show();

        Random rd = new Random();
        Circle cr = new Circle();
        cr.setRadius(10);
        cr.setStroke(Color.RED);
        cr.setStrokeWidth(2);
        cr.setFill(Color.YELLOW);
        cr.setTranslateX(20 + rd.nextInt((int) ancPane.getWidth() - 40));
        cr.setTranslateY(20 + rd.nextInt((int) ancPane.getHeight() - 40));
        ancPane.getChildren().add(cr);

// whatever the max value should be.. can use a property and bind to scene width if needed...
        final DoubleProperty rectangleVelocityX = new SimpleDoubleProperty();
        final DoubleProperty rectangleVelocityY = new SimpleDoubleProperty();
        final LongProperty lastUpdateTime = new SimpleLongProperty();

        Polyline polyline = new Polyline();
        polyline.setStrokeWidth(30);
        polyline.setStroke(Color.DARKGREEN);
        /* Polyline polyline1 = new Polyline();
        polyline1.setStrokeWidth(10);
        polyline1.setStroke(Color.BLACK);*/
        DropShadow borderEffect = new DropShadow();
        borderEffect.setSpread(0.1);
        borderEffect.setColor(Color.YELLOWGREEN);
        //borderEffect.setSpread(1);
        polyline.setEffect(borderEffect);
        rect.setEffect(borderEffect);

        //Paint p =new ; 
        Label l = new Label("0");
        l.setTranslateX(50);
        l.setFont(Font.font(30));
        l.setTextFill(Color.FIREBRICK);
        l.setStyle("-fx-font-weight: bold");
        ancPane.getChildren().add(l);
        Random r = new Random();
        //polyline.set(Color.RED);
        //polyline.getPoints().addAll(50.0,50.0,150.0,150.0);       
        ancPane.getChildren().add(1, polyline);
        //ancPane.getChildren().add(1, polyline1);

        final AnimationTimer rectangleAnimation0 = new AnimationTimer() {
            @Override
            public void handle(long now) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        };
        final AnimationTimer rectangleAnimation = new AnimationTimer() {
            @Override
            public void handle(long timestamp) {
                timestamp = 10;
                deltaX = 0.02 * rectangleVelocityX.get();
                deltaY = 0.02 * rectangleVelocityY.get();

                oldX = rect.getTranslateX();
                newX = Math.max(0, Math.min(maxX, oldX + deltaX));
                oldY = rect.getTranslateY();
                newY = Math.max(0, Math.min(maxY, oldY + deltaY));
                maxX = scene.getWidth() - rect.getWidth();
                maxY = scene.getHeight() - rect.getHeight();
                rect.setTranslateX(newX);
                rect.setTranslateY(newY);
                if (!(newX - oldX == 0) || !(newY - oldY == 0)) {

                    polyline.getPoints().addAll(newX + 25, newY + 25);
                    // polyline1.getPoints().addAll(newX + 25, newY + 25);

                    if (lenght <= 0) {

                        if (!polyline.getPoints().isEmpty()) {
                            polyline.getPoints().remove(0);
                            polyline.getPoints().remove(0);
                            // polyline1.getPoints().remove(0);
                            // polyline1.getPoints().remove(0);
                        }

                    }

                    if (lenght > 0) {
                        lenght--;
                    }
                    g = (Math.abs(rect.getTranslateX() + 25 - cr.getTranslateX()));
                    g2 = (Math.abs(rect.getTranslateY() + 25 - cr.getTranslateY()));

                    //ls:                     
                    if ((25 >= g) && (25 >= g2)) {
                        cr.setTranslateX(+10 + rd.nextInt((int) ancPane.getWidth() - 30));
                        cr.setTranslateY(20 + rd.nextInt((int) ancPane.getHeight() - 40));
                        lenght = lenght + 1000 / x;
                        l.setText(String.valueOf(++Score));
                        mediaPlayer = new MediaPlayer(media);
                        mediaPlayer.play();

                    }

                }
                if (Ai) {
                    if (g <= 20) {
                        rectangleVelocityX.set(0);
                        if (rect.getTranslateY() + 25 - cr.getTranslateY() < 0) {
                            rectangleVelocityX.set(0);
                            rectangleVelocityY.set(x);
                            rect.setRotate(0);
                        }
                        if (rect.getTranslateY() + 25 - cr.getTranslateY() > 0) {
                            rectangleVelocityX.set(0);
                            rectangleVelocityY.set(-x);
                            rect.setRotate(180);
                        }
                    } else {
                        if (rect.getTranslateX() + 25 - cr.getTranslateX() < 0) {
                            rectangleVelocityX.set(x);
                            rectangleVelocityY.set(0);
                            rect.setRotate(270);

                        }
                        if (rect.getTranslateX() + 25 - cr.getTranslateX() > 0) {
                            rectangleVelocityX.set(-x);
                            rectangleVelocityY.set(0);
                            rect.setRotate(90);

                        }
                    }
                }
            }

        };

        rectangleAnimation.start();
        ArrayList<Integer> cc = new ArrayList<>();

        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //polyline.getPoints().addAll(event.getX(), event.getY());
                maxX = scene.getWidth() - rect.getWidth();
                maxY = scene.getHeight() - rect.getHeight();

                rectangleVelocityX.set(1 * (event.getX() - rect.getTranslateX() - 25));
                rectangleVelocityY.set(1 * (event.getY() - rect.getTranslateY() - 25));
                //rect.setRotate(x);//Math.acos((event.getX() - rect.getTranslateX())));
                double angle = ((180 / Math.PI) * (Math.atan((event.getY() - rect.getTranslateY() - 25) / (event.getX() - rect.getTranslateX() - 25))));
                if (event.getX() > rect.getTranslateX() + 25) {
                    //System.out.println("left");
                    rect.setRotate(-90 + angle);
                }
                if (event.getX() < rect.getTranslateX() + 25) {
                    //System.out.println("right");
                    rect.setRotate(90 + angle);
                }
                //System.out.println((180/Math.PI)*(Math.atan((event.getY() - rect.getTranslateY()-25)/(event.getX() - rect.getTranslateX()-25))));

            }
        });
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                rectangleAnimation.start();
                switch (e.getCode()) {

                    case W:
                        rectangleVelocityY.set(-x);
                        rectangleVelocityX.set(0);
                        rect.setRotate(180);
                        break;
                    case S:
                        rectangleVelocityY.set(x);
                        rectangleVelocityX.set(0);
                        rect.setRotate(0);
                        break;
                    case A:
                        rectangleVelocityX.set(-x);
                        rectangleVelocityY.set(0);
                        rect.setRotate(90);
                        break;
                    case D:
                        rectangleVelocityX.set(x);
                        rectangleVelocityY.set(0);
                        rect.setRotate(270);
                        break;
                    case T:
                        Ai = !Ai;
                        break;
                    case ENTER:
                        rectangleAnimation.stop();
                        break;
                    case UP:
                        x += 20;
                        //System.out.println(x);
                        break;
                    case LEFT:
                        //System.out.println("bettersnake.Bettersnake.start()");
                        polyline.setStroke(Color.color(r.nextDouble(), r.nextDouble(), r.nextDouble()));
                        //polyline.setStroke(Color.color(0.5,0.221,0.21));
                        break;
                    case F11:
                        primaryStage.setFullScreen(true);

                        break;
                    /*case ESCAPE:
                        primaryStage.setFullScreen(false);
                        
                        break;*/
                    case DOWN:
                        if (x >= 20) {
                            x -= 20;
                        }
                        //System.out.println(x);
                        break;
                    case E:

                        if (!polyline.getPoints().isEmpty()) {
                            for (int i = 0; i < 10; i++) {
                                polyline.getPoints().remove(0);
                                polyline.getPoints().remove(0);
                                // polyline1.getPoints().remove(0);
                                // polyline1.getPoints().remove(0);
                            }
                            if (Score > 0) {
                                l.setText(String.valueOf(--Score));
                            }

                        }
                        break;
                    case NUMPAD1:
                        if (soundcounter < 3) {
                            soundcounter+=2;
                        }

                        //System.out.println(sound);
                        
                    case NUMPAD0:
                        if (soundcounter > 0) {
                            try {
                                soundcounter--;

                                sound = sound.substring(0, sound.length() - 1) + String.valueOf(soundcounter);
                                media = new Media(new File(sound + wav).toURI().toURL().toString());
                                mediaPlayer = new MediaPlayer(media);
                                //mediaPlayer.play();
                            } catch (MalformedURLException ex) {
                                Logger.getLogger(Bettersnake.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        //System.out.println(sound);
                        break;

                }
            }
        });

    }
}
