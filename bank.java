package javaMySQLtest;

import java.sql.*;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class javasqltst {

	public static void main(String[] args) throws SQLException
	{
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the  Source Account Number:");
		int srcno=sc.nextInt();
		System.out.println("Enter the Destination Account Number:");
		int destno = sc.nextInt();
		System.out.println("Enter the Amount To Transfer:");
		int amt=sc.nextInt();
		//creating JDBC Objects
		
		
		String url = "jdbc:mysql://localhost:3306/bank";
		String username = "root";
		String password = "your-database-password";
		
		Connection conn = null;
		Statement stmt = null;
		
		try {
			conn = DriverManager.getConnection(url, username, password);
			stmt = conn.createStatement();
			conn.setAutoCommit(false);
			//String sql = "UPDATE transactions SET totalbalance = totalbalance-"+amt+" WHERE accountnumber = "+srcno;
			//String sqla = "UPDATE transactions SET totalbalance = totalbalance-"+amt+" WHERE accountnumber = "+destno;
			
		
			PreparedStatement statement1 = conn.prepareStatement( "UPDATE transactions SET totalbalance = totalbalance-"+amt+" WHERE accountnumber = "+srcno);
			PreparedStatement statement2 = conn.prepareStatement( "UPDATE transactions SET totalbalance = totalbalance+"+amt+" WHERE accountnumber = "+destno);
			statement1.executeUpdate();
			statement2.executeUpdate();
			
			int res[]= stmt.executeBatch();
			boolean flag = false;
			for(int i = 0;i<res.length;++i)
			{
				if(res[i]==0) 
				{
					flag=true;
					break;
				}
			}
			if(flag==true)
			{
				conn.rollback();
				System.out.println("Transaction is rollback,Amount is not trasfered!");
			}
			else
			{
				conn.commit();
				System.out.println("Transaction is commited,Amount is trasfered Succesfully!");
			}
			
			
			System.out.println("Connected to database");
			System.out.println("Update Completed");
			
			
			}
		catch (SQLException e) {
			System.out.println("oops error");
			e.printStackTrace();
		}
		finally { 
			if (stmt != null) {
				stmt.close();
			}
			if(conn!=null) {
				conn.close();
			}
		}
		
		
}

}