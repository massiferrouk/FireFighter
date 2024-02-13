package view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

public class MotorizedFireFighterElement implements ViewElement{
    @Override
    public Color getColor() {
        return  Color.BLUE;
    }

    @Override
    public void paintBox(int x, int y, GraphicsContext graphicsContext, int width, int height){
        Image image =  ImageCache.getImage("file:src\\main\\resources\\view\\864564.png");
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillRect(x,y,width,height);

        double imageWidth = 0.6 * width;
        double imageHeight = 0.6 * height;

        double imageX = x + (width - imageWidth) / 2;
        double imageY = y + (height - imageHeight) /2;

        graphicsContext.drawImage(image, imageX, imageY, imageWidth, imageHeight);


    }

    @Override
    public Image getImage() {
        return ImageCache.getImage("file:src\\main\\resources\\view\\864564.png");
    }
}


