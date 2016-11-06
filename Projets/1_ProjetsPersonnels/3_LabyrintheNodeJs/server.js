//Création des variables globales
var express = require('express') 
var app = express()
var http = require('http')
var socketIo = require('socket.io');

//Création du serveur
var server =  http.createServer(app);
var io = socketIo.listen(server);
server.listen(8080);

app.use(express.static(__dirname + '/static'));

var coords = [];
var names = [];
var map = [];
var pos = [];
var mapSize = 48;

var numberNearWall=function(x,y){
	var res = 0;
	if(x <= 0)
		res++;
	else
		res += map[y][x-1];

	if(x >= mapSize-1)
		res++;
	else
		res += map[y][x+1];

	if(y <= 0)
		res++;
	else
		res += map[y-1][x];

	if(y >= mapSize-1)
		res++;
	else
		res += map[y+1][x];

	return res;
}

var canGeneratePath = function(x,y){
	return(map[y][x] == 1 && numberNearWall(x,y)>2);
}

var findBestPos = function(){
	var bestPos = [0,0];
	var minDist = 9999;
	for(var i = 0;i < mapSize;i++){
		for(var j = 0;j < mapSize;j++){
			if(map[i][j] == 0 && distance(j,i,mapSize-1,mapSize-1)<minDist){
				minDist = distance(j,i,mapSize-1,mapSize-1);
				bestPos = [j,i];
				console.log("BestPos:"+bestPos);
			}
		}
	}
	return bestPos;
}

var distance = function(x1,y1,x2,y2){
	return(Math.abs(x1-x2)+Math.abs(y1-y2));
}

var generatePath = function(x,y){
	var dir = 0;
	pos = [x,y];
	var oldPos = [];
	var dirDir = 0;
	var stop = false;

	var move = function(){
		switch(dir){
			case 0:if(pos[1]>0)pos[1]--;break;
			case 1:if(pos[0]<mapSize-1)pos[0]++;break;
			case 2:if(pos[1]<mapSize-1)pos[1]++;break;
			case 3:if(pos[0]>0)pos[0]--;break;
		}
	}

	while(!stop){
		oldPos[0] = pos[0];
		oldPos[1] = pos[1];
		if(Math.floor(Math.random()*100)+1<=33)
			dir = Math.floor(Math.random()*4);
		if(Math.floor(Math.random()*2)==0)
			dirDir = 1;
		else
			dirDir = -1;
		move();
		console.log("Direction essayée: "+dir);
		var numberTry = 0;
		while(!canGeneratePath(pos[0],pos[1]) && numberTry < 3){
			pos[0] = oldPos[0];
			pos[1] = oldPos[1];
			dir = (4+(dir+dirDir))%4;
			move();
			numberTry++;
			console.log("Direction essayée: "+dir);
		}
		if(pos[0] == mapSize-1 && pos[1] == mapSize-1){
			map[pos[1]][pos[0]] = 0;
			stop = true;
			console.log("Sortie trouvée");
		}else{
			if(canGeneratePath(pos[0],pos[1])){
				console.log("Direction retenue: "+dir);
				map[pos[1]][pos[0]] = 0;
			}else{
				console.log("Bloqué");
				stop = true;
			}
		}
	}
}

var generateMap = function(){
	console.log("Generating map ...");
	for(var i = 0; i < mapSize; i++){
		map[i] = [];
		for(var j = 0; j < mapSize;j++){
			map[i][j] = 1;
		}
	}

	map[0][0] = 0;
	var limit = 0;

	
			/*bestPos = findBestPos();
			pos[0] = bestPos[0];
			pos[1] = bestPos[1];
			limit++;
			/*if(posHistory.length>0){
				tmp = posHistory.pop();
				pos[0] = tmp[0];
				pos[1] = tmp[1];
			}
			else{
				pos[0] = 0;
				pos[1] = 0;
			}*/
	while((pos[0] != mapSize-1 || pos[1] != mapSize-1) && limit <100){
		bestPos = findBestPos();
		pos[0] = bestPos[0];
		pos[1] = bestPos[1];
		generatePath(pos[0],pos[1]);
		limit++;
	}
	if(limit == 100){
		generateMap();
	}else{
		for(var i = 0;i< 1000;i++){
			do{
				var r1 = Math.floor(Math.random()*mapSize);
				var r2 = Math.floor(Math.random()*mapSize);
			}while(map[r1][r2]!=0);
			generatePath(r2,r1);
		}
		do{
				var r1 = Math.floor(Math.random()*mapSize);
				var r2 = Math.floor(Math.random()*mapSize);
		}while(map[r1][r2]!=0);
		map[r1][r2]=2;
		console.log("Fin positionnée en "+r1+","+r2);
	}
	console.log("Map generated");
}


var getFirstAvailableId = function(){
	var i = 0;
	for(i = 0;i < coords.length;i++){
		if(coords[i] == undefined){
			return i;
		}
	}
	return i;
}

generateMap();

io.on('connection', function (socket) {
	socket.id = getFirstAvailableId();
	coords[socket.id] = [0,0];
	socket.broadcast.emit("newPlayer",socket.id);
	socket.emit("initPlayer",socket.id,coords,names);
	socket.emit("initWorld",map);
	console.log("Le joueur "+socket.id+" s'est connecté");

	socket.on('move', function (x,y){
		coords[socket.id] = [x,y];
		socket.broadcast.emit("movePlayer",socket.id,x,y);
	});

	socket.on("pseudo",function(pseudo){
		names[socket.id] = pseudo;
		socket.broadcast.emit("setPseudo",socket.id,pseudo);
	})

	socket.on('disconnect',function(){
		console.log("Le joueur "+socket.id+" s'est deconnecté");
		delete coords[socket.id];
		socket.broadcast.emit("disconnectPlayer",socket.id);
	});

	socket.on("win",function(){
		socket.broadcast.emit("message",names[socket.id]+" a gagné !");
	});
});

