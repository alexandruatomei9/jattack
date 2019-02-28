package com.softvision.jattack;

import com.softvision.jattack.elements.Element;
import com.softvision.jattack.elements.ElementFactory;
import com.softvision.jattack.elements.ElementType;
import com.softvision.jattack.images.ImageLoader;
import com.softvision.jattack.util.Constants;
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
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class JAttack extends Application {
    private Canvas canvas;
    private List<Element> elements;

    @Override
    public void init() {
        this.elements = new ArrayList<>();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("J Attack 1.0 Alpha");
        Group root = new Group();
        this.canvas = new Canvas(Constants.GRID_COLUMNS * Constants.ELEMENT_WIDTH, Constants.GRID_LINES * Constants.ELEMENT_HEIGHT);
        GraphicsContext gc = this.canvas.getGraphicsContext2D();

        generateElements();

        elements.forEach(element -> drawImage(gc, element));

        StackPane holder = new StackPane();
        holder.getChildren().add(this.canvas);
        root.getChildren().add(holder);

        ImagePattern backgroundImage = new ImagePattern(ImageLoader.getImage(ElementType.BACKGROUND));
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

    private void generateElements() {
        for (int i = 0; i < Constants.NUMBER_OF_PLANES; i++) {
            elements.add(ElementFactory.generateElement(ElementType.PLANE));
        }

        for (int i = 0; i < Constants.NUMBER_OF_TANKS; i++) {
            elements.add(ElementFactory.generateElement(ElementType.TANK));
        }

        for (int i = 0; i < Constants.NUMBER_OF_HELICOPTERS; i++) {
            elements.add(ElementFactory.generateElement(ElementType.HELICOPTER));
        }
    }

    private void drawImage(GraphicsContext gc, Element element) {
        gc.drawImage(element.getImage(),
                element.getCoordinates().getX() * Constants.ELEMENT_WIDTH,
                element.getCoordinates().getY() * Constants.ELEMENT_HEIGHT,
                element.getImage().getWidth(),
                element.getImage().getHeight());
    }
}
