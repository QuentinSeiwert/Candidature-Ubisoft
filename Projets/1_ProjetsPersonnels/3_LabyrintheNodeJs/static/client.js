document.addEventListener("DOMContentLoaded", function() {
	var canvas = document.getElementById("canvas");
	var ctx = canvas.getContext("2d");

	var width   = window.innerWidth;
   	var height  = window.innerHeight;

	var socket = io.connect();

	var BlockSize = 64;
	var mapSize = 48;

	canvas.width = 800;
   	canvas.height = 600;

   	var delta = 16;

   	var ID;

   	var players = [];
   	var walls = [];
   	var end = new End(-1,-1,0,0);

   	var posCamera =[0,0];
   	var dimCamera = [canvas.width,canvas.height]

   	var colors = ["blue","cyan","pink","green","rgb(200,50,200)"]
   	var currentColor = 0;

   	window.onkeydown = function(event){
		var e = event || window.event;
		var key = e.which ||e.keyCode;
		switch(key){
			case 38:players[ID].isMoving = true;players[ID].dir = 0;break;
			case 39:players[ID].isMoving = true;players[ID].dir = 1;break;
			case 40:players[ID].isMoving = true;players[ID].dir = 2;break;
			case 37:players[ID].isMoving = true;players[ID].dir = 3;break;
			case 32:currentColor++;break;
		}
	}

	window.onkeyup = function(event){
		players[ID].isMoving = false;
	}

	socket.on("initPlayer",function(id,coords,names){
		ID = id;
		var pseudo = prompt('Quel est votre nom ?');
		socket.emit("pseudo",pseudo);
		for(var i = 0;i < coords.length;i++){
			if(i != id && coords[i] != undefined){
				players[i] = new Player(i,names[i],coords[i][0],coords[i][1]);
			}
		}
		update();
	});

	socket.on("initWorld",function(map){
		borderSize = 10*BlockSize;
		walls.push(new Wall(-borderSize,-borderSize,BlockSize*mapSize+2*borderSize,borderSize));
		walls.push(new Wall(-borderSize,BlockSize*mapSize,BlockSize*mapSize+2*borderSize,borderSize));
		walls.push(new Wall(-borderSize,-borderSize,borderSize,BlockSize*mapSize+2*borderSize));
		walls.push(new Wall(BlockSize*mapSize,-borderSize,borderSize,BlockSize*mapSize+2*borderSize));
		for(var i = 0;i < map.length;i++){
			for(var j = 0;j < map[i].length;j++){
				if(map[i][j] == 1){
					walls.push(new Wall(j*BlockSize,i*BlockSize,BlockSize+1,BlockSize+1));
				}else if(map[i][j]==2){
					end = new End(j*BlockSize,i*BlockSize,BlockSize,BlockSize);
					alert("Fin générée");
				}
			}
		}
	});

	socket.on("newPlayer",function(id,pseudo){
		players[id] = new Player(id,pseudo,0, 0);
	});
	socket.on("disconnectPlayer",function(id){
		delete players[id];
	});
	socket.on("movePlayer",function(id,x,y){
		players[id].x = x;
		players[id].y = y;
	});
	socket.on("setPseudo",function(id,pseudo){
		players[id].pseudo = pseudo;
	});
	socket.on("message",function(message){
		alert(message);
	});

   	update = function(){
   		requestAnimationFrame(update);
   		ctx.fillStyle = 'rgb(220,220,150)';
		ctx.fillRect(0,0,canvas.width,canvas.height);
   		if(players[ID].isMoving){

			players[ID].move(delta);

			for(var i = 0;i < walls.length;i++){
				while(Collisions.collRectRect(players[ID],walls[i])){
					if(players[ID].oldX > players[ID].x){
						players[ID].x++;
					}else if(players[ID].oldX < players[ID].x){
						players[ID].x--;
					}
					if(players[ID].oldY > players[ID].y){
						players[ID].y++;
					}
					else if(players[ID].oldY < players[ID].y){
						players[ID].y--;
					}
				}
			}

			if(Collisions.collRectRect(players[ID],end)){
				alert("Vous avez gagné !");
				socket.emit("win");
			}

			/*for(var i = 0;i < players.length;i++){
				if(i != ID){
					while(Collisions.collRectRect(players[ID],players[i])){
						if(players[ID].oldX > players[ID].x){
							players[ID].x++;
						}else if(players[ID].oldX < players[ID].x){
							players[ID].x--;
						}
						if(players[ID].oldY > players[ID].y){
							players[ID].y++;
						}
						else if(players[ID].oldY < players[ID].y){
							players[ID].y--;
						}
					}
				}
			}*/

			players[ID].oldX = players[ID].x;
			players[ID].oldY = players[ID].y;
			posCamera[0] = players[ID].x;
			posCamera[1] = players[ID].y;
			socket.emit("move",players[ID].x,players[ID].y);
   		}
   		end.render(ctx,posCamera,dimCamera);
   		for(var i = 0;i< walls.length;i++){
   			if(walls[i]!=undefined){
   				walls[i].render(ctx,posCamera,dimCamera);
   			}
   		}
   		ctx.fillStyle = "red";
   		for(var i = 0;i< players.length;i++){
   			if(players[i]!=undefined && i != ID){
   				players[i].render(ctx,posCamera,dimCamera);
   			}
   		}
   		ctx.fillStyle = colors[currentColor%colors.length];
   		players[ID].render(ctx,posCamera,dimCamera);
   	};
});