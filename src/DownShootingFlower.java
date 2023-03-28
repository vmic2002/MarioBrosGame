import java.awt.Image;

import acm.graphics.GObject;

public class DownShootingFlower extends ShootingFlower{
	//downShootingFlower comes in and out of down pipes
	private static Image downShootingFlowerLeftDownClosedImage, downShootingFlowerLeftDownOpenImage,
	downShootingFlowerLeftUpClosedImage, downShootingFlowerLeftUpOpenImage,
	downShootingFlowerRightDownClosedImage, downShootingFlowerRightDownOpenImage,
	downShootingFlowerRightUpClosedImage, downShootingFlowerRightUpOpenImage;
	public DownShootingFlower() {
		super(downShootingFlowerLeftDownClosedImage);
	}
	public Point[] getPoints() {
		return new Point[] {
				new Point(getX(), getY()+getHeight()+dy), 
				new Point(getX()+getWidth()/2, getY()+getHeight()+dy),
				new Point(getX()+getWidth(), getY()+getHeight()+dy)};
	}

	public static void setObjects(Image downShootingFlowerLeftDownClosedImage1, Image downShootingFlowerLeftDownOpenImage1,
			Image downShootingFlowerLeftUpClosedImage1, Image downShootingFlowerLeftUpOpenImage1,
			Image downShootingFlowerRightDownClosedImage1, Image downShootingFlowerRightDownOpenImage1,
			Image downShootingFlowerRightUpClosedImage1, Image downShootingFlowerRightUpOpenImage1) {
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