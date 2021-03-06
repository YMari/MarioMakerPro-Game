package Game.Entities.DynamicEntities;

import Game.Entities.EntityBase;
import Game.Entities.StaticEntities.BaseStaticEntity;
import Game.Entities.StaticEntities.BoundBlock;
import Game.Entities.StaticEntities.Flag;
import Game.GameStates.State;
import Main.Handler;
import Resources.Animation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends BaseDynamicEntity {

	protected double velX,velY;

	public String facing = "Left";
	public boolean moving = false;
	public Animation playerSmallLeftAnimation,playerSmallRightAnimation,playerBigLeftWalkAnimation,playerBigRightWalkAnimation,playerBigLeftRunAnimation,playerBigRightRunAnimation;
	public boolean falling = true, jumping = false,isBig=false,running = false,changeDirrection=false;
	public double gravityAcc = 0.38;
	public double gravityAcc2 = 0.21; // player two has less gravity on jump
	
	public boolean doubleJumping = false; // double jump for player one
	public boolean dJumpLock = false; // to avoid spamming double jump
	
	public static BaseDynamicEntity winPlayer; // what player won
	public static BaseDynamicEntity deadPlayer; // what player died
	public static boolean win = false;
	
	int changeDirectionCounter = 0;

	public Player(int x, int y, int width, int height, Handler handler, BufferedImage sprite,Animation PSLA,Animation PSRA,Animation PBLWA,Animation PBRWA,Animation PBLRA,Animation PBRRA) {
		super(x, y, width, height, handler, sprite);
		playerBigLeftRunAnimation=PBLRA;
		playerBigLeftWalkAnimation=PBLWA;
		playerBigRightRunAnimation=PBRRA;
		playerBigRightWalkAnimation=PBRWA;
		playerSmallLeftAnimation=PSLA;
		playerSmallRightAnimation=PSRA;
	}

	@Override
	public void tick(){

		if (changeDirrection) {
			changeDirectionCounter++;
		}
		if(changeDirectionCounter>=10){
			changeDirrection=false;
			changeDirectionCounter=0;
		}

		checkBottomCollisions();
		checkMarioHorizontalCollision();
		checkTopCollisions();
		checkItemCollision();
		if(!isBig) {
			if (facing.equals("Left") && moving) {
				playerSmallLeftAnimation.tick();
			} else if (facing.equals("Right") && moving) {
				playerSmallRightAnimation.tick();
			}
		}else{
			if (facing.equals("Left") && moving && !running) {
				playerBigLeftWalkAnimation.tick();
			} else if (facing.equals("Left") && moving && running) {
				playerBigLeftRunAnimation.tick();
			} else if (facing.equals("Right") && moving && !running) {
				playerBigRightWalkAnimation.tick();
			} else if (facing.equals("Right") && moving && running) {
				playerBigRightRunAnimation.tick();
			}
		}
		
	}

	private void checkItemCollision() {

		for (BaseDynamicEntity entity : handler.getMap().getEnemiesOnMap()) {
			if (entity != null && getBounds().intersects(entity.getBounds()) && entity instanceof Item && !isBig) {
				isBig = true;
				this.y -= 8;
				this.height += 8;
				setDimension(new Dimension(width, this.height));
				((Item) entity).used = true;
				entity.y = -100000;
			}
		}
	}


	public void checkBottomCollisions() {
		Player mario = this;
		ArrayList<BaseStaticEntity> bricks = handler.getMap().getBlocksOnMap();
		ArrayList<BaseDynamicEntity> enemies =  handler.getMap().getEnemiesOnMap();

		Rectangle marioBottomBounds =getBottomBounds();

		boolean marioDies = false;

		if (!mario.jumping) {
			falling = true;
		}

		for (BaseStaticEntity brick : bricks) {
			Rectangle brickTopBounds = brick.getTopBounds();
			
			if (brick instanceof Flag && marioBottomBounds.intersects(brick.getTopBounds())) { //win condition
				winPlayer = this; // gets the player that won
				win = true;
				State.setState(handler.getGame().winState);
			}
			
			if (brick instanceof BoundBlock && marioBottomBounds.intersects(brick.getTopBounds())) {
				deadPlayer = this; // gets the player that died
				marioDies = true;
				break;
			}
			
			if (marioBottomBounds.intersects(brickTopBounds)) {
				mario.setY(brick.getY() - mario.getDimension().height + 1);
				falling = false;
				velY=0;
			}
		}

		for (BaseDynamicEntity enemy : enemies) {
			Rectangle enemyTopBounds = enemy.getTopBounds();
			if (marioBottomBounds.intersects(enemyTopBounds) && !(enemy instanceof Player) && !(enemy instanceof Item) && !(enemy instanceof Ghost)) {
				if(!enemy.ded) {
					handler.getGame().getMusicHandler().playStomp();
				}
				enemy.kill();
				falling=false;
				velY=0;

			}
			if (marioBottomBounds.intersects(enemyTopBounds) && (enemy instanceof Ghost)){
				deadPlayer = this; // gets the player that died
				marioDies = true;
				break;
			}
			
		}
		if (marioDies) {
			handler.getGame().getMusicHandler().pauseBackground();
			handler.getGame().getMusicHandler().playmarioDies();
			State.setState(handler.getGame().gameOverState);
		}
	}

	public void checkTopCollisions() {
		Player mario = this;
		ArrayList<BaseStaticEntity> bricks = handler.getMap().getBlocksOnMap();
		
		boolean marioDies = false;

		Rectangle marioTopBounds = mario.getTopBounds();
		for (BaseStaticEntity brick : bricks) {
			Rectangle brickBottomBounds = brick.getBottomBounds();
			
			if (brick instanceof BoundBlock && marioTopBounds.intersects(brick.getBottomBounds())) {
				deadPlayer = this; // gets the player that died
				marioDies = true;
				break;
			}
			
			if (brick instanceof Flag && marioTopBounds.intersects(brickBottomBounds)) { //win condition
				winPlayer = this; //gets the player that won
				win = true;
				State.setState(handler.getGame().winState);
			}
			
			if (marioTopBounds.intersects(brickBottomBounds)) {
				velY=0;
				mario.setY(brick.getY() + brick.height);
			}
			
			if (marioDies) {
				handler.getGame().getMusicHandler().pauseBackground();
				handler.getGame().getMusicHandler().playmarioDies();
				State.setState(handler.getGame().gameOverState);
			}
		}
	}

	public void checkMarioHorizontalCollision(){
		Player mario = this;
		ArrayList<BaseStaticEntity> bricks = handler.getMap().getBlocksOnMap();
		ArrayList<BaseDynamicEntity> enemies = handler.getMap().getEnemiesOnMap();

		boolean marioDies = false;
		boolean toRight = moving && facing.equals("Right");

		Rectangle marioBounds = toRight ? mario.getRightBounds() : mario.getLeftBounds();

		for (BaseStaticEntity brick : bricks) {
			Rectangle brickBounds = !toRight ? brick.getRightBounds() : brick.getLeftBounds();
			
			if (brick instanceof Flag && marioBounds.intersects(brickBounds)) { //win condition
				winPlayer = this; //gets the player that won
				win = true;
				State.setState(handler.getGame().winState);
			}
			
			if (brick instanceof BoundBlock && marioBounds.intersects(brickBounds)) {
				deadPlayer = this; // gets the player that died
				marioDies = true;
				break;
			}
			if (marioBounds.intersects(brickBounds)) {
				velX=0;
				if(toRight)
					mario.setX(brick.getX() - mario.getDimension().width);
				else
					mario.setX(brick.getX() + brick.getDimension().width);
			}
		}

		for(BaseDynamicEntity enemy : enemies){
			if (enemy instanceof Ghost || enemy instanceof Goomba) {
				Rectangle enemyBounds = !toRight ? enemy.getRightBounds() : enemy.getLeftBounds();
				if (marioBounds.intersects(enemyBounds)) {
					deadPlayer = this; // gets the player that died
					marioDies = true;
					break;
				}
			}
		}



		if (marioDies) {
			handler.getGame().getMusicHandler().pauseBackground();
			handler.getGame().getMusicHandler().playmarioDies();
			State.setState(handler.getGame().gameOverState);
		}
	}

	public void jump() {
		if(!jumping && !falling){
			jumping=true;
			velY=10;
			handler.getGame().getMusicHandler().playJump();
		}
	}
	
	public void doubleJump() {  // mario only
		if(!jumping && falling && dJumpLock == false){
			doubleJumping=true;
			dJumpLock = true;
			velY = -8;
			handler.getGame().getMusicHandler().playJump();
		}
	}

	public double getVelX() {
		return velX;
	}
	public double getVelY() {
		return velY;
	}


}
