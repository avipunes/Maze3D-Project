package Model;

import java.io.IOException;

import View.ViewRequestType;

public interface Model {

		void moveUp();
		void moveDown();
		void moveLeft();
		void moveRight();
		void moveUpLevel();
		void moveDownLevel();
		
		int[][][] getData();

		public void Request(ViewRequestType<Object> request) throws IOException;
}
