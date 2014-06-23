package theGame;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import utils.Logger;

public class Main extends StateBasedGame {

	private static AppGameContainer appgc;
	
	public static void main(String[] args){
		//entry point of program
		try{
			appgc = new AppGameContainer(new Main("This Is The Title"));
			appgc.setDisplayMode(800, 480, false);
			appgc.setShowFPS(true);
			appgc.start();			
		}catch(SlickException se){
			//Logger.logException(se)
		}
	}
	
	public Main(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void enterState(int id){
		Logger.logNote("Transitioning to state " + id);
		super.enterState(id);
	}
	
	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new MainMenu());
		addState(new InGame());
	}

}
