package fr.world;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class World extends BasicGameState{
	
	float x,y;
	float width,height;
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		x=0; y=284;
		width = 32; height = 32;
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		x++;
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.fillRect(x, y, width, height);
	}

	@Override
	public int getID() {
		return 0;
	}

	
}
