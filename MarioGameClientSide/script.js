
const gameContainer = document.getElementById('game-container');
const imagesOnScreen = {};
//imagesOnScreen is hashmap that keeps track of all images on screen
//key is id and object is the image
//function works
function addImageToScreen(imageName, id, x, y) {
    const imageElement = new Image();
    imageElement.src = `Images/${imageName}.png`;
    imageElement.className = 'game-image';
    imageElement.alt = imageName;
    // Set the provided ID for the image element
    imageElement.id = id;
    // Add the image element to the hashmap
    imagesOnScreen[id] = imageElement;
    // Append the image to the container (add to screen)
    gameContainer.appendChild(imageElement);
    imageElement.style.left = `${x}px`;
    imageElement.style.top = `${y}px`;
   /* note about moving images: set .left and .top when adding the image to screen at first
    then when modifying the position, use the transform property as it is faster    
*/
    imageElement.onload = function() {
        // Get the actual width and height of the loaded image
        const imageWidth = this.width;
        const imageHeight = this.height;

        // Set the width and height of the image element to match the loaded image
        this.style.width = `${imageWidth}px`;
        this.style.height = `${imageHeight}px`;
        
    };
}

function removeImageFromScreen(id) {
    const image = imagesOnScreen[id];
    if (image) {
        // Remove the image from the game container (remove from screen)
        gameContainer.removeChild(image);

        // Remove the image from the imagesOnScreen hashmap
        delete imagesOnScreen[id];
    }
}


function moveImage(id, dx, dy) {
   /*moveImage works by changing the transform value. see css for transform val. .top and .left is only used for initial position
    ChatGPT: Using the transform property to move elements is generally more efficient than directly modifying the top and left properties. The reason is that changes to the transform property trigger GPU-based transformations, which can be hardware-accelerated and are generally more efficient for moving and transforming elements, especially when there are many elements involved.

On the other hand, modifying top and left directly can trigger layout reflows and repaints, which can be more resource-intensive and slower, especially when dealing with a large number of elements.

So, using transform for movement is often recommended for performance reasons.*/
    //TODO TODO WILL HAVE TO MOVE LEVEL IN ONE WEBSOCKET CALL: {TYPE:MOVELEVEL, DX:10, DY:15} INSTEAD OF A MOVE IMAGE WEBSOCKET MESSAGE FOR EACH IMAGE (for now long levels lag a lot when moving level) -> MAKE SURE NOT TO MOVE THE MARIO CHARACTER THAT CAUSED THE LEVEL TO MOVE
     const image = imagesOnScreen[id];
    if (image) {            
        const currentTransform = getComputedStyle(image).getPropertyValue('transform');
        const matrixValues = currentTransform.slice(7, -1).split(', ');

        const currentDX = parseFloat(matrixValues[4]);
        const currentDY = parseFloat(matrixValues[5]);

        const newDX = currentDX + parseFloat(dx);
        const newDY = currentDY + parseFloat(dy);

        image.style.transform = `translate(${newDX}px, ${newDY}px)`;
    } else {
        console.log(`NO IMAGE WITH ID ${id}`);
    }   
}



function moveAllImages(dx, dy) { 
    for (const id in imagesOnScreen) {
        moveImage(id, dx, dy);
    }
}








function changeVisibility(imageId, bool) {
    const image = imagesOnScreen[imageId];
    if (image) {
        if (bool==="true") {
            //to make image visible
            image.style.display = 'block';
            console.log("MAKE IMAGE VISIBLE");
        } else {
            //to make image not visible
            image.style.display = 'none';
            console.log("HIDE IMAGE");
        }
    }
}


