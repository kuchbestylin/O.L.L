import java.util.Scanner;
import java.util.Stack;

public class GlobalMembers {
	Stack<String> bookSections = new Stack<String>();
	Scanner sc = new Scanner(System.in);
	static void cls() {
	    try{
	        String operatingSystem = System.getProperty("os.name"); //Check the current operating system
	        if(operatingSystem.contains("Windows")){ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls"); Process startProcess = pb.inheritIO().start();startProcess.waitFor();
	        } else { ProcessBuilder pb = new ProcessBuilder("clear");   Process startProcess = pb.inheritIO().start(); startProcess.waitFor();} 
	    }catch(Exception e){System.out.println(e);  }
	}

	static void sleep(int seconds) {
					//Makes the console wait (in seconds) until the next statement can be executed
					try {
						Thread.sleep(seconds);
					} catch (InterruptedException e) {
						System.out.println(e);
						//e.printStackTrace();
						}
		}
}
