package fr.world;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import fr.character.Player;

public class World extends BasicGameState{
	
	public enum direction {HAUT,DROITE,BAS,GAUCHE};
	Player Bob;
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		Bob = new Player();
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		Bob.render(container, game, g);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		Bob.update(container, game, delta);
	}
	
	public void keyReleased(int key, char c) {
		Bob.keyReleased(key, c);
	}


	public void keyPressed(int key, char c) {
		Bob.keyPressed(key, c);
	}

	@Override
	public int getID() {
		return 0;
	}
	
}
