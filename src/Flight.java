
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.util.List;
import java.util.ArrayList;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author zhang
 */
public class Flight {
    private static final String URL = "jdbc:derby://localhost:1527/FlightSchedulerDBZichaoZhang938122098 ";
    private static final String USERNAME ="java";
    private static final String PASSWORD ="java";
   
    private static Connection connection;
    private static PreparedStatement  seletAllFlight;
    private static PreparedStatement  insertFlight;
     private static PreparedStatement deleteFlight;
    
   public Flight ()
   {
       try
        {
          connection=DriverManager.getConnection(URL,USERNAME,PASSWORD);
          seletAllFlight=connection.prepareStatement("SELECT*FROM FLIGHTS");
          insertFlight=connection.prepareStatement("INSERT INTO FLIGHTS (NAME,SEATS) values (?,?)"); 
          deleteFlight=connection.prepareStatement("DELETE FROM FLIGHTS WHERE NAME =?"); 
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            System.exit(1);
        }
    }
   
   public static List<String> getAllFlight()
   {
           List<String> results=null;
           ResultSet resultSet = null;
          
           try
           {
             resultSet  =seletAllFlight.executeQuery();
             results = new ArrayList<String>();
             while (resultSet.next())
             {
                 results.add(resultSet.getString(1));
             }
             
           }
          catch(SQLException sqlException)
          {
            sqlException.printStackTrace(); 
          }
           
           return results;
                         
   }   
   
     public static int addFlight(String flightNum,int seat)
        {
            int result = 0;
            try 
            {
                insertFlight.setString(1,flightNum);
                insertFlight.setInt(2,seat);
                
                
                result = insertFlight.executeUpdate();  
            }
            catch(SQLException sqlException)
            {
                sqlException.printStackTrace();
               
            }
            return result;
        }
    
    public void close()
    {
        try 
        {
            connection.close();
        }
        catch (SQLException sqlException)
        {
             sqlException.printStackTrace();
        }
    }
    public static int cancelBooking(String flightNum)
    {
        int result =0;
        try
        {
           deleteFlight.setString(1,flightNum);
            result = deleteFlight.executeUpdate();
        }
        catch(SQLException sqlException)
            {
                sqlException.printStackTrace();
               
            }
            return result;
    }
   /* public static String [] getAllFlight()
    {
        String [] flights =null;
        try
        {
            result  =seletAllFlight.executeQuery();
            
        }
        catch(SQLException sqlException)
          {
            sqlException.printStackTrace(); 
          }
        
    }*/
}
