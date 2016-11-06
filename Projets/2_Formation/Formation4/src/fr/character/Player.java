package fr.character;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import fr.world.World.direction;

public class Player {
	
	double x,y;
	double speedX,speedY;
	double accelX,accelY;
	double width,height;
	boolean isMoving;
	int jump;
	
	public Player(){
		x=384; y=284;
		speedX = 0; speedY=0;
		accelX = 0; accelY=0.5;
		width = 32; height = 32;
		jump = 1;
		isMoving = true;
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.fillRect((float)x, (float)y, (float)width, (float)height);
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		speedY += accelY;
		move();
		if(y<0){
			y=0;
		}
		if(y>=600-height){
			jump = 1;
			y=600-height;
			speedY = 0;
		}
		if(x>=800-width){
			x=800-width;
		}
		if(x<0){
			x=0;
		}
	}
	
	public void move(){
		if(isMoving){
			x+=speedX;
			y+=speedY;
		}
	}
	
	public void jump(){
		if(jump > 0){
		speedY = -10;
		jump--;
		}
	}
	
	public void keyReleased(int key, char c) {
		switch (key) {
		case Input.KEY_LEFT:
		case Input.KEY_RIGHT:
			speedX = 0;
			break;
		}
	}


	public void keyPressed(int key, char c) {
			switch (key) {
			case Input.KEY_UP:
				jump();
				break;
			case Input.KEY_LEFT:
				speedX = -3;
				break;
			case Input.KEY_RIGHT:
				speedX = 3;
				break;
			}
	}

}
