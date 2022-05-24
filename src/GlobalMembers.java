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
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * This abstract {@code GlobalMembers} class contains all the common
 * implementation for which we will reuse across all the different classes
 * we have in our project. It is an abstract class and cannot be instantiated
 *
 * @author T. Kucherera
 * @author Chris Mukadi
 * @author Helao Hangula
 * @author Tjihimise Kaunatjike
 * @author T. Bvocho
 * @author Linus Amukuhu
 * @author Daphine Gondo
 * @version 1.3
 * @since 2022-04-01
 */

public abstract class GlobalMembers
	implements ICC
{
	
	/**
	 * We use the <em>Scanner<em> to take in user input from the terminal
	 */
	protected static Scanner scanner = new Scanner(System.in);
	
	/**
	 * We declare this interface which will be initialised with any class
	 * that implements it to be able to access operation that retrieve or
	 * update records of our applications database. 
	 */
	protected static IDataAccessObject dbUtil;
	
	/**
	 * The Member class defines all attributes that a member should have and
	 * here we create a {@code currentMember} object of the member class so
	 * that each time the a member successfully logs in the system, all the
	 * members attributes will be retrieve from the database and stored in
	 * the {@code currentMember} object for instant reference be it needed. 
	 */
	protected static Member currentMember = new Member();
	
	/**
	 * The {@code Librarian} class defines all attributes that a Librarian has
	 * and here we create a {@code currentLibrarian} object of the Librarian 
	 * class so that when the Librarian tries to log in, first the valid
	 * librarians attributes are loaded in this object including credentials
	 * and these credentials are <em>compared</em> to those that the user enters to
	 * see if they are the real Librarian or not.
	 */
	protected static Librarian currentLibrarian = new Librarian();
	
	/**
	 * This map is sorted according to the natural ordering of its keys and is
	 * Intended to be used to store key-value pairs of a songs <em>id</em> in
	 * the database, along with the songs corresponding path in the systems
	 * file system. Mainly used when the user choose to play a song and a path
	 * to the song is needed by the player so it is retrieved from this map.
	 */
	protected static TreeMap<Integer, String> treeMapMusic = new TreeMap<>();
	
	/**
	 * The {@code treeMapPodcasts} object stores the id of the podcast in the
	 * database with the corresponding path to the podcast as key-value pairs,
	 * for future reference in the application.
	 */
	protected static TreeMap<Integer, String> treeMapPodcasts = new TreeMap<>();
	
	/**
	 * This is a key and its values data structure which we are using to store
	 * the Artists Name as the key, with all the songs of the artist present
	 * in the database, as the values.
	 */
	protected static GenericOneToManyMap<String, String> artistSongPair = new GenericOneToManyMap<String, String>(); 
	
	/**
	 * Each time a user logs in, all of the music lists are retrieved from the database
	 * and stored in this object of the {@code GenericMusicList} class.
	 */
	protected static GenericMusicList<String, String, String, String, String, String> lst = new GenericMusicList<>();
	
	/**
	 * The {@code choice}, string variable is used to capture the choice's of
	 * users throughout the lifetime of the application. 
	 */
	protected static String choice;
	
	/**
	 * The {@code bookSections} variable is a string array that stores all
	 * of the book sections present in the database
	 */
	protected static String[] bookSections = new String[6];    
	
	/**
	 * We are using this DecimalFormat instance to format the way we display
	 * how long a particular song is to the user.
	 */
	protected static DecimalFormat df = new DecimalFormat("00:00");
	
	/**
	 * This path variable is used to store a path to a file at any given time
	 * in the application.  
	 */
	protected static String path;
	
	/**
	 * The {@code file} variable will be initialised given a path to a given
	 * file in the system's directories. The {@code File} class is an abstract
	 * representation of file and directory pathnames. 
	 */
	protected static File file;
	
	/**
	 * This is the variable we are using to get the byte stream of a .wav file
	 * type clip(audio) so that we can play it. An audio input stream is an
	 * input stream with a specified audio format and length.
	 */
	protected static AudioInputStream audioStream;
	
	/**
	 * The {@code clip} is the actual audio file with which in our case the wav
	 * music files we are using to play music The {@code Clip} interface 
	 * represents a special kind of data line whose <em>audio data</em> can be
	 * loaded prior to play-back, instead of being streamed in real time.
	 */
	protected static Clip clip;
	
	/**
	 * The {@codemusicPlayer method} is in literal terms a <em>music player</em>
	 * with which we are parsing in a clip object which will get the input stream
	 * of the audio file and then operate on the clip once it is open through
	 * some of the methods like {@code start()} and {@code stop()} of the Clip
	 * interface.
	 * 
	 * @param clip is a music file whose audio data can be loaded prior to
	 * 		  play-back.
	 * @throws IOException 
	 * @throws UnsupportedAudioFileException 
	 * @throws LineUnavailableException 
	 */
	protected static final void musicPlayer(Clip clip)
			throws UnsupportedAudioFileException, IOException, LineUnavailableException 
	{
		file = new File(path);
		audioStream = AudioSystem.getAudioInputStream(file);
		clip = AudioSystem.getClip();
		clip.open(audioStream);
		String responce = "";
		String status = "";

		while(!responce.equals("q")) {
			cls();
			System.out.println("\n\t\t\t\t"+BLUE+"P = play, S = stop, R = Reset, Q = Quit, B = Background"+R);
			status = (clip.isRunning()) ? "Playing" : "Stopped";
			System.out.println("\t\t\t\tStatus: " + status);
			System.out.print("\n\t\t\t\t"+BU+"Input:"+R+" "+BLUE);
			responce = scanner.nextLine();
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
			case "B": Console.musicSessions();
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
	
	/**
	 * This is a method with implementation that will cause the next output to
	 * be displayed on a fresh screen in on the Command terminal. So every other
	 * output lines printed on the terminal before this method is called, is
	 * wiped off.
	 */
	protected static void cls() {
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
	 * This method causes the currently executing thread to sleep or temporarily
	 * cease execution for the specified number of milliseconds parsed in
	 * 
	 * @param millisec are the length of time to sleep in milliseconds
	 */
	protected static void sleep(int millisec) {
		try {
			Thread.sleep(millisec);
		} catch (InterruptedException e) {
			System.out.println(e);
		}
	}

	/**
	 * This method is usually used to display a general purpose message as
	 * a result of an {@link InvalidChoiceException} exception being thrown
	 * 
	 * <p>The message displayed is not tied to that of the {@link InvalidChoiceException}
	 * but any other <em>Exceptions</em> detail message.
	 * 
	 * @param e the exception for which to get the detail message.
	 */
	protected static void invalidChoice(Exception e) {
		char[] dts = {'.','.','.'};
		cls();
		System.out.println(RED_BOLD_BRIGHT+"\n\t\t\t\t"+ e.getMessage()+R);
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
	 * When an error occurs with the database being the problem, this is a
	 * general purpose method we are using to display to the user that a
	 * server error occurred and that they will be redirected to the previous
	 * menu. 
	 */
	protected static void serverError() {
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
	
	/*
	 * Prints three dots in 1100millisecond intervals as a way to resemble loading
	 */
	protected static void loading() {
		char[] dts = {'.','.','.'};
        for (int i = 0; i < dts.length; ++i) {
            System.out.print(dts[i]);
            sleep(1100);
        }
	}
	
	/*
	 * The purpose of these variables is to simplify the way we include tabs
	 * in our output messages
	 */
	static String t4 = "\t\t\t\t"; 
	static String nt = "\n\t\t\t\t"; 
	static String t5 = "\t\t\t\t\t"; 
	static String nt5 = "\n\t\t\t\t\t";
	
	////////////////////////////////////////////////////////////////////////////////////////////
	// These are all variables necessary for establishing a database connection and filing an 
	// database query in the form of SQL statement or stored procedures.
	//////////////////////////////////////////////////////////////////////////////////////////
	String query;
	Connection connection;
	Statement statement;
	ResultSet rs;
	String userFeedback;
	CallableStatement callableStatement;
	PreparedStatement ps;
	String determiner;
}