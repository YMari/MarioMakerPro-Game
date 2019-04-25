package Game.GameStates;

import Display.UI.UIStringButton;
import Game.Entities.DynamicEntities.BaseDynamicEntity;
import Game.Entities.DynamicEntities.Ghost;
import Game.Entities.DynamicEntities.Goomba;
import Game.Entities.DynamicEntities.Mario;
import Game.Entities.DynamicEntities.Player;
import Game.Entities.DynamicEntities.Wario;
import Main.Handler;
import Resources.Images;
import Display.UI.UIManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Created by AlexVR on 7/1/2018.
 */
public class WinState extends State {

	private UIManager uiManager;
	public String marioWin = "Player One Wins!";
	public String warioWin = "Player two Wins!";

	public WinState(Handler handler) {
		super(handler);
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUimanager(uiManager);
//		uiManager.addObjects(new UIStringButton(56, 30, 128, 64, "Play Again?", () -> {
//			handler.getMouseManager().setUimanager(null);
//			State.setState(handler.getGame().gameState);
//			handler.getGame().reStart();
//		},handler,Color.BLACK));

//		uiManager.addObjects(new UIStringButton(56, 223+(64+16), 128, 64, "Options", () -> {
//			handler.getMouseManager().setUimanager(null);
//			handler.setIsInMap(false);
//			State.setState(handler.getGame().menuState);
//		},handler,Color.WHITE));

		uiManager.addObjects(new UIStringButton(56, 120, 128, 64, "Main Menu", () -> {
			handler.getMouseManager().setUimanager(null);
			handler.setIsInMap(false);
			State.setState(handler.getGame().menuState);
		},handler,Color.BLACK));

	}

	@Override
	public void tick() {
		handler.getMouseManager().setUimanager(uiManager);
		uiManager.tick();
		

//		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)){
//			State.setState(handler.getGame().gameState);
//		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Images.WinScreen,0,0,handler.getWidth(),handler.getHeight(),null);
		uiManager.Render(g);
		g.setFont(new Font("TimesRoman", Font.BOLD, 35));
		g.setColor(Color.BLACK);
		
//		if (Mario.marioWin) {
//			g.drawString(marioWin, 56, 90);
//		}
//		else if(Wario.warioWin) {
//			g.drawString(warioWin, 56, 90);
//		}
		
		if (Player.winPlayer.equals(handler.getMario())) {
			g.drawString(marioWin, 56, 90);
		}
		else if(Player.winPlayer.equals(handler.getWario())) {
			g.drawString(warioWin, 56, 90);
		}



	}
}
