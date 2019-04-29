package Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GameResource {
	public static void saveGame(Serializable serl, String fname) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(fname)));
	}
	
	public static Object loadGame(String fname) {
		try {
			File file = new File(fname);
			if (file.exists()) {
				 ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
				 return ois.readObject();
			}
		} catch (Exception e) {
            e.printStackTrace();
        }
		return null;
	}
}
