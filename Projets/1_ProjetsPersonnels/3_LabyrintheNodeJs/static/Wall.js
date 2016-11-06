function Wall(x,y,width,height){
	this.x = x;
	this.y = y;
	this.width = width;
	this.height = height;
}

Wall.prototype.render = function(ctx,posCamera,dimCamera){
	ctx.fillStyle = "rgb(80,40,10)";
	ctx.fillRect(this.x-posCamera[0]+dimCamera[0]/2,this.y-posCamera[1]+dimCamera[1]/2,this.width,this.height);
}