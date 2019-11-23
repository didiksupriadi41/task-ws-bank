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
  /**
   * DB URL.
   */
  public static final String JDBC_MARIADB_BANK =
    "jdbc:mariadb://localhost:3306/bank";

  /**
   * DB User.
   */
  public static final String USER = "didik";

  /**
   * DB Password.
   */
  public static final String PASSWORD = "didik";

  /**
   * Query paramenter index.
   */
  public static final int Q_PARAM_1 = 1;

  /**
   * Query paramenter index.
   */
  public static final int Q_PARAM_2 = 2;

  /**
   * Query paramenter index.
   */
  public static final int Q_PARAM_3 = 3;

  /**
   * Query paramenter index.
   */
  public static final int Q_PARAM_4 = 4;

  /**
   * Query paramenter index.
   */
  public static final int Q_PARAM_5 = 5;

  /**
   * Query paramenter index.
   */
  public static final int Q_PARAM_6 = 6;

  /**
   * Query paramenter index.
   */
  public static final int Q_PARAM_7 = 7;

  /**
   * Query paramenter index.
   */
  public static final int Q_PARAM_8 = 8;


  /**
   * Query paramenter index.
   */
  public static final int Q_PARAM_9 = 9;

  /**
   * Query paramenter index.
   */
  public static final int Q_PARAM_10 = 10;

  /**
   * Return 1000.
   */
  public static final int SERIBU = 1000;

  /**
   * Return 900.
   */
  public static final int SEMBILANRATUS = 900;

  /**
   * Return 100.
   */
  public static final int SERATUS = 100;

  /**
   * Length of virtual account number.
   */
  public static final int VIRTUAL_LEN = 16;

  /**
   * Length of account number.
   */
  public static final int ACCOUNT_LEN = 10;

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
  public String getUserData(
    @WebParam(name = "accountNumber") final String accountNumber)
    throws SQLException {
    JSONArray userData = new JSONArray();
    String query = "SELECT * "
      + "FROM account "
      + "LEFT JOIN transaction "
      + "USING (account_number) "
      + "WHERE account_number = ?;";

    try (Connection conn = DriverManager.getConnection(
      JDBC_MARIADB_BANK,
      USER,
      PASSWORD)) {
      try (PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(Q_PARAM_1, accountNumber);
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
   * @param amount       String of amount.
   * @param startDate    String of start date.
   * @param endDate      String of end date.
   * @return <code>true</code> if transaction exist,
   *         <code>false</code> otherwise.
   * @throws SQLException Triggered if there are problems with SQL
   */
  @WebMethod(operationName = "checkTransactionBetween")
  public String checkTransactionBetween(
      @WebParam(name = "linkedNumber") final String linkedNumber,
      @WebParam(name = "amount") final String amount,
      @WebParam(name = "startDate") final String startDate,
      @WebParam(name = "endDate") final String endDate) throws SQLException {

    String query = "SELECT * " + "FROM transaction "
        + "WHERE transaction_time > ? " + "AND transaction_time < ? "
        + "AND linked_number = ? " + "AND amount = ? " + "AND type = 'D';";

    try (Connection conn = DriverManager.getConnection(
      JDBC_MARIADB_BANK,
      USER,
      PASSWORD)) {
      try (PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(Q_PARAM_1, startDate);
        stmt.setString(Q_PARAM_2, endDate);
        stmt.setString(Q_PARAM_3, linkedNumber);
        stmt.setString(Q_PARAM_4, amount);
        try (ResultSet rs = stmt.executeQuery()) {
          if (rs.first()) {
            return rs.getString("transaction_time");
          } else {
            return "";
          }
        }
      }
    }
  }

  /**
   * Validate an account number.
   *
   * @param accountNumber String account number.
   * @return true if account number exist, false otherwise.
   * @throws SQLException Triggered if there are problems with SQL
   */
  @WebMethod(operationName = "validateAccountNumber")
  public boolean validateAccountNumber(
    @WebParam(name = "accountNumber") final String accountNumber)
      throws SQLException {

    String query = "SELECT * " + "FROM account "
      + "WHERE account_number=? ";

    try (Connection conn = DriverManager.getConnection(
      JDBC_MARIADB_BANK,
      USER,
      PASSWORD)) {
      try (PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(Q_PARAM_1, accountNumber);
        try (ResultSet rs = stmt.executeQuery()) {
          return rs.first();
        }
      }
    }

  }

  /**
   * Create Virtual Account for specific accountNumber.
   *
   * @param accountNumber accountNumber for virtualaccount
   * @return virtualAccount as String
   * @throws SQLException Triggered if there are problems with SQL
   */
  @WebMethod(operationName = "createVirtualAccount")
  public String createVirtualAccount(
      @WebParam(name = "accountNumber") final String accountNumber)
      throws SQLException {

    Random rnd = new Random();
    String vAccount = Long.toString(System.currentTimeMillis() * SERIBU
    + rnd.nextInt(SEMBILANRATUS) + SERATUS);

    String query = "INSERT INTO virtual_account VALUES(?, ?)";

    try (Connection conn = DriverManager.getConnection(
      JDBC_MARIADB_BANK,
      USER,
      PASSWORD)) {
      try (PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(Q_PARAM_1, vAccount);
        stmt.setString(Q_PARAM_2, accountNumber);
        stmt.executeQuery();

        return vAccount;
      }
    }
  }

  /**
   * Do transactions.
   *
   * @param accountNumber String of account number.
   * @param linkedNumber String of linked number.
   * @param amount String of amount.
   * @return <code>true</code> if transaction exist,
   * <code>false</code> otherwise.
   * @throws SQLException Triggered if there are problems with SQL
   */
  @WebMethod(operationName = "doTransaction")
  public boolean doTransaction(
      @WebParam(name = "accountNumber") final String accountNumber,
      @WebParam(name = "linkedNumber") final String linkedNumber,
      @WebParam(name = "amount") final String amount)
      throws SQLException {

    if (!isEnough(accountNumber, amount)) {
      return false;
    }

    String validNumber = "";
    if (linkedNumber.length() == VIRTUAL_LEN) {
      validNumber = getAccountNumberOf(linkedNumber);
    } else if (linkedNumber.length() == ACCOUNT_LEN) {
      validNumber = linkedNumber;
    } else {
      return false;
    }

    String queryInsertD = "INSERT INTO "
      + "transaction VALUES(NOW(), ?, 'D', ?, ?);";
    String queryInsertK = "INSERT INTO "
      + "transaction VALUES(NOW(), ?, 'K', ?, ?);";
    String queryUpdateK = "UPDATE account "
      + "SET balance = balance + ? WHERE account_number = ?;";
    String queryUpdateD = "UPDATE account "
      + "SET balance = balance - ? WHERE account_number = ?;";

    try (Connection conn = DriverManager.getConnection(
        JDBC_MARIADB_BANK,
        USER,
        PASSWORD)) {
      try (PreparedStatement stmtInsertD = conn.prepareStatement(queryInsertD);
          PreparedStatement stmtInsertK = conn.prepareStatement(queryInsertK);
          PreparedStatement stmtUpdateK = conn.prepareStatement(queryUpdateK);
          PreparedStatement stmtUpdateD = conn.prepareStatement(queryUpdateD)) {
        stmtInsertD.setString(Q_PARAM_1, linkedNumber);
        stmtInsertD.setString(Q_PARAM_2, amount);
        stmtInsertD.setString(Q_PARAM_3, accountNumber);
        stmtInsertK.setString(Q_PARAM_1, accountNumber);
        stmtInsertK.setString(Q_PARAM_2, amount);
        stmtInsertK.setString(Q_PARAM_3, validNumber);
        stmtUpdateK.setString(Q_PARAM_1, amount);
        stmtUpdateK.setString(Q_PARAM_2, validNumber);
        stmtUpdateD.setString(Q_PARAM_1, amount);
        stmtUpdateD.setString(Q_PARAM_2, accountNumber);
        try (ResultSet rs1 = stmtInsertD.executeQuery();
            ResultSet rs2 = stmtInsertK.executeQuery();
            ResultSet rs3 = stmtUpdateK.executeQuery();
            ResultSet rs4 = stmtUpdateD.executeQuery()) {
          return true;
        }
      }
    }
  }

  /**
   * Get account number from virtual number.
   *
   * @param virtualNumber String of virtual number.
   * @return String of account number.
   * @throws SQLException Triggered if there are problems with SQL
   */
  private String getAccountNumberOf(
      final String virtualNumber)
      throws SQLException {
    String query = "SELECT account_number "
        + "FROM virtual_account "
        + "WHERE virtual_number = ?;";

    try (Connection conn = DriverManager.getConnection(
        JDBC_MARIADB_BANK,
        USER,
        PASSWORD)) {
      try (PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(Q_PARAM_1, virtualNumber);
        try (ResultSet rs = stmt.executeQuery()) {
          rs.first();
          return rs.getString("account_number");
        }
      }
    }
  }

  /**
   * Check if account balance is enough.
   *
   * @param accountNumber String of account number.
   * @param amount String of amount.
   * @return <code>true</code> if enough,
   * <code>false</code> otherwise.
   * @throws SQLException Triggered if there are problems with SQL
   */
  private boolean isEnough(final String accountNumber, final String amount)
      throws SQLException {
    String query = "SELECT * "
      + "FROM account "
      + "WHERE balance >= ? "
      + "AND account_number = ?;";

    try (Connection conn = DriverManager.getConnection(
        JDBC_MARIADB_BANK,
        USER,
        PASSWORD)) {
      try (PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(Q_PARAM_1, amount);
        stmt.setString(Q_PARAM_2, accountNumber);
        try (ResultSet rs = stmt.executeQuery()) {
          return rs.first();
        }
      }
    }
  }
}
