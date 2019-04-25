package Game.GameStates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import Display.UI.UIManager;
import Display.UI.UIStringButton;
import Game.Entities.DynamicEntities.Player;
import Main.Handler;
import Resources.Images;

/**
 * Created by AlexVR on 7/1/2018.
 */
public class GameOverState extends State {

	private UIManager uiManager;

	public GameOverState(Handler handler) {
		super(handler);
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUimanager(uiManager);

		uiManager.addObjects(new UIStringButton(56, 30, 128, 64, "Main Menu", () -> {
			handler.getMouseManager().setUimanager(null);
			handler.setIsInMap(false);
			State.setState(handler.getGame().menuState);
		},handler,Color.WHITE));
		
		uiManager.addObjects(new UIStringButton(handler.getWidth() - 85, 450, 128, 64, "Exit", () -> {
			System.exit(0);
		},handler,Color.WHITE));

	}

	@Override
	public void tick() {
		handler.getMouseManager().setUimanager(uiManager);
		uiManager.tick();

	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Images.GameOver,0,0,handler.getWidth(),handler.getHeight(),null);
		uiManager.Render(g);
		g.setFont(new Font("TimesRoman", Font.BOLD, 30));
		
		if (Handler.multiplayer) {
			if (Player.deadPlayer.equals(handler.getWario())) {
				g.drawString(WinState.marioWin, 56, 475);
			}
			else if (Player.deadPlayer.equals(handler.getMario())) {
				g.drawString(WinState.warioWin, 56, 475);
			}
		}
	}
}
