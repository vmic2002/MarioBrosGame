import {imageDimensions} from './imageDimensions.js';
//imageDimensions is dictionary that keeps track of every width, height for every image. This way JS doesn't have to wait for an image to load to know its dimensions

const gameContainer = document.getElementById('game-container');
var levelImages = {};
var characterImages = {};
//levelImages is dictionary that keeps track of all images on screen (except for Mario, Luigi etc characters)
//characterImages is dictionary that keeps track of Mario, Luigi etc characters
//key is id and object is the image for both dictionaries



function getQueryParams() {
    const params = new URLSearchParams(window.location.search);
    return {
        lobbyId: params.get('lobbyId'),
//        numCharacters: params.get('numCharacters'),
        username: params.get('username')
    };
}

// Use the parameters
const queryParams = getQueryParams();



function addCharacterImageToScreen(imageName, id, x, y) {
    const imageElement = addImageToScreen(imageName, id, x, y);
    characterImages[id] = imageElement;
}

function addLevelImageToScreen(imageName, id, x, y) {
    const imageElement = addImageToScreen(imageName, id, x, y);
    levelImages[id] = imageElement;
}


function addImageToScreen(imageName, id, x, y) {
    const imageElement = new Image();
    imageElement.src = `Images/${imageName}.png`;
    imageElement.className = 'game-image';
    imageElement.alt = imageName;
    //console.log(imageElement.alt);
    imageElement.id = id;
    gameContainer.appendChild(imageElement);
    imageElement.style.left = `${x}px`;
    imageElement.style.top = `${y}px`;
    //image.style.transform = `translate(0px, 0px)`;
    setTransform(imageElement, 0, 0);
    /* 
    note about moving images: 
    1. set .left and .top to be coordinate with transformation 0,0 when adding the image to screen at first
    2. then when moving image, modify the transform property as it is faster
    3. when replacing image, take old image .top and .left + transform values and make that the new .top and .left with transformation 0,0 
    */
    return imageElement;
}

function setTransform(imageElement, x, y) {
    //the if else makes sure that the setTransform will happen, whether the image is already loaded or not
    if (imageElement.complete) {
        //if image is already loaded (complete is true once image is loaded), no need for onload function
        //THIS IF STATEMENT MAKES GAME WAY SMOOTHER :)
        imageElement.style.transform = `translate(${x}px, ${y}px)`; 
    } else {
        imageElement.onload = function() {
                this.style.transform = `translate(${x}px, ${y}px)`; 
        };
    }
}
    

function removeAllImagesFromScreen() {
    for (const id in levelImages) {
        gameContainer.removeChild(levelImages[id]);
    }
    for (const id in characterImages) {
        gameContainer.removeChild(characterImages[id]);
    }
    levelImages = {};
    characterImages = {};
}



function removeImageFromScreen(id) {
    const levelImage = levelImages[id];
    if (levelImage) {
        removeImage(levelImage, levelImages, id); 
    } else {
        const characterImage = characterImages[id];
        if (characterImage) {
            removeImage(characterImage, characterImages, id);
        }
    }
}

function removeImage(image, map, id) {
    // Remove the image from the game container (remove from screen)
    gameContainer.removeChild(image);
    // Remove the image from hashmap
    delete map[id];
}




function moveLevelImage(id, dx, dy) {
    const levelImage = levelImages[id];
    if (levelImage) {
        moveImage(levelImage, dx, dy);
    }
}

function moveCharacterImage(id, dx, dy) {
    const characterImage = characterImages[id];
    if (characterImage) {
        moveImage(characterImage, dx, dy);
    }
}

function moveImage(image, dx, dy) {
   /*moveImage works by changing the transform value. see css for transform val. .top and .left is only used for initial position
    https://www.paulirish.com/2012/why-moving-elements-with-translate-is-better-than-posabs-topleft/
    ChatGPT: Using the transform property to move elements is generally more efficient than directly modifying the top and left properties. The reason is that changes to the transform property trigger GPU-based transformations, which can be hardware-accelerated and are generally more efficient for moving and transforming elements, especially when there are many elements involved.

On the other hand, modifying top and left directly can trigger layout reflows and repaints, which can be more resource-intensive and slower, especially when dealing with a large number of elements.

So, using transform for movement is often recommended for performance reasons.*/ 
//TODO TODO NEED TO USE TRANSLATE3D IT USES GPU SO MUCH FASTER RENDERING
    
    const currentTransform = getComputedStyle(image).getPropertyValue('transform');
    const matrixValues = currentTransform.slice(7, -1).split(', ');

    const currentDX = parseFloat(matrixValues[4]);
    const currentDY = parseFloat(matrixValues[5]);

    const newDX = currentDX + parseFloat(dx);
    const newDY = currentDY + parseFloat(dy);
    
    //TO MAKE SURE THAT the move always happens, call setTransform (can play around with this...)
    
    //image.style.transform = `translate(${newDX}px, ${newDY}px)`; 
    setTransform(image, newDX, newDY);
}



