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

import java.io.IOException;

/**
 * The final {@code Console} class contains most if not all of the presentation
 * responsibilities specifically for he user such as displaying choices to users
 * and taking in their choices(user input).
 * 
 * <p> This class cannot be extended nor altered for it represents a solid
 * implementation(Concept) that should always be in a single place. All methods
 * are final</p>
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

public final class Console
	extends Authentication 
{

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
    	System.out.println( WHITE_BOLD_BRIGHT+"\n\n\t\t\t\tWelcome To Open Learning, Featuring podcasts,"
    			+ " publications and\n\t\t\t\tdozens of books from a variety of "
    			+ "different authors gathered\n\t\t\t\there for you to read online "
    			+ "or offline which we hope you find\n\t\t\t\tenjoyable. Feedback "
    			+ "is welcome - feel free to proceed as guest\n\t\t\t\tor register "
    			+ "with us at N$550 a year.\n" + R);
        System.out.println("\n\t\t\t\tPress 1: Sign In");
        System.out.println("\t\t\t\tPress 2: Register as new user.");
        System.out.println("\t\t\t\tPress 3: Continue as Guest.\n");
        System.out.print("\t\t\t\tInput: " + PURPLE_BRIGHT);
        String userInput = scanner.nextLine();
        System.out.print(R);
        try {
        	if(userInput.equalsIgnoreCase("I am admin")) ConfidentialViews.start();
        	else
            switch (Integer.valueOf(userInput)) {
                case 1: Authentication.SIGN_IN_MEMBER(scanner);
                		break;
                case 2: Authentication.register(scanner);
                		Authentication.SIGN_IN_MEMBER(scanner);
                		break;
                case 3: Console.guestPage();
                		break;
                default : throw new InvalidChoiceException();
            }
        }catch (Exception exception) {
            invalidChoice(exception);
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
	 * 
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
        
        System.out.println(BU +"\n\t\t\t\t\tWelcome back " + currentMember.getFirstName()+"!" + R + BLACK_BRIGHT); 
        System.out.println(t4 + "    Type "+BLUE_BOLD_BRIGHT+"\"logout\""+R+BLACK_BRIGHT+" to sign-Out\n\n\t\t\t\t"
        		+ "Select either of the following options\n\t\t\t\t"
        		+ "=========================================\n\n\t\t\t\t" + R
        		+ "    1: Press \"1\" to browse Books\n\n\t\t\t\t"
        		+ "    2: Press \"2\" to listen to Podcasts\n\n\t\t\t\t"
        		+ "    3: Press \"3\" for music Sessions\n\n\t\t\t\t"
        		+ "    4: Press \"4\" for Account Detail\n\n\t\t\t"
        		+ "	    5: Press \"5\" to enter Live Chat");
        System.out.println(BLACK_BRIGHT+"\n\t\t\t\t========================================="+R);
        System.out.print(  BU+ "\n\t\t\t\tEnter:" + R+ " " + BLUE);
        choice = Console.scanner.nextLine();
        System.out.print(R);
        Console.routeActions(choice);
        
    }
    
    /**
     * <h1>routeActions</h1>
     * 
     * this method takes in a String which would be the choice that the user had
     * chosen previously between things that have to do with books, podcast's,
     * music or account detail and then further do some routing, that is calling
     * the right method to the actions the user wants to perform.
     * 
     * <p>apart from the choice parsed in as the string parameter, if the user
     * had previously chose to go with books, they will be further prompted to
     * choose if they want all books to be displayed or if they want to search
     * by any criteria that could be the title of the book or the author of the 
     * book</p>
     * 
     * <p>For the rest of the code it is just basic routing logic to call the
     * appropriate method for the choice that the user took.</p>
     * 
     * 
     * @param chosenAction this is the parameter of the previously selected choice in 
     * the {@code membersHomePage} method
     * 
	 * @throws IllegalArgumentException Though well handled in a try and catch 
	 * statement, this <em>exception<em> is thrown when a user inputs an option
	 * thats out of the sample of those provided and also when a string is input.
     */
    public static void routeActions(String chosenAction) {    
        if (chosenAction.equalsIgnoreCase("logout")) {
        	cls();
        	Console.startScreen();
        }
        else {
        	Console.cls();
            try {
                Integer.parseInt(chosenAction);
                if (Integer.parseInt(chosenAction) == 1) {
                    System.out.println("\n\t\t\t\t"
                    		+ "======================================="
                    		+ "\n\t\t\t\t"+"Type "+BLUE+"\"#\""+R+" to return to previous section"+R
                    		+ "\n\n\t\t\t\t"+BU+"Select a retrieval Criteria\n\n\t\t\t\t"+R
                    		+ "1: Press \"1\" to proceed to book Sections.\n\n\t\t\t\t"
                    		+ "2: Press \"2\" to search by keywords.\n\n\t\t\t\t"
                    		+ "3: Press \"3\" to search by author.\n\n\t\t\t\t"
                    		+ "4: Press \"4\" to search by category\n");
                    System.out.print(BU+"\t\t\t\tEnter:"+R+" "+BLUE_BOLD_BRIGHT);
                    String string2 = Console.scanner.nextLine();
                    System.out.println(R);
                    if (string2.equals("#")) Console.membersHomePage();
                    else if (Integer.parseInt(string2) == 1) Console.bookSections();
                    else if (
                    		Integer.parseInt(string2) == 2 |
                    		Integer.parseInt(string2) == 3 | 
                    		Integer.parseInt(string2) == 4
                    		) 
                    	Console.queryBooks(Integer.parseInt(string2));
                    else throw new InvalidChoiceException();
                    Console.sleep((int)1000);
                }
                else if (Integer.parseInt(chosenAction) == 2) Console.podcasts();
                else if (Integer.parseInt(chosenAction) == 3) Console.musicSessions();
                else if (Integer.parseInt(chosenAction) == 4) Console.accountRealatedInfo();
                else if (Integer.parseInt(chosenAction) == 5) {
                	currentMember.liveChart();
                	Console.membersHomePage();
                }
            }
            catch (Exception e) {
                invalidChoice(e);
                Console.membersHomePage();
            }
        }
    }

	/**
	 * <h1>membersHomePage</h1>
	 * 
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
	 * </li></p>
	 * 
	 * @throws IllegalArgumentException Though well handled in a try and catch 
	 * statement, this <em>exception<em> is thrown when a user inputs an option
	 * thats out of the sample of those provided and also when a string is input.
	 */
    protected static void accountRealatedInfo() {
        Console.cls();
        System.out.println("\n\t\t\t\t\t  " + currentMember.getFirstName() + " " +
        		currentMember.getSurname() + "\n\n\t\t\t\t"+BLACK_BOLD_BRIGHT+"Type"
        		+ "  "+BLUE+"\"#\""+R+BLACK_BOLD_BRIGHT+" to return to previous"
        		+ " Menu"+R+"\n\n\t\t\t\t"+BU+"Choose any option:"+R+"\n\n\t\t\t\t"
        		+ "    Type \"1\" to Change Credentials\n\n\t\t\t\t    Type \"2\" to Check Deadlines\n\n\t\t\t\t"
        		+ "    Type \"3\" to Return Book");
        System.out.print("\n\t\t\t\t"+BU+"Enter:"+R+" "+BLUE);
        String string = scanner.nextLine();
        System.out.println(R);
        if (string.equals("#")) Console.membersHomePage();
        else {
            try {
                Integer.parseInt(string);
                switch (Integer.parseInt(string)) {
                    case 1: changeCredentials();
                    		break;
                    case 2: Console.checkDeadlines();
                    		break;
                    case 3:	Console.returnBook();
                    		break;
                    default: throw new InvalidChoiceException();
                }
            }
            catch (Exception e) {
            	invalidChoice(e);
                Console.accountRealatedInfo();
            }
        }
    }

	/**
	 * <h1>returnBook</h1>
	 * The {@code returnBook} method provides the user with the presentation
	 * assets they need to return books they borrowed.
	 * 
	 * <p> First thing all books will be displayed with the help of a {@code 
	 * DatabaseUtil} Class method then from there the user inputs the ISBN of
	 * the book they want to return so its passed into another {@code DatabaseUtil}
	 * class method for database updating. 
	 * 
	 * @throws Exception Though well handled in a try and catch statement,
	 * this <em>exception<em> is thrown when a user inputs an option thats 
	 * out of the sample of those provided and also when a string is input.
	 */
    public static void returnBook() {
    	var dbUtil = new DataAccessUtility();
        Console.cls();
        System.out.println("\n\t\t\t\tSelect book to return: ");
        System.out.println("\t\t\t\ttype "+BLUE+"\"exit\""+R+" to return to Main Menu");
        System.out.println("\n\t\t\t\t"+BU+"======================================"+R);
        int n = dbUtil.printBorrowedBooks();
        System.out.println("\n\t\t\t\t"+BU+"======================================"+R);
        switch (n) {
            case 0: System.out.print("\n\t\t\t\t"+BU+"Press any key to exit: "+R);
            		scanner.nextLine();
            		Console.accountRealatedInfo();
            		break;
            		
            case 1: System.out.print("\n\t\t\t"+BU+"Enter Books ISBN:"+R+" "+BLUE);
		            String string = scanner.nextLine();
		            System.out.println(R);
		            if (string.equalsIgnoreCase("exit")) {
		            	Console.membersHomePage();
		                break;
		            }
		            try {
		                Integer.parseInt(string);
		                dbUtil.hasReturned(string);
		            }
		            catch (Exception e) {
		                invalidChoice(e);
		                Console.returnBook();
		            }
		            break;
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
    	var dbUtil = new DataAccessUtility();
        Console.cls();
        System.out.println("\n\t\t\t\t"+BU+"These are all currently borrowed books"+R+"\n");
        dbUtil.printAllBooksIBorrowed();
        System.out.print("\n\t\t\t\tPress any key to exit: ");
        scanner.nextLine();
        Console.accountRealatedInfo();
    }

	/**
	 * <h1>changeCredentials</h1>
	 * This <code>changeCredentials</code> method pretty much like the name, it
	 * will change the users credentials.
	 * 
	 * <p>This method provides only two options until the time of writing of this
	 * writing {@literal v1.4}, to either Change the Email and Password for the
	 * user but not necessarily other things like the address etc.</p>
	 * 
	 * <p>Furthermore logic for changing the Email is currently not implemented as
	 * there exits no valid email service provider for this application. It is the
	 * reason why the feature is disabled but the feature of changing an Email
	 * will be embedded in later updates</p> 
	 *
	 * @throws Exception Though well handled in a try and catch statement, this <em>exception<em>
	 * is thrown when a user inputs an option thats out of the sample of those provided 
	 * and also when a string is input.
	 */
    private static void changeCredentials() {
        block6: {
            Console.cls();
            System.out.println("\n\t\t\t\tSelect one of the following options:");
            System.out.println("\t\t\t\tType "+BLUE+"\"back\""+R+" to return to previous Menu");
            System.out.println("\n\t\t\t\t"+BU+"======================================"+R);
            System.out.println("\n\t\t\t\t    Press \"1\" to Change Email.");
            System.out.println("\n\t\t\t\t    Press \"2\" to Change Password.");
            System.out.println("\n\t\t\t\t"+BU+"======================================"+R);
            System.out.print("\n\t\t\t\t"+BU+"Enter:"+R+" "+BLUE);
            String string = scanner.nextLine();
            System.out.println(R);
            if (string.equalsIgnoreCase("back")) Console.accountRealatedInfo();
            else {
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
	                else throw new InvalidChoiceException();
	            }
	            catch (Exception e) {
	                invalidChoice(e);
	                Console.changeCredentials();
	            }
            }
        }
    }

    /**
     * <h1>changePassword</h1>
     * 
     * This method prompts the user to enter their current password. The password
     * they input will be compared to logged in users password by getting the
     * password from the {@code currentMember} object of the {@code Member} class,
     * and the comparison is gone with this logic
     * <code>enteredPassword.equals(currentMember.getPassword())</code> in an if
     * statement.
     * 
     * <p>If the <em>passwords<em> match then will they be prompted to enter the
     * new password they desire and then will the password be parsed into a 
     * {@code DataAccessUtil} class method to be changed. 
     * 
     * <p>If the password entered does not match with the correct password, the
     * user is notified and then return to the start of the {@code Console.changePassword()}
     * method.
     */
    protected static void changePassword() {
    	var dbUtil = new DataAccessLogic() {};
        System.out.print("\n\n\t\t\t\tEnter Current Password: "+WHITE_BACKGROUND);
        String enteredPassword = scanner.nextLine();
        System.out.println(R);
        if (enteredPassword.equals(currentMember.getPassword())) {
            System.out.print("\n\n\t\t\t\t  Enter New Password: "+WHITE_BACKGROUND);
            String string2 = scanner.nextLine();
            System.out.println(R);
            System.out.print("\n\t\t\t\tConfirm New Password: "+WHITE_BACKGROUND);
            String string3 = scanner.nextLine();
            System.out.println(R);
            if (string2.equals(string3)) {
                int n = dbUtil.changePassword(string3);
                if (n == 1) {
                    Console.cls();
                    System.out.println("\n\t\t\t\t"+GREEN_BOLD_BRIGHT+"Password successfully changed!"+R);
                    System.out.print("\n\n\n\t\t\t"+BLACK_BRIGHT+"Press enter to return: "+R);
                    scanner.nextLine();
                    Console.accountRealatedInfo();
                }
            } else {
                cls();
                System.out.println("\n\t\t\t\t"+RED_BOLD_BRIGHT+"Password could not be change"+R);
                sleep(1000);
                System.out.print("\n\n\t\t\t\t"+BLACK_BRIGHT+"Reverting Changes");
                loading();
                System.out.println(R);
                Console.accountRealatedInfo();
            }
        } else {
            Console.cls();
            System.out.println("\n\t\t\t\t"+RED_BOLD_BRIGHT+"Password did not match!"+R);
            Console.sleep((int)2000);
            Console.changePassword();
        }
    }

    /**
     * <h1>changeEmail</h1>
     * 
     * This method should provide utility
     * to change a users email.
     * 
     * <p>No code has been provided to
     * implement this method solely because
     * no email validation service provider
     * exits for this application but changes
     * are likely to be done in future releases.</p>
     */
    private static void changeEmail() {
    	System.out.println("\n\t\t\t\tComing soon!");
    	System.out.println("\n\t\t\t\tPress any key to exit.");
    	scanner.nextLine();
    	Console.membersHomePage();
    }
    
    /**
     * <h1>musicSessions</h1>
     * When a user select a choice to {@code Music Sessions} from the options presented by
     * the {@code Concole.membersHomePage} method, the are brought to this {@code musicSessions}
     * method which contains all the appropriate presentation logic that has to deal with music
     * related queries.
     * 
     * <p>The user is prompted to get songs using any of the following criteria:
     * <ul>
     * 	<li>To browse all songs</li>
     * 	<li>To search by title keywords</li>
     * 	<li>To search by artist name</li>
     * 	<li>To search by genre</li>
     * </ul>
     * a switch statement is then executed based on the choice of the user. If they chose to browse
     * all songs then every song present in the database will be displayed but if they chose to
     * search by any criteria, they are prompted to supply the <pre>keyword<pre>, <pre>genre<pre>
     * or <pre>artist<pre> input then it is stored in a String variable and get parsed into the
     * {@code dbUtil.searchSongs} method to execute a select query that will return the desired
     * songs</p>
     * 
     * <p>The {@code dbUtil} instance of the {@code DataAccessUtil} class contains two <pre>overloads<pre>
     * for the {@code searchSongs()} method. the one that takes in one parameter, <pre>searchSongs(int i)<pre>
     * is a way to notify the database that we want to return every song as for the first option of the
     * provided choices. The second overload <pre>searchSongs(int x, String y)<pre> has the x notifying
     * the database the search criteria which could either be by title, genre or artist while y is the
     * String given to the database to filter the query. "y" can either be the songs title, arst or
     * genre.
     * 
     * @throws LineUnavailableException 
     * @throws IOException 
     * @throws UnsupportedAudioFileException 
     */
    protected final static void musicSessions(){
    	cls();
    	var dbUtil = new DataAccessLogic() {
		};
        System.out.println("\n\t\t\t\t"+BLACK_BOLD_BRIGHT+"Welcome to the Music Hub " + currentMember.getFirstName()+R);
        System.out.println("\t\t\t\t"+BLACK_BOLD_BRIGHT+"Type "+BLUE+"\"#\""+R+BLACK_BOLD_BRIGHT+" to return to previous Menu"+R);
        System.out.println("\n\t\t\t\t"+BU+"Please select any search criteria:"+R);
        System.out.println("\n\t\t\t\t"+BU+"==================================="+R);
        System.out.println("\n\t\t\t\t    1 : Browse all songs");
        System.out.println("\n\t\t\t\t    2 : Search by title keywords");
        System.out.println("\n\t\t\t\t    3 : Search by artist name");
        System.out.println("\n\t\t\t\t    4 : Search by genre");
        System.out.println("\n\t\t\t\t"+BU+"==================================="+R);
        System.out.print("\n\n\t\t\t\t"+BU+"Enter:"+R+" "+BLUE);
        String string = scanner.nextLine();
        System.out.println(R);
        if(string.equals("#")) Console.membersHomePage();
        try {
            int n = Integer.parseInt(string);
            Console.cls();
            switch (n) {
                case 1:
                    dbUtil.searchSongs(1);
                    System.out.print("\n\n\t\t\t"+BU+"Enter key:"+R+" "+BLUE);
                    String string2 = scanner.nextLine();
                    System.out.println(R);
                    Integer.parseInt(string2);
                    path = "C:\\eclipse-workspace\\0.L.L\\music\\" + (String)treeMapMusic.get(Integer.parseInt(string2));
                    musicPlayer(clip);
                    break;
                case 2:
                	System.out.print("\n\n\t\t\t"+BU+"Enter keyword:"+R+" "+BLUE);
                	String keyword = scanner.nextLine();
                	System.out.println(R);
                    dbUtil.searchSongs(2, keyword);
                    System.out.print("\n\n\t\t\t"+BLACK_BOLD_BRIGHT+"Enter key:"+R+" "+BLUE);
                    String string3 = scanner.nextLine();
                    System.out.println(R);
                    Integer.parseInt(string3);
                    path = "C:\\eclipse-workspace\\0.L.L\\music\\" + (String)treeMapMusic.get(Integer.parseInt(string3));
                    musicPlayer(clip);
                    break;
                case 3:
                    System.out.print("\n\t\t\t\t"+BLACK_BOLD_BRIGHT+"Enter Artist Name:"+R+" ");
                    String artistName = scanner.nextLine();
                    System.out.println(R);
                    dbUtil.searchSongs(3, artistName);
                    System.out.print("\n\n\t\t\t"+BLACK_BOLD_BRIGHT+"Enter key:"+R+" "+BLUE);
                    String string4 = scanner.nextLine();
                    System.out.println(R);
                    Integer.parseInt(string4);
                    path = "C:\\eclipse-workspace\\0.L.L\\music\\" + (String)treeMapMusic.get(Integer.parseInt(string4));
                    musicPlayer(clip);
                    break;
                case 4: 
                    Console.cls();
                    System.out.println("\n\t\t\t\t"+BU+"Please Select Any Genre"+R);
                    System.out.println("\n\t\t\t\t   Select \"1\" for "+CYAN+"Gospel"+R+"\n");
                    System.out.println("\t\t\t\t   Select \"2\" for "+CYAN+"HipHop"+R+"\n");
                    System.out.println("\t\t\t\t   Select \"3\" for "+CYAN+"AfroHouse"+R+"\n");
                    System.out.println("\t\t\t\t   Select \"4\" for "+CYAN+"Oldschool"+R+"\n");
                    System.out.println("\t\t\t\t   Select \"5\" for "+CYAN+"80s"+R+"\n");
                    System.out.println("\t\t\t\t   Select \"6\" for "+CYAN+"Lo-fi"+R+"");
                    System.out.print("\n\n\t\t\t\t"+BU+"Genre Code:"+R+" "+BLUE);
                    String string5 = scanner.nextLine();
                    System.out.println(R);
                    int n2 = Integer.parseInt(string5);
                    if (!(n2 == 1 | n2 == 2 | n2 == 3 | n2 == 4 | n2 == 5 | n2 == 6)) {
                        throw new InvalidChoiceException();
                    }
                    dbUtil.searchSongs(4, string5);
                    System.out.print("\n\n\t\t\t"+BLACK_BOLD_BRIGHT+"Enter key:"+R+" "+BLUE);
                    String string6 = scanner.nextLine();
                    System.out.println(R);
                    Integer.parseInt(string6);
                    GlobalMembers.path = "C:\\eclipse-workspace\\0.L.L\\music\\" + (String)treeMapMusic.get(Integer.parseInt(string6));
                    musicPlayer(clip);
                    break;
                default:
                	Console.musicSessions();
            }
            Console.cls();
            System.out.println("\n\t\t\t\tWe hope you enjoyed!");
            Console.sleep((int)3000);
            Console.membersHomePage();
        }
        catch (Exception e) {
            invalidChoice(e);
            Console.musicSessions();
        }
    }

    /**
     * <h1>queryBooks</h1>
     * This method takes in an integer n as a form of determining which criteria has
     * been chosen.
     * 
     * <p>If the integer is 2, it knows and prompts the user to enter the keyword for
     * searching the books. both the keyword and the <pre>parameter</pre> <em>n</em>
     * are then parsed in a {@code dbUtil.searchBooksByKeyword(int x, String y)}
     * method just so first, the method also knows which criteria is in use and the
     * String as the filter for the select query in the database.
     * 
     * <p>If the integer is 3, it knows and prompts the user to enter the <em>author</em>
     * for searching the books. both the author and the <pre>parameter</pre> <em>n</em>
     * are then parsed in a {@code dbUtil.searchBooksByKeyword(int x, String y)}
     * method just so first, the method also knows which criteria is in use and the
     * String as the filter for the select query in the database.
     * 
     * <p>If the integer is 4, it knows and prompts the user to enter the <em>category</em>
     * for searching the books. both the keyword and the <pre>parameter<pre/> <em>n</em>
     * are then parsed in a {@code dbUtil.searchBooksByKeyword(int x, String y)}
     * method just so first, the method also knows which criteria is in use and the
     * String as the filter for the select query in the database.
     * 
     * @param n The criteria previously chosen in the  for searching a book in the
     * {@code routeActions(String chosenAction)} method.
     */
    static void queryBooks(Integer n) {
    	var dbUtil = new DataAccessUtility();
        Console.cls();
        if (n == 2) {
            System.out.print("\n\t\t\t\t"+BU+"Enter keyword:"+R+" ");
            dbUtil.searchBooksByKeyword(n.intValue(), scanner.nextLine());
        }
        else if (n == 3) {
            System.out.print("\n\t\t\t\t"+BU+"Enter author:"+R+" ");
            dbUtil.searchBooksByKeyword(n.intValue(), scanner.nextLine());
        }
        else if (n == 4) {
            System.out.print("\n\t\t\t\t"+BU+"Enter category:"+R+" ");
            dbUtil.searchBooksByKeyword(n.intValue(), scanner.nextLine());
        }
    }

    /**
     * <h1>podcasts</h1>
     *This method prompts the user if they want to search for podcast's whether
     *using the Name of the podcast or the Guest Speaker of the podcast. The
     *Input is parsed on in the {@code dbUtil.retrievePodcasts(sc.nextLine())}
     *method for searching. If an empty string is entered by the user, all
     *podcasts will be returned.
     */
    
    private static void podcasts() {
    	var dbUtil = new DataAccessUtility();
        System.out.println("\n\t\t\t\tSearch by "+CYAN+"Name"+R+" or "+CYAN+"GuestSpeaker"+R);
        System.out.println("\t\t\t\tPress Enter to retain All");
        System.out.print("\n\n\t\t\t\t"+BU+"Search:"+R+" "+BLUE_BRIGHT);
        dbUtil.retrievePodcasts(scanner.nextLine());
    }

    /**
     * <h1>bookSections</h1>
     * Gets executed prior to the user's intention to get all books from a certain
     * section, from the {@code routeActions} method.
     * 
     * <p>A {@code dbUtil} <em>instance<em> of the {@code DatabaseUtility} class will
     * be invoked first to print all sections present in the database. The user will
     * then choose the section with whom to display books that belong to it.
     * 
	 * @throws IllegalArgumentException Though well handled in a try and catch 
	 * statement, this <em>exception<em> is thrown when a user inputs an option
	 * thats out of the sample of those provided and also when a string is input.
     */
    protected static void bookSections() {
    	var dbUtil = new DataAccessUtility(); 
        dbUtil.getBookSections();
        Console.cls();
        System.out.println("\n\t\t\t\t==========================================\n");
        System.out.println("\t\t\t\t"+BU+"Choose Section of Interest"+R);
        int n = 1;
        for (String string : Console.bookSections) System.out.println("\n\t\t\t\t    Press: \"" + n++ + "\" for " + WHITE_BOLD_BRIGHT +string +R);
        System.out.print("\n\n\t\t\t\t"+BU+"Enter Section:"+R+" "+PURPLE_BOLD_BRIGHT);
        String string = Console.scanner.nextLine();
        System.out.println(R);
        try {
            dbUtil.allBooksInSection(Integer.valueOf(string));
        }
        catch (Exception e) {
        	invalidChoice(e);
            Console.bookSections();
        }
    }
    

}