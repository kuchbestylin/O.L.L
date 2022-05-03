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
 * The {@code final Console} class contains most if not all of the presentation
 * responsibilities such as displaying choices to users and taking in their
 * choices(user input).
 * 
 * <p> This class cannot be extended nor altered from it represents a solid
 * implementation
 *
 * @author T. Kucherera
 * @version 1.3
 * @since 2022-04-01
 */

public final class Console
	extends Authentication 
{
	
	/*
	 *  
	 * 
	 * 
	 * 
	 */
    
    /**
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * @param databaseUtil
     */
    
	/**
	 * <h1>startScreen</h1>
	 * The {@code startScreen} method prints the first Display the the
	 * user should see when they launch the application.
	 * 
	 * <p> A user should specify whether they either want to sign-in, register
	 * or continue as guest. after they type their input it is stored in a
	 * {@code userInput} variable that will be used in a switch statement to
	 * determined the appropriate method to call for the choice they chose.
	 */
    public static void startScreen() {
    	System.out.println("\t\t\t\tWelcome To Open Learning, Featuring podcasts, publications and\n\t\t\t\tdozens of books from a variety of different authors gathered\n\t\t\t\there for you to read online or offline which we hope you find\n\t\t\t\tenjoyable. Feedback is welcome - feel free to proceed as guest\n\t\t\t\tor register with us at N$550 a year.\n");
        System.out.println("\n\n\t\t\t\tPress 1: Sign In");
        System.out.println("\t\t\t\tPress 2: Register as new user.");
        System.out.println("\t\t\t\tPress 3: Continue as Guest.\n");
        System.out.print("\t\t\t\tInput: ");
        String userInput = sc.nextLine();
        try {
            switch (Integer.valueOf(userInput)) {
                case 1: Console.signIn(sc);
                		break;
                case 2: Console.register(sc);
                		break;
                case 3: Console.guestPage();
                		break;
            }
        }catch (Exception exception) {
            System.out.println("\n\t\t\t\tInvalid Input!");
            Console.cls();
            Console.startScreen();
        }
    }

    /**
     * <h1 style="text-decoration:underline">guestPage</h1>
     * 
     * This {@code guestPage} method is deprecated and on its way out. Any usage
     * of this method is not advised
     */
    @Deprecated
    private static void guestPage() {
    }
    
	/**
	 * <h1>membersHomePage</h1>
	 * 
	 * The {@code membersHomePage} method displays the Home view with which the
	 * user would have had Signed into the application thereafter they can choose
	 * what activity they want to do.
	 * 
	 * <p> A user should specify whether they either want to sign-in, register
	 * or continue as guest. after they type their input it is stored in a
	 * {@code userInput} variable that will be used in a switch statement to
	 * determined the appropriate method to call for the choice they chose.</p>
	 * 
	 * @throws IllegalArgumentException Though well handled in a try and catch 
	 * statement, this <em>exception<em> is thrown when a user inputs an option
	 * thats out of the sample of those provided and also when a string is input.
	 */
    protected static void membersHomePage() {
        Console.cls();
        
        System.out.println(nt5 + "Welcome back " + currentMember.getfName());
        System.out.println(t4 + "Type \"logout\" to sign-Out\n\n\t\t\t\t"
        		+ "Select either of the following options\n\n\t\t\t\t\t"
        		+ "1 : Browse Books\n\n\t\t\t\t\t"
        		+ "2 : Listen to Podcasts\n\n\t\t\t\t\t"
        		+ "3 : Music Sessions\n\n\t\t\t\t\t"
        		+ "4 : Manage Account Information");
        System.out.print("\n\t\t\t\tEnter: ");
        String string = Console.sc.nextLine();
        Console.actions(string);
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
     * 
     * 
     * 
     * @param string
     */
    public static void actions(String string) {    
        if (string.equalsIgnoreCase("logout")) Console.startScreen();
        else {
        	Console.cls();
            try {
                Integer.parseInt(string);
                if (Integer.parseInt(string) == 1) {
                    System.out.println("\n\t\t\t\t"
                    		+ "======================================="
                    		+ "\n\t\t\t\t[Type \"#\" to return to previous section"
                    		+ "\n\n\t\t\t\tSelect a retrieval Criteria\n\t\t\t\t\t"
                    		+ "1 : Proceed to book Sections.\n\t\t\t\t\t"
                    		+ "2 : Search by keywords.\n\t\t\t\t\t"
                    		+ "3 : Search by author.\n\t\t\t\t\t"
                    		+ "4 : Search by category\n");
                    System.out.print("\t\t\t\tEnter: ");
                    String string2 = Console.sc.nextLine();
                    if (string2.equals("#")) Console.membersHomePage();
                    else if (Integer.parseInt(string2) == 1) Console.bookSections();
                    else if (
                    		Integer.parseInt(string2) == 2 |
                    		Integer.parseInt(string2) == 3 | 
                    		Integer.parseInt(string2) == 4
                    		) 
                    	Console.queryBooks(Integer.parseInt(string2));
                    else throw new Exception();
                    Console.sleep((int)1000);
                }
                else if (Integer.parseInt(string) == 2) Console.podcast();
                else if (Integer.parseInt(string) == 3) Console.musicSessions();
                else if (Integer.parseInt(string) == 4) Console.accountRealatedInfo();
            }
            catch (Exception exception) {
                System.out.println("\n\t\t\t\tOption Invalid!");
                Console.sleep((int)1000);
                Console.membersHomePage();
            }
        }
    }

	/**
	 * <h1>membersHomePage</h1>
	 * The {@code accountRealatedInfo} method displays a view with which the
	 * active user gets exposed to several options to perform actions like 
	 * returning a book or changing their password.
	 * 
	 * <p> From the time of this writing, a user can only choose of 3 options
	 * that is to:
	 * <li>
	 * 		<ul>Change their credentials</ul>
	 * 		<ul>Check on Approaching deadlines</ul>
	 * 		<ul>Return a book</ul>
	 * </li>
	 * 
	 * @throws IllegalArgumentException Though well handled in a try and catch 
	 * statement, this <em>exception<em> is thrown when a user inputs an option
	 * thats out of the sample of those provided and also when a string is input.
	 */
    protected static void accountRealatedInfo() {
        Console.cls();
        System.out.println("\n\t\t\t\t\t" + currentMember.getfName() + " " + currentMember.getsName() + "\n\t\t\t\tType \"#\" to return to previous Menu\n\n\t\t\t\tChoose any option:\n\t\t\t\t1:Change Credentials\n\t\t\t\t2:Check Deadlines\n\t\t\t\t3:Return Book");
        System.out.print("\n\n\t\t\tEnter: ");
        String string = sc.nextLine();
        if (string.equals("#")) {
        	Console.membersHomePage();
        } else {
            try {
                Integer.parseInt(string);
                switch (Integer.parseInt(string)) {
                    case 1: changeCredentials();
                    		break;
                    case 2: Console.checkDeadlines();
                    		break;
                    case 3:	Console.returnBook();
                    		break;
                    default: throw new IllegalArgumentException();
                }
            }
            catch (Exception exception) {
                Console.cls();
                System.out.println("\n\t\t\t\tInvalid option");
                Console.sleep((int)2000);
                Console.accountRealatedInfo();
            }
        }
    }

	/**
	 * <h1>returnBook</h1>
	 * The {@code returnBook} method provides the user with the presentation
	 * assets they need to return books they borrowed.
	 * 
	 * <p> First thing all books will be displayed with the help of a {@code DatabaseUtil}
	 * Class method then from there the user inputs the ISBN of the book they want to
	 * return so its passed into another {@code DatabaseUtil} class method for database
	 * updating. 
	 * 
	 * @throws Exception Though well handled in a try and catch statement, this <em>exception<em>
	 * is thrown when a user inputs an option thats out of the sample of those provided 
	 * and also when a string is input.
	 */
    public static void returnBook() {
        Console.cls();
        System.out.println("\n\t\t\t\tSelect book to return: ");
        System.out.println("\t\t\t\ttype \"exit\" to return to Main Menu");
        int n = dbUtil.printBorrowedBooks();
        switch (n) {
            case 0: {
                System.out.print("\n\n\n\t\t\t\tPress any key to exit: ");
                sc.nextLine();
                Console.accountRealatedInfo();
                break;
            }
            case 1: {
                System.out.print("\n\t\t\tEnter Books ISBN: ");
                String string = sc.nextLine();
                if (string.equalsIgnoreCase("exit")) {
                	Console.membersHomePage();
                    break;
                }
                try {
                    Integer.parseInt(string);
                    dbUtil.hasReturned(string);
                }
                catch (Exception exception) {
                    System.out.println("\n\t\t\t\tInvalid Choice");
                    Console.returnBook();
                }
                break;
            }
        }
    }

	/**
	 * <h1>returnBook</h1>
	 * 
	 * The {@code returnBook} method provides the user with the presentation
	 * assets they need to return books they borrowed.
	 * 
	 * <p> With the help of the {@code databaseUtil.printAllBooksIBorrowed()} 
	 * method, all books that a user has borrowed up to the current point, they
	 * will all be displayed and then the program will wait for user input so
	 * that it calls the mother method
	 */
    private static void checkDeadlines() {
        Console.cls();
        System.out.println("\n\t\t\t\tThese are all currently borrowed books\n");
        dbUtil.printAllBooksIBorrowed();
        System.out.print("\n\t\t\t\tPress any key to exit: ");
        sc.nextLine();
        Console.accountRealatedInfo();
    }

	/**
	 * <h1>changeCredentials</h1>
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
    private static void changeCredentials() {
        block6: {
            Console.cls();
            System.out.println("\n\t\t\t\tSelect one of the following options:");
            System.out.println("\t\t\t\tType \"back\" to return to previous Menu");
            System.out.println("\n\t\t\t\t1: Change Email.");
            System.out.println("\t\t\t\t2: Change Password.");
            System.out.print("\n\n\t\t\tEnter: ");
            String string = sc.nextLine();
            if (string.equalsIgnoreCase("back")) {
            	Console.accountRealatedInfo();
            } else {
                try {
                    int n = Integer.parseInt(string);
                    if (n == 1) {
                    	Console.changeEmail();
                        break block6;
                    }
                    if (n == 2) {
                        Console.changePassword();
                        break block6;
                    }
                    throw new IllegalArgumentException();
                }
                catch (Exception exception) {
                    Console.cls();
                    System.out.println("\n\n\t\t\t\tOption Invalid");
                    Console.sleep((int)2000);
                    Console.changeCredentials();
                }
            }
        }
    }

    /**
     * <h1>changePassword</h1>
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
    protected static void changePassword() {
        System.out.println(currentMember.getPassword());
        System.out.print("\n\n\t\t\t\tEnter Current Password: ");
        String string = sc.nextLine();
        System.out.println("2ede2c2e");
        if (string.equals(currentMember.getPassword())) {
            System.out.print("\n\n\t\t\t\t  Enter New Password: ");
            String string2 = sc.nextLine();
            System.out.print("\n\t\t\t\tConfirm New Password: ");
            String string3 = sc.nextLine();
            if (string2.equals(string3)) {
                int n = dbUtil.changePassword(string3);
                if (n == 1) {
                    Console.cls();
                    System.out.println("\n\t\t\t\tPassword successfully changed!");
                    System.out.print("\n\n\n\t\t\tPress enter to return: ");
                    sc.nextLine();
                    Console.accountRealatedInfo();
                }
            } else {
                cls();
                System.out.println("\n\t\t\t\tsorry password could not be change");
                sleep((int)2000);
                System.out.println("\n\t\t\t\tReverting Changes..");
                sleep((int)2000);
                System.out.println("\n\t\t\t\tRedirecting to Main Menu");
                sleep((int)2000);
                Console.accountRealatedInfo();
            }
        } else {
            Console.cls();
            System.out.println("\n\t\t\t\tPassword did not match!");
            Console.sleep((int)2000);
            Console.changePassword();
        }
    }

    /**
     * <h1></h1>
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
    @Deprecated
    private static void changeEmail() {
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
     * 
     * 
     * 
     * 
     * 
     * 
     */
    protected static void musicSessions() {
        System.out.println("\n\t\t\t\tWelcome to the Music Hub " + currentMember.getfName());
        System.out.println("\n\t\t\t\tPlease select any search criteria: ");
        System.out.println("\n\t\t\t\t    1 : Browse all songs");
        System.out.println("\n\t\t\t\t    2 : Search by title keywords");
        System.out.println("\n\t\t\t\t    3 : Search by artist name");
        System.out.println("\n\t\t\t\t    4 : Search by genre");
        System.out.print("\n\n\t\t\tEnter: ");
        String string = sc.nextLine();
        try {
            int n = Integer.parseInt(string);
            switch (n) {
                case 1: {
                    Console.cls();
                    dbUtil.searchSongs(1);
                    System.out.print("\n\n\t\t\tEnter key: ");
                    String string2 = sc.nextLine();
                    Integer.parseInt(string2);
                    path = "C:\\eclipse-workspace\\0.L.L\\src\\grindin.wav";
                    musicPlayer(clip);
                    break;
                }
                case 2: {
                    System.out.print("\n\t\t\t\tEnter keyword: ");
                    dbUtil.searchSongs(2, sc.nextLine());
                    System.out.print("\n\n\t\t\tEnter key: ");
                    String string3 = sc.nextLine();
                    Integer.parseInt(string3);
                    path = (String)treeMapMusic.get(Integer.parseInt(string3));
                    musicPlayer(clip);
                    break;
                }
                case 3:
                    System.out.print("\n\t\t\t\tEnter Artist Name: ");
                    dbUtil.searchSongs(3, sc.nextLine());
                    System.out.print("\n\n\t\t\tEnter key: ");
                    String string4 = sc.nextLine();
                    Integer.parseInt(string4);
                    path = (String)treeMapMusic.get(Integer.parseInt(string4));
                    musicPlayer(clip);
                    break;
                case 4: 
                    Console.cls();
                    System.out.println("\n\t\t\t\tPlease Select Any Genre");
                    System.out.println("\n\t\t\t\t   1: Gospel");
                    System.out.println("\t\t\t\t   2: HipHop");
                    System.out.println("\t\t\t\t   3: AfroHouse");
                    System.out.println("\t\t\t\t   4: Oldschool");
                    System.out.println("\t\t\t\t   5: 80s");
                    System.out.println("\t\t\t\t   6: Lo-fi");
                    System.out.print("\n\n\t\t\t\tGenre Code: ");
                    String string5 = sc.nextLine();
                    int n2 = Integer.parseInt(string5);
                    if (!(n2 == 1 | n2 == 2 | n2 == 3 | n2 == 4 | n2 == 5 | n2 == 6)) {
                        throw new IllegalArgumentException();
                    }
                    dbUtil.searchSongs(4, string5);
                    System.out.print("\n\n\t\t\tEnter key: ");
                    String string6 = sc.nextLine();
                    Integer.parseInt(string6);
                    GlobalMembers.path = (String)GlobalMembers.treeMapMusic.get(Integer.parseInt(string6));
                    musicPlayer(clip);
                    break;
                default:
                	Console.musicSessions();
            }
            Console.cls();
            System.out.println("\n\t\t\t\tWe hope you had a good time!");
            Console.sleep((int)2000);
            System.out.println("\n\t\t\t\tYou will be redirected to the home page");
            Console.sleep((int)2000);
            Console.membersHomePage();
        }
        catch (Exception exception) {
            exception.getMessage();
            exception.printStackTrace();
            invalidChoice();
            Console.musicSessions();
        }
    }

    /**
     * <h1></h1>
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
    static void queryBooks(Integer n) {
        Console.cls();
        if (n == 2) {
            System.out.print("\n\t\t\t\tEnter keyword: ");
            dbUtil.searchBooksByKeyword(n.intValue(), sc.nextLine());
        }
        if (n == 3) {
            System.out.print("\n\t\t\t\tEnter author: ");
            dbUtil.searchBooksByKeyword(n.intValue(), sc.nextLine());
        }
        if (n == 4) {
            System.out.print("\n\t\t\t\tEnter category: ");
            dbUtil.searchBooksByKeyword(n.intValue(), sc.nextLine());
        }
    }

    /**
     * <h1></h1>
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
    private static void podcast() {
        System.out.println("\n\t\t\t\tSearch by Name or Author");
        System.out.println("\t\t\t\tPress Enter to retain All");
        System.out.print("\n\n\t\t\t\tSearch: ");
        dbUtil.retrievePodcasts(sc.nextLine());
    }

    /**
     * <h1></h1>
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
    protected static void bookSections() {
        dbUtil.getBookSections();
        Console.cls();
        System.out.println("\t\t\t\tChoose Section of Interest");
        int n = 1;
//        Stack<Object> stack = new Stack<Object>();
        for (String string : Console.bookSections) {
            System.out.println("\n\t\t\t\t\t" + n++ + " : " + string);
        }
        System.out.print("\n\n\t\t\t\tEnter Section: ");
        String string = Console.sc.nextLine();
        try {
            dbUtil.allBooksInSection(Integer.valueOf(string));
        }
        catch (Exception exception) {
            System.out.println("\n\t\t\t\tInvalid Choice!");
            Console.bookSections();
        }
    }

}