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



/**
 * The {@code Main} class represents the way you can launch an app with 
 * an .exe file and bundles the main method for our application.
 * 
 * <p> An instance of a {@code Main} will not be synchronized against
 * concurrent multithreaded access if that access is not read-only.
 *
 * @author T. Kucherera
 * @author Tjihimise Kaunatjike
 * @author Chris Mukadi
 * @author Linus Amukuhu
 * @author Helao Hangula
 * @author T. Bvocho
 * @version 1.0
 * @since 2022-04-01
 */
public class Main
{
	
	/**
	 * <h1>main</h1>
	 * The {@code main} method is the entry point to our application.
	 * 
	 * <p> After the terminal screen is cleared by the 
	 * {@code GlobalMembers.cls()} method, a new {@code Console} Object 
	 * is created and all the {@code Console} members dependencies are
	 * parsed in as arguments in one of its 2 argument constructor methods.
	 * 
	 * <p>Its a fundamental principle that a Class should not new up its
	 * Dependencies. Its a way to reduce coupling between the classes thus
	 * the reason why new Class objects are being parsed in as arguments
	 * when the Console Class is Instantiated. This is a feature thats
	 * recognised as {@code constructor injection} in Java.  
	 * 
	 * @param args Unused parameter.
	 * @return Nothing is returned by this method.
	 * @throws AuthenticationException
	 * @see <a>https://www.tutorialspoint.com/spring/
	 * constructor_based_dependency_injection.htm</a>
	 * 
	 */
	public static void main(String[] args) {
		
		GlobalMembers.cls(); //wipes the terminal screen
		
		Console.methodDependancyInjection(new DatabaseUtil());;
		
	}
	
}
