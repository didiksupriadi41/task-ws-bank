package org.test.ws;

import java.sql.SQLException;
import java.util.List;

/**
 * App class is used to experiment with SQL queries.
 *
 * Execute with <code>mvn clean install exec:java -Dexec.mainClass="org.test.ws.App"</code>
 */
public final class App {

  /**
   * Hide utility class constructor.
   */
  private App() { }

  /**
   * main program.
   * @param args external arguments
   * @throws SQLException handle SQL problems
   */
  public static void main(final String[] args) throws SQLException {
    System.out.println(ValidationService.validateAccount("0527481553"));
    List<List<String>> list = UserDataService.getUserData("0527481550");
  }

}