function replaceImage(oldImageId, newImageName) {
    const image = imagesOnScreen[oldImageId];
    if (image) {
        const oldImageName = image.alt;
        
        // Get the current position of the image
        const currentPosition = image.getBoundingClientRect();
        const oldX = currentPosition.left;
        const oldY = currentPosition.top;
        
        const oldWidth = currentPosition.width;
        const oldHeight = currentPosition.height;
        
        removeImageFromScreen(oldImageId); 
        
        addImageToScreen(newImageName, oldImageId, oldX, oldY);
        //CHANGE POSITION OF NEW IMAGE LIKE IN SETIMAGEANDRELOCATE IN JAVA
        const newImage = imagesOnScreen[oldImageId];
        if (newImage) {
            //console.log("---------------------------");
            //console.log(`Replacing ${oldImageName} with ${newImage.alt}`);
            //need to relocate the image like in setImageAndRelocate method of MovingObject.java
            //TODO might have to check that image loaded properly before getting width/height
            const newWidth = newImage.getBoundingClientRect().width;
            const newHeight = newImage.getBoundingClientRect().height;
            //console.log(`${oldImageName} has width: ${oldWidth} and height: ${oldHeight}`);
            //console.log(`${newImage.alt} has width: ${newWidth} and height: ${newHeight}`);
            const dx = (oldWidth - newWidth)/2;
            const dy = oldHeight - newHeight;
            moveImage(oldImageId, dx, dy);
            //console.log(`Moving image to relocate: dx: ${dx} and dy: ${dy}`);
            //console.log("CHANGED IMAGE AND RELOCATED!");
            //console.log("-----------------------------");
        } else {
            console.log("BIG PROBLEM");
        }   
    }
}

function scaleImage(image, scaleWidth, scaleHeight) {
    //console.log(`NOT Scaled image: ${image.id}  ${image.style.height} ${image.style.width}`);
    const newWidth = parseFloat(image.style.width)*scaleWidth;
    const newHeight = parseFloat(image.style.height)*scaleHeight;
    image.style.width = `${newWidth}px`;
    image.style.height = `${newHeight}px`;
    //console.log(`Scaled image: ${image.id}  ${image.style.height} ${image.style.width}`);
}


function scaleAllImagesOnScreen(scaleWidth, scaleHeight) {
  for (const id in imagesOnScreen) {
    scaleImage(imagesOnScreen[id], scaleWidth, scaleHeight);
  }
}

