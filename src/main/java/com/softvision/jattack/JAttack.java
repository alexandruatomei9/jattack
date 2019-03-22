package com.softvision.jattack;

import com.softvision.jattack.coordinates.FixedCoordinates;
import com.softvision.jattack.elements.Element;
import com.softvision.jattack.elements.defender.Defender;
import com.softvision.jattack.elements.invaders.InvaderFactory;
import com.softvision.jattack.images.ImageLoader;
import com.softvision.jattack.images.ImageType;
import com.softvision.jattack.manager.DefaultElementManager;
import com.softvision.jattack.manager.ElementManager;
import com.softvision.jattack.manager.DefaultGameManager;
import com.softvision.jattack.manager.GameManager;
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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Set;

public class JAttack extends Application implements Runnable {
    private final Thread gameThread;
    private Set<Thread> invaderThreads;
    private GraphicsContext graphicsContext;
    private Defender defender;
    private ElementManager invadersManager;
    private GameManager gameManager;

    @Override
    public void init() {
    }

    public JAttack() {
        gameThread = new Thread(this);
    }

    public static void main(String... args) throws Exception {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("J Attack 1.0 Alpha");
        Group root = new Group();
        Canvas canvas = new Canvas(Constants.WIDTH, Constants.HEIGHT);
        graphicsContext = canvas.getGraphicsContext2D();
        invadersManager = new DefaultElementManager(graphicsContext);
        gameManager = new DefaultGameManager(invadersManager);

        invaderThreads = generateInvaderThreads();

        StackPane holder = new StackPane();
        holder.getChildren().add(canvas);

        root.getChildren().add(holder);

        defender = new Defender(new FixedCoordinates((Constants.WIDTH / 2) - 50, Constants.HEIGHT - 150), gameManager);
        Thread defenderThread = new Thread(defender);

        ImagePattern backgroundImage = new ImagePattern(ImageLoader.getImage(ImageType.BACKGROUND));
        holder.setBackground(new Background(new BackgroundFill(backgroundImage, CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(root);
        scene.setOnKeyPressed(defender.getEventHandler());

        primaryStage.setScene(scene);

        primaryStage.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });

        invadersManager.getElements().forEach(e -> invadersManager.drawElement(e));

        primaryStage.show();
        gameThread.start();
        invaderThreads.forEach(Thread::start);
        defenderThread.start();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        System.out.println("Inside stop() method! Destroy resources. Perform Cleanup.");
    }

    @Override
    public void run() {
        while (!gameManager.gameEnded()) {
            try {
                Thread.sleep(Util.getTick());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            for (Thread invaderThread : invaderThreads) {
                invaderThread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        clearCanvas();

        if (gameManager.getInvadersNumber() == 0) {
            Image youWon = ImageLoader.getImage(ImageType.YOU_WON);
            graphicsContext.drawImage(youWon,
                    Constants.WIDTH / 2 - 100,
                    Constants.HEIGHT / 2 - 100,
                    youWon.getWidth(),
                    youWon.getHeight());
        } else if (!this.defender.isAlive()) {
            Image youLost = ImageLoader.getImage(ImageType.YOU_LOST);
            graphicsContext.drawImage(youLost,
                    Constants.WIDTH / 2 - 100,
                    Constants.HEIGHT / 2 - 100,
                    youLost.getWidth(),
                    youLost.getHeight());
        }
    }

    private Set<Thread> generateInvaderThreads() {
        Set<Thread> threads = new HashSet<>();
        for (int i = 0; i < Constants.NUMBER_OF_PLANES; i++) {
            Element element = InvaderFactory.generateElement(ImageType.PLANE, gameManager);
            invadersManager.addElement(element);
            threads.add(new Thread(element));
        }

        for (int i = 0; i < Constants.NUMBER_OF_TANKS; i++) {
            Element element = InvaderFactory.generateElement(ImageType.TANK, gameManager);
            invadersManager.addElement(element);
            threads.add(new Thread(element));
        }

        for (int i = 0; i < Constants.NUMBER_OF_HELICOPTERS; i++) {
            Element element = InvaderFactory.generateElement(ImageType.HELICOPTER, gameManager);
            invadersManager.addElement(element);
            threads.add(new Thread(element));
        }

        return threads;
    }

    private void clearCanvas() {
        graphicsContext.clearRect(0, 0, Constants.WIDTH, Constants.HEIGHT);
    }
}
