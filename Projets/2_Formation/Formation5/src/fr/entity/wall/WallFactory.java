package fr.entity.wall;

import fr.world.World;

public class WallFactory {
	
	public static void newWall(double x,double y,double width,double height){
		World.getWalls().add(new Wall(x,y,width,height));
	}
	
}
