import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;

public class JavaIntern {
	static String URL = "localhost:3306/";
	static String DATABASE_NAME = "STUDENT";
	static String USERNAME = "root";
	static String PASSWORD = "0000";
	    
	public static Connection getConnection() {
		Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + DATABASE_NAME, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
        return con;
	}
	
	public void insertStudent() {
		Connection con = getConnection();
		PreparedStatement ps;
		try {
			ps = con.prepareStatement("insert into Student values (?, ?, ?, ?);");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			
			do {
				System.out.print("Enter Student Roll Number: ");
				int rollNo = Integer.parseInt(br.readLine());
				
				System.out.print("Enter Student Name: ");
				String name = br.readLine();
				
				System.out.print("Enter Student DOB (yyyy-mm-dd): ");
				String dob = br.readLine();
				
				System.out.print("Enter Student DOJ (yyyy-mm-dd): ");
				String doj = br.readLine();
				
				ps.setInt(1, rollNo);
				ps.setString(2, name);
				ps.setString(3, dob);
				ps.setString(4, doj);
				
				int i = ps.executeUpdate();
				System.out.println(i +" record affected\n");
				
				System.out.print("Do you want to continue (Enter 'n' for No): ");
				String s = br.readLine();
				if(s.equals("n"))
					break;
			} while(true);
			
			System.out.println("Thank You");
			con.close();
			br.close();
		} catch (SQLException | NumberFormatException | IOException e) {
			e.printStackTrace();
		}
		
	}

	public void updateStudent() {
		try {
			Connection con = getConnection();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			
			System.out.print("Enter Student Roll Number: ");
			int rollNo = Integer.parseInt(br.readLine());
			
			System.out.print("Enter Student Name: ");
			String name = br.readLine();
			
			String query = "update STUDENT set STUDENT_NAME=? where STUDENT_NO=?";
			
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, name);
			ps.setInt(2, rollNo);
			
			int i = ps.executeUpdate();
			
			System.out.println(i +" records affected");
			
			con.close();
			
		} catch(IOException | SQLException e ){
			e.printStackTrace();
		}
	}

	public void deleteStudent() {
		try {
			Connection con = getConnection();
			Statement stmt = con.createStatement();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			
			System.out.print("Enter Student Roll Number: ");
			int rollNo = Integer.parseInt(br.readLine());
			String query = "delete from Student where student_no="+rollNo;
			
			int result = stmt.executeUpdate(query);
			System.out.println(result +" record affected");
			con.close();
		} catch (SQLException | NumberFormatException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getAllStudents() {
		try {
			Connection con = getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from student;");
			
			while(rs.next()) {
				System.out.println("Student Roll Number: " + rs.getInt(1));
				System.out.println("Student Name: " + rs.getString(2));
				System.out.println("Student DOB: " + rs.getString(3));
				System.out.println("Student DOj: " + rs.getString(4));
				System.out.println("*********************************");
			}
			
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void getOneStudent()  throws NumberFormatException, IOException {
		try {
			Connection con = getConnection();
			Statement stmt = con.createStatement();
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			
			System.out.print("Enter Student Roll Number: ");
			int rollNo = Integer.parseInt(br.readLine());
			ResultSet rs = stmt.executeQuery("select * from student where student_no = " + rollNo);
			
			if (!rs.isBeforeFirst() && rs.getRow() == 0) 
				System.out.println("no data");
			else {
				while(rs.next()) {
					System.out.println("Student Roll Number: " + rs.getInt(1));
					System.out.println("Student Name: " + rs.getString(2));
					System.out.println("Student DOB: " + rs.getString(3));
					System.out.println("Student DOj: " + rs.getString(4));
					System.out.println("*********************************");
				}
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			int ch;
			
			JavaIntern ji = new JavaIntern();
			
			do {
				System.out.println("1. Insert student data into Student table: ");
				System.out.println("2. Update student data into Student table: ");
				System.out.println("3. Delete student data from Student table: ");
				System.out.println("4. Get a list of all students: ");
				System.out.println("5. Get one student information depending on the student id filter: ");
				System.out.println("6. Exit: ");
				System.out.print("Enter your choice (1-6): ");
				ch = Integer.parseInt(br.readLine());
				
				switch(ch) {
				case 1: System.out.println();
				ji.insertStudent();
				System.out.println();
				break;
				
				case 2: System.out.println();
				ji.updateStudent();
				System.out.println();
				break;
				
				case 3: System.out.println();
				ji.deleteStudent();
				System.out.println();
				break;
				
				case 4: System.out.println();
				ji.getAllStudents();
				System.out.println();
				break;
				
				case 5: System.out.println();
				ji.getOneStudent();
				System.out.println();
				break;
				
				case 6: System.out.println("\nExit\n");
				break;
				
				default: System.out.println("Invalid Choice\n");
				}
				
			}while(ch != 6);
			
			br.close();
		} catch(IOException e) {
				e.printStackTrace();
		}	
	}
}
