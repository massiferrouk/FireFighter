package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;


public class FireElement implements ViewElement{

    public Color getColor()
    {
        return Color.RED;
    }

    @Override
    public void paintBox(int x, int y, GraphicsContext graphicsContext, int width, int height)
    {
        Image image = ImageCache.getImage("file:src\\main\\resources\\view\\1172477.png");
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillRect(x,y,width,height);

        double imageWidth = 0.5 * width;
        double imageHeight = 0.5 * height;

        double imageX = x + (width - imageWidth) / 2;
        double imageY = y + (height - imageHeight)/2 ;

        graphicsContext.drawImage(image, imageX, imageY, imageWidth, imageHeight);

    }

    @Override
    public Image getImage() {
        return ImageCache.getImage("file:src\\main\\resources\\view\\1172477.png");
    }
}
