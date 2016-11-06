package fr.entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public abstract class Entity {
	
	protected double x,y,oldX,oldY;
	protected double speedX,speedY;
	protected double accelX,accelY;
	protected double width,height;
	protected boolean isMoving;
	protected int jump;
	protected int jumpMax;
	protected boolean collision;
	
	public Entity(){
		speedX = 0;
		speedY = 0;
		accelX = 0;
		accelY = 0;
		isMoving = false;
	}
	
	public abstract void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException;
	public abstract void update(GameContainer container, StateBasedGame game, int delta) throws SlickException;
	
	public void moveX(int delta){
		if(isMoving){
			x+=speedX*delta;
		}
	}
	
	public void moveY(int delta){
		if(isMoving){
			y+=speedY*delta;
		}
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getSpeedX() {
		return speedX;
	}

	public void setSpeedX(double speedX) {
		this.speedX = speedX;
	}

	public double getSpeedY() {
		return speedY;
	}

	public void setSpeedY(double speedY) {
		this.speedY = speedY;
	}

	public double getAccelX() {
		return accelX;
	}

	public void setAccelX(double accelX) {
		this.accelX = accelX;
	}

	public double getAccelY() {
		return accelY;
	}

	public void setAccelY(double accelY) {
		this.accelY = accelY;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public boolean isMoving() {
		return isMoving;
	}

	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}

	public double getOldX() {
		return oldX;
	}

	public void setOldX(double oldX) {
		this.oldX = oldX;
	}

	public double getOldY() {
		return oldY;
	}

	public void setOldY(double oldY) {
		this.oldY = oldY;
	}

	public int getJump() {
		return jump;
	}

	public void setJump(int jump) {
		this.jump = jump;
	}

	public boolean isCollision() {
		return collision;
	}

	public void setCollision(boolean collision) {
		this.collision = collision;
	}

}