function moveLevel(dx, dy) { 
    for (const id in levelImages) {
        moveImage(levelImages[id], dx, dy);
    }
}




function setImageToFrontOrBack(imageId, frontOrBack) {
    //frontOrBack == true if we want to set image to the front and false if we want to send it to back
    //console.log("params: "+imageId+" "+frontOrBack);
    const image = getImage(imageId);
    if (image) {
       if (frontOrBack == "true") {
            image.style.zIndex = "9999"; // Set a high z-index value to send it to the front
            //console.log("front");
        } else {
            image.style.zIndex = "-9999"; // Set a low z-index value to send it to the back
            //console.log("back");
        } 
    } 
}



function changeVisibility(imageId, bool) {
    const image = getImage(imageId); 
    if (image) {
        if (bool==="true") {
            //to make image visible
            //image.style.display = 'block';
            image.style.visibility = 'visible';
            //console.log("MAKE IMAGE VISIBLE");
        } else {
            //to make image not visible
            //image.style.display = 'none';
            image.style.visibility = 'hidden';
            //console.log("HIDE IMAGE");
        }
    }
}


function getImage(id) {
    const levelImage = levelImages[id];
    if (levelImage) {
        return levelImage;
    }
    const characterImage = characterImages[id];
    if (characterImage) {
        return characterImage;
    }
    return null;
}

function replaceImage(oldImageId, newImageName) {
    const image = getImage(oldImageId);
    if (image) {
        const oldImageName = image.alt;
                
        const currentTransform = getComputedStyle(image).getPropertyValue('transform');
   	    const matrixValues = currentTransform.slice(7, -1).split(', ');
        //matrixValues[4] is x transform, matrixValues[5] is y transform 
   	    const oldX = parseFloat(image.style.left) + parseFloat(matrixValues[4]);
   	    const oldY = parseFloat(image.style.top) + parseFloat(matrixValues[5]);
   	    const oldWidth = imageDimensions[oldImageName].width;
        const oldHeight = imageDimensions[oldImageName].height;
   	    
        var levelOrCharacter;
        if (levelImages[oldImageId]){
            levelOrCharacter = true;
        } else if (characterImages[oldImageId]) {
            levelOrCharacter = false;
        } else {
            console.log("ERROR image not in either map");
            return;
        }

        removeImageFromScreen(oldImageId); 

        const newWidth = imageDimensions[newImageName].width;
        const newHeight = imageDimensions[newImageName].height;
        //console.log(`${oldImageName} has width: ${oldWidth} and height: ${oldHeight}`);
        //console.log(`${newImage.alt} has width: ${newWidth} and height: ${newHeight}`);
        const dx = (parseFloat(oldWidth) - parseFloat(newWidth))/2;
        const dy = parseFloat(oldHeight) - parseFloat(newHeight);
        //console.log(`Old position is ${oldX}, ${oldY}`);
        //dx, dy are to relocate the image like in setImageAndRelocate method of MovingObject.java

        const newX = oldX + dx;
        const newY = oldY + dy;
        //console.log(`DX: ${dx}, DY: ${dy}`);
        //console.log(`New position is ${newX}, ${newY}`);

        const newImage = addImageToScreen(newImageName, oldImageId, newX, newY);
        if (levelOrCharacter) {
            levelImages[oldImageId] = newImage;
        } else {
            characterImages[oldImageId] = newImage;
        }
    }
}

