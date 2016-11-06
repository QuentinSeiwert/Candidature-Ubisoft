function Player(id,pseudo,x,y){
	this.x = x;
	this.y = y;
	this.oldX = x;
	this.oldY = y;
	this.width = 45;
	this.height = 45;
	this.dir = 0;
	this.speedX= 0.4;
	this.speedY= 0.4;
	this.isMoving = false;
	this.id = id;
	this.pseudo = pseudo;
}

Player.prototype.render = function(ctx,posCamera,dimCamera){
		ctx.fillRect(this.x-posCamera[0]+dimCamera[0]/2,this.y-posCamera[1]+dimCamera[1]/2,this.width,this.height);
		ctx.font = "20pt Calibri";
		ctx.fillText(this.pseudo,this.x-posCamera[0]+dimCamera[0]/2,(this.y-posCamera[1]+dimCamera[1]/2)-10);
}

Player.prototype.move = function(delta){
	switch(this.dir){
		case 0:this.y-=this.speedY*delta;break;
		case 1:this.x+=this.speedX*delta;break;
		case 2:this.y+=this.speedY*delta;break;
		case 3:this.x-=this.speedX*delta;break;
	}
}