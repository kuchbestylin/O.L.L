
import java.util.Scanner;
public abstract class Authentication extends GlobalMembers {
	private static String fName;
	private static String sName;
	private static String homeAddress;
	private static String dOB;
	private static String email;
	private static String password;
	private static String password2;
	private static String inputValidation;
	String[][] entityDetails = {
			{"fName",""},
			{"sName",""},
			{"homeAddress",""},
			{"dOB",""},
			{"email",""},
			{"password",""},
			{"password2",""}
	};
	
	protected final static void register(Scanner sc){
		cls();
		attributeCollection(sc);
		new DatabaseUtil(fName, sName, homeAddress, dOB, email, password);
	}

	protected final static void signIn(Scanner sc) {
		cls();
		System.out.print("\n\t\t\t\tEnter Email: ");
		email = sc.nextLine();
		System.out.print("\n\t\t\t\tEnter Password: ");
		password = sc.nextLine();
		new DatabaseUtil(email, password);
		sleep(2900);
	}
	
	private static void attributeCollection(Scanner sc) {
		String flag = "";
		int counter = 0;
		String message = "";
		do {
			switch(counter) {
			case(0) : break;
			case(1) : {
				System.out.println("\n\t\t\t\t\t\tRegister");
				System.out.println("\t\t\t\t\tEnter 'r' to restart process");
				System.out.print("\n\t\t\t\t            Enter fName : ");
				flag = sc.nextLine();
				if(!(flag.equalsIgnoreCase("r")) && !(flag.equalsIgnoreCase("")) && flag.length() > 2) fName = flag;
				else if(flag.length() < 3) {
					cls();
					inputValidation = "\t\t\t\t            Name too short";
					System.out.println(inputValidation);
					sleep(1000);
					cls();
					counter = 0;
					break;
				}
				else {cls();counter = 0; break; }
				break;
			}
			case(2) : {
				System.out.print("\n\t\t\t\t          Enter Surname : ");
				flag = sc.nextLine();
				if((flag.equalsIgnoreCase("r")))
				{cls();counter = 0; break; }
				else if(flag.length() < 3) {
					inputValidation = "\n\t\t\t\t          Name too short!";
					System.out.println(inputValidation);
					counter = 1;
					break;
				}
				else if((flag.equalsIgnoreCase("")))
				{counter = 1; break; }
				sName = flag;
				break;
			}
			case(3) : {
				System.out.print("\n\t\t\t\t     Enter Home Address : ");
				flag = sc.nextLine();
				if((flag.equalsIgnoreCase("r")))
				{cls();counter = 0; break; }
				else if((flag.equalsIgnoreCase("")))
				{counter = 2; break; }
				homeAddress = flag;
				break;
			}
			case(4) : {
				System.out.print("\n\t\t\t\tEnter D.O.B(YYYY/MM/DD) : ");
				flag = sc.nextLine();
				if((flag.equalsIgnoreCase("r")))
				{cls();counter = 0; break; }
				else if((flag.equalsIgnoreCase("")))
				{counter = 3; break; }
				dOB = flag;
				break;
			}
			case(5) : {
				System.out.print("\n\t\t\t\t            Enter email : ");
				flag = sc.nextLine();
				if((flag.equalsIgnoreCase("r")))
				{cls();counter = 0; break; }
				else if((flag.equalsIgnoreCase("")))
				{counter = 4; break; }
				email = flag;
				break;
			}
			case(6) : {
				System.out.print("\n\t\t\t\t         Enter password : ");
				flag = sc.nextLine();
				if((flag.equalsIgnoreCase("r")))
				{cls();counter = 0; break; }
				else if((flag.equalsIgnoreCase("")) || flag.length() < 7)
				{
					inputValidation = "\t\t\t\t         Password complexity requirements not met";
					System.out.println(inputValidation);
					counter = 5; break;
				}
				password = flag;
				break;
			}
			case(7) : {
				System.out.print("\n\t\t\t\t      Re-Enter password : ");
				flag = sc.nextLine();
				if((flag.equalsIgnoreCase("r")))
				{cls();counter = 0; break; }
				else if((flag.equalsIgnoreCase("")))
				{counter = 6; break; }
				password2 = flag;
				boolean matched = matchTest(password,password2);
				message = (matched == true) ? "\t\t\t\t      Password Matched!" : "\t\t\t\tPassword did not match";
				System.out.println(message);
				if(matched == false) counter = 5;
				break;
			}
			}
			counter++;
		}while(counter < 8);
		System.out.println("\n\t\t\t\tcollection complete");
	}

	public static boolean matchTest(String password, String password2) {
		boolean flag = false;
		if(password.equals(password2)) flag = true;
		return flag;
	}

}