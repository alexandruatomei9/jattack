package com.softvision.jattack;

import com.softvision.jattack.coordinates.CoordinatesCache;
import com.softvision.jattack.elements.bullets.Bullet;
import com.softvision.jattack.elements.bullets.PlaneBullet;
import com.softvision.jattack.elements.bullets.TankBullet;
import com.softvision.jattack.elements.invaders.Invader;
import com.softvision.jattack.elements.invaders.InvaderFactory;
import com.softvision.jattack.elements.invaders.InvaderType;
import com.softvision.jattack.elements.bullets.HelicopterBullet;
import com.softvision.jattack.images.ImageLoader;
import com.softvision.jattack.util.Constants;
import com.softvision.jattack.util.Util;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class JAttack extends Application implements Runnable {
    private Canvas canvas;
    private List<Invader> invaders;
    private final Thread gameThread;
    private GraphicsContext graphicsContext;

    @Override
    public void init() {
        this.invaders = new ArrayList<>();
    }

    public JAttack() {
        gameThread = new Thread(this);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("J Attack 1.0 Alpha");
        Group root = new Group();
        this.canvas = new Canvas(Constants.WIDTH, Constants.HEIGHT);
        graphicsContext = this.canvas.getGraphicsContext2D();

        generateElements();

        invaders.forEach(this::drawImage);

        StackPane holder = new StackPane();
        holder.getChildren().add(this.canvas);
        root.getChildren().add(holder);

        ImagePattern backgroundImage = new ImagePattern(ImageLoader.getImage(InvaderType.BACKGROUND));
        holder.setBackground(new Background(new BackgroundFill(backgroundImage, CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(root);
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT) {
                System.out.println("Left key was pressed");
            }

            if (e.getCode() == KeyCode.RIGHT) {
                System.out.println("Right key was pressed");
            }

            if (e.getCode() == KeyCode.SPACE) {
                System.out.println("Space key was pressed");
            }
        });

        primaryStage.setScene(scene);

        primaryStage.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });

        primaryStage.show();
        gameThread.start();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        System.out.println("Inside stop() method! Destroy resources. Perform Cleanup.");
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(Util.getTick());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < invaders.size(); i++) {
                synchronized (Util.lockOn()) {
                    Invader invader = invaders.get(i);
                    if (invader.wasHit()) {
                        invader.decrementLife();
                        if (invader.isDead()) {
                            invaders.remove(invader);
                            CoordinatesCache.getInstance().getCoordinatesInUse().remove(invader.getCoordinates());
                        }
                    } else {
                        invader.shoot(graphicsContext);
                        invader.move();
                    }
                }
            }

            redraw();
        }
    }

    private void generateElements() {
        for (int i = 0; i < Constants.NUMBER_OF_PLANES; i++) {
            invaders.add(InvaderFactory.generateElement(InvaderType.PLANE));
        }

        for (int i = 0; i < Constants.NUMBER_OF_TANKS; i++) {
            invaders.add(InvaderFactory.generateElement(InvaderType.TANK));
        }

        for (int i = 0; i < Constants.NUMBER_OF_HELICOPTERS; i++) {
            invaders.add(InvaderFactory.generateElement(InvaderType.HELICOPTER));
        }
    }

    private void drawImage(Invader invader) {
        graphicsContext.drawImage(invader.getImage(),
                invader.getCoordinates().getX(),
                invader.getCoordinates().getY(),
                invader.getImage().getWidth(),
                invader.getImage().getHeight());
    }

    private void drawBullet(Bullet bullet) {
        switch (bullet.getShape()) {
            case OVAL:
                bullet.getCoordinates().setY(bullet.getCoordinates().getY() + bullet.getVelocity());
                graphicsContext.setFill(bullet.getColor());
                graphicsContext.fillOval(bullet.getCoordinates().getX(), bullet.getCoordinates().getY(), ((PlaneBullet) bullet).getWidth(), ((PlaneBullet) bullet).getHeight());
                break;
            case CHAR:
                bullet.getCoordinates().setY(bullet.getCoordinates().getY() + bullet.getVelocity());
                graphicsContext.setFill(bullet.getColor());
                graphicsContext.setFont(new Font("Arial Bold", ((HelicopterBullet) bullet).getBulletSize()));
                graphicsContext.fillText(((HelicopterBullet) bullet).getBulletShape(), bullet.getCoordinates().getX(), bullet.getCoordinates().getY());
                break;
            case CIRCLE:
                bullet.getCoordinates().setY(bullet.getCoordinates().getY() + bullet.getVelocity());
                graphicsContext.setFill(bullet.getColor());
                graphicsContext.fillOval(bullet.getCoordinates().getX(), bullet.getCoordinates().getY(), ((TankBullet) bullet).getBulletDiameter(), ((TankBullet) bullet).getBulletDiameter());
                break;
            default:
                throw new IllegalArgumentException("Illegal bullet shape");
        }
    }

    private void redraw() {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        invaders.forEach(this::drawImage);
        CoordinatesCache.getInstance().getEnemyBullets().forEach(this::drawBullet);
    }
}
