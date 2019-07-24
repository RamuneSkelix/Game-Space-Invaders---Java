package com.ramunepr.main;

import com.ramunepr.entities.Inimigo;
import java.util.Random;

public class SpawnInimigo {
    
    public int targetTime = 60*2;
    public int curTime = 0;
    
    Random random = new Random();
    public void tick(){
        curTime++;
        if (curTime == targetTime){
            targetTime = 30;
            curTime = 0;
            int yy = 0;
            int xx = random.nextInt(200-40);
                    Inimigo inimigo = new Inimigo(xx,yy,40,40, Game.spritesheet.getSprite(80, 0, 40, 40));
                    Game.entities.add(inimigo);
        }
    }
}
