package fr.world;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import fr.entity.character.Player;
import fr.entity.wall.Wall;
import fr.entity.wall.WallFactory;

public class World extends BasicGameState{
	
	public enum direction {HAUT,DROITE,BAS,GAUCHE};
	private static Player Bob;
	private static ArrayList<Wall> walls;
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		Bob = new Player();
		walls = new ArrayList<Wall>();
		WallFactory.newWall(0, 0, 32, 32);
		WallFactory.newWall(200, 400, 100, 200);
		WallFactory.newWall(500, 550, 80, 50);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		Bob.render(container, game, g);
		for(int i = 0;i<walls.size();i++){
			walls.get(i).render(container,game,g);
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		Bob.update(container, game, delta);
		for(int i = 0;i<walls.size();i++){
			walls.get(i).update(container,game,delta);
		}
	}
	
	public void keyReleased(int key, char c) {
		Bob.keyReleased(key, c);
	}


	public void keyPressed(int key, char c) {
		Bob.keyPressed(key, c);
	}

	public static ArrayList<Wall> getWalls() {
		return walls;
	}

	public static void setWalls(ArrayList<Wall> wallsP) {
		walls = wallsP;
	}

	@Override
	public int getID() {
		return 0;
	}
	
}
