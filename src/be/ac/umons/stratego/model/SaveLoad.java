package be.ac.umons.stratego.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import be.ac.umons.stratego.view.GameView;

public class SaveLoad {

	public void save(GameView game) throws IOException {
		try {
			FileOutputStream fos = new FileOutputStream("data.txt");
			ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(game);
			os.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public GameView load() throws IOException, ClassNotFoundException {
		FileInputStream fis;

		fis = new FileInputStream("data.txt");
		ObjectInputStream is = new ObjectInputStream(fis);

		return (GameView) is.readObject();
	}
}
