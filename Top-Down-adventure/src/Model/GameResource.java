package Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Wrapper class providing static methods for writing and reading saveGame to file.
 * @author TianZeHu
 *
 */
public class GameResource {
	
/**
 *  saveGame the current situations as well as relevant elements into the file with given name
 * @param serl - Serializable class that need to store in the file for saving game
 * @param fname - name and path of the file storing the information of saved game.
 * @throws IOException
 */
	public static void saveGame(Serializable serl, String fname) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(fname)));
		oos.writeObject(serl); 
		oos.close();
	}

	/**
	 * loadGame the all the necessary information and elements of last saved game.
	 * @param fname - name and path of the file storing the information of saved game.
	 * @return
	 */
	public static Object loadGame(String fname) {
		try {
			File file = new File(fname);
			if (file.exists()) {
				 ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
				 Object ois2 = ois.readObject();
				 ois.close();
				 return ois2;
			}
		} catch (Exception e) {
            e.printStackTrace();
        }
		return null;
	}
}
