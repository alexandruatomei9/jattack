package com.softvision.jattack.images;

import com.softvision.jattack.elements.invaders.ImageType;
import com.softvision.jattack.exception.UnableToLoadImagesException;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

public class ImageLoader {

    private final Map<ImageType, Image> images = new HashMap<>();
    private final static String imagesBasePath = "src/main/resources/images/";
    private static ImageLoader instance;

    private ImageLoader() throws Exception {
        FileInputStream input = new FileInputStream(imagesBasePath + "plane.png");
        Image image = new Image(input);
        input.close();
        images.put(ImageType.PLANE, image);

        input = new FileInputStream(imagesBasePath + "tank.png");
        image = new Image(input);
        input.close();
        images.put(ImageType.TANK, image);

        input = new FileInputStream(imagesBasePath + "helicopter.png");
        image = new Image(input);
        input.close();
        images.put(ImageType.HELICOPTER, image);

        input = new FileInputStream(imagesBasePath + "background.png");
        image = new Image(input);
        input.close();
        images.put(ImageType.BACKGROUND, image);

        input = new FileInputStream(imagesBasePath + "defender.png");
        image = new Image(input);
        input.close();
        images.put(ImageType.DEFENDER, image);

        input = new FileInputStream(imagesBasePath + "you_won.png");
        image = new Image(input);
        input.close();
        images.put(ImageType.YOU_WON, image);

        input = new FileInputStream(imagesBasePath + "you_lost.png");
        image = new Image(input);
        input.close();
        images.put(ImageType.YOU_LOST, image);
    }

    public static Image getImage(ImageType imageType){
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

        return instance.images.get(imageType);
    }
}
