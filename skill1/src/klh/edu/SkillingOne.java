package klh.edu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SkillingOne {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		String url="jdbc:mysql://localhost:3306/jfsd";
	    String user="root";
	    String pass="Madhu143";
	    
	    Connection con=DriverManager.getConnection(url,user,pass);
	    Statement stmt = con.createStatement();

        Scanner scanner = new Scanner(System.in);

        create_specalization_table(stmt, con);

       create_student_choice_table(stmt);

        boolean exit = false;

        while (!exit) {
            System.out.println("1. Select a specalization");
            System.out.println("2. View students choices");
            System.out.println("3. Exit the program");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    chooseSpecalization(stmt, con, scanner);
                    break;

                case 2:
                    print_student_table_data_to_console(stmt);
                    break;

                case 3:
                    exit = true;
                    break;

                default:
                    System.out.println("Please Try Again");
                    break;
            }

        }

        con.close();

        scanner.close();
    }

    public static void chooseSpecalization(Statement stmt, Connection con, Scanner sc) {
        System.out.println("Please enter your id number ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.println("Please select a specalization from the given list");
        System.out.println("Available specalizations");
        System.out.println("Specalization name      |     Slots available");
        print_available_specalizations(stmt);
        System.out.println("Please enter your preffered specalization name");
        String specalization_choice = sc.nextLine();
        if (is_selection_valid(con, specalization_choice)) {
            reduce_available_slots(con, specalization_choice);
            insert_into_student_table(con, id, specalization_choice);
            System.out.println("Specalization selected successfully");
        } else {
            System.out.println("please select a valid specalization from the given list");
        }

    }

    public static void reduce_available_slots(Connection con, String specalization_choice) {
        try {
            String reduce_available_slots_query = "UPDATE specalization SET slots_available = slots_available-1 WHERE specalization_name=?";

            PreparedStatement pstmt = con.prepareStatement(reduce_available_slots_query);

            pstmt.setString(1, specalization_choice);
            pstmt.executeUpdate();
            System.out.println("decreased available slots succesfully");
        } catch (Exception e) {
            System.out.println("Error" + e);
        }
    }

   public static void insert_into_student_table(Connection con, int id, String specalization_choice) {
        try {
            String insert_into_student_table_query = "INSERT INTO student_choice(student_id, specalization_name) VALUES(?, ?)";
            PreparedStatement pstmt = con.prepareStatement(insert_into_student_table_query);

            pstmt.setInt(1, id);
            pstmt.setString(2, specalization_choice);
            pstmt.executeUpdate();

            System.out.println("inserted into Student database succesfully");
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public static boolean is_selection_valid(Connection con, String specalization_choice) {
        try {

            boolean isValid = false;
            String is_choice_valid_query = "SELECT * FROM specalization WHERE specalization_name=?";

            PreparedStatement pstmt = con.prepareStatement(is_choice_valid_query);

            pstmt.setString(1, specalization_choice);

            ResultSet rs = pstmt.executeQuery();
            if (!rs.isBeforeFirst()) {
                return isValid;
            } else {
                rs.next();
                if (rs.getInt(2) > 1) {
                    isValid = true;
                    return isValid;
                }
            }
            return isValid;
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return false;
        }
    }

    public static void print_available_specalizations(Statement stmt) {
        try {
            ResultSet rs = stmt.executeQuery("select * from specalization where slots_available>0");
            if (!rs.isBeforeFirst()) {
                System.out.println("All specalizations are full");
            } else {
                while (rs.next()) {
                    System.out.format("%24s|%10d", rs.getString(1), rs.getInt(2));
                    System.out.println();
                    // System.out.println(rs.getString(1) + " " + rs.getInt(2));
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public static void create_student_choice_table(Statement stmt) {
        try {
            String create_student_specalization_choice_table_query = "CREATE TABLE student_choice( student_id INTEGER, specalization_name VARCHAR(22))";
            stmt.executeUpdate(create_student_specalization_choice_table_query);
            System.out.println("student specalization choice table created succesfully");
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public static void create_specalization_table(Statement stmt, Connection con) {
        try {
            String create_specalization_table_query = "CREATE TABLE specalization(specalization_name VARCHAR(22), slots_available INTEGER)";
            stmt.executeUpdate(create_specalization_table_query);
            System.out.println("specalization table created succesfully");

            String[] specalization_name = { "cloud", "AI", "big data", "gaming", "ui/ux" };
            int[] slots_available = { 22, 44, 26, 28, 24 };

            String specalization_insert_query = "INSERT INTO specalization (specalization_name, slots_available) VALUES (?,?)";

            PreparedStatement pstmt = con.prepareStatement(specalization_insert_query);

            for (int i = 0; i < specalization_name.length; i++) {
                pstmt.setString(1, specalization_name[i]);
                pstmt.setInt(2, slots_available[i]);
                pstmt.executeUpdate();
                System.out.println("Inserted " + specalization_name[i] + " data into table");
            }
            System.out.println("Inserted specalization data succesfully");
            print_specialization_table_data_to_console(stmt);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public static void print_student_table_data_to_console(Statement stmt) {
        try {
            // prints data to console
            ResultSet rs = stmt.executeQuery("select * from student_choice");
            if (!rs.isBeforeFirst()) {
                System.out.println("No data");
            } else {
                while (rs.next())
                    System.out.println(rs.getInt(1) + "  " + rs.getString(2));
            }

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public static void print_specialization_table_data_to_console(Statement stmt) {
        try {
            // prints data to console
            ResultSet rs = stmt.executeQuery("select * from specalization");
            if (!rs.isBeforeFirst()) {
                System.out.println("No data");
            } else {
                while (rs.next())
                    System.out.println(rs.getString(1) + "  " + rs.getInt(2));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
	}
}