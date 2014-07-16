package guiElements;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * A horizontal menu that will scale dynamically as Buttons are added and removed
 *
 */
public class Menu {

	
	private int X;
	private int Y;
	private int width = 0;
	private int height = 0;
	private int buttonHeight;
	private ArrayList<Button> buttons;
	private Color prev;
	private Color bg = new Color(128, 128, 128, 0.3f); 
	private Button b;
	
	
	public Menu(int X, int Y){
		this.X = X;
		this.Y = Y;
		buttons = new ArrayList<Button>();
	}
	
	public void render(GameContainer container, Graphics g){
		if(buttons.isEmpty())
			return;
		prev = g.getColor();
		g.setColor(bg);
		g.fillRect(X-5, Y-2, width+5, height+5);
		g.setColor(prev);
		
		for(int i=0; i<buttons.size(); i++){
			b = buttons.get(i);
			if(b != null){
				b.setLocation(X, Y+(buttonHeight+1)*i);
				b.render(container, g);
			}
		}
	}
	
	public void addButton(Button b){
		buttons.add(b);
		if(b.getHeight() > buttonHeight)
			buttonHeight = b.getHeight();
		if(b.getWidth() > width)
			width = b.getWidth();
		height += b.getHeight();
	}
	
	public void removeButton(Button b){
		if(buttons.remove(b)){
			height -= b.getHeight();
		}
		if(!buttons.isEmpty()){
			width = buttons.get(0).getWidth();
			for(Button t: buttons)
				if(t.getWidth() > width)
					width = t.getWidth();
		}else width = 0;
	}
	
	public void clear(){
		for(Button b: buttons)
			b.setUnclickable(true);
		buttons.clear();
	}

	public void init() {
		for(Button b: buttons)
			b.setUnclickable(false);
	}
	
}