package fr.util;

import fr.entity.character.Player;
import fr.entity.wall.Wall;


public class Collisions {
	
	public static boolean isCollision(Player player, Wall wall){
		int marge = 0;
    	if (player.getX()+player.getWidth()-marge >= wall.getX() && player.getX()+marge <= wall.getX()+wall.getWidth()){
    		if(player.getY()+player.getHeight()-marge >= wall.getY() && player.getY()+marge <= wall.getY()+wall.getHeight()){
    			return true;
    		}else return false;
    	}else return false;
    }
}