/*function scaleImage(image, scaleWidth, scaleHeight) {
    //console.log(`NOT Scaled image: ${image.id}  ${image.style.height} ${image.style.width}`);
    const newWidth = parseFloat(image.style.width)*scaleWidth;
    const newHeight = parseFloat(image.style.height)*scaleHeight;
    image.style.width = `${newWidth}px`;
    image.style.height = `${newHeight}px`;
    //console.log(`Scaled image: ${image.id}  ${image.style.height} ${image.style.width}`);
}


function scaleAllImagesOnScreen(scaleWidth, scaleHeight) {
    for (const id in levelImages) {
        scaleImage(levelImages[id], scaleWidth, scaleHeight);
    }
    for (const id in characterImages) {
        scaleImage(characterImages[id], scaleWidth, scaleHeight);
    }
}

function printAllImagesOnScreen() {
   // for (const id in imagesOnScreen) {
     //   console.log(`ID: ${id} -> ${imagesOnScreen[id].alt}`);
    //}
}

// Function to calculate the scale percentage based on window dimensions
function calculateScalePercentage(previousWidth, previousHeight) {
    const currentWidth = window.innerWidth;
    const currentHeight = window.innerHeight;

    const widthPercentageChange = currentWidth / previousWidth;
    const heightPercentageChange = currentHeight / previousHeight;

    const scaleWidth = widthPercentageChange;
    const scaleHeight = heightPercentageChange;
    return [scaleWidth, scaleHeight];
}

// Initialize previousWidth and previousHeight with the initial window dimensions
let previousWidth = window.innerWidth;
let previousHeight = window.innerHeight;


const imageWindowRatioWidth = 0.069;//increase/decrease value to make images wider/thinner compared to window width
const imageWindowRatioHeight = 0.2;//increase/decrease value to make images taller/shorter compared to window height



// Call the scaleAllImages function with the initial scale percentage when the page loads
const initialWidthScale = (imageWindowRatioWidth*previousWidth)/96;//bigmarioright is 96x160, used him as baseline for how big images should be relative to window size
const initialHeightScale = (imageWindowRatioHeight*previousHeight)/160;
//console.log(`${initialWidthScale}  ${initialHeightScale}`);
//window.addEventListener('load', () => scaleAllImagesOnScreen(initialWidthScale, initialHeightScale));

// Call the scaleAllImages function with the new scale percentage when the window is resized
window.addEventListener('resize', () => {
    const [scaleWidth, scaleHeight] = calculateScalePercentage(previousWidth, previousHeight);
    scaleAllImagesOnScreen(scaleWidth, scaleHeight);
    //TODO nee to move all images as well when window dimensions change or else looks weird
    // Update previousWidth and previousHeight with the new window dimensions
    previousWidth = window.innerWidth;
    previousHeight = window.innerHeight;
});
*/




//ALL 12 sounds are correctly imported and the playSound func works with all 12 sounds
// Create an object to hold references to the loaded sound files
const sounds = {};
//max of 2 sounds to be played at the same time
let currentSound1 = null;
let currentSound2 = null;

// Define the list of sound files and their paths
const soundList = [
    { name: 'Bump.mp3', path: 'Sounds/Bump.mp3' },
    { name: 'Fireball.mp3', path: 'Sounds/Fireball.mp3' },
    { name: 'Kick.mp3', path: 'Sounds/Kick.mp3' },
    { name: 'Pipe.mp3', path: 'Sounds/Pipe.mp3' },
    { name: 'Squish.mp3', path: 'Sounds/Squish.mp3' },
    { name: 'tail.mp3', path: 'Sounds/tail.mp3' },
    { name: 'Death.mp3', path: 'Sounds/Death.mp3' },
    { name: 'Item Box.mp3', path: 'Sounds/Item Box.mp3' },
    { name: 'Mario Jump.mp3', path: 'Sounds/Mario Jump.mp3' },
    { name: 'Powerup.mp3', path: 'Sounds/Powerup.mp3' },
    { name: 'Transformation.mp3', path: 'Sounds/Transformation.mp3' },
    { name: 'Coin.mp3', path: 'Sounds/Coin.mp3' }
    // Add more sound objects as needed
];

// Function to load the sound files
function loadSounds() {
  for (const sound of soundList) {
    const audio = new Audio();
    audio.src = sound.path;
    audio.load();
    sounds[sound.name] = audio;
  }
}

// Function to play a sound by its name
/*function playSound(soundName) {
    const sound = sounds[soundName];
    if (sound) {
        sound.currentTime = 0;
        sound.play();
    }
}*/

