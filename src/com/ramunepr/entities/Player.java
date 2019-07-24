package com.ramunepr.entities;

import com.ramunepr.main.Game;
import java.awt.image.BufferedImage;

public class Player extends Entity{
    
    public boolean right,left;
    public int speed = 3;
    public boolean atirando = false;
    public static int pontos = 0;
    public static int vida = 0;
    public Player(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
    }
    public void tick(){
		if(right)
		{
			x+=speed;
		}else if(left) {
			x-=speed;
		}
        if (x >= 200){
            x=-16;
        }else if (x+16 < 0){
            x = 200;
        }
        if (vida <= 0){
            Game.GAME_STATE = "GAMEOVER";
        }
        if (atirando){
            atirando = false;
            int xx = this.getX() + 20;
            int yy = this.getY();
            Tiro tiro = new Tiro(xx, yy, 3, 3, null);
            Game.entities.add(tiro);
        }
    }
    
}
