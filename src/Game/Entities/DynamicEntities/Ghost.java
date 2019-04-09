package Game.Entities.DynamicEntities;

import Main.Handler;
import Resources.Images;

public class Ghost extends Item {

	public Ghost(int x, int y, int width, int height, Handler handler) {
		super(x, y, width, height, handler, Images.ghost);
	}

	@Override
	public void tick(){
		if(!used) {
			if (falling) {
				y = (int) (y + velY);
				velY = velY + gravityAcc;
				checkFalling();
			}
			checkHorizontal();
			move();
		}
	}
}