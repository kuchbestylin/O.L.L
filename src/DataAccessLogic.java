

import java.sql.*; //This is step 1

/**
 * 
 * 
 * <h1>How SQL SERVER JDBC works</h1>
 * 
 * <h6>When ever you work with Microsoft JDBC you have to follow these 7 steps:</h6>
 * <ol>
 * 		<li>Import the package --> java.sql</li>
 * 		<li>Load and Register the driver --> com.microsoft.sqlserver.jdbc</li>
 * 		<li>Create a connection</li>
 * 		<li>Create a statement</li>
 * 		<li>Execute the query</li>
 * 		<li>Process the results</li>
 * 		<li>Close connection</li>
 * </li>
 * 
 * @author Takudzwa Kucherera
 *
 */
public abstract class DataAccessLogic extends GlobalMembers implements IDataAccessObject{
	String query;
	Connection connection;
	Statement statement;
	ResultSet rs;
	String userFeedback;
	CallableStatement callableStatement;
	PreparedStatement ps;
	String determiner;
	

	/**
	 * 
	 */
	public DataAccessLogic() {
		super();
	}
	
	/**
	 * 
	 */
	public DataAccessLogic(String email, String password, Member member) 
	{
		currentMember.setEmail(email.toLowerCase());
		currentMember.setPassword(password);
		this.verifyCredentials(email, password, member); 
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
	 */
	@Override
	public void registration(Member member) {
		try 
		{
			connect();
			query = "{call registerNewMember (?,?,?,?,?,?,?,?)}";
			callableStatement = connection.prepareCall(query);
			callableStatement.setString(1, member.getFirstName());
			callableStatement.setString(2, member.getSurname());
			callableStatement.setDate(3, member.getdOB());
			callableStatement.setString(4, member.getHomeAddress());
			callableStatement.setString(5, "Paid".toLowerCase());
			callableStatement.setString(6, member.getEmail().toLowerCase());
			callableStatement.setString(7, member.getPassword());
			callableStatement.registerOutParameter(8, Types.INTEGER);
			callableStatement.execute();
			if (callableStatement.getInt(8) == 0) userFeedback = "\n\t\t\t\tUser already registered";
			else userFeedback = (callableStatement.getInt(8) == 1) ? "\n\t\t\t\tMember registration succesfull!" : "\n\t\t\t\tRegistration failed!";
			cls();
			System.out.println(userFeedback + " : with " + callableStatement.getInt(8) + " row(s) affected");
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
	 */
	@Override
	public void verifyCredentials(String signInEmail, String password, Member member) {
		
		try {
			connect();
			callableStatement = connection.prepareCall("{call credentialsValidation (?,?,?,?,?,?,?,?)}");
			callableStatement.setString(1, signInEmail);
			callableStatement.setString(2, password);
			callableStatement.registerOutParameter(3, Types.INTEGER);
			callableStatement.registerOutParameter(4, Types.INTEGER);
			callableStatement.registerOutParameter(5, Types.VARCHAR);
			callableStatement.registerOutParameter(6, Types.VARCHAR);
			callableStatement.registerOutParameter(7, Types.DATE);
			callableStatement.registerOutParameter(8, Types.VARCHAR);
			callableStatement.executeQuery();
			currentMember.setId(callableStatement.getInt(4));
			cls();
			userFeedback = (callableStatement.getInt(3) == 1) ? ( GREEN_BOLD_BRIGHT + "\n\t\t\t\tValidation Successful!") : ( RED_BOLD_BRIGHT + "\n\t\t\t\tEmail Address or Password Invalid!");
			System.out.println(userFeedback + R);
			sleep(2000);	
			if(callableStatement.getInt(3) == 1) {
				member.setId(callableStatement.getInt(4));
				member.setFirstName(callableStatement.getString(5));
				member.setSurname(callableStatement.getString(6));
				member.setdOB(callableStatement.getDate(7));
				member.setHomeAddress(callableStatement.getString(8));
				getBookSections();
				loadMusicList();
				Console.membersHomePage();
			}
			else Console.signIn(sc);
			callableStatement.close();
			connection.close();
			
		} catch (Exception e) {
			System.out.println(e);
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
	 */
	@Override
	public void getBookSections() {
		try {
			connect();
			ps = connection.prepareStatement("\n\t\t\t\tSelect sectionName from section");
			boolean hasResultSet = ps.execute();
			if(hasResultSet) {
				rs = ps.getResultSet();
				int k = 0;
				while(rs.next()) {
					//bookSections.add(rs.getString(1));
					bookSections[k++] = rs.getString(1);
				}
			}
			ps.close();
			connection.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
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
	 */
	@Override
	public void searchBooksByKeyword(int criteria, String keyword) {


		try {
			connect();
			if(keyword.equals("*****")) {
				ps = connection.prepareStatement("Select ISBN, title, edition, author, publishDate, description FROM book " + determiner);

			}
			else {
				determiner = "where title like (?) AND isBorrowed = 0";
				if(criteria == 2) determiner = "where title like (?) AND isBorrowed = 0";
				else if(criteria == 3) determiner = "where author like (?) AND isBorrowed = 0";
				else if(criteria == 4) determiner = "where category like (?) AND isBorrowed = 0";

				String key = "%" + keyword + "%";
				ps = connection.prepareStatement("Select ISBN, title, edition, author, publishDate, description FROM book " + determiner);
				ps.setString(1, key);
			}
			String isbn = "";
			boolean hasResultset = ps.execute();
			if(hasResultset) {
				rs = ps.getResultSet();
				cls();
				System.out.println("\n\t\t\t\t\t"+BLACK_BOLD_BRIGHT+" Type "+BLUE_BOLD_BRIGHT+"\"Exit\""+BLACK_BOLD_BRIGHT+" to return" + R);
				System.out.println("\n\t\t\t\t\t    "+BU+"AVAILABLE BOOKS"+R);
				while(rs.next()) {
					System.out.println("\n\t\t\t\t"+BLACK_BRIGHT+"======================================="+R+"\n");
					System.out.println("\t\t\t\t         ISBN : " + rs.getString(1));
					System.out.println("\t\t\t\t        TITLE : " + rs.getString(2));
					System.out.println("\t\t\t\t      EDITION : " + rs.getString(3));
					System.out.println("\t\t\t\t       AUTHOR : " + rs.getString(4));
					System.out.println("\t\t\t\t PUBLISH_DATE : " + rs.getDate(5));
					System.out.println("\t\t\t\t  DISCRIPTION : " + rs.getString(6) + "\n");
				}
			}
			System.out.print("\n\n\t\t\t\t"+BU+"Enter ISBN:"+R+" "+PURPLE_BOLD_BRIGHT);
			try {
				isbn = sc.nextLine();
				System.out.println(R);
				if(isbn.equalsIgnoreCase("exit"))Console.membersHomePage();
				Integer.valueOf(isbn);
				connect();
				callableStatement = connection.prepareCall("{call loanBook (?,?,?,?,?,?,?,?)}");
				callableStatement.setInt(1, currentMember.getId());
				callableStatement.setString(2, isbn);
				callableStatement.registerOutParameter(3, Types.INTEGER);
				callableStatement.registerOutParameter(4, Types.DATE);
				callableStatement.registerOutParameter(5, Types.VARCHAR);
				callableStatement.registerOutParameter(6, Types.VARCHAR);
				callableStatement.registerOutParameter(7, Types.VARCHAR);
				callableStatement.registerOutParameter(8, Types.VARCHAR);
				callableStatement.execute();
				if(callableStatement.getInt(3) == -10) {
					cls();
					System.out.println("\n\t\t\t\t"+RED_BOLD_BRIGHT+"Invalid Userid!"+R);
					sleep(1000);
					System.out.println("\n\t\t\t\t"+BLACK_BRIGHT+"Contact Administrator"+R);
					sleep(2000);
					searchBooksByKeyword(criteria, keyword);
				}
				else if(callableStatement.getInt(3) == 0) {
					cls();
					System.out.println("\n\t\t\t\t"+BLACK_BOLD_BRIGHT+"Sorry no more copies for this book."+R);
					sleep(4000);
					searchBooksByKeyword(criteria, keyword);
					
				}
				else if(callableStatement.getInt(3) == -1) {
					serverError();
					sleep(2000);
					searchBooksByKeyword(criteria, keyword);
				}
				else if(callableStatement.getInt(3) == 10) {
					cls();
					System.out.println("\n\t\t\t\t\t"+RED_BRIGHT+"Take note you cannot borrow the same book twice"+R);
					System.out.println("\n\t\t\t\t\t"+BLACK_BRIGHT+"Try another book"+R);
					sleep(2000);
					searchBooksByKeyword(criteria, keyword);
				}
				else if(callableStatement.getInt(3) == 1) {
					cls();
					System.out.println("\n\t\t\t\t\t  "+BU+"Open Learning Library"+R);
					System.out.println("\n\t\t\t\t\t"+BLACK_BRIGHT+"preview - Digital Receipt"+R);
					System.out.println("\n\t\t\t\t"+BLACK_BRIGHT+"======================================="+R);
					System.out.println("\n\t\t\t\t "+BLACK_BRIGHT+"Members FullName:"+R+BLUE+" Takudzwa Kucherera\t"+R);
					System.out.println("\n\t\t\t\t    "+BLACK_BRIGHT+"Book Borrowed:"+R+CYAN+" Pure Math\t"+R);
					System.out.println("\n\t\t\t\t    "+BLACK_BRIGHT+"Date Borrowed:"+R+GREEN+" 2022-04-11\t"+R);
					System.out.println("\n\t\t\t\t   "+BLACK_BRIGHT+"Date of Return:"+R+YELLOW+" 2022-05-10\t"+R);
					System.out.println("\n\t\t\t\t"+BLACK_BRIGHT+"======================================="+R);		
					System.out.print("\n\n\t\t\t\t\t"+BLACK_BRIGHT+"Press Enter to continue:"+R+" ");
					sc.nextLine();
					cls();
					System.out.print("\n\n\t\t\t\t"+GREEN_BOLD_BRIGHT+"Redirecting to Home Page");
					loading();
					System.out.println(R);
					sleep(2500);
					Console.membersHomePage();

				}
			} catch (NumberFormatException e) {
				cls();
				System.out.println("\n\t\t\t\t"+RED_BOLD_BRIGHT+"Invalid ISBN!"+R);
				sleep(2000);
				searchBooksByKeyword(criteria, keyword);
			}
			
		} catch (Exception e) {
			serverError();
			sleep(2000);
			Console.queryBooks(criteria);
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
	 */
	@Override
	public int printBorrowedBooks() {
		int flagBit = 1;
		try {
			connect();
			callableStatement = connection.prepareCall("{call returnBorrowedBooks (?)}");
			callableStatement.setInt(1, currentMember.getId());
			boolean x = callableStatement.execute();
			if(x == true) {
				int cntr = 0;
				rs = callableStatement.getResultSet();
				while(rs.next()) {
					System.out.println("\t\t\t\t" + ++cntr + ": ISBN = " + rs.getString(1) + " Book-Name = " + rs.getString(2) + " Return-Date = " + rs.getDate(3));
				}
				if(cntr < 1) {
					System.out.println("\n\t\t\t\t"+GREEN_BOLD_BRIGHT+"  No Pending Deadlines!"+R);
					flagBit = 0;
				}
			}
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
		}
		return flagBit;
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
	 */
	@Override
	public void hasReturned(String isbnChoice) {
		try {
			cls();
			connect();
			callableStatement = connection.prepareCall("{call hasReturned (?,?,?)}");
			callableStatement.setInt(1, currentMember.getId());
			callableStatement.setString(2, isbnChoice);
			callableStatement.registerOutParameter(3, Types.INTEGER);
			callableStatement.execute();
			if (callableStatement.getInt(3) == 1) {
				System.out.println("\n\t\t\t\t"+GREEN_BOLD_BRIGHT+"Book returned successfully!"+R);
				sleep(3000);
				System.out.print("\n\n\t\t\t\t"+BLACK_BRIGHT+"Press Enter to continue:"+R);
				sc.nextLine();
				Console.accountRealatedInfo();
			}
			else throw new Exception();
		} catch (Exception e) {
			System.out.println("\n\t\t\t\t"+RED+"Returning book failed!"+R);
			sleep(2000);
			System.out.print("\n\n\t\t\t\t"+BLACK_BRIGHT+"Press Enter to continue:"+R);
			sc.nextLine();			
			Console.returnBook();
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
	 */
	@Override
	public void printAllBooksIBorrowed() {
		try {
			connect();
			callableStatement = connection.prepareCall("{call printAllBooksIBorrowed (?)}");
			callableStatement.setInt(1, currentMember.getId());
			boolean x = callableStatement.execute();
			if(x == true) {
				int cntr = 0;
				rs = callableStatement.getResultSet();
				while(rs.next()) {
					System.out.println("\t\t\t\t" + ++cntr + ": Book-Name = " + rs.getString(1) + "\t Days-Left = " + rs.getInt(2) +"\n");
				}
				if(cntr < 1) {
					System.out.println("\n\n\n\t\t\t\tUnfortunately you do not have any pending books");
				}
				
			}

			
			
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
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
	 */
	@Override
	public int changePassword(String p2) {
		int flagBit = 0;
		try {
			connect();
			callableStatement = connection.prepareCall("{call changePassword(?,?)}");
			callableStatement.setInt(1, currentMember.getId());
			callableStatement.setString(2, p2);
//			callableStatement.registerOutParameter(3, Types.INTEGER);
			int x = callableStatement.executeUpdate();
			flagBit = (x == 1) ? 1 : 0;
				
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
		}
		return flagBit;	
		
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
	 */
	@Override
	public void searchSongs(int i) {
		try {
			connect();
//			System.out.println("\t\t\t\t" + ++cntr + ": Title : " + rs.getString(1) + "\t Artist = " + rs.getInt(2) + "\t"+ df.format(rs.getInt(3))  +"\n");
			callableStatement = connection.prepareCall("{call loadMusic}");
			callableStatement.execute();
			rs = callableStatement.getResultSet();
			int counter = 0;
			while(rs.next()) {
				treeMapMusic.put(rs.getInt(1), rs.getString(6));
				System.out.println("\n\t\tkey:" + rs.getInt(1) +"    title:" + rs.getString(2) + " by " + rs.getString(3) + "\tgenre:" + rs.getString(4));
				counter++;
			}
			if(counter < 1) {
				System.out.println("\n\t\t\t\t"+RED+"Unfortunately no Music was found"+R);
				sleep(3000);
				Console.musicSessions();
			}
		} catch (Exception e) {
			serverError();
			Console.musicSessions();
		}
		
	}
	
	
	private final void connect() throws ClassNotFoundException, SQLException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		connection = DriverManager.getConnection(url,userName,databasePassword);
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
	 */
	@Override
	public void searchSongs(int i, String s) {
		// TODO Auto-generated method stub
		try{
			connect();
			callableStatement = connection.prepareCall("{call searchSongs (?,?)}");
			callableStatement.setInt(1, i);
			callableStatement.setString(2, s);
			callableStatement.execute();
			rs = callableStatement.getResultSet();
			int counter = 0;
			while(rs.next()) {
				treeMapMusic.put(rs.getInt(1), rs.getString(6));
				System.out.println("\n\t\tkey:" + rs.getInt(1) +"    title:" + rs.getString(2) + " by " + rs.getString(3) + "\tgenre:" + rs.getString(4));
				counter++;
			}
			if(counter < 1) {
				System.out.println("\n\t\t\t\t"+RED+"Unfortunately no Music was found"+R);
				sleep(3000);
				Console.musicSessions();
			}
			
		} catch (Exception e) {
			serverError();
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
	 */
	@Override
	public void loadMusicList() {
		try {
			connect();
			callableStatement = connection.prepareCall("{call loadMusic}");
			boolean x = callableStatement.execute();
			if(x == true) {
				rs = callableStatement.getResultSet();
				while(rs.next()) {
					
					lst.addRecord(rs.getString(3), rs.getString(1), rs.getString(2), rs.getString(4), rs.getString(5), rs.getString(6));
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
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
	 */
	@Override
	public void allBooksInSection(Integer valueOf) {
		int section = 0;
		switch (valueOf) {
		case 1:
			section = 6; //Mathematics
			break;
		case 2:
			section = 5; //Marketing
			break;
		case 3:
			section = 4; //Personal Development
			break;
		case 4:
			section = 3; //Construction
			break;
		case 5:
			section = 2; //History
			break;
		case 6:
			section = 1; //Science Fiction
			break;
			
		default:
			break;
		}
		determiner = "where shelfNumber = "+section+" AND available > 0";
		searchBooksByKeyword(section, "*****");
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
	 */
	@Override
	public void retrievePodcasts(String nextLine) {
		try {
			connect();
			callableStatement = connection.prepareCall("{call retrievePodcasts (?)}");
			callableStatement.setString(1, nextLine);
			boolean x = callableStatement.execute();
			if(x == true) {
				rs = callableStatement.getResultSet();
				int counter = 0;
				while(rs.next()) {
					treeMapPodcasts.put(rs.getInt(1), rs.getString(5));
					System.out.println("\n\t\t\tkey:" + rs.getInt(1) + "\tSubject:" +rs.getString(2) + "\t\tSpeaker:" + rs.getString(3));
					counter++;
				}
				if(counter < 1) {
					System.out.println(R);
					System.out.println("\n\t\t\t\t"+RED+"Unfortunately no Podcasts are Currently available"+R);
					sleep(2000);
					System.out.println("\n\t\t\t\t"+BLACK_BRIGHT+"Redirection to previous Menu"+R);
					sleep(2000);
					Console.membersHomePage();
				}
			}
			
		} catch (Exception e) {
			serverError();
			Console.membersHomePage();
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
	 */
	@Override
	public void retriveMembers() {
		cls();
		try {
			connect();
			callableStatement = connection.prepareCall("{call retrieveMembers(?)}");
			callableStatement.registerOutParameter(1, Types.INTEGER);
			boolean x = callableStatement.execute();
			if(x == true) {
				int count = 0;
				rs = callableStatement.getResultSet();
				System.out.println("\n\t\t\t\t\t\t\tResults\n");
				while(rs.next()) {
					System.out.println("\n\t\t\t\t" + count + "\tMemberID: " + rs.getInt(1) + "\tFullName: " + rs.getString(2) + "\tEmail: " + rs.getString(3));
					count++;
				}
				if(count < 0) System.out.println("\n\t\t\t\tCurrently no members available");
				System.out.println("\n\n\t\t\t\tTotalMembers = " + count);
			}
			
		} catch (Exception e) {
			System.out.println("\n\t\t\t\t" + e.getMessage());
			serverError();
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
	 */
	@Override
	public int storeNewBooks(Book newBook) {
		int successFlag = 0;
		try {
			connect();
			callableStatement = connection.prepareCall("{call storeNewBooks(?,?,?,?,?,?,?,?,?,?,?)}");
			callableStatement.setString(1, newBook.getISBN());
			callableStatement.setString(2, newBook.getTitle());
			callableStatement.setString(3, newBook.getEdition());
			callableStatement.setString(4, newBook.getAuthor());
			callableStatement.setDate(5, newBook.getPublishDate());
			callableStatement.setString(6, newBook.getDescription());
			callableStatement.setString(7, newBook.getCategory());
			callableStatement.setInt(8, newBook.getIsBorrowed());
			callableStatement.setInt(9, newBook.getShelfNumber());
			callableStatement.setInt(10, newBook.getAvailable());
			callableStatement.registerOutParameter(11, Types.INTEGER);
			callableStatement.execute();
			if(callableStatement.getInt(11) == 1) successFlag = 1;
		} catch (Exception e) {
			serverError();
		}
		return successFlag;
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
	 */
	@Override
	public int uploadNewSong(Music newSong) {
		int successFlag = 0;
		try {
			connect();
			callableStatement = connection.prepareCall("{call uploadNewSong(?,?,?,?,?,?)}");
			callableStatement.setString(1, newSong.getTitle());
			callableStatement.setString(2, newSong.getArtist());
			callableStatement.setString(3, newSong.getGenre());
			callableStatement.setString(4, newSong.getTime());
			callableStatement.setString(5, newSong.getPath());
			callableStatement.registerOutParameter(6, Types.INTEGER);
			callableStatement.execute();
			successFlag = callableStatement.getInt(6);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			serverError();
			return successFlag;
		}
		return successFlag;
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
	 */
	@Override
	public int deleteMember(int id) {
		int successFlag = 0;
		try {
			connect();
			callableStatement = connection.prepareCall("{call retainMember (?,?,?)}");
			callableStatement.setInt(1, id);
			callableStatement.registerOutParameter(2, Types.VARCHAR);
			callableStatement.registerOutParameter(3, Types.INTEGER);
			callableStatement.execute();
			if(callableStatement.getInt(3) == 0) {
				System.out.println(RED_BOLD_BRIGHT+"\n\t\t\t\tMember with ID = " + id + " does not exist in database"+R);
				System.out.print("\n\n\t\t\t\t"+BU+"Press Enter to return:"+R);
				sc.nextLine();
			}
			else {
				System.out.println("\t\t\t\t1: Type "+RED_BRIGHT+"\"accept\""+R+" to proceed with deleting the following Member: ");
				System.out.println("\t\t\t\t2: Type anything else to cancel process");
				System.out.println("\n\t\t\t\tMemberId=" + id + "\tFull Name=" + callableStatement.getString(2));
				System.out.print("\n\n\t\t\t\t"+BU+"Enter:"+R+" "+RED);
				choice = sc.nextLine();
				System.out.println(R);
				if(choice.equalsIgnoreCase("accept")) {
					callableStatement = connection.prepareCall("{call deleteMember (?,?)}");
					callableStatement.setInt(1, id);
					callableStatement.registerOutParameter(2, Types.INTEGER);
					callableStatement.executeUpdate();
					if(callableStatement.getInt(2) == 1) successFlag = 1;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			sc.next();
			serverError();
		}
		return successFlag;
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
	 */
	@Override
	public int queryMembers(int i, String nextLine) {
		int successFlag = 0;
		try {
			connect();
			callableStatement = connection.prepareCall("{call queryMembers (?,?)}");
			callableStatement.setInt(1, i);
			callableStatement.setString(2, nextLine);
			boolean x = callableStatement.execute();
			if(x == true) {
				rs = callableStatement.getResultSet();
				int counter = 0;
				cls();
				System.out.println("\n\t\t\t\t\t\tSearch Result\n");
				System.out.println("\n\t\t\t\t"+BU+"======================================"+R);
				while(rs.next()) {
					System.out.println("\t\t\t\t  "+ counter +"\tID = " + rs.getInt(1) + "\tFullName = " + rs.getString(2) + "\tTotalBooksBorrowed = " + rs.getInt(3));
					counter++;
				}
				if(counter < 1) System.out.println("\n\t\t\t\tUnfortunately no members were found");
				else successFlag = 1;
				System.out.println("\n\t\t\t\t"+BU+"======================================"+R);
				
			}
			else throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
			serverError();
		}
		return successFlag;
	}

}
