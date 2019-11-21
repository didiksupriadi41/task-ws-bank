package org.test.ws;

import org.json.JSONObject;
import org.json.JSONArray;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * BankService serves bank functionality.
 */
@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class BankService {

  /**
   * DEMO: sayHello.
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
   * @return name of the first nasabah in db
   * @throws SQLException if there are problems with the SQL
   */
  @WebMethod(operationName = "getFirstNasabah")
  public String getFirstNasabah() throws SQLException {
    try (Connection conn = DriverManager.getConnection(
        "jdbc:mariadb://localhost:3306/bank",
        "didik",
        "didik")) {
      try (Statement stmt = conn.createStatement()) {
        try (ResultSet rs = stmt.executeQuery("select * from account")) {
          rs.first();
          return rs.getString(1);
        }
      }
    }
  }
}
