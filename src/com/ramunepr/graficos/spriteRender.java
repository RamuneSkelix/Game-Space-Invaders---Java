
package com.ramunepr.graficos;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class spriteRender {
    
    private BufferedImage sprite;
    
    public spriteRender(String path){
        try {
            sprite = ImageIO.read(getClass().getResource(path));
        } catch (IOException ex) {
            Logger.getLogger(spriteRender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public BufferedImage getSprite(int x, int y, int width, int height){
        return sprite.getSubimage(x, y, width, height);
    }
}
