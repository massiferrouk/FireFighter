package view;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class ImageCache {

    private final static Map<String, Image> imageCache = new HashMap<>();

    public static Image getImage(String path) {
        if (imageCache.containsKey(path)) {
            return imageCache.get(path);
        } else {
            Image image = new Image(path);
            imageCache.put(path, image);
            return image;
        }
    }
}
