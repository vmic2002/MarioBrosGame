const imageList = [
  //{ id: 'image1', path: 'Images/image1.jpg', x: 100, y: 100 },
  //{ id: 'image2', path: 'Images/image2.jpg', x: 100, y: 200 },
  //{ id: 'image3', path: 'Images/image3.jpg', x: 100, y: 300 },
  // Add more image objects as needed
//THIS WORKS ALL IMAGES ARE AT X AND Y 100, 100
//Images directory has ALL (177) images needed for current version of mario game and JS includes all 177 images
{ id: 'bigLuigiLeftCrouchingImage', path: 'Images/bigLuigiLeftCrouchingImage.png', x: 300, y: 100 },
{ id: 'bigLuigiPipe', path: 'Images/bigLuigiPipe.png', x: 400, y: 100 },
{ id: 'catLuigiPipeImage', path: 'Images/catLuigiPipeImage.png', x: 500, y: 100 },
{ id: 'bigMarioRightImage', path: 'Images/bigMarioRightImage.png', x: 400, y: 100 },
{ id: 'leftFireBall2', path: 'Images/leftFireBall2.png', x: 100, y: 100 },
{ id: 'grassLeftImage', path: 'Images/grassLeftImage.png', x: 100, y: 100 },
{ id: 'bigMarioLeftCrouchingImage', path: 'Images/bigMarioLeftCrouchingImage.png', x: 100, y: 100 },
{ id: 'bigMarioRightJumpingImage', path: 'Images/bigMarioRightJumpingImage.png', x: 100, y: 100 },
{ id: 'redTurtleStandingLeft', path: 'Images/redTurtleStandingLeft.png', x: 100, y: 100 },
{ id: 'leftTanookiMarioJumpingUp', path: 'Images/leftTanookiMarioJumpingUp.png', x: 100, y: 100 },
{ id: 'leftFireBall3', path: 'Images/leftFireBall3.png', x: 100, y: 100 },
{ id: 'leftTanookiMarioTail2', path: 'Images/leftTanookiMarioTail2.png', x: 100, y: 100 },
{ id: 'pipeUpTopLeft', path: 'Images/pipeUpTopLeft.png', x: 100, y: 100 },
{ id: 'bigLuigiRightCrouchingImage', path: 'Images/bigLuigiRightCrouchingImage.png', x: 100, y: 100 },
{ id: 'catLuigiRightImage', path: 'Images/catLuigiRightImage.png', x: 100, y: 100 },
{ id: 'bigLuigiRightWalkingImage', path: 'Images/bigLuigiRightWalkingImage.png', x: 100, y: 100 },
{ id: 'goombaSquished', path: 'Images/goombaSquished.png', x: 100, y: 100 },
{ id: 'smallLuigiLeftJumpingImage', path: 'Images/smallLuigiLeftJumpingImage.png', x: 100, y: 100 },
{ id: 'catLuigiLeftWalkingImage', path: 'Images/catLuigiLeftWalkingImage.png', x: 100, y: 100 },
{ id: 'bigLuigiRightJumpingDownImage', path: 'Images/bigLuigiRightJumpingDownImage.png', x: 100, y: 100 },
{ id: 'smallMarioRightImage', path: 'Images/smallMarioRightImage.png', x: 100, y: 100 },
{ id: 'bigMarioLeftWalkingFireImage', path: 'Images/bigMarioLeftWalkingFireImage.png', x: 100, y: 100 },
{ id: 'catLuigiTail3', path: 'Images/catLuigiTail3.png', x: 100, y: 100 },
{ id: 'bigMarioRightJumpingDownImage', path: 'Images/bigMarioRightJumpingDownImage.png', x: 100, y: 100 },
{ id: 'leftFireBall1', path: 'Images/leftFireBall1.png', x: 100, y: 100 },
{ id: 'bigMarioRightCrouchingImage', path: 'Images/bigMarioRightCrouchingImage.png', x: 100, y: 100 },
{ id: 'leftTanookiMarioCrouching', path: 'Images/leftTanookiMarioCrouching.png', x: 100, y: 100 },
{ id: 'bigLuigiLeftJumpingDownImage', path: 'Images/bigLuigiLeftJumpingDownImage.png', x: 100, y: 100 },
{ id: 'goombaLeft', path: 'Images/goombaLeft.png', x: 100, y: 100 },
{ id: 'pipeDownTopLeft', path: 'Images/pipeDownTopLeft.png', x: 100, y: 100 },
{ id: 'tanookiMarioTail1', path: 'Images/tanookiMarioTail1.png', x: 100, y: 100 },
{ id: 'luigiDeadImage', path: 'Images/luigiDeadImage.png', x: 100, y: 100 },
{ id: 'catLuigiRightJumpingTail1Image', path: 'Images/catLuigiRightJumpingTail1Image.png', x: 100, y: 100 },
{ id: 'rightTanookiMarioWalking', path: 'Images/rightTanookiMarioWalking.png', x: 100, y: 100 },
{ id: 'smallLuigiRightWalkingImage', path: 'Images/smallLuigiRightWalkingImage.png', x: 100, y: 100 },
{ id: 'leftFireBall4', path: 'Images/leftFireBall4.png', x: 100, y: 100 },
{ id: 'bigMarioLeftCatImage', path: 'Images/bigMarioLeftCatImage.png', x: 100, y: 100 },
{ id: 'smallMarioPipe', path: 'Images/smallMarioPipe.png', x: 100, y: 100 },
{ id: 'bigMarioLeftJumpingDownCatImage', path: 'Images/bigMarioLeftJumpingDownCatImage.png', x: 100, y: 100 },
{ id: 'bigMarioLeftJumpingFireImage', path: 'Images/bigMarioLeftJumpingFireImage.png', x: 100, y: 100 },
{ id: 'smallMarioRightJumpingImage', path: 'Images/smallMarioRightJumpingImage.png', x: 100, y: 100 },
{ id: 'leftTanookiMarioWalking', path: 'Images/leftTanookiMarioWalking.png', x: 100, y: 100 },
{ id: 'DOWNshootingFlowerLeftUpOpen', path: 'Images/DOWNshootingFlowerLeftUpOpen.png', x: 100, y: 100 },
{ id: 'bigMarioLeftJumpingImage', path: 'Images/bigMarioLeftJumpingImage.png', x: 100, y: 100 },
{ id: 'DOWNshootingFlowerRightUpClosed', path: 'Images/DOWNshootingFlowerRightUpClosed.png', x: 100, y: 100 },
{ id: 'leftTanookiMario', path: 'Images/leftTanookiMario.png', x: 100, y: 100 },
{ id: 'smallLuigiLRightJumpingImage', path: 'Images/smallLuigiLRightJumpingImage.png', x: 100, y: 100 },
{ id: 'bigMarioLeftCrouchingFireImage', path: 'Images/bigMarioLeftCrouchingFireImage.png', x: 100, y: 100 },
{ id: 'pipeDownTopRight', path: 'Images/pipeDownTopRight.png', x: 100, y: 100 },
{ id: 'rightTanookiMarioJumpingDown', path: 'Images/rightTanookiMarioJumpingDown.png', x: 100, y: 100 },
{ id: 'smallLuigiLeftImage', path: 'Images/smallLuigiLeftImage.png', x: 100, y: 100 },
{ id: 'redTurtleSpinning3', path: 'Images/redTurtleSpinning3.png', x: 100, y: 100 },
{ id: 'bigMarioRightJumpingFireShooting1Image', path: 'Images/bigMarioRightJumpingFireShooting1Image.png', x: 100, y: 100 },
{ id: 'shootingFlowerLeftUpClosed', path: 'Images/shootingFlowerLeftUpClosed.png', x: 100, y: 100 },
{ id: 'shootingFlowerRightUpClosed', path: 'Images/shootingFlowerRightUpClosed.png', x: 100, y: 100 },
{ id: 'bigMarioLeftCrouchingCatImage', path: 'Images/bigMarioLeftCrouchingCatImage.png', x: 100, y: 100 },
{ id: 'smallLuigiPipe', path: 'Images/smallLuigiPipe.png', x: 100, y: 100 },
{ id: 'DOWNshootingFlowerRightUpOpen', path: 'Images/DOWNshootingFlowerRightUpOpen.png', x: 100, y: 100 },
{ id: 'redTurtleStandingRight', path: 'Images/redTurtleStandingRight.png', x: 100, y: 100 },
{ id: 'bigMarioRightJumpingDownFireImage', path: 'Images/bigMarioRightJumpingDownFireImage.png', x: 100, y: 100 },
{ id: 'catLuigiRightJumpingImage', path: 'Images/catLuigiRightJumpingImage.png', x: 100, y: 100 },
{ id: 'billBlasterMiddle', path: 'Images/billBlasterMiddle.png', x: 100, y: 100 },
{ id: 'redTurtleSpinning2', path: 'Images/redTurtleSpinning2.png', x: 100, y: 100 },
{ id: 'rightBulletBill', path: 'Images/rightBulletBill.png', x: 100, y: 100 },
{ id: 'bigMarioRightFireShooting1Image', path: 'Images/bigMarioRightFireShooting1Image.png', x: 100, y: 100 },
{ id: 'bigMarioRightWalkingCatImage', path: 'Images/bigMarioRightWalkingCatImage.png', x: 100, y: 100 },
{ id: 'smallMarioLeftJumpingImage', path: 'Images/smallMarioLeftJumpingImage.png', x: 100, y: 100 },
{ id: 'pipeDownMiddleRight', path: 'Images/pipeDownMiddleRight.png', x: 100, y: 100 },
{ id: 'tanookiTail3', path: 'Images/tanookiTail3.png', x: 100, y: 100 },
{ id: 'catLuigiLeftJumpingTail1Image', path: 'Images/catLuigiLeftJumpingTail1Image.png', x: 100, y: 100 },
{ id: 'rightLeafImage', path: 'Images/rightLeafImage.png', x: 100, y: 100 },
{ id: 'bigMarioRightJumpingCatTail1Image', path: 'Images/bigMarioRightJumpingCatTail1Image.png', x: 100, y: 100 },
{ id: 'redTurtleSpinning1', path: 'Images/redTurtleSpinning1.png', x: 100, y: 100 },
{ id: 'bigMarioPipe', path: 'Images/bigMarioPipe.png', x: 100, y: 100 },
{ id: 'catLuigiLeftTail2Image', path: 'Images/catLuigiLeftTail2Image.png', x: 100, y: 100 },
{ id: 'billBlasterTop', path: 'Images/billBlasterTop.png', x: 100, y: 100 },
{ id: 'bigMarioLeftJumpingCatTail1Image', path: 'Images/bigMarioLeftJumpingCatTail1Image.png', x: 100, y: 100 },
{ id: 'shootingFlowerRightUpOpen', path: 'Images/shootingFlowerRightUpOpen.png', x: 100, y: 100 },
{ id: 'catLuigiRightCrouchingImage', path: 'Images/catLuigiRightCrouchingImage.png', x: 100, y: 100 },
{ id: 'bigMarioLeftJumpingFireShooting3Image', path: 'Images/bigMarioLeftJumpingFireShooting3Image.png', x: 100, y: 100 },
{ id: 'bigMarioLeftJumpingFireShooting2Image', path: 'Images/bigMarioLeftJumpingFireShooting2Image.png', x: 100, y: 100 },
{ id: 'grassMiddleTopImage', path: 'Images/grassMiddleTopImage.png', x: 100, y: 100 },
{ id: 'DOWNshootingFlowerLeftUpClosed', path: 'Images/DOWNshootingFlowerLeftUpClosed.png', x: 100, y: 100 },
{ id: 'bigMarioLeftFireShooting2Image', path: 'Images/bigMarioLeftFireShooting2Image.png', x: 100, y: 100 },
{ id: 'leftTanookiMarioJumpTail2', path: 'Images/leftTanookiMarioJumpTail2.png', x: 100, y: 100 },
{ id: 'grassLeftTopImage', path: 'Images/grassLeftTopImage.png', x: 100, y: 100 },
{ id: 'redTurtleSpinning4', path: 'Images/redTurtleSpinning4.png', x: 100, y: 100 },
{ id: 'bigMarioLeftTail2', path: 'Images/bigMarioLeftTail2.png', x: 100, y: 100 },
{ id: 'bigMarioLeftJumpingCatImage', path: 'Images/bigMarioLeftJumpingCatImage.png', x: 100, y: 100 },
{ id: 'bigMarioLeftJumpingDownFireImage', path: 'Images/bigMarioLeftJumpingDownFireImage.png', x: 100, y: 100 },
{ id: 'catLuigiRightTail2Image', path: 'Images/catLuigiRightTail2Image.png', x: 100, y: 100 },
{ id: 'bigLuigiLeftImage', path: 'Images/bigLuigiLeftImage.png', x: 100, y: 100 },
{ id: 'catLuigiRightJumpingDownImage', path: 'Images/catLuigiRightJumpingDownImage.png', x: 100, y: 100 },
{ id: 'bigLuigiLeftJumpingImage', path: 'Images/bigLuigiLeftJumpingImage.png', x: 100, y: 100 },
{ id: 'shootingFlowerLeftDownClosed', path: 'Images/shootingFlowerLeftDownClosed.png', x: 100, y: 100 },
{ id: 'pipeUpTopRight', path: 'Images/pipeUpTopRight.png', x: 100, y: 100 },
{ id: 'bigMarioRightCatImage', path: 'Images/bigMarioRightCatImage.png', x: 100, y: 100 },
{ id: 'pipeUpMiddleRight', path: 'Images/pipeUpMiddleRight.png', x: 100, y: 100 },
{ id: 'leftTanookiMarioJumpTail1', path: 'Images/leftTanookiMarioJumpTail1.png', x: 100, y: 100 },
{ id: 'catLuigiLeftJumpingDownImage', path: 'Images/catLuigiLeftJumpingDownImage.png', x: 100, y: 100 },
{ id: 'marioDeadImage', path: 'Images/marioDeadImage.png', x: 100, y: 100 },
{ id: 'smallLuigiLeftWalkingImage', path: 'Images/smallLuigiLeftWalkingImage.png', x: 100, y: 100 },
{ id: 'catLuigiLeftJumpingImage', path: 'Images/catLuigiLeftJumpingImage.png', x: 100, y: 100 },
{ id: 'fireMarioPipe', path: 'Images/fireMarioPipe.png', x: 100, y: 100 },
{ id: 'bigMarioLeftWalkingCatImage', path: 'Images/bigMarioLeftWalkingCatImage.png', x: 100, y: 100 },
{ id: 'bigLuigiRightJumpingImage', path: 'Images/bigLuigiRightJumpingImage.png', x: 100, y: 100 },
{ id: 'pipeDownMiddleLeft', path: 'Images/pipeDownMiddleLeft.png', x: 100, y: 100 },
{ id: 'bigMarioRightTail2', path: 'Images/bigMarioRightTail2.png', x: 100, y: 100 },
{ id: 'bigMarioRightWalkingImage', path: 'Images/bigMarioRightWalkingImage.png', x: 100, y: 100 },
{ id: 'rightFireBall4', path: 'Images/rightFireBall4.png', x: 100, y: 100 },
{ id: 'DOWNshootingFlowerLeftDownOpen', path: 'Images/DOWNshootingFlowerLeftDownOpen.png', x: 100, y: 100 },
{ id: 'bigLuigiRightImage', path: 'Images/bigLuigiRightImage.png', x: 100, y: 100 },
{ id: 'redTurtleWalkingLeft', path: 'Images/redTurtleWalkingLeft.png', x: 100, y: 100 },
{ id: 'rightTanookiMarioJumpTail1', path: 'Images/rightTanookiMarioJumpTail1.png', x: 100, y: 100 },
{ id: 'grassRightTopImage', path: 'Images/grassRightTopImage.png', x: 100, y: 100 },
{ id: 'rightFireBall1', path: 'Images/rightFireBall1.png', x: 100, y: 100 },
{ id: 'smallMarioLeftImage', path: 'Images/smallMarioLeftImage.png', x: 100, y: 100 },
{ id: 'bigMarioLeftWalkingImage', path: 'Images/bigMarioLeftWalkingImage.png', x: 100, y: 100 },
{ id: 'catLuigiRightJumpingTail2Image', path: 'Images/catLuigiRightJumpingTail2Image.png', x: 100, y: 100 },
{ id: 'smallMarioRightWalkingImage', path: 'Images/smallMarioRightWalkingImage.png', x: 100, y: 100 },
{ id: 'bigMarioRightJumpingDownCatImage', path: 'Images/bigMarioRightJumpingDownCatImage.png', x: 100, y: 100 },
{ id: 'rightTanookiMario', path: 'Images/rightTanookiMario.png', x: 100, y: 100 },
{ id: 'rightFireBall3', path: 'Images/rightFireBall3.png', x: 100, y: 100 },
{ id: 'shootingFlowerRightDownOpen', path: 'Images/shootingFlowerRightDownOpen.png', x: 100, y: 100 },
{ id: 'rightTanookiMarioJumpTail2', path: 'Images/rightTanookiMarioJumpTail2.png', x: 100, y: 100 },
{ id: 'bigMarioRightJumpingCatImage', path: 'Images/bigMarioRightJumpingCatImage.png', x: 100, y: 100 },
{ id: 'smallLuigiRightImage', path: 'Images/smallLuigiRightImage.png', x: 100, y: 100 },
{ id: 'shootingFlowerLeftDownOpen', path: 'Images/shootingFlowerLeftDownOpen.png', x: 100, y: 100 },
{ id: 'bigMarioRightCrouchingFireImage', path: 'Images/bigMarioRightCrouchingFireImage.png', x: 100, y: 100 },
{ id: 'shootingFlowerRightDownClosed', path: 'Images/shootingFlowerRightDownClosed.png', x: 100, y: 100 },
{ id: 'bigMarioLeftFireImage', path: 'Images/bigMarioLeftFireImage.png', x: 100, y: 100 },
{ id: 'rightFireBall2', path: 'Images/rightFireBall2.png', x: 100, y: 100 },
{ id: 'rightTanookiMarioTail2', path: 'Images/rightTanookiMarioTail2.png', x: 100, y: 100 },
{ id: 'bigMarioRightFireImage', path: 'Images/bigMarioRightFireImage.png', x: 100, y: 100 },
{ id: 'DOWNshootingFlowerRightDownClosed', path: 'Images/DOWNshootingFlowerRightDownClosed.png', x: 100, y: 100 },
{ id: 'bigMarioTail3', path: 'Images/bigMarioTail3.png', x: 100, y: 100 },
{ id: 'leftTanookiMarioJumpingDown', path: 'Images/leftTanookiMarioJumpingDown.png', x: 100, y: 100 },
{ id: 'DOWNshootingFlowerRightDownOpen', path: 'Images/DOWNshootingFlowerRightDownOpen.png', x: 100, y: 100 },
{ id: 'mysteryBox2', path: 'Images/mysteryBox2.png', x: 100, y: 100 },
{ id: 'bigMarioLeftJumpingDownImage', path: 'Images/bigMarioLeftJumpingDownImage.png', x: 100, y: 100 },
{ id: 'bigMarioRightWalkingFireImage', path: 'Images/bigMarioRightWalkingFireImage.png', x: 100, y: 100 },
{ id: 'smallMarioLeftWalkingImage', path: 'Images/smallMarioLeftWalkingImage.png', x: 100, y: 100 },
{ id: 'goombaRight', path: 'Images/goombaRight.png', x: 100, y: 100 },
{ id: 'mysteryBox3', path: 'Images/mysteryBox3.png', x: 100, y: 100 },
{ id: 'rightTanookiMarioCrouching', path: 'Images/rightTanookiMarioCrouching.png', x: 100, y: 100 },
{ id: 'bigMarioLeftJumpingCatTail2Image', path: 'Images/bigMarioLeftJumpingCatTail2Image.png', x: 100, y: 100 },
{ id: 'catLuigiRightWalkingImage', path: 'Images/catLuigiRightWalkingImage.png', x: 100, y: 100 },
{ id: 'grassRightImage', path: 'Images/grassRightImage.png', x: 100, y: 100 },
{ id: 'rightTanookiMarioJumpingUp', path: 'Images/rightTanookiMarioJumpingUp.png', x: 100, y: 100 },
{ id: 'mysteryBox1', path: 'Images/mysteryBox1.png', x: 100, y: 100 },
{ id: 'bigMarioLeftJumpingFireShooting1Image', path: 'Images/bigMarioLeftJumpingFireShooting1Image.png', x: 100, y: 100 },
{ id: 'bigMarioRightCrouchingCatImage', path: 'Images/bigMarioRightCrouchingCatImage.png', x: 100, y: 100 },
{ id: 'grassMiddleImage', path: 'Images/grassMiddleImage.png', x: 100, y: 100 },
{ id: 'mysteryBoxFinal', path: 'Images/mysteryBoxFinal.png', x: 100, y: 100 },
{ id: 'pipeUpMiddleLeft', path: 'Images/pipeUpMiddleLeft.png', x: 100, y: 100 },
{ id: 'bigMarioLeftFireShooting1Image', path: 'Images/bigMarioLeftFireShooting1Image.png', x: 100, y: 100 },
{ id: 'bigMarioTail1', path: 'Images/bigMarioTail1.png', x: 100, y: 100 },
{ id: 'tanookiPowerUp', path: 'Images/tanookiPowerUp.png', x: 100, y: 100 },
{ id: 'bigMarioLeftImage', path: 'Images/bigMarioLeftImage.png', x: 100, y: 100 },
{ id: 'fireFlowerImage', path: 'Images/fireFlowerImage.png', x: 100, y: 100 },
{ id: 'bigMarioRightFireShooting2Image', path: 'Images/bigMarioRightFireShooting2Image.png', x: 100, y: 100 },
{ id: 'mysteryBox4', path: 'Images/mysteryBox4.png', x: 100, y: 100 },
{ id: 'leftLeafImage', path: 'Images/leftLeafImage.png', x: 100, y: 100 },
{ id: 'bigLuigiLeftWalkingImage', path: 'Images/bigLuigiLeftWalkingImage.png', x: 100, y: 100 },
{ id: 'catLuigiLeftJumpingTail2Image', path: 'Images/catLuigiLeftJumpingTail2Image.png', x: 100, y: 100 },
{ id: 'leftBulletBill', path: 'Images/leftBulletBill.png', x: 100, y: 100 },
{ id: 'catLuigiLeftCrouchingImage', path: 'Images/catLuigiLeftCrouchingImage.png', x: 100, y: 100 },
{ id: 'bigMarioRightJumpingCatTail2Image', path: 'Images/bigMarioRightJumpingCatTail2Image.png', x: 100, y: 100 },
{ id: 'redTurtleWalkingRight', path: 'Images/redTurtleWalkingRight.png', x: 100, y: 100 },
{ id: 'shootingFlowerLeftUpOpen', path: 'Images/shootingFlowerLeftUpOpen.png', x: 100, y: 100 },
{ id: 'bigMarioRightJumpingFireShooting3Image', path: 'Images/bigMarioRightJumpingFireShooting3Image.png', x: 100, y: 100 },
{ id: 'bigMarioRightJumpingFireShooting2Image', path: 'Images/bigMarioRightJumpingFireShooting2Image.png', x: 100, y: 100 },
{ id: 'billBlasterBottom', path: 'Images/billBlasterBottom.png', x: 100, y: 100 },
{ id: 'DOWNshootingFlowerLeftDownClosed', path: 'Images/DOWNshootingFlowerLeftDownClosed.png', x: 100, y: 100 },
{ id: 'bigMarioRightJumpingFireImage', path: 'Images/bigMarioRightJumpingFireImage.png', x: 100, y: 100 },
{ id: 'mushroomImage', path: 'Images/mushroomImage.png', x: 100, y: 100 },
{ id: 'catLuigiLeftImage', path: 'Images/catLuigiLeftImage.png', x: 100, y: 100 },
{ id: 'coin1', path: 'Images/coin1.png', x: 100, y: 100 },
{ id: 'coin2', path: 'Images/coin2.png', x: 100, y: 100 },
{ id: 'coin3', path: 'Images/coin3.png', x: 100, y: 100 },


];

