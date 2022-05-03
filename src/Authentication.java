
import java.util.Scanner;
public abstract class Authentication extends GlobalMembers {
	
	protected final static void register(Scanner sc){
		cls();
		Member.attributeCollection(sc);
		dbUtil.registration(currentMember);
	}

	protected final static void signIn(Scanner sc) {
		cls();
		System.out.println("\n\t\t\t\t\tLog In");
		System.out.print("\n\n\t\t\t\tEnter Email: ");
		currentMember.setEmail(sc.nextLine());
		System.out.print("\n\t\t\t\tEnter Password: ");
		currentMember.setPassword(sc.nextLine());
		dbUtil.verifyCredentials(currentMember.getEmail(), currentMember.getPassword(), currentMember);
		sleep(2900);
	}
	


}