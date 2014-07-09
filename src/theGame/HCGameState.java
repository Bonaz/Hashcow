package theGame;

import java.io.File;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import utils.Logger;

public class HCGameState extends BasicGameState{

	private static boolean displayLog;
	private static final int DISPLAY_LENGTH = 3500; //how long to display the log
	
	private Input in;
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException{
		if(!Logger.isInit()){
			try {
				Logger.init(new File("logs"), container, false);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		in = container.getInput();
		Logger.logLine("State " + getID() + " is initializing");
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		Logger.setContainer(container);
		if(displayLog || Logger.getAge() <= DISPLAY_LENGTH)
			Logger.render();
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		Logger.update(delta);
		if(in.isKeyPressed(Input.KEY_F1))
			displayLog = !displayLog;
		if(in.isKeyPressed(Input.KEY_F) && in.isKeyDown(Input.KEY_LCONTROL) && in.isKeyDown(Input.KEY_LSHIFT))
			container.setShowFPS(!container.isShowingFPS());
	}

	//MUST BE OVERRIDDEN IN EACH SUB CLASS
	@Override
	public int getID(){
		return -1;
	}

}
