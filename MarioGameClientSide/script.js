
function goToLobby() {
    // Retrieve the values from the text boxes
    const lobbyId = document.getElementById("lobbyId").value;
    //const numCharacters = document.getElementById("numCharacters").value;
    const username = document.getElementById("username").value;

    // Display the retrieved values (or you can process them as needed)
    console.log("Lobby ID:", lobbyId);
   // console.log("Number of Characters:", numCharacters);
    console.log("Username:", username);
    console.log("connect");

    const queryString = `lobbyId=${encodeURIComponent(lobbyId)}&username=${encodeURIComponent(username)}`;

    const url = `game.html?${queryString}`;



    window.location.href = url; // Navigate to game.html


    
}





// Attach the event listener to the form
document.getElementById('lobbyForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent default form submission
    goToLobby();
});


function getQueryParams() {
    const params = new URLSearchParams(window.location.search);
    return {
        errMsg: params.get('errMsg')
    };
}

// Use the parameters
const queryParams = getQueryParams();


var paragraph = document.getElementById('errorMessage');
    
if (queryParams.errMsg) {    
    paragraph.textContent = `Lobby ${queryParams.errMsg} is already full! Please join another lobby...`;
}

