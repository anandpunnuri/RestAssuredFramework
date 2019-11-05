package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.lang.reflect.*;

public class DBUtility {
	
	public static Connection con;
	public static Statement stmt;
	public static ResultSet rs;
	
	public void getDBConnection() throws ClassNotFoundException, SQLException
	{
	Class.forName("com.mysql.jdbc.Driver");  
	con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sonoo","root","root"); 
	stmt=con.createStatement();
	//here sonoo is database name, root is username and password  
//	Statement stmt=con.createStatement();  
//	ResultSet rs=stmt.executeQuery("select * from emp"); 
	}
	
	public static String[] getContentTypeDetails(String item)
	{
		String[] CTdetails = new String[3];
		try {
			rs = stmt.executeQuery("select RECORDID, REFERENCEKEY, NAME from CONTENTCHANNEL where NAME='"+item+"'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int i=0,j=1;
		try {
			while(rs.next())
			{
				CTdetails[i]=rs.getString(j);
				i++;
				j++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return CTdetails;
	}
	
//	public static String[] getViewDetails(String item) throws SQLException
//	{
//		String[] Viewdetails = new String[3];
//		stmt=con.createStatement();
//		rs = stmt.executeQuery("select RECORDID, REFERENCEKEY, NAME from CONTENTCHANNEL where NAME='"+item+"'");
//		int i=0,j=1;
//		while(rs.next())
//		{
//			CTdetails[i]=rs.getString(j);
//			i++;
//			j++;
//		}
//		return CTdetails;
//	}
	
}
