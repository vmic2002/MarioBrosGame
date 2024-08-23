

import java.awt.Image;

import acm.graphics.GObject;

public class UpShootingFlower extends ShootingFlower{
	//upShootingFlower comes in and out of up pipes
	private static MyImage shootingFlowerLeftDownClosedImage, shootingFlowerLeftDownOpenImage,
	shootingFlowerLeftUpClosedImage, shootingFlowerLeftUpOpenImage,
	shootingFlowerRightDownClosedImage, shootingFlowerRightDownOpenImage,
	shootingFlowerRightUpClosedImage, shootingFlowerRightUpOpenImage;
	public UpShootingFlower(int timeOffset, Lobby lobby) {
		super(shootingFlowerLeftDownClosedImage, timeOffset, lobby);
	}

	public  Point[] getPoints() {
		return new Point[] {
				new Point(getX(), getY()+dy), 
				new Point(getX()+getWidth()/2, getY()+dy),
				new Point(getX()+getWidth(), getY()+dy)};
	}

	@Override
	public void lookDownClosedMouth(boolean rightOrLeft) {
		setImageAndRelocate(rightOrLeft?shootingFlowerRightDownClosedImage:shootingFlowerLeftDownClosedImage);
	}

	@Override
	public void lookDownOpenMouth(boolean rightOrLeft) {
		setImageAndRelocate(rightOrLeft?shootingFlowerRightDownOpenImage:shootingFlowerLeftDownOpenImage);
	}

	@Override
	public void lookUpClosedMouth(boolean rightOrLeft) {
		setImageAndRelocate(rightOrLeft?shootingFlowerRightUpClosedImage:shootingFlowerLeftUpClosedImage);
	}

	@Override
	public void lookUpOpenMouth(boolean rightOrLeft) {
		setImageAndRelocate(rightOrLeft?shootingFlowerRightUpOpenImage:shootingFlowerLeftUpOpenImage);
	}
	
	public static void setObjects(MyImage shootingFlowerLeftDownClosedImage1, MyImage shootingFlowerLeftDownOpenImage1,
			MyImage shootingFlowerLeftUpClosedImage1, MyImage shootingFlowerLeftUpOpenImage1,
			MyImage shootingFlowerRightDownClosedImage1, MyImage shootingFlowerRightDownOpenImage1,
			MyImage shootingFlowerRightUpClosedImage1, MyImage shootingFlowerRightUpOpenImage1) {
		shootingFlowerLeftDownClosedImage = shootingFlowerLeftDownClosedImage1; 
		shootingFlowerLeftDownOpenImage = shootingFlowerLeftDownOpenImage1; 
		shootingFlowerLeftUpClosedImage = shootingFlowerLeftUpClosedImage1;
		shootingFlowerLeftUpOpenImage = shootingFlowerLeftUpOpenImage1; 
		shootingFlowerRightDownClosedImage = shootingFlowerRightDownClosedImage1;
		shootingFlowerRightDownOpenImage = shootingFlowerRightDownOpenImage1;
		shootingFlowerRightUpClosedImage = shootingFlowerRightUpClosedImage1;
		shootingFlowerRightUpOpenImage = shootingFlowerRightUpOpenImage1;
	}
}
