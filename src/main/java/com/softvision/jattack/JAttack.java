package com.softvision.jattack;

import com.softvision.jattack.coordinates.Coordinates;
import com.softvision.jattack.images.ImageLoader;
import com.softvision.jattack.coordinates.RandomCoordinates;
import com.softvision.jattack.images.SimpleImageLoader;
import javafx.animation.AnimationTimer;
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
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JAttack extends Application {

    private static final int eHeight = 40;
    private static final int eWidth = 40;
    private static final int gridWidth = 40;
    private static final int gridHeight = 20;
    private static final int NUMBER_OF_PLANES = 6;
    private static final int NUMBER_OF_TANKS = 13;
    private static final int NUMBER_OF_HELICOPTERS = 7;


    private Canvas canvas;
    private ImageLoader loader;

    @Override
    public void init() throws Exception {
        this.loader = new SimpleImageLoader();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("J Attack 1.0 Alpha");
        Group root = new Group();
        this.canvas = new Canvas(gridWidth * eWidth, gridHeight * eHeight);
        GraphicsContext gc = this.canvas.getGraphicsContext2D();

        loadImagesAndDrawShapes(gc);

        StackPane holder = new StackPane();
        holder.getChildren().add(this.canvas);
        root.getChildren().add(holder);

        ImagePattern backgroundImage = new ImagePattern(loader.load(ElementType.BACKGROUND));
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
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        System.out.println("Inside stop() method! Destroy resources. Perform Cleanup.");
    }

    private void loadImagesAndDrawShapes(GraphicsContext gc) {
        List<Coordinates> coordinatesInUse = new ArrayList<>();
        Image plane = loader.load(ElementType.PLANE);
        Image tank = loader.load(ElementType.TANK);
        Image helicopter = loader.load(ElementType.HELICOPTER);

        for (int i = 0; i < NUMBER_OF_PLANES; i++) {
            computeCoordinatesAndDrawImage(gc, plane, coordinatesInUse);
        }

        for (int i = 0; i < NUMBER_OF_TANKS; i++) {
            computeCoordinatesAndDrawImage(gc, tank, coordinatesInUse);
        }

        for (int i = 0; i < NUMBER_OF_HELICOPTERS; i++) {
            computeCoordinatesAndDrawImage(gc, helicopter, coordinatesInUse);
        }
    }

    private void computeCoordinatesAndDrawImage(GraphicsContext gc, Image image, List<Coordinates> coordinatesInUse) {
        Coordinates coordinates;
        do {
            coordinates = new RandomCoordinates(gridWidth - (int) (image.getWidth() / eWidth), gridHeight - (int) (image.getHeight() / eHeight));
        } while (coordinatesOverlapAnotherImage(coordinates, coordinatesInUse));

        coordinatesInUse.add(coordinates);

        gc.drawImage(image,
                coordinates.getX() * eWidth,
                coordinates.getY() * eHeight,
                image.getWidth(),
                image.getHeight());
    }

    private boolean coordinatesOverlapAnotherImage(Coordinates coordinates, List<Coordinates> coordinatesInUse) {
        Optional<Coordinates> existingImageAtTheSpecifiedCoordinates = coordinatesInUse.stream().filter(c -> {
            return !((c.getX() - coordinates.getX() >= 3 || c.getX() - coordinates.getX() <= -3)
                     || (c.getY() - coordinates.getY() >= 3 || c.getY() - coordinates.getY() <= -3));
        }).findFirst();

        return existingImageAtTheSpecifiedCoordinates.isPresent();
    }
}
