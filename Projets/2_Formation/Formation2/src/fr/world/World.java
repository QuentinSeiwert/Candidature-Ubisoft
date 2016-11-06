package fr.world;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class World extends BasicGameState{
	
	float x,y;
	float width,height;
	enum direction {HAUT,DROITE,BAS,GAUCHE};
	direction dir;
	boolean isMoving;
	
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		x=384; y=284;
		width = 32; height = 32;
		isMoving = false;
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.fillRect(x, y, width, height);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		if(isMoving){
			switch(dir){
			case HAUT:	y-=3; break;
			case BAS:	y+=3; break;
			case GAUCHE:x-=3; break;
			case DROITE:x+=3; break;
			
			}
		}
	}

	@Override
	public int getID() {
		return 0;
	}
	
	public void keyReleased(int key, char c) {
		isMoving = false;
	}


	public void keyPressed(int key, char c) {
			switch (key) {
			case Input.KEY_UP:
				isMoving = true;
				dir = direction.HAUT;
				break;
			case Input.KEY_DOWN:
				isMoving = true;
				dir = direction.BAS;
				break;
			case Input.KEY_LEFT:
				isMoving = true;
				dir = direction.GAUCHE;
				break;
			case Input.KEY_RIGHT:
				isMoving = true;
				dir = direction.DROITE;
				break;
			}
	}
	
}