function printAllImagesOnScreen() {
    for (const id in imagesOnScreen) {
        console.log(`ID: ${id} -> ${imagesOnScreen[id].alt}`);
    }
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













//ALL 12 sounds are correctly imported and the playSound func works with all 12 sounds
// Create an object to hold references to the loaded sound files
const sounds = {};

// Define the list of sound files and their paths
const soundList = [
    { name: 'Bump.wav', path: 'Sounds/Bump.wav' },
    { name: 'Fireball.wav', path: 'Sounds/Fireball.wav' },
    { name: 'Kick.wav', path: 'Sounds/Kick.wav' },
    { name: 'Pipe.wav', path: 'Sounds/Pipe.wav' },
    { name: 'Squish.wav', path: 'Sounds/Squish.wav' },
    { name: 'tail.wav', path: 'Sounds/tail.wav' },
    { name: 'Death.wav', path: 'Sounds/Death.wav' },
    { name: 'Item Box.wav', path: 'Sounds/Item Box.wav' },
    { name: 'Mario Jump.wav', path: 'Sounds/Mario Jump.wav' },
    { name: 'Powerup.wav', path: 'Sounds/Powerup.wav' },
    { name: 'Transformation.wav', path: 'Sounds/Transformation.wav' },
    { name: 'Coin.wav', path: 'Sounds/Coin.wav' },
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
function playSound(soundName) {
    const sound = sounds[soundName];
    if (sound) {
        sound.currentTime = 0;
        sound.play();
    }
}

// Load the sounds
loadSounds();

//addImageToScreen('bigLuigiLeftCrouchingImage', '1', 50,110);    

//BUTTON IS ONLY for testing
// Get a reference to the play button
const testButton = document.getElementById('test');
// Add a click event listener to the play button
var bo = true;
//addImageToScreen('smallLuigiLeftImage', 0, 100, 100);
testButton.addEventListener('click', () => {
    // Example usage
    //everytime button is clicked, message is sent to server
   // const data = { imageName: 'bigLuigiSomething', otherData: 30 };
    //sendJsonMessage(data);
    //playSound('Death.wav');
    //const width = window.innerWidth;
    //const height = window.innerHeight;
    //console.log(`The viewport's width is ${width} and the height is ${height}.`);
    //console.log(imagesHashMap['bigLuigiLeftCrouchingImage']);
    //moveImage('1', 10, 20);
   // removeImageFromScreen('1');
    //  printAllImagesOnScreen();
    //  console.log(`Num elements in imagesOnScreen map: ${Object.keys(imagesOnScreen).length}`);
    //replaceImage(0, 'bigLuigiLeftCrouchingImage');
    //printAllImagesOnScreen();
    //moveImage(0, 10, 0);
    //console.log('END OF CLICKED');
    /*if (bo) {
        changeVisibility(1, "false");
        changeVisibility(0, "false");
        bo = false;
    } else {
        changeVisibility(1, "true");
        changeVisibility(0, "true");
        bo = true;
    }*/
    console.log('Moving all images');
    moveAllImages(10, 10);
});



//The following is for websocket connection to interact with Java backend
// Establish WebSocket connection
const socket = new WebSocket('ws://localhost:8080/MarioGameServerSide/websocket/myusername'); // Replace with your server address
//tomcat runs java websocket server on port 8080 and @ServerEndpoint in MyWebSocketServer.java is /websocket
//instead of myusername in future will need to ask user for username and password

// Using onmessage for 'message' event
socket.onmessage = function(event) {
  // Handle the received message here
    const message = event.data;
    //console.log('Received message from server:', message);
    
    //ALL POSSIBLE MESSAGES TO RECEIVE: MOVE AN IMAGE, PLAY A SOUND, REPLACE AN IMAGE WITH ANOTHER (SETIMAGEANDRELOCATE, showImageAndSetlocation, and hideImage

    try {
        const parsedMessage = JSON.parse(message);

        if (parsedMessage.type === 'moveImage') {
            // Example: { "type": "moveImage", "imageId": "imageId", "dx":"10", "dy":"20" }
            const {type, imageId, dx, dy} = parsedMessage;
            console.log('Received moveImage data:', `${type}, ${imageId}, ${dx}, ${dy}`);
            //this works!!!!!!, json is received and parsed correctly
            moveImage(imageId, dx, dy);
        } else if (parsedMessage.type === 'replaceImage') {
            // Example: { "type": "replaceImage", "oldImageId":"id", "newImageName":"luigiWalking" }
            const {type, oldImageId, newImageName} = parsedMessage;
            //oldImageId is used to find which image to replace and newImageName is used to find image in Images directory to replace it with
            //console.log('Received replaceImage data:', `${type}, ${oldImageId}, ${newImageName}`);
            //this works!!! json is received and parsed correctly
            //TODO need to write replaceImage func
            replaceImage(oldImageId, newImageName);
        } else if (parsedMessage.type === 'playSound') {
            // Example: { "type": "playSound", "soundName": "Coin.wav" }
            //console.log('Received playSound data:', parsedMessage.soundName);
            playSound(parsedMessage.soundName);
            //this works!!!!!
        } else if (parsedMessage.type === 'addImageToScreen') {
            // Example: { "type": "addImageToScreen", "imageName": "luigiBigLeft", "id":"25", "x":"10", "y":"10" }
            const {type, imageName, id, x, y} = parsedMessage;
            //console.log('Received addImageToScreen data:', `${type}, ${imageName}, ${id}, ${x}, ${y}`);
            //this works!!!!!
            addImageToScreen(imageName, id, x, y);
        } else if (parsedMessage.type === 'removeImageFromScreen') {
            // Example: { "type": "removeImageFromScreen", "id": "i" }
            //console.log('Received removeImageFromScreen data:', parsedMessage.id);
            //works!!!!
            removeImageFromScreen(parsedMessage.id);
        } else if (parsedMessage.type === 'setVisible') { 
           //Example { "type": "setVisible", "imageId": "12", "bool":"true" } 
            const {type, imageId, bool} = parsedMessage;
            console.log('>>>>>>>>>>>>>Received setVisible data:', `${imageId}, ${bool}`);
            changeVisibility(imageId, bool);
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
    console.log(`Sending message to server: ${message}`);
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
        const data = { keyEvent: "keyPressed", key: `${event.key}`, character: "Mario" };
        sendJsonMessage(data); 
    }
});

document.addEventListener('keyup', (event) => {
    if (keys.includes(event.key)) {
        //console.log(`Arrow key released: ${event.key}`);
        const data = { keyEvent: "keyReleased", key: `${event.key}`, character: "Mario" };
        sendJsonMessage(data);
    }
});