function playSound(soundName) {
    const sound = sounds[soundName];
    if (sound) {
        //MAX OF 2 SOUNDS TO BE PLAYED AT THE SAME TIME
        //first two if statements is in case currentSound1 or 2 is not set to null by 'onended' function
        // Ensure currentSound1 is actually null if it should be
        if (currentSound1 && currentSound1.ended) {
            currentSound1 = null;
        }
        // Ensure currentSound2 is actually null if it should be
        if (currentSound2 && currentSound2.ended) {
            currentSound2 = null;
        }
        if (!currentSound1) {
            // Play in the first slot if it's available
            currentSound1 = sound;
            currentSound1.currentTime = 0;
            currentSound1.play();
            // Set currentSound1 to null when the sound finishes
            currentSound1.onended = function() {
                currentSound1 = null;
            };
        } else if (!currentSound2) {
            // Play in the second slot if the first is occupied
            currentSound2 = sound;
            currentSound2.currentTime = 0;
            currentSound2.play();
            // Set currentSound2 to null when the sound finishes
            currentSound2.onended = function() {
                currentSound2 = null;
            };
        } else {
            // If both slots are occupied, replace the sound that is almost done
            const remainingTime1 = currentSound1.duration - currentSound1.currentTime;
            const remainingTime2 = currentSound2.duration - currentSound2.currentTime;

            if (remainingTime1 < remainingTime2) {
                // Replace the sound in the first slot
                currentSound1.pause();
                currentSound1.currentTime = 0;
                currentSound1 = sound;
                currentSound1.currentTime = 0;
                currentSound1.play();
                // Set currentSound1 to null when the sound finishes
                currentSound1.onended = function() {
                    currentSound1 = null;
                };
            } else {
                // Replace the sound in the second slot
                currentSound2.pause();
                currentSound2.currentTime = 0;
                currentSound2 = sound;
                currentSound2.currentTime = 0;
                currentSound2.play();
                // Set currentSound2 to null when the sound finishes
                currentSound2.onended = function() {
                    currentSound2 = null;
                };
            }
        }
    }
}




// Load the sounds
loadSounds();

//addImageToScreen('bigLuigiLeftCrouchingImage', '1', 50,110);    








//BUTTON IS ONLY for testing
// Get a reference to the play button
const testButton = document.getElementById('test');
// Add a click event listener to the play button
//var bo = true;
//addImageToScreen('smallLuigiLeftImage', 0, 100, 100);
testButton.addEventListener('click', () => {
    console.log('CLICK!');
    /*if (!currentSound1) {
        console.log("null");
    } else {
        console.log(`${currentSound1.src}`);
    }
    if (!currentSound2) {
        console.log("null");
    } else {
        console.log(`${currentSound2.src}`);
    }*/


    console.log(queryParams.lobbyId);
   // console.log(queryParams.numCharacters);
    console.log(queryParams.username);

    
});




const readyButton = document.getElementById('ready');

readyButton.addEventListener('click', () => {
    //console.log("TELL SERVER WE ARE READY TO START GAME!");
    socket.send("ready");
});

let character = null;//server will send message to update this val to MARIO, or LUIGI, etc


const websocketurl =`ws://localhost:8080/MarioGameServerSide/websocket/${queryParams.lobbyId}/${queryParams.username}`
console.log(websocketurl)
const socket = new WebSocket(websocketurl); // Replace with your server address
///websocket/{lobbyId}/{username}
//tomcat runs java websocket server on port 8080 and @ServerEndpoint in MyWebSocketServer.java is /websocket


