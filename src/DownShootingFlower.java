import java.awt.Image;

import acm.graphics.GObject;

public class DownShootingFlower extends ShootingFlower{
	//downShootingFlower comes in and out of down pipes
	private static MyImage downShootingFlowerLeftDownClosedImage, downShootingFlowerLeftDownOpenImage,
	downShootingFlowerLeftUpClosedImage, downShootingFlowerLeftUpOpenImage,
	downShootingFlowerRightDownClosedImage, downShootingFlowerRightDownOpenImage,
	downShootingFlowerRightUpClosedImage, downShootingFlowerRightUpOpenImage;
	public DownShootingFlower(int timeOffset) {
		super(downShootingFlowerLeftDownClosedImage, timeOffset);
	}
	
	public Point[] getPoints() {
		return new Point[] {
				new Point(getX(), getY()+getHeight()+dy), 
				new Point(getX()+getWidth()/2, getY()+getHeight()+dy),
				new Point(getX()+getWidth(), getY()+getHeight()+dy)};
	}

	@Override
	public void lookDownClosedMouth(boolean rightOrLeft) {
		setImageAndRelocate(rightOrLeft?downShootingFlowerRightUpClosedImage:downShootingFlowerLeftUpClosedImage);
	}

	@Override
	public void lookDownOpenMouth(boolean rightOrLeft) {
		setImageAndRelocate(rightOrLeft?downShootingFlowerRightUpOpenImage:downShootingFlowerLeftUpOpenImage);
	}

	@Override
	public void lookUpClosedMouth(boolean rightOrLeft) {
		setImageAndRelocate(rightOrLeft?downShootingFlowerRightDownClosedImage:downShootingFlowerLeftDownClosedImage);
	}

	@Override
	public void lookUpOpenMouth(boolean rightOrLeft) {
		setImageAndRelocate(rightOrLeft?downShootingFlowerRightDownOpenImage:downShootingFlowerLeftDownOpenImage);
	}

	public static void setObjects(MyImage downShootingFlowerLeftDownClosedImage1, MyImage downShootingFlowerLeftDownOpenImage1,
			MyImage downShootingFlowerLeftUpClosedImage1, MyImage downShootingFlowerLeftUpOpenImage1,
			MyImage downShootingFlowerRightDownClosedImage1, MyImage downShootingFlowerRightDownOpenImage1,
			MyImage downShootingFlowerRightUpClosedImage1, MyImage downShootingFlowerRightUpOpenImage1) {
		downShootingFlowerLeftDownClosedImage = downShootingFlowerLeftDownClosedImage1; 
		downShootingFlowerLeftDownOpenImage = downShootingFlowerLeftDownOpenImage1; 
		downShootingFlowerLeftUpClosedImage = downShootingFlowerLeftUpClosedImage1;
		downShootingFlowerLeftUpOpenImage = downShootingFlowerLeftUpOpenImage1; 
		downShootingFlowerRightDownClosedImage = downShootingFlowerRightDownClosedImage1;
		downShootingFlowerRightDownOpenImage = downShootingFlowerRightDownOpenImage1;
		downShootingFlowerRightUpClosedImage = downShootingFlowerRightUpClosedImage1;
		downShootingFlowerRightUpOpenImage = downShootingFlowerRightUpOpenImage1;
	}
	
}