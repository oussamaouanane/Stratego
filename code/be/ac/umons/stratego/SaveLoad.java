package be.ac.umons.stratego;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import be.ac.umons.stratego.model.GameProcess;

public class SaveLoad {
	
	public static void write(GameProcess game) throws FileNotFoundException, IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("saves/save.bin"));
		try {
			oos.writeObject(game);
			oos.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static GameProcess read() throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("saves/save.bin"));
		return (GameProcess) ois.readObject();
	}

}

