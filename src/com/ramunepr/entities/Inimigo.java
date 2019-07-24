package com.ramunepr.entities;

import com.ramunepr.main.Game;
import com.ramunepr.main.Sound;
import com.ramunepr.main.SpawnInimigo;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Inimigo extends Entity{
    public int speed = 2;
    private int life = 2;
    public Inimigo(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
    }
    Random random = new Random();
    public int nave = random.nextInt(2+1);
    public void tick(){
        y += speed;
        if (y >= 300){
            Game.entities.remove(this);
            if (Game.GAME_STATE == "NORMAL"){
                Player.vida--;
            }
            
            return;
        }
		for(int i =0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Tiro) {
				if(Entity.isColidding(this, e)) {
					Game.entities.remove(e);
					life--;
					if(life == 0)
					{       
                                         	Explosion explosion = new Explosion(x,y,40,40,null);
                                                Sound.explosion.play();
						Game.entities.add(explosion);
                                                Player.pontos++;
						Game.entities.remove(this);
						return;
					}
					break;
				}
			}
		}
    }


    public void render(Graphics g){
        if (Game.GAME_STATE == "NORMAL"){
        g.setColor(Color.yellow);
        if (nave == 1){
           g.drawImage(Game.spritesheet.getSprite(80, 0, 40, 40), x, y, null);  
        }else{
            g.drawImage(Game.spritesheet.getSprite(40, 0, 40, 40), x, y, null);
        }    
        }

         
        
    }
}
