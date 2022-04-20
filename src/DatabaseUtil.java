/* When ever you work with JDBC you have to follow 7 steps
 * 1. Import the package --> java.sql
 * 2. Load and Register the driver --> com.microsoft.sqlserver.jdbc
 * 3. Create a connection
 * 4. Create a statement
 * 5. Execute the query
 * 6. Process the results
 * 7. Close connection
 */

import java.sql.*; //This is step 1

public class DatabaseUtil extends BaseContoller{
	Date date;
	private String fName;
	private String sName;
	private String homeAddress;
	private String dOB = "2000-11-14";
	private String email;
	private String password;
	String url = "jdbc:sqlserver://localhost:1433;databaseName=OpenLearningLibrary;instance=SQLSERVER;encrypt=true;trustServerCertificate=true";
	String uname = "sa";
	String dbPassword = "Tkuch";
	String query;
	Connection connection;
	Statement statement;
	ResultSet rs;
	String userFeedback;
	CallableStatement callableStatement;
	PreparedStatement ps;
	int membersID;

	
	public DatabaseUtil() {
		super();
	}
	public DatabaseUtil(String email, String password) 
	{
		this.verifyCredentials(email, password);
	}
	public DatabaseUtil(
			String fName,
			String sName,
			String homeAddress,
			String dOB,
			String email,
			String password
			){
		this.fName = fName;
		this.sName = sName;
		this.homeAddress = homeAddress;
		this.dOB = dOB;
		this.email = email;
		this.password = password;
		this.registration();
	}

