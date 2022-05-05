
import java.util.Scanner;
public abstract class Authentication extends GlobalMembers {
	
	protected final static void register(Scanner sc){
		cls();
		Member.attributeCollection(sc);
		dbUtil.registration(currentMember);
	}

	protected final static void signIn(Scanner sc) {
		cls();
		System.out.println( WHITE_UNDERLINED + WHITE_BOLD_BRIGHT+"\n\t\t\t\t\t\tLog In" + r);
		System.out.print("\n\n\t\t\t\tEnter Email: " + PURPLE_BOLD_BRIGHT);
		currentMember.setEmail(sc.nextLine());
		System.out.println(r);
		System.out.print("\n\t\t\t\tEnter Password: " + WHITE_BACKGROUND);
		currentMember.setPassword(sc.nextLine());
		System.out.println(r);
		dbUtil.verifyCredentials(currentMember.getEmail(), currentMember.getPassword(), currentMember);
		sleep(2900);
	}
	


}