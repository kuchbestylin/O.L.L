import java.util.*;

public class BaseContoller extends Authentication {
	static String responce;
	
	public static void homeDisplay(Scanner sc) {
		System.out.print(
				"\t\t\t\tWelcome To Open Learning, Featuring podcasts, publications and\n"
				+ "\t\t\t\tdozens of books from a variety of different authors gathered\n"
				+ "\t\t\t\there for you to read online or offline which we hope you find\n"
				+ "\t\t\t\tenjoyable. Feedback is welcome - feel free to proceed as guest\n"
				+ "\t\t\t\tor register with us at N$550 a year.\n");
		System.out.println("\n\n\t\t\t\tPress 1: Sign In");
		System.out.println("\t\t\t\tPress 2: Register as new user.");
		System.out.println("\t\t\t\tPress 2: Continue as Guest.\n");
		System.out.print("\t\t\t\tInput: ");
		responce = sc.nextLine();
		try {
			switch(Integer.valueOf(responce)) {
			case(1):{
				signIn(sc);
				break;
			}
			case(2):{
				register(sc);
				break;
			}
			case(3):{
				guestPage();
				break;
			}
			}
		} catch (Exception e) {
			System.out.println("\n\t\t\t\tInvalid Input!");
			cls(); homeDisplay(sc);
		}
	}
	
	private static void guestPage() {
		
	}

	protected void membersHomePage(){
		cls();
		System.out.println(
				"\n\t\t\t\tSelect either of the following options\n"
				+ "\t\t\t\t\t1 : Browse Books\n"
				+ "\t\t\t\t\t2 : Listen to Podcasts\n"
				+ "\t\t\t\t\t3 : Enter Music Sessions\n"
				+ "\t\t\t\t\t4 : Manage Account Information"
				);
		System.out.print("\n\n\t\t\t\tEnter: ");
		String choice = sc.nextLine();
		try {
			Integer.parseInt(choice);
			cls();
			if(Integer.parseInt(choice) == 1) {
				System.out.println("\n\t\t\t\t=======================================\n"
						+ "\t\t\t\tSelect a retrieval Criteria\n" +
						"\t\t\t\t\t1 : Proceed to book Sections.\n" +
						"\t\t\t\t\t2 : Search by keywords.\n" +
						"\t\t\t\t\t3 : Search by author.\n" +
						"\t\t\t\t\t4 : Search by category\n"
						);
				System.out.print("\t\t\t\tEnter: ");
				int x = Integer.parseInt(sc.nextLine());
				if(x == 1) bookSections();
				else if(x == 2 | x == 3 | x == 4) {
					queryBooks(x);
				}

			}
				
			
			else if (Integer.parseInt(choice) == 2) podcast();
			else if (Integer.parseInt(choice) == 3) musicSessions();
			else if (Integer.parseInt(choice) == 4) accountInfo();
			else throw new Exception("\n\t\t\t\tChoose a valid choice");
			sleep(1000);
		} catch (Exception e) {
			System.out.println("\n\t\t\t\tOption Invalid!");
			sleep(1000);
			membersHomePage();
		}
	}

	private void accountInfo() {
		// TODO Auto-generated method stub
		
	}

	private void musicSessions() {
		// TODO Auto-generated method stub
		
	}

	void queryBooks(Integer valueOf) {
		cls();
		var dbUtil = new DatabaseUtil();
		if(valueOf == 2) {
			System.out.print("\n\t\t\t\tEnter keyword: ");
			dbUtil.searchBooksByKeyword(valueOf, sc.nextLine());
		}
		if(valueOf == 3) {
			System.out.print("\n\t\t\t\tEnter author: ");
			dbUtil.searchBooksByKeyword(valueOf, sc.nextLine());
		}
		if(valueOf == 4) {
			System.out.print("\n\t\t\t\tEnter category: ");
			dbUtil.searchBooksByKeyword(valueOf, sc.nextLine());
		}
		
	}

	private void podcast() {
		// TODO Auto-generated method stub
		
	}

	protected void bookSections() {
		cls();
		System.out.println("\t\t\t\tChoose Section of Interest");
		int i = 1;
		while(!bookSections.isEmpty()) {
			System.out.println("\n\t\t\t\t\t" + i++ + " : " + bookSections.pop());
		}
		System.out.print("\n\n\t\t\t\tEnter Section: ");
		String choice = sc.nextLine();
		try {
			Integer.valueOf(choice);
			switch(Integer.valueOf(choice)) {
			case(1) : { //choice 1 pertains..
				
			}
			case(2) : { //choice 1 pertains..
				
			}
			case(3) : { //choice 1 pertains..
				
			}
			case(4) : { //choice 1 pertains..
				
			}
			case(5) : { //choice 1 pertains..
				
			}
			case(6) : { //choice 1 pertains..
				
			}
			}
		} catch (Exception e) {
			System.out.println("\n\t\t\t\tInvalid Choice!");
			bookSections();
		}
		
	}
}