package Game.Entities.DynamicEntities;

import Main.Handler;
import Resources.Animation;
import Resources.Images;

import java.awt.*;

public class Ghost extends BaseDynamicEntity {

	public Ghost(int x, int y, int width, int height, Handler handler) {
		super(x, y, width, height, handler, Images.ghost);
	}

	@Override
	public void tick(){
		if(!ded && dedCounter==0) {
			super.tick();
			if (falling) {
				y = (int) (y + velY);
				velY = velY + gravityAcc;
				checkFalling();
			}
			checkHorizontal();
			move();
		}else if(ded&&dedCounter==0){
			y++;
			height--;
			setDimension(new Dimension(width,height));
			if (height==0){
				dedCounter=1;
				y=-10000;
			}
		}
	}
	
}
