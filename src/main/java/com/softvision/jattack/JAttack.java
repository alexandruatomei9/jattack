package com.softvision.jattack;

import com.softvision.jattack.coordinates.Coordinates;
import com.softvision.jattack.coordinates.CoordinatesCache;
import com.softvision.jattack.coordinates.FixedCoordinates;
import com.softvision.jattack.elements.Element;
import com.softvision.jattack.elements.defender.Defender;
import com.softvision.jattack.elements.invaders.InvaderFactory;
import com.softvision.jattack.images.ImageLoader;
import com.softvision.jattack.images.ImageType;
import com.softvision.jattack.manager.DefaultElementManager;
import com.softvision.jattack.manager.ElementManager;
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
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class JAttack extends Application implements Runnable, GameManager {
    private final Thread gameThread;
    private Set<Thread> invaderThreads;
    private GraphicsContext graphicsContext;
    private Defender defender;
    private ElementManager invadersManager;
    private AtomicBoolean gameEnded = new AtomicBoolean();

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
        invadersManager = new DefaultElementManager();
        invaderThreads = generateInvaderThreads();

        StackPane holder = new StackPane();
        holder.getChildren().add(canvas);

        root.getChildren().add(holder);

        defender = new Defender(new FixedCoordinates((Constants.WIDTH / 2) - 50, Constants.HEIGHT - 150), this);
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

        invadersManager.getElements().forEach(e -> draw(e));

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
        while (!this.gameEnded()) {
            try {
                Thread.sleep(Util.getTick());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        synchronized (Util.lockOn()) {
            clearCanvas();
            if (this.getInvadersNumber() == 0) {
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
    }

    private Set<Thread> generateInvaderThreads() {
        Set<Thread> threads = new HashSet<>();
        for (int i = 0; i < Constants.NUMBER_OF_PLANES; i++) {
            Element element = InvaderFactory.generateElement(ImageType.PLANE, this);
            invadersManager.addElement(element);
            threads.add(new Thread(element));
        }

        for (int i = 0; i < Constants.NUMBER_OF_TANKS; i++) {
            Element element = InvaderFactory.generateElement(ImageType.TANK, this);
            invadersManager.addElement(element);
            threads.add(new Thread(element));
        }

        for (int i = 0; i < Constants.NUMBER_OF_HELICOPTERS; i++) {
            Element element = InvaderFactory.generateElement(ImageType.HELICOPTER, this);
            invadersManager.addElement(element);
            threads.add(new Thread(element));
        }

        return threads;
    }

    private void clearCanvas() {
        graphicsContext.clearRect(0, 0, Constants.WIDTH, Constants.HEIGHT);
    }

    @Override
    public void choseAndExecuteAction(Element element) {
        boolean shouldShoot = Util.randomBoolean();
        if (shouldShoot) {
            element.shoot();
        } else {
            invadersManager.move(element);
        }

        draw(element);
        drawElementBullets(element);
    }

    @Override
    public boolean isAlive(Element element) {
        if (!invadersManager.isAlive(element)) {
            invadersManager.removeElement(element);
        }

        return invadersManager.isAlive(element);
    }

    @Override
    public void decrementLife(Element element) {
        invadersManager.decrementLife(element);
        if(!isAlive(element)) {
            invadersManager.removeElement(element);
        }
    }

    @Override
    public boolean wasHit(Element element) {
        boolean wasHit = false;

        if(element instanceof Defender) {
            Iterator<Coordinates> invaderBulletsCoordinatesIterator = CoordinatesCache.getInstance().getEnemyBulletsCoordinates().iterator();
            while (invaderBulletsCoordinatesIterator.hasNext()) {
                Coordinates bulletCoordinates = invaderBulletsCoordinatesIterator.next();
                int bulletX = bulletCoordinates.getX();
                int bulletY = bulletCoordinates.getY();
                if (element.getCoordinates().getX() <= bulletX && bulletX <= element.getCoordinates().getX() + 100) {
                    if (element.getCoordinates().getY() - 10 <= bulletY) {
                        invaderBulletsCoordinatesIterator.remove();
                        wasHit = true;
                        break;
                    }
                }
            }
        } else {
            Iterator<Coordinates> defenderBulletCoordinatesIterator = CoordinatesCache.getInstance().getDefenderBulletsCoordinates().iterator();
            while (defenderBulletCoordinatesIterator.hasNext()) {
                Coordinates bulletCoordinates = defenderBulletCoordinatesIterator.next();
                int bulletX = bulletCoordinates.getX();
                int bulletY = bulletCoordinates.getY();
                if (element.getCoordinates().getY() < 0) {
                    defenderBulletCoordinatesIterator.remove();
                } else if (element.getCoordinates().getX() <= bulletX && bulletX <= element.getCoordinates().getX() + 110) {
                    if (element.getCoordinates().getY() + 100 >= bulletY) {
                        defenderBulletCoordinatesIterator.remove();
                        wasHit = true;
                        break;
                    }
                }
            }
        }
        return wasHit;
    }

    @Override
    public void draw(Element element) {
        emptySpace(element);
        graphicsContext.drawImage(element.getImage(),
                element.getCoordinates().getX(),
                element.getCoordinates().getY(),
                element.getImage().getWidth(),
                element.getImage().getHeight());
    }

    @Override
    public void drawElementBullets(Element element) {
        element.getBulletsCoordinates().forEach(c ->  {
            graphicsContext.clearRect(c.getX(), c.getY(), element.getBulletWidth(), element.getBulletHeight());
            graphicsContext.setFill(element.getBulletColor());
            c.setY(c.getY() + element.getBulletVelocity());
            graphicsContext.fillRect(c.getX(), c.getY(), element.getBulletWidth(), element.getBulletHeight());
        });
    }

    @Override
    public boolean gameEnded() {
        return gameEnded.get();
    }

    @Override
    public void setGameEnded(boolean gameEnded) {
        this.gameEnded.set(gameEnded);
    }

    @Override
    public void clearElement(Element element) {
        element.setPreviousPosition(element.getCoordinates());
        emptySpace(element);
        cleanBullets(element);
        invadersManager.removeElement(element);
    }

    @Override
    public int getInvadersNumber() {
        return invadersManager.getElements().size();
    }

    public void emptySpace(Element element) {
        if(element.getPreviousPosition() != null) {
            graphicsContext.clearRect(element.getPreviousPosition().getX(),
                    element.getPreviousPosition().getY(),
                    element.getImage().getWidth(),
                    element.getImage().getHeight());
        }
    }

    public void cleanBullets(Element element) {
        element.getBulletsCoordinates().forEach(b -> graphicsContext.clearRect(b.getX(), b.getY(), element.getBulletWidth(), element.getBulletHeight()));
    }
}