	public void registration() {
		try 
		{
			connect();
			query = "{call registerNewMember (?,?,?,?,?,?,?,?)}";
			callableStatement = connection.prepareCall(query);
			callableStatement.setString(1, fName.toLowerCase());
			callableStatement.setString(2, sName.toLowerCase());
			callableStatement.setString(3, dOB);
			callableStatement.setString(4, homeAddress.toLowerCase());
			callableStatement.setString(5, "Paid.toLowerCase()");
			callableStatement.setString(6, email.toLowerCase());
			callableStatement.setString(7, password);
			callableStatement.registerOutParameter(8, Types.INTEGER);
			callableStatement.execute();
			if (callableStatement.getInt(8) == 0) userFeedback = "\n\t\t\t\tUser already registered";
			else userFeedback = (callableStatement.getInt(8) == 1) ? "\n\t\t\t\tMember registration succesfull!" : "\n\t\t\t\tRegistration failed!";
			cls();
			System.out.println(userFeedback + " : with " + callableStatement.getInt(8) + " rows affected");
			sleep(2000);
			callableStatement.close();
			connection.close();
			
		} 
		
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		} 
		
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void verifyCredentials(String signInEmail, String password) {
		
		try {
			connect();
			callableStatement = connection.prepareCall("{call credentialsValidation (?,?,?,?)}");
			callableStatement.setString(1, signInEmail);
			callableStatement.setString(2, password.trim());
			callableStatement.registerOutParameter(3, Types.INTEGER);
			callableStatement.registerOutParameter(4, Types.INTEGER);
			callableStatement.executeQuery();
			membersID = callableStatement.getInt(4);
			cls();
			userFeedback = (callableStatement.getInt(3) == 1) ? "\n\t\t\t\tValidation Successful!" : "\n\t\t\t\tEmail Address or Password Invalid!";
			System.out.println(userFeedback);
			sleep(2000);	
			if(callableStatement.getInt(3) == 1) {
				getBookSections();
				membersHomePage();
			}
			else signIn(sc);
			callableStatement.close();
			connection.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void getBookSections() {
		try {
			connect();
			ps = connection.prepareStatement("\n\t\t\t\tSelect sectionName from section");
			boolean hasResultSet = ps.execute();
			if(hasResultSet) {
				rs = ps.getResultSet();
				while(rs.next()) {
					System.out.println("\n\t\t\t\t" + rs.getString(1));
					bookSections.add(rs.getString(1));
				}
			}
			ps.close();
			connection.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	void searchBooksByKeyword(int criteria, String keyword) {
		String determiner = "where title like (?) AND isBorrowed = 0";
		if(criteria == 2) determiner = "where title like (?) AND isBorrowed = 0";
		else if(criteria == 3) determiner = "where author like (?) AND isBorrowed = 0";
		else if(criteria == 4) determiner = "where category like (?) AND isBorrowed = 0";
		String isbn = "";
		String key = "%" + keyword + "%";
		try {
			connect();
			ps = connection.prepareStatement("Select ISBN, title, edition, author, publishDate, description FROM book " + determiner);
			ps.setString(1, key);
			boolean hasResultset = ps.execute();
			if(hasResultset) {
				rs = ps.getResultSet();
				cls();
				System.out.println("\n\t\t\t\t\t    AVAILABLE BOOKS");
				while(rs.next()) {
					System.out.println("\n\t\t\t\t=======================================\n");
					System.out.println("\t\t\t\t         ISBN : " + rs.getString(1));
					System.out.println("\t\t\t\t        TITLE : " + rs.getString(2));
					System.out.println("\t\t\t\t      EDITION : " + rs.getString(3));
					System.out.println("\t\t\t\t       AUTHOR : " + rs.getString(4));
					System.out.println("\t\t\t\t PUBLISH_DATE : " + rs.getDate(5));
					System.out.println("\t\t\t\t  DISCRIPTION : " + rs.getString(6) + "\n");
				}
			}
			System.out.print("\n\n\t\t\t\tEnter ISBN: ");
			try {
				isbn = sc.nextLine();
				Integer.valueOf(isbn);
				connect();
				callableStatement = connection.prepareCall("{call loanBook (?,?,?,?,?,?,?,?)}");
				callableStatement.setInt(1, membersID);
				callableStatement.setString(2, isbn);
				callableStatement.registerOutParameter(3, Types.INTEGER);
				callableStatement.registerOutParameter(4, Types.DATE);
				callableStatement.registerOutParameter(5, Types.VARCHAR);
				callableStatement.registerOutParameter(6, Types.VARCHAR);
				callableStatement.registerOutParameter(7, Types.VARCHAR);
				callableStatement.registerOutParameter(8, Types.VARCHAR);
				callableStatement.execute();
				if(callableStatement.getInt(3) == 0) {
					cls();
					System.out.println("\\n\\t\\t\\t\\tSorry this book is unavailable");
					sleep(2000);
					searchBooksByKeyword(criteria, keyword);
					
				}
				else if(callableStatement.getInt(3) == -1) {
					cls();
					System.out.println("\n\t\t\t\tDatabase Server unavailable. Try later!");
					sleep(2000);
					searchBooksByKeyword(criteria, keyword);
				}
				else if(callableStatement.getInt(3) == 10) {
					cls();
					System.out.println("\n\t\t\t\t\tTake note you cannot borrow the same book twice");
					System.out.println("\n\t\t\t\t\tTry another book");
					sleep(2000);
					searchBooksByKeyword(criteria, keyword);
				}
				else if(callableStatement.getInt(3) == 1) {
					cls();
					System.out.println("\n\t\t\t\t\ta.k.a Digital Receipt");
					System.out.println("\n\t\t\t\tMembers Full-Name: \t" + callableStatement.getString(5) + " " + callableStatement.getString(6));
					System.out.println("\n\t\t\t\t    Book Borrowed: \t" + callableStatement.getString(7));
					System.out.println("\n\t\t\t\t    Date Borrowed: \t" + new java.sql.Date(System.currentTimeMillis()));
					System.out.println("\n\t\t\t\t   Date of Return: \t" + callableStatement.getString(4));
					System.out.println("\n\t\t\t\tPrinting Recieipt...\t");
					sleep(7000);
					cls();
					System.out.println("\n\t\t\t\tRedirecting to Home Page..");
					sleep(2500);
					membersHomePage();

				}
			} catch (NumberFormatException e) {
				cls();
				System.out.println("\n\t\t\t" + e.getMessage());
				System.out.println("\n\t\t\tInvalid ISBN!");
				sleep(2000);
				searchBooksByKeyword(criteria, keyword);
			}
			
		} catch (Exception e) {
			System.out.println("\n\t\t\t" + e.getMessage());
			System.out.println("\n\t\t\tSerchFailed");
			sleep(2000);
			queryBooks(criteria);
		}
	}
	
	private final void connect() throws ClassNotFoundException, SQLException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		connection = DriverManager.getConnection(url,uname,dbPassword);
	}
}
