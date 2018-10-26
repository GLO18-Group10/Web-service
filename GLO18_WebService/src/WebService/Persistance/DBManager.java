package WebService.Persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Robin
 */
public class DBManager {

    private final String dbURL = "jdbc:postgresql://tek-mmmi-db0a.tek.c.sdu.dk:5432/si3_2018_group_10_db";
    private final String dbUsername = "si3_2018_group_10";
    private final String dbPassWord = "Punic3;drops";

    /**
     * this is test method to test the database connection
     *
     * @return a string of data from the database
     */
    public String getTest() {
        String testResult = "";
        try (Connection db = DriverManager.getConnection(dbURL, dbUsername, dbPassWord); Statement statement = db.createStatement()) {
            ResultSet result = statement.executeQuery("SELECT * FROM testTable");

            StringBuilder sb = new StringBuilder();
            while (result.next()) {
                sb.append(result.getString("col01") + ";");
                sb.append(result.getString("col02") + ";");
            }
            testResult = sb.toString();
        } catch (SQLException ex) {
            System.out.println("SQL exception");
            ex.printStackTrace();
        }
        return testResult;
    }

    /**
     * a test method to set something in the database testTable
     */
    public void setTest(String c1, String c2) {
        try (Connection db = DriverManager.getConnection(dbURL, dbUsername, dbPassWord); Statement statement = db.createStatement()) {
            String s = "insert into testTable values('" + c1 + "','" + c2 + "')";
            statement.execute(s);

        } catch (SQLException ex) {
            System.out.println("SQL exception");
            ex.printStackTrace();
        }

    }
    
/**
    Method to get the customer info
    @param customerID: the id of the customer that wants their info
    @return a string of the data
    */
    public String getCustomerInfo(String customerID) {
        String testResult = "";
        try (Connection db = DriverManager.getConnection(dbURL, dbUsername, dbPassWord); Statement statement = db.createStatement()) {
            ResultSet result = statement.executeQuery("SELECT name, birthday, phonenumber, address, email FROM customer WHERE id = '" + customerID + "'");

            StringBuilder sb = new StringBuilder();
            while (result.next()) {
                sb.append(result.getString("name") + ";");
                sb.append(result.getString("birthday") + ";");
                sb.append(result.getString("phonenumber") + ";");
                sb.append(result.getString("address") + ";");
                sb.append(result.getString("email") + ";");
            }
            testResult = sb.toString();
        } catch (SQLException ex) {
            System.out.println("SQL exception");
            ex.printStackTrace();
        }
        return testResult;
    }
    
    public String getAccountBalance(String accountID){
        String Balance = "";
        try (Connection db = DriverManager.getConnection(dbURL, dbUsername, dbPassWord); Statement statement = db.createStatement()) {
            ResultSet result = statement.executeQuery("SELECT Balance FROM BankAccount WHERE ID = '" + accountID + "'");
            
            StringBuilder sb = new StringBuilder();
            while(result.next()){
                    sb.append(result.getString("Balance"));
            }
            Balance = sb.toString();
        } catch (SQLException ex) {
            System.out.println("SQL exception");
            ex.printStackTrace();
        }
        return Balance;
    }

    
    public String login(String ID, String password){
        
        String id = ID.toLowerCase();
        String testResult = "";
        //Query for admin login
        if (id.startsWith("a")) {
        
            try (Connection db = DriverManager.getConnection(dbURL, dbUsername, dbPassWord); Statement statement = db.createStatement()) {
                ResultSet result = statement.executeQuery("SELECT password FROM admin WHERE id = '"+ ID + "'");

                StringBuilder sb = new StringBuilder();
                while(result.next()){
                    sb.append(result.getString("password"));
                }
                testResult = sb.toString();
            } catch (SQLException ex) {
                System.out.println("SQL exception");
                ex.printStackTrace();
            }
        }
        // Query for customer login
        else if (id.startsWith("c")) {
        
            try (Connection db = DriverManager.getConnection(dbURL, dbUsername, dbPassWord); Statement statement = db.createStatement()) {
                ResultSet result = statement.executeQuery("SELECT password FROM customer WHERE id = '" + ID + "'");

                StringBuilder sb = new StringBuilder();
                while(result.next()){
                    sb.append(result.getString("password"));
                }
                testResult = sb.toString();
            } catch (SQLException ex) {
                System.out.println("SQL exception");
                ex.printStackTrace();
            }
        }
        return testResult;
    }
    
//main method for testing
//    public static void main(String[] args) {
//        DBManager db = new DBManager();
//        System.out.println(db.getTest());
//        db.setTest("mytest1","mytest2");
//        System.out.println(db.getTest());
//        
//    }
}