const gameContainer = document.getElementById('game-container');

// Dynamically generate CSS rules for each image
const cssStyles = imageList.map(image => `#${image.id} { background-image: url('${image.path}'); }`);
const dynamicStyle = document.createElement('style');
dynamicStyle.textContent = cssStyles.join('\n');
document.head.appendChild(dynamicStyle);

// Create image elements in the game container
const images = imageList.map(image => {
  const { id, path, x, y} = image;
  const imageElement = document.createElement('div');
  imageElement.id = id;
  imageElement.className = 'game-image';
    imageElement.style.top = `${y}px`; // Set initial vertical position
    imageElement.style.left = `${x}px`; // Set initial horizontal position
  gameContainer.appendChild(imageElement);
    
  
    // Load the image and retrieve its dimensions
  const img = new Image();
  img.onload = function() {
    imageElement.style.width = `${this.width}px`;
    imageElement.style.height = `${this.height}px`;
  };
  img.src = path;

  return imageElement;
});

//ALL 11 sounds are correctly imported and the playSound func works with all 11 sounds
// Create an object to hold references to the loaded sound files
const sounds = {};

// Define the list of sound files and their paths
const soundList = [
    { name: 'Bump', path: 'Sounds/Bump.wav' },
    { name: 'Fireball', path: 'Sounds/Fireball.wav' },
    { name: 'Kick', path: 'Sounds/Kick.wav' },
    { name: 'Pipe', path: 'Sounds/Pipe.wav' },
    { name: 'Squish', path: 'Sounds/Squish.wav' },
    { name: 'tail', path: 'Sounds/tail.wav' },
    { name: 'Death', path: 'Sounds/Death.wav' },
    { name: 'ItemBox', path: 'Sounds/ItemBox.wav' },
    { name: 'MarioJump', path: 'Sounds/MarioJump.wav' },
    { name: 'Powerup', path: 'Sounds/Powerup.wav' },
    { name: 'Transformation', path: 'Sounds/Transformation.wav' },
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



//BUTTON IS ONLY TO TEST sending messages
// Get a reference to the play button
const sendMessageButton = document.getElementById('send-message');
// Add a click event listener to the play button
sendMessageButton.addEventListener('click', () => {
    // Example usage
    //everytime button is clicked, message is sent to server
    const data = { imageName: 'bigLuigiSomething', otherData: 30 };
    sendJsonMessage(data);
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
  console.log('Received message from server:', message);
    
    //message could be id of image and new x, y positions to move this image
  //message could be sound to play etc
    // Parse the message received from the backend
  /*UNCOMMENT TO MOVE IMAGES USING WEBSOCKET MESSAGE
    const { id, x, y } = JSON.parse(message);

  // Update the position of the specified image element
  const image = images.find(img => img.id === id);
  if (image) {
    image.style.top = `${y}px`;
    image.style.left = `${x}px`;
  }
    */
};

// Additional event listeners
socket.onopen = function(event) {
  console.log('WebSocket connection opened');
  // Perform actions specific to when the connection is open
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
    console.log('Sending message to server...');
    socket.send(message);
    
  } else {
    console.log('WebSocket connection is not open.');
  }
}


