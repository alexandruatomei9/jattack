package com.softvision.jattack.images;

import com.softvision.jattack.ElementType;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

public class SimpleImageLoader implements ImageLoader {

    private final Map<ElementType, Image> images = new HashMap<>();
    private final static String imagesBasePath = "src/main/resources/images/";

    public SimpleImageLoader() throws Exception {
        System.getProperty("user.dir");

        FileInputStream input = new FileInputStream(imagesBasePath + "plane.png");
        Image image = new Image(input);
        input.close();
        images.put(ElementType.PLANE, image);

        input = new FileInputStream(imagesBasePath + "tank.png");
        image = new Image(input);
        input.close();
        images.put(ElementType.TANK, image);

        input = new FileInputStream(imagesBasePath + "boat.png");
        image = new Image(input);
        input.close();
        images.put(ElementType.BOAT, image);

        input = new FileInputStream(imagesBasePath + "helicopter.png");
        image = new Image(input);
        input.close();
        images.put(ElementType.HELICOPTER, image);

        input = new FileInputStream(imagesBasePath + "background.png");
        image = new Image(input);
        input.close();
        images.put(ElementType.BACKGROUND, image);
    }

    @Override
    public Image load(ElementType e) {
        return images.get(e);
    }
}
