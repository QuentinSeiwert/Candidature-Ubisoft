function Collisions(){}

Collisions.collRectRect = function(r1,r2){
	if(r1.x + r1.width <= r2.x){
		return false;
	}else if(r1.x >= r2.x + r2.width){
		return false;
	}else{
		if(r1.y + r1.height <= r2.y){
			return false;
		}else if(r1.y >= r2.y + r2.height){
			return false;
		}else{
			return true;
		}
	}
}