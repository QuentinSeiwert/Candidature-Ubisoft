package fr.entity.character;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import fr.entity.Entity;
import fr.util.Collisions;
import fr.world.World;

public class Player extends Entity{
	
	public Player(){
		x=384; y=284;
		speedX = 0; speedY=0;
		accelX = 0; accelY=0.03;
		width = 32; height = 32;
		jump = 1;
		jumpMax = 1;
		isMoving = true;
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.setColor(Color.red);
		g.fillRect((float)x, (float)y, (float)width, (float)height);
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		speedY += accelY;
		
		moveX(delta);
		collision = false;	
		for(int i = 0;i<World.getWalls().size();i++){	
			if(Collisions.isCollision(this, World.getWalls().get(i)))
				collision = true;
		}
		if(collision){
			x = oldX;
		}else{
		    oldX = x;
		}
		
		moveY(delta);
		collision = false;	
		for(int i = 0;i<World.getWalls().size();i++){	
			if(Collisions.isCollision(this, World.getWalls().get(i)))
				collision = true;
		}
		
		if(collision){
			y = oldY;
			jump = jumpMax;
			speedY = 0;
		}else{
		    oldY = y;
		}
		
		if(y<0){
			y=0;
		}
		if(y>=600-height){
			y=600-height;
			jump = jumpMax;
			speedY = 0;
		}
		if(x>=800-width){
			x=800-width;
		}
		if(x<0){
			x=0;
		}
		
	}
	
	public void jump(){
		if(jump > 0){
		speedY = -0.75;
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
				speedX = -0.3;
				break;
			case Input.KEY_RIGHT:
				speedX = 0.3;
				break;
			}
	}

}
