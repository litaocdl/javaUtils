// package jdbc;

// import java.sql.Connection;
// import java.sql.Driver;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.util.Properties;

// class DB2JdbcTest {



//     public static void main(String[] args){

//        String url = "jdbc:db2://taodb21.fyre.ibm.com:50000/BLUDB" ;
//        String user = "db2inst1";
//        String password = "passw0rd";
//        testConnection(url, user, password);
//     }



//     public static void testConnection(String url,String username,String password){
//         Class driverClass = null ;
//         Driver jdbcDriver = null ;
//         try{
//             driverClass = Class.forName("com.ibm.db2.jcc.DB2Driver") ;
//             jdbcDriver = (Driver)driverClass.newInstance();
//         }catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
//             e.printStackTrace();
//         }
//         System.out.println("[testConnection] driver loaded.");
//         Properties props = new Properties() ;

//         props.setProperty("securityMechanism", "3") ;
//         props.setProperty("sslConnection", "false") ;
//         props.setProperty("user", username);
//         props.setProperty("password", password);

//         try(Connection conn = jdbcDriver.connect(url, props)){
//             System.out.println("[testConnection] connection established.");
//             String sql = "select session_auth_id from table(mon_get_connection(null, -2))" ;
//             PreparedStatement stmt = conn.prepareStatement(sql) ;
//             ResultSet rs =  stmt.executeQuery() ;

//             while(rs.next()){
//                 System.out.println("'" + rs.getString(1) + "'");
//             }
//         }catch(SQLException e){
//             e.printStackTrace();
//         }
//     }
// }