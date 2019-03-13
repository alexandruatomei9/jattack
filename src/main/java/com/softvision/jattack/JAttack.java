package com.softvision.jattack;

import com.softvision.jattack.coordinates.CoordinatesCache;
import com.softvision.jattack.coordinates.FixedCoordinates;
import com.softvision.jattack.elements.Defender;
import com.softvision.jattack.elements.bullets.Bullet;
import com.softvision.jattack.elements.bullets.DefenderBullet;
import com.softvision.jattack.elements.bullets.PlaneBullet;
import com.softvision.jattack.elements.bullets.TankBullet;
import com.softvision.jattack.elements.invaders.Invader;
import com.softvision.jattack.elements.invaders.InvaderFactory;
import com.softvision.jattack.elements.invaders.ImageType;
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
import javafx.scene.image.Image;
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
import java.util.Random;

public class JAttack extends Application implements Runnable {
    private Canvas canvas;
    private List<Invader> invaders;
    private final Thread gameThread;
    private GraphicsContext graphicsContext;
    private Defender defender;
    private Random random = new Random();

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

        defender = new Defender(new FixedCoordinates((Constants.WIDTH / 2) - 50, Constants.HEIGHT - 150));
        drawDefender(defender);

        StackPane holder = new StackPane();
        holder.getChildren().add(this.canvas);
        root.getChildren().add(holder);

        ImagePattern backgroundImage = new ImagePattern(ImageLoader.getImage(ImageType.BACKGROUND));
        holder.setBackground(new Background(new BackgroundFill(backgroundImage, CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(root);
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.RIGHT) {
                defender.move(e.getCode());
            }

            if (e.getCode() == KeyCode.SPACE) {
                defender.shoot(graphicsContext);
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
        boolean gameEnded = false;
        while (!gameEnded) {
            try {
                Thread.sleep(Util.getTick());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (this.invaders.isEmpty()) {
                CoordinatesCache.getInstance().getEnemyBullets().clear();
                CoordinatesCache.getInstance().getDefenderBullets().clear();
                redraw();
                Image youWon = ImageLoader.getImage(ImageType.YOU_WON);
                graphicsContext.drawImage(youWon,
                        Constants.WIDTH / 2 - 100,
                        Constants.HEIGHT / 2 - 100,
                        youWon.getWidth(),
                        youWon.getHeight());
                gameEnded = true;
            } else if (this.defender.isDead()) {
                CoordinatesCache.getInstance().getEnemyBullets().clear();
                CoordinatesCache.getInstance().getDefenderBullets().clear();
                invaders.clear();
                redraw();
                Image youLost = ImageLoader.getImage(ImageType.YOU_LOST);
                graphicsContext.drawImage(youLost,
                        Constants.WIDTH / 2 - 100,
                        Constants.HEIGHT / 2 - 100,
                        youLost.getWidth(),
                        youLost.getHeight());
                gameEnded = true;
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
                        //either move or shoot
                        boolean shouldShoot = random.nextBoolean();
                        if (shouldShoot) {
                            invader.shoot(graphicsContext);
                        } else {
                            invader.move();
                        }
                    }
                }
            }

            synchronized (Util.lockOn()) {
                if (this.defender.wasHit()) {
                    this.defender.decreaseLife();
                }
            }

            if(!gameEnded) {
                redraw();
            }
        }
    }

    private void generateElements() {
        for (int i = 0; i < Constants.NUMBER_OF_PLANES; i++) {
            invaders.add(InvaderFactory.generateElement(ImageType.PLANE));
        }

        for (int i = 0; i < Constants.NUMBER_OF_TANKS; i++) {
            invaders.add(InvaderFactory.generateElement(ImageType.TANK));
        }

        for (int i = 0; i < Constants.NUMBER_OF_HELICOPTERS; i++) {
            invaders.add(InvaderFactory.generateElement(ImageType.HELICOPTER));
        }
    }

    private void drawImage(Invader invader) {
        graphicsContext.drawImage(invader.getImage(),
                invader.getCoordinates().getX(),
                invader.getCoordinates().getY(),
                invader.getImage().getWidth(),
                invader.getImage().getHeight());
    }

    private void drawDefender(Defender defender) {
        if (!defender.isDead()) {
            graphicsContext.drawImage(defender.getImage(),
                    defender.getCoordinates().getX(),
                    defender.getCoordinates().getY(),
                    defender.getImage().getWidth(),
                    defender.getImage().getHeight());
        }
    }

    private void redraw() {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        invaders.forEach(this::drawImage);
        drawDefender(defender);
        CoordinatesCache.getInstance().getEnemyBullets().forEach(b -> b.draw(graphicsContext));
        CoordinatesCache.getInstance().getDefenderBullets().forEach(b -> b.draw(graphicsContext));
    }
}
