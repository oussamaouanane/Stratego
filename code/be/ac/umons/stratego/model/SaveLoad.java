package be.ac.umons.stratego.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import be.ac.umons.stratego.view.GameView;

public class SaveLoad {

	public static void save(GameProcess game) throws IOException {
		try {
			FileOutputStream fos = new FileOutputStream("saves/data.bin");
			ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(game);
			os.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static GameProcess load() throws IOException, ClassNotFoundException {
		FileInputStream fis;

		fis = new FileInputStream("saves/data.bin");
		ObjectInputStream is = new ObjectInputStream(fis);
		GameProcess game = (GameProcess) is.readObject();
		is.close();
		return game;
	}
}
