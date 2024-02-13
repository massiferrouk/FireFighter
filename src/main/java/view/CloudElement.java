package view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

public class CloudElement implements  ViewElement{
    @Override
    public Color getColor() {
        return Color.GRAY;
    }


    @Override
    public Image getImage() {
        return ImageCache.getImage("file:src\\main\\resources\\view\\icone-de-nuage-gris.png");
    }

    @Override
    public void paintBox(int x, int y, GraphicsContext graphicsContext, int width, int height) {
        Image image =  ImageCache.getImage("file:src\\main\\resources\\view\\icone-de-nuage-gris.png");
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillRect(x,y,width,height);

        double imageWidth = 0.5 * width;
        double imageHeight = 0.5 * height;

        double imageX = x + (width - imageWidth) / 2;
        double imageY = y + (height - imageHeight) / 2;

        graphicsContext.drawImage(image, imageX, imageY, imageWidth, imageHeight);

    }

}

