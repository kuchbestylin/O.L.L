import java.io.File;
import java.text.DecimalFormat;
import java.util.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class GlobalMembers{
	static Member currentMember = new Member();
	static Librarian currentLibrarian = new Librarian();
	GenericMusicList<String, String, String, String, String, String> lst = new GenericMusicList<>();
	static TreeMap<Integer, String> treeMapMusic = new TreeMap<>();
	TreeMap<Integer, String> treeMapPodcasts = new TreeMap<>();
	String choice;
//	Stack<String> bookSections = new Stack<String>();
	static String[] bookSections = new String[6];
	oneToManyMap<String, String> artistSongPair = new oneToManyMap<String, String>();
	oneToManyMap<Integer, String> idSongPair = new oneToManyMap<Integer, String>();
	static Scanner sc = new Scanner(System.in);
	static DecimalFormat df = new DecimalFormat("00:00");
	static String path;
	static File file;
	static AudioInputStream audioStream;
	static Clip clip;
	static void cls() {
	    try{
	        String operatingSystem = System.getProperty("os.name"); //Check the current operating system
	        if(operatingSystem.contains("Windows")){ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls"); Process startProcess = pb.inheritIO().start();startProcess.waitFor();
	        } else { ProcessBuilder pb = new ProcessBuilder("clear");   Process startProcess = pb.inheritIO().start(); startProcess.waitFor();} 
	    }catch(Exception e){System.out.println(e);  }
	}

	static void sleep(int seconds) {
					//Makes the console wait (in seconds) until the next statement can be executed
					try {
						Thread.sleep(seconds);
					} catch (InterruptedException e) {
						System.out.println(e);
						//e.printStackTrace();
						}
		}

	public static void invalidChoice() {
		cls();
		System.out.println("\n\t\t\t\tYou have Selected an Invalid Choice");
		sleep(2000);
		System.out.println("\n\t\t\t\tReturning to previous Menu");
		sleep(2000);
	}
	public void serverError() {
		cls();
		System.out.println("\n\t\t\t\tServer error occured!");
		sleep(2000);
		System.out.println("\n\t\t\t\tRedirecting to previous Menu");
		sleep(2000);
	}
	public static void musicPlayer(Clip clip) throws Exception {
		file = new File(path);
		audioStream = AudioSystem.getAudioInputStream(file);
		clip = AudioSystem.getClip();
		clip.open(audioStream);
		String responce = "";

		while(!responce.equals("q")) {
			cls();
			System.out.println("\n\t\t\t\tP = play, S = stop, R = Reset, Q = Quit");
			System.out.print("\n\t\t\t\tInput: ");
			responce = sc.nextLine();
			responce.toLowerCase();
			switch (responce) {
			case "p": clip.start();
				break;
			case "s": clip.stop();
				break;
			case "r": clip.setMicrosecondPosition(0);
				break;
			case "q": clip.close();
				break;
			case "": 
				if(clip.isRunning()) clip.stop();
				else clip.start();
			break;
			default:
				break;
			}
		}

	}
}