
import java.util.Scanner;
public abstract class Authentication extends GlobalMembers {
	
	protected final static void register(Scanner sc){
		cls();
		Member.attributeCollection(sc);
		dbUtil.registration(currentMember);
	}

	protected final static void signIn(Scanner sc) {
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
	
	protected final static int authenticateLibrarian(Scanner sc) {
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