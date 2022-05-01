/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  DatabaseUtil
 */
import java.util.Scanner;
import java.util.Stack;

public class BaseContoller
extends Authentication {
    static String responce;
    DatabaseUtil dbUtil;

    public BaseContoller() {
    }

    public BaseContoller(Scanner scanner, DatabaseUtil databaseUtil) {
        this.sc = scanner;
        this.dbUtil = databaseUtil;
        this.homeDisplay();
    }

    public void homeDisplay() {
        System.out.print("\t\t\t\tWelcome To Open Learning, Featuring podcasts, publications and\n\t\t\t\tdozens of books from a variety of different authors gathered\n\t\t\t\there for you to read online or offline which we hope you find\n\t\t\t\tenjoyable. Feedback is welcome - feel free to proceed as guest\n\t\t\t\tor register with us at N$550 a year.\n");
        System.out.println("\n\n\t\t\t\tPress 1: Sign In");
        System.out.println("\t\t\t\tPress 2: Register as new user.");
        System.out.println("\t\t\t\tPress 3: Continue as Guest.\n");
        System.out.print("\t\t\t\tInput: ");
        responce = this.sc.nextLine();
        try {
            switch (Integer.valueOf(responce)) {
                case 1: {
                    BaseContoller.signIn(this.sc);
                    break;
                }
                case 2: {
                    BaseContoller.register(this.sc);
                    break;
                }
                case 3: {
                    this.guestPage();
                }
            }
        }
        catch (Exception exception) {
            System.out.println("\n\t\t\t\tInvalid Input!");
            BaseContoller.cls();
            this.homeDisplay();
        }
    }

    private void guestPage() {
    }

    protected void membersHomePage() {
        BaseContoller.cls();
        System.out.println("\n\t\t\t\t\tWelcome back " + currentMember.getfName());
        System.out.println("\t\t\t\tType \"logout\" to sign-Out\n\n\t\t\t\tSelect either of the following options\n\n\t\t\t\t\t1 : Browse Books\n\n\t\t\t\t\t2 : Listen to Podcasts\n\n\t\t\t\t\t3 : Music Sessions\n\n\t\t\t\t\t4 : Manage Account Information");
        System.out.print("\n\t\t\t\tEnter: ");
        String string = this.sc.nextLine();
        if (string.equalsIgnoreCase("logout")) {
            this.homeDisplay();
        } else {
            try {
                Integer.parseInt(string);
                BaseContoller.cls();
                if (Integer.parseInt(string) == 1) {
                    System.out.println("\n\t\t\t\t=======================================\n\t\t\t\t[Type \"#\" to return to previous section\n\n\t\t\t\tSelect a retrieval Criteria\n\t\t\t\t\t1 : Proceed to book Sections.\n\t\t\t\t\t2 : Search by keywords.\n\t\t\t\t\t3 : Search by author.\n\t\t\t\t\t4 : Search by category\n");
                    System.out.print("\t\t\t\tEnter: ");
                    String string2 = this.sc.nextLine();
                    if (string2.equals("#")) {
                        this.membersHomePage();
                    } else if (Integer.parseInt(string2) == 1) {
                        this.bookSections();
                    } else if (Integer.parseInt(string2) == 2 | Integer.parseInt(string2) == 3 | Integer.parseInt(string2) == 4) {
                        this.queryBooks(Integer.parseInt(string2));
                    }
                } else if (Integer.parseInt(string) == 2) {
                    this.podcast();
                } else if (Integer.parseInt(string) == 3) {
                    this.musicSessions();
                } else if (Integer.parseInt(string) == 4) {
                    this.accountInfo();
                } else {
                    throw new Exception("\n\t\t\t\tChoose a valid choice");
                }
                BaseContoller.sleep((int)1000);
            }
            catch (Exception exception) {
                System.out.println(exception.getMessage());
                exception.printStackTrace();
                System.out.println("\n\t\t\t\tOption Invalid!");
                BaseContoller.sleep((int)1000);
                this.membersHomePage();
            }
        }
    }

    protected void accountInfo() {
        BaseContoller.cls();
        System.out.println("\n\t\t\t\t\t" + currentMember.getfName() + " " + currentMember.getsName() + "\n\t\t\t\tType \"#\" to return to previous Menu\n\n\t\t\t\tChoose any option:\n\t\t\t\t1:Change Credentials\n\t\t\t\t2:Check Deadlines\n\t\t\t\t3:Return Book");
        System.out.print("\n\n\t\t\tEnter: ");
        String string = this.sc.nextLine();
        if (string.equals("#")) {
            this.membersHomePage();
        } else {
            try {
                Integer.parseInt(string);
                switch (Integer.parseInt(string)) {
                    case 1: {
                        this.changeCredentials();
                        break;
                    }
                    case 2: {
                        this.checkDeadlines();
                        break;
                    }
                    case 3: {
                        this.returnBook();
                        break;
                    }
                    default: {
                        throw new IllegalArgumentException();
                    }
                }
            }
            catch (Exception exception) {
                BaseContoller.cls();
                System.out.println("\n\t\t\t\tInvalid option");
                System.out.println(exception.getMessage());
                exception.printStackTrace();
                BaseContoller.sleep((int)2000);
                this.accountInfo();
            }
        }
    }

    public void returnBook() {
        BaseContoller.cls();
        DatabaseUtil databaseUtil = new DatabaseUtil();
        System.out.println("\n\t\t\t\tSelect book to return: ");
        System.out.println("\t\t\t\ttype \"exit\" to return to Main Menu");
        int n = databaseUtil.printBorrowedBooks();
        switch (n) {
            case 0: {
                System.out.print("\n\n\n\t\t\t\tPress any key to exit: ");
                this.sc.nextLine();
                this.accountInfo();
                break;
            }
            case 1: {
                System.out.print("\n\t\t\tEnter Books ISBN: ");
                String string = this.sc.nextLine();
                if (string.equalsIgnoreCase("exit")) {
                    this.membersHomePage();
                    break;
                }
                try {
                    Integer.parseInt(string);
                    databaseUtil.hasReturned(string);
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                    System.out.println("\n\t\t\t\tInvalid Choice");
                    System.out.println(exception.getMessage());
                    this.returnBook();
                }
                break;
            }
        }
    }

    private void checkDeadlines() {
        BaseContoller.cls();
        DatabaseUtil databaseUtil = new DatabaseUtil();
        System.out.println("\n\t\t\t\tThese are all currently borrowed books\n");
        databaseUtil.printAllBooksIBorrowed();
        System.out.print("\n\t\t\t\tPress any key to exit: ");
        this.sc.nextLine();
        this.accountInfo();
    }

    private void changeCredentials() {
        block6: {
            BaseContoller.cls();
            System.out.println("\n\t\t\t\tSelect one of the following options:");
            System.out.println("\t\t\t\tType \"back\" to return to previous Menu");
            System.out.println("\n\t\t\t\t1: Change Email.");
            System.out.println("\t\t\t\t2: Change Password.");
            System.out.print("\n\n\t\t\tEnter: ");
            String string = this.sc.nextLine();
            if (string.equalsIgnoreCase("back")) {
                this.accountInfo();
            } else {
                try {
                    int n = Integer.parseInt(string);
                    if (n == 1) {
                        this.changeEmail();
                        break block6;
                    }
                    if (n == 2) {
                        this.changePassword();
                        break block6;
                    }
                    throw new IllegalArgumentException();
                }
                catch (Exception exception) {
                    BaseContoller.cls();
                    System.out.println("\n\n\t\t\t\tOption Invalid");
                    BaseContoller.sleep((int)2000);
                    this.changeCredentials();
                }
            }
        }
    }

    protected void changePassword() {
        DatabaseUtil databaseUtil = new DatabaseUtil();
        System.out.println(currentMember.getPassword());
        System.out.print("\n\n\t\t\t\tEnter Current Password: ");
        String string = this.sc.nextLine();
        System.out.println("2ede2c2e");
        if (string.equals(currentMember.getPassword())) {
            System.out.print("\n\n\t\t\t\t  Enter New Password: ");
            String string2 = this.sc.nextLine();
            System.out.print("\n\t\t\t\tConfirm New Password: ");
            String string3 = this.sc.nextLine();
            if (string2.equals(string3)) {
                int n = databaseUtil.changePassword(string3);
                if (n == 1) {
                    BaseContoller.cls();
                    System.out.println("\n\t\t\t\tPassword successfully changed!");
                    System.out.print("\n\n\n\t\t\tPress enter to return: ");
                    this.sc.nextLine();
                    this.accountInfo();
                }
            } else {
                BaseContoller.cls();
                System.out.println("\n\t\t\t\tsorry password could not be change");
                BaseContoller.sleep((int)2000);
                System.out.println("\n\t\t\t\tReverting Changes..");
                BaseContoller.sleep((int)2000);
                System.out.println("\n\t\t\t\tRedirecting to Main Menu");
                BaseContoller.sleep((int)2000);
                this.accountInfo();
            }
        } else {
            BaseContoller.cls();
            System.out.println("\n\t\t\t\tPassword did not match!");
            BaseContoller.sleep((int)2000);
            this.changePassword();
        }
    }

    private void changeEmail() {
    }

    protected void musicSessions() {
        DatabaseUtil databaseUtil = new DatabaseUtil();
        System.out.println("\n\t\t\t\tWelcome to the Music Hub " + currentMember.getfName());
        System.out.println("\n\t\t\t\tPlease select any search criteria: ");
        System.out.println("\n\t\t\t\t    1 : Browse all songs");
        System.out.println("\n\t\t\t\t    2 : Search by title keywords");
        System.out.println("\n\t\t\t\t    3 : Search by artist name");
        System.out.println("\n\t\t\t\t    4 : Search by genre");
        System.out.print("\n\n\t\t\tEnter: ");
        String string = this.sc.nextLine();
        try {
            int n = Integer.parseInt(string);
            switch (n) {
                case 1: {
                    BaseContoller.cls();
                    databaseUtil.searchSongs(1);
                    System.out.print("\n\n\t\t\tEnter key: ");
                    String string2 = this.sc.nextLine();
                    Integer.parseInt(string2);
                    this.path = "C:\\eclipse-workspace\\0.L.L\\src\\grindin.wav";
                    this.musicPlayer(this.clip);
                    break;
                }
                case 2: {
                    System.out.print("\n\t\t\t\tEnter keyword: ");
                    databaseUtil.searchSongs(2, this.sc.nextLine());
                    System.out.print("\n\n\t\t\tEnter key: ");
                    String string3 = this.sc.nextLine();
                    Integer.parseInt(string3);
                    this.path = (String)this.treeMapMusic.get(Integer.parseInt(string3));
                    this.musicPlayer(this.clip);
                    break;
                }
                case 3: {
                    System.out.print("\n\t\t\t\tEnter Artist Name: ");
                    databaseUtil.searchSongs(3, this.sc.nextLine());
                    System.out.print("\n\n\t\t\tEnter key: ");
                    String string4 = this.sc.nextLine();
                    Integer.parseInt(string4);
                    this.path = (String)this.treeMapMusic.get(Integer.parseInt(string4));
                    this.musicPlayer(this.clip);
                    break;
                }
                case 4: {
                    BaseContoller.cls();
                    System.out.println("\n\t\t\t\tPlease Select Any Genre");
                    System.out.println("\n\t\t\t\t   1: Gospel");
                    System.out.println("\t\t\t\t   2: HipHop");
                    System.out.println("\t\t\t\t   3: AfroHouse");
                    System.out.println("\t\t\t\t   4: Oldschool");
                    System.out.println("\t\t\t\t   5: 80s");
                    System.out.println("\t\t\t\t   6: Lo-fi");
                    System.out.print("\n\n\t\t\t\tGenre Code: ");
                    String string5 = this.sc.nextLine();
                    int n2 = Integer.parseInt(string5);
                    if (!(n2 == 1 | n2 == 2 | n2 == 3 | n2 == 4 | n2 == 5 | n2 == 6)) {
                        throw new IllegalArgumentException();
                    }
                    databaseUtil.searchSongs(4, string5);
                    System.out.print("\n\n\t\t\tEnter key: ");
                    String string6 = this.sc.nextLine();
                    Integer.parseInt(string6);
                    this.path = (String)this.treeMapMusic.get(Integer.parseInt(string6));
                    this.musicPlayer(this.clip);
                    break;
                }
                default: {
                    this.musicSessions();
                }
            }
            BaseContoller.cls();
            System.out.println("\n\t\t\t\tWe hope you had a good time!");
            BaseContoller.sleep((int)2000);
            System.out.println("\n\t\t\t\tYou will be redirected to the home page");
            BaseContoller.sleep((int)2000);
            this.membersHomePage();
        }
        catch (Exception exception) {
            exception.getMessage();
            exception.printStackTrace();
            this.invalidChoice();
            this.musicSessions();
        }
    }

    void queryBooks(Integer n) {
        DatabaseUtil databaseUtil = new DatabaseUtil();
        BaseContoller.cls();
        if (n == 2) {
            System.out.print("\n\t\t\t\tEnter keyword: ");
            databaseUtil.searchBooksByKeyword(n.intValue(), this.sc.nextLine());
        }
        if (n == 3) {
            System.out.print("\n\t\t\t\tEnter author: ");
            databaseUtil.searchBooksByKeyword(n.intValue(), this.sc.nextLine());
        }
        if (n == 4) {
            System.out.print("\n\t\t\t\tEnter category: ");
            databaseUtil.searchBooksByKeyword(n.intValue(), this.sc.nextLine());
        }
    }

    private void podcast() {
        DatabaseUtil databaseUtil = new DatabaseUtil();
        System.out.println("\n\t\t\t\tSearch by Name or Author");
        System.out.println("\t\t\t\tPress Enter to retain All");
        System.out.print("\n\n\t\t\t\tSearch: ");
        databaseUtil.retrievePodcasts(this.sc.nextLine());
    }

    protected void bookSections() {
        DatabaseUtil databaseUtil = new DatabaseUtil();
        databaseUtil.getBookSections();
        BaseContoller.cls();
        System.out.println("\t\t\t\tChoose Section of Interest");
        int n = 1;
        Stack stack = new Stack();
        for (String string : this.bookSections) {
            System.out.println("\n\t\t\t\t\t" + n++ + " : " + string);
        }
        System.out.print("\n\n\t\t\t\tEnter Section: ");
        String string = this.sc.nextLine();
        try {
            databaseUtil.allBooksInSection(Integer.valueOf(string));
        }
        catch (Exception exception) {
            System.out.println("\n\t\t\t\tInvalid Choice!");
            this.bookSections();
        }
    }
}