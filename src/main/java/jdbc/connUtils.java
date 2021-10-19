// package utils;

// import java.sql.Connection;
// import java.sql.SQLException;
// import java.util.Properties;

// import com.ibm.db2.jcc.DB2Driver;
// import com.ibm.db2.jcc.am.StandbyServerSQLException;

// public class ConnectionUtil {

// 	public ConnectionUtil() {
// 	}

// 	public static int exit_code_success = 0;
// 	public static int exit_code_failed_general = 101;
// 	public static int exit_code_failed_invald_password = 102;
// 	public static int exit_code_failed_database_not_available = 103;
// 	public static int exit_code_failed_invalid_args = 104;
// 	public static int exit_code_failed_driver_not_found = 105;
// 	public static int exit_code_hadr_standby = 106;
// 	public static int exit_code_failed_no_reason = 107;
// 	public static String ERRORCODE = "[ERRORCODE]";
// 	public static String SQLSTATE = "[SQLSTATE]";
// 	public static String MESSAGE = "[MESSAGE]";
// 	public static String ReturnMsg = null;
// 	public static int exit_code = 1;
// 	public static void main(String[] args) {
// 		if (args == null || args.length == 0 || args.length < 3) {
// 			System.out.println(
// 					"[Invalid args] java  com.ibm.aps.tools.repository.util.ConnectionUtil jdbc:db2://<ip>:<port>/<dbname>  username password 9  k=v");
// 			System.exit(exit_code_failed_invalid_args);
// 		}
// 		Connection conn= null;
// 		String url = args[0];
// 		String username = args[1];
// 		String password = args[2];

// 		String securityMechanism = "3";
// 		if (args.length >= 4) {
// 			securityMechanism = args[3];
// 			try {
// 				Integer.parseInt(securityMechanism);
// 			} catch (NumberFormatException ex) {
// 				securityMechanism = "3";
// 			}
// 		}

// 		Properties prop = new Properties();
// 		prop.setProperty("user", username);
// 		prop.setProperty("password", password);
// 		prop.setProperty("securityMechanism", securityMechanism);

// 		for (int i = 3; i < args.length; i++) {
// 			String kv = args[i];
// 			if (kv != null && kv.indexOf('=') > 0) {
// 				String kvs[] = kv.split("=");
// 				if (kvs.length == 2) {
// 					prop.setProperty(kvs[0], kvs[1]);
// 				}

// 			}
// 		}
// 		try {
// 			conn = getConnection(url, prop);
// 			if (conn != null) {
// 				System.out.println("[SUCCEED]");
// 				System.exit(exit_code_success);
// 			} else {
// 				System.out.println("[FAILED] [Can not establish connection to host" + url
// 						+ ", no exception throw, probably url is not correct.]");
// 				System.exit(exit_code_failed_no_reason);
// 			}
// 		} catch (StandbyServerSQLException e) {
// 			int errorCode = e.getErrorCode();
// 			if (url.startsWith("jdbc:db2") && errorCode == -1776) {
// 				System.out.println(ERRORCODE + "=" + e.getErrorCode() + " " + SQLSTATE + "=" + e.getSQLState() + " "
// 						+ MESSAGE + "=" + e.getMessage());
// 				System.exit(exit_code_hadr_standby);
// 			} else {
// 				System.out.println(ERRORCODE + "=" + e.getErrorCode() + " " + SQLSTATE + "=" + e.getSQLState()
// 						+ " MESSAGE=" + e.getMessage());
// 				System.exit(exit_code_failed_general);
// 			}
// 		} catch (SQLException e) {
// 			int errorCode = e.getErrorCode();
// 			if (url.startsWith("jdbc:db2") && errorCode == -4214) {
// 				System.out.println(ERRORCODE + "=" + e.getErrorCode() + " " + SQLSTATE + "=" + e.getSQLState() + " "
// 						+ MESSAGE + "=" + e.getMessage());
// 				System.exit(exit_code_failed_invald_password);
// 			} else if (url.startsWith("jdbc:db2") && errorCode == -4499) {
// 				System.out.println(ERRORCODE + "=" + e.getErrorCode() + " " + SQLSTATE + "=" + e.getSQLState() + " "
// 						+ MESSAGE + "=" + e.getMessage());
// 				System.exit(exit_code_failed_database_not_available);
// 			} else {
// 				System.out.println(ERRORCODE + "=" + e.getErrorCode() + " " + SQLSTATE + "=" + e.getSQLState() + " "
// 						+ MESSAGE + "=" + e.getMessage());
// 				System.exit(exit_code_failed_general);
// 			}

// 		} catch (Exception e){
// 			if(ReturnMsg!=null) {
// 				System.out.println(ReturnMsg);
// 				System.exit(exit_code);
// 			}else {
// 				System.out.println(e.getMessage());
// 			}
			
			
// 		} finally {
// 			if (conn != null) {
// 				try {
// 					conn.close();
// 				} catch (SQLException e) {
// 				}
// 			}
// 		}

// 	}

// 	public static Connection getConnection(String url, Properties prop) throws SQLException, ReflectiveOperationException {
// 		DB2Driver jdbcDriver = null;
// 		@SuppressWarnings("rawtypes")
// 		Class driverClass = null;
// 		try {
// 			driverClass = Class.forName("com.ibm.db2.jcc.DB2Driver");
// 			jdbcDriver = (DB2Driver) driverClass.newInstance();
// 		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
// 			ReturnMsg = "[FAILED] [Can not load jcc drivers]"; 
// 			exit_code = exit_code_failed_driver_not_found;
// 			throw e;
// 		}
// 		Connection conn = null;
// 		conn = jdbcDriver.connect(url, prop);
// 		return conn;
// 	}

// }
