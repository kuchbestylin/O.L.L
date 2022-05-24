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

import java.util.Scanner;

/**
 * This {@code Authentication} provides implementation that makes it posible
 * for members to register for the library services and also an authentication
 * mechanism for returning users be it the Librarian or Member.
 * 
 * <p> This method cannot be instantiated but extended only for it represents a
 * an concept that is only intended to be reused.</p>
 *
 * @author T. Kucherera
 * @author Chris Mukadi
 * @author Helao Hangula
 * @author Tjihimise Kaunatjike
 * @author T. Bvocho
 * @author Linus Amukuhu
 * @version 1.3
 * @since 2022-04-01
 */

public abstract class Authentication
	extends GlobalMembers 
{
	
	/**
	 * <h1>register</h1>
	 * This method is made solely for the registration of members. If a
	 * member is new and since the guest page functionality is deprecated,
	 * a member will have to register first.
	 * 
	 * <p>When this method is called, the {@code Member.attributeCollection(sc)}
	 * method which takes in a scanner object, is called and so all the members
	 * details are collected. After all the members details are validly captured,
	 * they are are then parsed to the <em>dbUtil</em> object of the 
	 * {@code DataAccessLogic} class to create the new member in the database.
	 * 
	 * @param sc a scanner object for reading input from the user
	 */
	protected final static void register(Scanner sc){
		cls();
		Member.attributeCollection(sc);
		dbUtil.registration(currentMember);
	}

	/**
	 * <h1>signIn</h1>
	 * This method is a template for collecting a persons email and password for 
	 * validation reused for both the members and the librarian.
	 * 
	 * <p>When the user enter's their email, it is then stored in the {@code email}
	 * attribute of the {@code currentMember} object. Likewise the password gets
	 * stored in the {@code password} attribute of the {@code currentMember}'s
	 * object. The {@code dbUtil.verifyCredentials} method of the IDataAccessObject
	 * Interface is then called parsing in the email, password of the current
	 * member as well as the currentMember object.
	 * 
	 * @param sc a scanner object for reading input from the user
	 */
	protected final static void SIGN_IN_MEMBER(Scanner sc) {
		cls();
		System.out.println( WHITE_UNDERLINED + WHITE_BOLD_BRIGHT+"\n\t\t\t\t\t\tLog In" + R);
		System.out.print("\n\n\t\t\t\tEnter Email: " + PURPLE_BOLD_BRIGHT);
		currentMember.setEmail(sc.nextLine());
		System.out.println(R);
		System.out.print("\n\t\t\t\tEnter Password: " + WHITE_BACKGROUND);
		currentMember.setPassword(sc.nextLine());
		System.out.println(R);
		dbUtil.verifyCredentials(currentMember.getEmail(), currentMember.getPassword(), currentMember);
		sleep(2900);
	}

	/**
	 * <h1>SIGN_IN_LIBRARIAN</h1>
	 * This method is used to validate the Librarians credentials before log in.
	 * 
	 * <p>Before this method is called, all the valid attributes of the Librarian would
	 * have been loaded in the {@code currentLibrarian} object of the Librarian class.
	 * So when the Librarian enters the email and password, they are validated against
	 * the correct values using get methods of the {@code currentLibrarian} object. If
	 * the values entered all match the true values, a flag attribute is set to 1 and
	 * returned.
	 * 
	 * @param sc
	 * @return flag is to give the calling method a signal if the credentials are correct
	 * or not.
	 */
	protected final static int SIGN_IN_LIBRARIAN(Scanner sc) {
		int flag = 0;
		cls();
		System.out.println( WHITE_UNDERLINED + WHITE_BOLD_BRIGHT+"\n\t\t\t\t\t\tLog In" + R);
		System.out.print("\n\n\t\t\t\tEnter Email: " + PURPLE_BOLD_BRIGHT);
		String email = sc.nextLine();
		System.out.println(R);
		System.out.print("\n\t\t\t\tEnter Password: " + WHITE_BACKGROUND);
		String password = sc.nextLine();
		System.out.println(R);
		if(email.equalsIgnoreCase(currentLibrarian.getEmail()) && password.equals(currentLibrarian.getPassword()))
			flag = 1;
		return flag;
	}
	


}