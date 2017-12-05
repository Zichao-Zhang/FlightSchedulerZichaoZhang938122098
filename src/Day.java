
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author zhang
 */
public class Day {
    private static final String URL = "jdbc:derby://localhost:1527/FlightSchedulerDBZichaoZhang938122098";
    private static final String USERNAME ="java";
    private static final String PASSWORD ="java";
    
    private static Connection connection;
    private static PreparedStatement  seletAllDate;
    private static PreparedStatement  insertDay;
    
   public Day ()
   {
       try
        {
          connection=DriverManager.getConnection(URL,USERNAME,PASSWORD);
          seletAllDate=connection.prepareStatement("SELECT*FROM DATE");
          insertDay=connection.prepareStatement("INSERT INTO DATE (DATE) values (?)");
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            System.exit(1);
        }
    }
   
   public static List<Date> getAllDay()
   {
           List<Date> result = null;
           ResultSet resultSet = null;
          
           try
           {
             resultSet =seletAllDate.executeQuery();
             result = new ArrayList<Date>();
             while (resultSet.next())
             {
                 result.add((resultSet.getDate("DATE")));
             }
             
           }
          catch(SQLException sqlException)
          {
            sqlException.printStackTrace(); 
          }
          
           finally
           {
               try
               {
                   resultSet.close();
               }
               catch(SQLException sqlException)
               {
                    sqlException.printStackTrace(); 
               }
           }
           
           return result;
   }      
   
   public static int addDay(Date day)
        {
            int result = 0;
            try 
            {
                insertDay.setDate(1,day);
                result = insertDay.executeUpdate();  
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
   
   

    
}
