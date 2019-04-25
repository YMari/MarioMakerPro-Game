package Game.Entities.StaticEntities;

import java.awt.Rectangle;

import Main.Handler;
import Resources.Images;

public class Flag extends BaseStaticEntity {

    public Flag(int x, int y, int width, int height, Handler handler) {
        super(x, y, width, height,handler, Images.flag);
    }

    public Rectangle getBounds(){
        return new Rectangle((int)x, (int)y, this.width, this.height);
    }
}