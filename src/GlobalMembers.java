/*
 * Copyright (c) 2022 NUST and/or its affiliates. All rights reserved.
 * NUST PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * @author Takudzwa Kucherera
 *
 */
public abstract class GlobalMembers implements CC{
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	public static IDataAccessObject dbUtil;
	
	/**
	 * 
	 * 
	 * 
	 * 
	 */
	static Member currentMember = new Member();
	
	/**
	 * 
	 * 
	 * 
	 * 
	 */
	static Librarian currentLibrarian = new Librarian();
	
	/**
	 * 
	 * 
	 * 
	 * 
	 */
	GenericMusicList<String, String, String, String, String, String> lst = new GenericMusicList<>();
	
	/**
	 * 
	 * 
	 * 
	 * 
	 */
	static TreeMap<Integer, String> treeMapMusic = new TreeMap<>();
	
	/**
	 * 
	 * 
	 * 
	 * 
	 */
	TreeMap<Integer, String> treeMapPodcasts = new TreeMap<>();
	
	/**
	 * 
	 * 
	 * 
	 * 
	 */
	static String choice;
	
	/**
	 * 
	 * 
	 * 
	 * 
	 */
	static String t4 = "\t\t\t\t"; 
	static String nt = "\n\t\t\t\t"; 
	static String t5 = "\t\t\t\t\t"; 
	static String nt5 = "\n\t\t\t\t\t"; 
//	Stack<String> bookSections = new Stack<String>();
	
	/**
	 * 
	 * 
	 * 
	 * 
	 */
	static String[] bookSections = new String[6];
	
	/**
	 * 
	 * 
	 * 
	 * 
	 */
	oneToManyMap<String, String> artistSongPair = new oneToManyMap<String, String>();
	
	/**
	 * 
	 * 
	 * 
	 * 
	 */
	oneToManyMap<Integer, String> idSongPair = new oneToManyMap<Integer, String>();
	
	/**
	 * 
	 * 
	 * 
	 * 
	 */
	static Scanner sc = new Scanner(System.in);
	
	/**
	 * 
	 * 
	 * 
	 * 
	 */
	static DecimalFormat df = new DecimalFormat("00:00");
	
	/**
	 * 
	 * 
	 * 
	 * 
	 */
	static String path;
	
	/**
	 * 
	 * 
	 * 
	 * 
	 */
	static File file;
	
	/**
	 * 
	 * 
	 * 
	 * 
	 */
	static AudioInputStream audioStream;
	
	/**
	 * 
	 * 
	 * 
	 * 
	 */
	static Clip clip;
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	static void cls() {
	    try{
	        String operatingSystem = System.getProperty("os.name"); //Check the current operating system
	        if(operatingSystem.contains("Windows")){
	        	ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
	        	Process startProcess = pb.inheritIO().start();
	        	startProcess.waitFor();
	        }else { 
	        	ProcessBuilder pb = new ProcessBuilder("clear");
	        	Process startProcess = pb.inheritIO().start(); 
	        	startProcess.waitFor();
	        	} 
	    }catch (Exception e) {
	    	System.out.println(e);
	    }
	}

	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	static void sleep(int seconds) {
		try {
			Thread.sleep(seconds);
		} catch (InterruptedException e) {
			System.out.println(e);
		}
	}

	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	public static void invalidChoice() {
		char[] dts = {'.','.','.'};
		cls();
		System.out.println(RED_BOLD_BRIGHT+"\n\t\t\t\tInvalid Choice!!!"+R);
		sleep(2000);
		System.out.print(BLACK_BOLD_BRIGHT+"\n\t\t\t\tReturning to previous Menu");
		sleep(200);
        for (int i = 0; i < dts.length; ++i) {
            System.out.print(dts[i]);
            sleep(1100);
        }
        System.out.println(R);
	}
	public static void loading() {
		char[] dts = {'.','.','.'};
        for (int i = 0; i < dts.length; ++i) {
            System.out.print(dts[i]);
            sleep(1100);
        }
	}
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	public void serverError() {
		cls();
		char[] dts = {'.','.','.'};
		System.out.println("\n\t\t\t\t"+RED_BOLD_BRIGHT+"Server error occured!"+R);
		sleep(2000);
		System.out.print(BLACK_BOLD_BRIGHT+"\n\t\t\t\tReturning to previous Menu");
		sleep(200);
        for (int i = 0; i < dts.length; ++i) {
            System.out.print(dts[i]);
            sleep(1100);
        }
        System.out.println(R);
	}
	/**
	 * @throws IOException 
	 * @throws UnsupportedAudioFileException 
	 * @throws LineUnavailableException 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	public static final void musicPlayer(Clip clip)
			throws UnsupportedAudioFileException, IOException, LineUnavailableException 
	{
		file = new File(path);
		audioStream = AudioSystem.getAudioInputStream(file);
		clip = AudioSystem.getClip();
		clip.open(audioStream);
		String responce = "";

		while(!responce.equals("q")) {
			cls();
			System.out.println("\n\t\t\t\t"+BLUE+"P = play, S = stop, R = Reset, Q = Quit"+R);
			System.out.print("\n\t\t\t\t"+BU+"Input:"+R+" "+BLUE);
			responce = sc.nextLine();
			System.out.println(R);
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