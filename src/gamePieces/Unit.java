package gamePieces;

import interfaceElements.Button;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;

import resourceManager.FontManager;
import resourceManager.UnitImage;
import resourceManager.UnitSound;

public abstract class Unit {

	protected static final TrueTypeFont f = FontManager.TINY_TRUETYPE;
	
	protected UnitImage image;
	protected UnitSound sound;
	protected Animation current;
	
	protected MapInfo map;
	protected int column;
	protected int row;
	protected int currentX;
	protected int currentY;
	protected String healthString;
	
	protected Player owner;
	protected boolean visible;
	protected boolean dead;
	
	protected int BASE_MOVE_RANGE;
	protected int BASE_SIGHT_RANGE;
	protected int BASE_ATTACK_RANGE;
	protected int BASE_ATTACK;
	protected int BASE_DEFENSE;
	protected int BASE_HEALTH;
	protected int currentHealth;
	
	//movementType
	//attackType
	//abilities
	//upgrades
	
	public Unit(int X, int Y, Player player){
		column = X;
		row = Y;
		currentX = column*32;
		currentY = row*32;
		owner = player;
		if(this instanceof Building)
			owner.addBuilding((Building)this);
		else
			owner.addUnit(this);
	}
	
	public UnitImage getImage(){
		return image;
	}
	
	public void kill(){
		dead = true;
		current = image.getAnimation(UnitImage.DEATH);
		current.stopAt(current.getFrameCount()-1);
		current.setLooping(false);
		current.start();
		sound.playSound(UnitSound.DEATH);
		//current
	}
	
	public ArrayList<Button> select(Player selector){
		ArrayList<Button> ret = new ArrayList<Button>();
		if(visible){
			current = image.getAnimation(UnitImage.SELECTED);
			sound.playSound(UnitSound.SELECT);
			if(selector.equals(owner)){
				//for(Ability a: abilites)
				//	ret.add(a.getButton());
				//draw move range?
			}else{
				//draw attack range?
			}
		}
		return ret;
	}
	
	public void deselect(){
		current = image.getAnimation(UnitImage.IDLE);
	}
	
	public int getColumn(){
		return column;
	}
	
	public int getRow(){
		return row;
	}
	
	public int getX(){
		return currentX;
	}
	
	public int getY(){
		return currentY;
	}
	
	public int getMoveRange(){
		return BASE_MOVE_RANGE;
	}
	
	public int getSightRange(){
		return BASE_SIGHT_RANGE;
	}
	
	public int getAttackRange(){
		return BASE_ATTACK_RANGE;
	}
	
	public int getAttack(){
		return BASE_ATTACK;
	}
	
	public int getDefense(){
		return BASE_DEFENSE;
	}
	
	public int getMaxHealth(){
		return BASE_HEALTH;
	}
	
	public int getCurrentHealth(){
		return currentHealth;
	}
	
	public void update(int delta){
		current.update(delta);
		if(currentHealth == 0 && !dead){
			kill();
			return;
		}
		else if(current.equals(image.getAnimation(UnitImage.DEATH)) && current.isStopped()){
			map.removeUnit(this);
			owner.removeUnit(this);
			return;
		}
		else if(currentX < column*32)
			currentX += 4;
		else if(currentX > column*32)
			currentX -= 4;
		else if(currentY < row*32)
			currentY += 4;
		else if(currentY > row*32)
			currentY -= 4;
		//movement logic goes here?
	}
	
	public boolean isAnimationStopped(){
		return current.isStopped();
	}
	
	public boolean isWalking(){
		if(currentX != column*32 || currentY != row*32)
			return true;
		return false;
	}
	
	public void render(Graphics g, int X, int Y){
		if(visible){
			current.draw(X+currentX, Y+currentY);
			g.setFont(f);
			g.drawString(healthString, X+(column+1)*32-f.getWidth(healthString), Y+(row+1)*32-f.getLineHeight());
			g.setFont(FontManager.DEFAULT_TRUETYPE);
		}
	}
	
	public boolean isAt(int X, int Y){
		return column == X && row == Y; 
	}
	
	public void moveUp(){
		map.getSightMap().updateUnit(this, row--, column);
		map.applySightMap();
	}
	
	public void moveDown(){
		map.getSightMap().updateUnit(this, row++, column);
		map.applySightMap();
	}
	
	public void moveLeft(){
		map.getSightMap().updateUnit(this, row, column--);
		map.applySightMap();
	}
	
	public void moveRight(){
		map.getSightMap().updateUnit(this, row, column++);
		map.applySightMap();
	}
	
	public int takeDamage(Unit attacker){
		int damage = attacker.getAttack() - BASE_DEFENSE;
		currentHealth -= damage;
		if(currentHealth < 0)
			currentHealth = 0;
		else if(currentHealth > BASE_HEALTH)
			currentHealth = BASE_HEALTH;
		healthString = currentHealth + "/" + BASE_HEALTH;
		return currentHealth;
	}
	
	public void age(){
		
	}
	
	public void setVisible(boolean b){
		visible = b;
	}
	
	public boolean isVisible(){
		return visible;
	}
}
