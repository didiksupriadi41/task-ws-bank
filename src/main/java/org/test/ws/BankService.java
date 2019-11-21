package org.test.ws;

import org.json.JSONObject;
import org.json.JSONArray;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.Random;

/**
 * BankService serves bank functionality.
 */
@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class BankService {

  public static final String JDBC_MARIADB_BANK = "jdbc:mariadb://localhost:3306/bank";
  public static final String USER = "didik";
  public static final String PASSWORD = "didik";

  /**
   * DEMO: sayHello.
   *
   * @param guestname someone's name
   * @return string that say hello.
   */
  @WebMethod(operationName = "sayHello")
  public String sayHello(@WebParam(name = "guestname") final String guestname) {
    if (guestname == null) {
      return "Hello";
    }
    return "Hello " + guestname;
  }

  /**
   * DEMO: checkCard.
   *
   * @param cardNumber someone's card number
   * @return <code>true</code> if the card is exist in db
   */
  @WebMethod(operationName = "checkCard")
  public boolean checkCard(
      @WebParam(name = "cardNumber")
      final String cardNumber) {
    return true;
  }

  /**
   * DEMO: Get the first nasabah's name in db.
   *
   * @return name of the first nasabah in db
   * @throws SQLException if there are problems with the SQL
   */
  @WebMethod(operationName = "getFirstNasabah")
  public String getFirstNasabah() throws SQLException {
    try (Connection conn = DriverManager.getConnection(
        JDBC_MARIADB_BANK,
        USER,
        PASSWORD)) {
      try (Statement stmt = conn.createStatement()) {
        try (ResultSet rs = stmt.executeQuery("select * from account")) {
          rs.first();
          return rs.getString(1);
        }
      }
    }
  }

  /**
   * Get user data based on accountNumber.
   *
   * @param accountNumber String of account number.
   * @return User data.
   * @throws SQLException Triggered if there are problems with the SQL
   */
  @WebMethod(operationName = "getUserData")
  public String getUserData(@WebParam(name = "accountNumber")
      final String accountNumber)
      throws SQLException {
    JSONArray userData = new JSONArray();
    String query = "SELECT * "
      + "FROM account NATURAL JOIN transaction "
      + "WHERE account_number = ?;";

    try (Connection conn = DriverManager.getConnection(
        JDBC_MARIADB_BANK,
        USER,
        PASSWORD)) {
      try (PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(1, accountNumber);
        try (ResultSet rs = stmt.executeQuery()) {
          while (rs.next()) {
            JSONObject obj = new JSONObject();
            obj.put("name", rs.getString("name"));
            obj.put("account_number", rs.getString("account_number"));
            obj.put("balance", rs.getString("balance"));
            obj.put("transaction_time", rs.getString("transaction_time"));
            obj.put("linked_number", rs.getString("linked_number"));
            obj.put("type", rs.getString("type"));
            obj.put("amount", rs.getString("amount"));
            userData.put(obj);
          }
          return userData.toString();
        }
      }
    }
  }

  /**
   * Check transactions between two Date.
   *
   * @param linkedNumber String of linked number.
   * @param amount String of amount.
   * @param startDate String of start date.
   * @param endDate String of end date.
   * @return <code>true</code> if transaction exist, <code>false</code> otherwise.
   * @throws SQLException Triggered if there are problems with SQL
   */
  @WebMethod(operationName = "checkTransactionBetween")
  public boolean checkTransactionBetween(
      @WebParam(name = "linkedNumber") final String linkedNumber,
      @WebParam(name = "amount") final String amount,
      @WebParam(name = "startDate") final String startDate,
      @WebParam(name = "endDate") final String endDate)
      throws SQLException {

    String query = "SELECT * "
      + "FROM transaction "
      + "WHERE transaction_time > '?' "
      + "AND transaction_time < '?' "
      + "AND linked_number = '?' "
      + "AND amount = '?' "
      + "AND type = 'K';";

    try (Connection conn = DriverManager.getConnection(
          JDBC_MARIADB_BANK,
          USER,
          PASSWORD)) {
      try (PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(1, startDate);
        stmt.setString(2, endDate);
        stmt.setString(3, linkedNumber);
        stmt.setString(4, amount);
        try (ResultSet rs = stmt.executeQuery()) {
          return rs.first();
        }
      }
    }
  }

  @WebMethod(operationName = "createVirtualAccount")
  public String createVirtualAccount(@WebParam(name="accountNumber") String accountNumber) throws SQLException {
    JSONArray virtualAccount = new JSONArray();
    Random rnd = new Random();
    String v_account = Long.toString(System.currentTimeMillis() * 1000 + rnd.nextInt(900) + 100);
    String query = "INSERT INTO virtual_account"
      + "VALUES(" + v_account + "," + accountNumber + ")";

    try (Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/bank", "didik", "didik")){
      try (Statement stmt = conn.createStatement()) {
        stmt.executeQuery(query);
        JSONObject obj = new JSONObject();
        obj.put("virtual_account", v_account);
        virtualAccount.put(obj);
        return virtualAccount.toString();
      }
    }
  }
}
