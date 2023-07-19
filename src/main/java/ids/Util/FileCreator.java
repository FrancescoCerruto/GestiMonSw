package ids.Util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class FileCreator {
	public static void createMansioniFile(int requiredPunteggio) {
		try {
			File myObj = new File("./src/main/resources/Mansioni.txt");
			myObj.createNewFile();
			try {
				FileWriter myWriter = new FileWriter("./src/main/resources/Mansioni.txt");
				int punteggioCreato = 0;
				while (punteggioCreato < requiredPunteggio) {
					Random r = new Random();
					char c = (char)(r.nextInt(26) + 'a');
					int punteggio = ThreadLocalRandom.current().nextInt(50, 70 + 1);
					punteggio -= punteggio % 10;
					myWriter.write(c + "\t" + Integer.toString(punteggio) + "\n");
					punteggioCreato += punteggio;
				}
				myWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
