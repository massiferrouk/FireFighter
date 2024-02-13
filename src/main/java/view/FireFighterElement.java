package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

public class FireFighterElement  implements ViewElement {

    public Color getColor()
    {
        return Color.LIGHTBLUE;
    }

    @Override
    public void paintBox(int x, int y, GraphicsContext graphicsContext, int width, int height){
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillRect(x,y,width,height);
        Image image = ImageCache.getImage("file:src\\main\\resources\\view\\pompiers-pompiers-icone-icone-autocollant.jpg");
        double imageWidth = 0.7 * width;
        double imageHeight = 0.7 * height;

        double imageX = x + (width - imageWidth) / 2;
        double imageY = y + (height - imageHeight)/2 ;

        graphicsContext.drawImage(image, imageX, imageY, imageWidth, imageHeight);

    }

    @Override
    public Image getImage() {
        return ImageCache.getImage("file:src\\main\\resources\\view\\pompiers-pompiers-icone-icone-autocollant.jpg");
    }
}

