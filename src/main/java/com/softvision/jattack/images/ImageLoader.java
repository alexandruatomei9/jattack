package com.softvision.jattack.images;

import com.softvision.jattack.elements.invaders.ElementType;
import com.softvision.jattack.exception.UnableToLoadImagesException;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

public class ImageLoader {

    private final Map<ElementType, Image> images = new HashMap<>();
    private final static String imagesBasePath = "src/main/resources/images/";
    private static ImageLoader instance;

    private ImageLoader() throws Exception {
        System.getProperty("user.dir");

        FileInputStream input = new FileInputStream(imagesBasePath + "plane.png");
        Image image = new Image(input);
        input.close();
        images.put(ElementType.PLANE, image);

        input = new FileInputStream(imagesBasePath + "tank.png");
        image = new Image(input);
        input.close();
        images.put(ElementType.TANK, image);

        input = new FileInputStream(imagesBasePath + "helicopter.png");
        image = new Image(input);
        input.close();
        images.put(ElementType.HELICOPTER, image);

        input = new FileInputStream(imagesBasePath + "background.png");
        image = new Image(input);
        input.close();
        images.put(ElementType.BACKGROUND, image);

        input = new FileInputStream(imagesBasePath + "defender.png");
        image = new Image(input);
        input.close();
        images.put(ElementType.DEFENDER, image);
    }

    public static Image getImage(ElementType elementType){
        if(instance == null){
            synchronized (ImageLoader.class) {
                if(instance == null){
                    try {
                        instance = new ImageLoader();
                    } catch (Exception e) {
                        throw new UnableToLoadImagesException("Something wrong happen when trying to load the images");
                    }
                }
            }
        }

        return instance.images.get(elementType);
    }
}