// Using onmessage for 'message' event
socket.onmessage = function(event) {
  // Handle the received message here
    const message = event.data;
    //console.log('Received message from server:', message);
    
    try {
        const parsedMessage = JSON.parse(message);

        if (parsedMessage.type === 'moveImage') {
            // Example: { "type": "moveImage", "imageId": "imageId", "dx":"10", "dy":"20" }
            const {type, imageId, dx, dy} = parsedMessage;
            //console.log('Received moveImage data:', `${type}, ${imageId}, ${dx}, ${dy}`);
            //this works!!!!!!, json is received and parsed correctly
            moveLevelImage(imageId, dx, dy);
        } else if (parsedMessage.type === 'replaceImage') {
            // Example: { "type": "replaceImage", "oldImageId":"id", "newImageName":"luigiWalking" }
            const {type, oldImageId, newImageName} = parsedMessage;
            //oldImageId is used to find which image to replace and newImageName is used to find image in Images directory to replace it with
            //console.log('Received replaceImage data:', `${type}, ${oldImageId}, ${newImageName}`);
            //this works!!! json is received and parsed correctly
            replaceImage(oldImageId, newImageName);
        } else if (parsedMessage.type === 'playSound') {
            // Example: { "type": "playSound", "soundName": "Coin.wav" }
            //console.log('Received playSound data:', parsedMessage.soundName);
            //TODO UNCOMMENT TO PLAY SOUND playSound(parsedMessage.soundName);
        } else if (parsedMessage.type === 'addLevelImageToScreen') {
            // Example: { "type": "addLevelImageToScreen", "imageName": "platform", "id":"25", "x":"10", "y":"10" }
            const {type, imageName, id, x, y} = parsedMessage;
            //console.log('Received addLevelImageToScreen data:', `${type}, ${imageName}, ${id}, ${x}, ${y}`);
            addLevelImageToScreen(imageName, id, x, y);
        } else if (parsedMessage.type === 'removeImageFromScreen') {
            // Example: { "type": "removeImageFromScreen", "id": "i" }
            //console.log('Received removeImageFromScreen data:', parsedMessage.id);
            removeImageFromScreen(parsedMessage.id);
        } else if (parsedMessage.type === 'setVisible') { 
           //Example { "type": "setVisible", "imageId": "12", "bool":"true" } 
            const {type, imageId, bool} = parsedMessage;
            //console.log('Received setVisible data:', `${imageId}, ${bool}`);
            changeVisibility(imageId, bool);
        } else if (parsedMessage.type === 'moveLevel') {
            //Example { "type": "moveLevel", "dx": "12", "dy":"true" }
             const {type, dx, dy} = parsedMessage;
             //console.log('Received moveLevel data:', `${dx}, ${dy}`);
             moveLevel(dx, dy);
        } else if (parsedMessage.type === 'moveMarioCharacter') {
            // Example: { "type": "moveMarioCharacter", "imageId": "imageId", "dx":"10", "dy":"20" }
            const {type, imageId, dx, dy} = parsedMessage;
            //console.log('Received moveMarioCharacter data:', `${type}, ${imageId}, ${dx}, ${dy}`);
            moveCharacterImage(imageId, dx, dy);
        } else if (parsedMessage.type === 'addCharacterImageToScreen') {
             // Example: { "type": "addCharacterImageToScreen", "imageName": "luigiBigLeft", "id":"25", "x":"10", "y": "10" }
            const {type, imageName, id, x, y} = parsedMessage;
            //console.log('Received addCHARACTERImageToScreen data:', `${type}, ${imageName}, ${id}, ${x}, ${y}`);
            addCharacterImageToScreen(imageName, id, x, y); 
        } else if (parsedMessage.type === 'removeAllImagesFromScreen') {
              // Example: { "type": "removeAllImagesFromScreen"}
            //console.log('Received removeAllImagesFromScreen data');
            removeAllImagesFromScreen();
        } else if (parsedMessage.type === 'setFrontOrBack') {
            //console.log('moving image to front or back');
            const {imageId, bool} = parsedMessage;
            setImageToFrontOrBack(imageId, bool);
        } else if (parsedMessage.type === 'yourCharacter') {
            // Example: { "type": "yourCharacter", "character": "MARIO" }
            character = parsedMessage.character;
            console.log(`character: ${character}`);
            if (readyButton) {
                readyButton.remove();
            } 
        } else if (parsedMessage.type == 'lobbyAlreadyFull') {
            // GO BACK TO INDEX.HTML to try joining another lobby, one that isn't full
            const queryString = `errMsg=${encodeURIComponent(queryParams.lobbyId)}`;
            const url = `index.html?${queryString}`;
            window.location.href = url;
        } else {
            // Handle other JSON data structures or handle unknown types
            console.log('Received unknown JSON data:', parsedMessage);
        }
    } catch (error) {
        //console.log('Error parsing JSON:', error);
        console.log(message);
    }      
};

// Additional event listeners
socket.onopen = function(event) {
    console.log('WebSocket connection opened!');
    //Perform actions specific to when the connection is open
};

socket.onclose = function(event) {
  console.log('WebSocket connection closed');
  // Handle the connection closed event
};

socket.onerror = function(event) {
    console.log('ERROR :(((');
    console.error('WebSocket error:', event);
  // Handle any errors that occur in the WebSocket connection
};


// Send a JSON object as a message to the server
function sendJsonMessage(data) {
  if (socket.readyState === WebSocket.OPEN) {
    const message = JSON.stringify(data);
    //console.log(`Sending message to server: ${message}`);
    socket.send(message);  
  } else {
    console.log('WebSocket connection is not open.');
  }
}

const keys = ['ArrowUp', 'ArrowDown', 'ArrowLeft', 'ArrowRight', 'q'];

document.addEventListener('keydown', (event) => {
    // Prevent default arrow key behavior (scrolling) for ArrowUp, ArrowDown, ArrowLeft, and ArrowRight
    if (event.key.includes('Arrow')) {
        event.preventDefault();
    }
    if (keys.includes(event.key)) {
        //console.log(`key pressed: ${event.key}`);
        const data = { keyEvent: "keyPressed", key: `${event.key}`, character: `${character}` };
        sendJsonMessage(data); 
    }
});

document.addEventListener('keyup', (event) => {
    if (keys.includes(event.key)) {
        //console.log(`Arrow key released: ${event.key}`);
        const data = { keyEvent: "keyReleased", key: `${event.key}`, character: `${character}` };
        sendJsonMessage(data);
    }
});




