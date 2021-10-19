// package jdbc;


// import java.sql.Connection;
// import java.sql.Driver;
// import java.sql.SQLException;
// import java.util.HashMap;
// import java.util.Iterator;
// import java.util.Properties;
// import java.util.Set;

// import org.apache.commons.dbcp.ConnectionFactory;
// import org.apache.commons.dbcp.PoolableConnectionFactory;
// import org.apache.commons.dbcp.PoolingDataSource;
// import org.apache.commons.pool.impl.GenericObjectPool;



// public class DBCP 
// {
//     public GenericObjectPool connectionPool = null;/* pauser 61419 expose connection pool for use by WatchConn */
//     protected String name = null;
//     protected PoolableConnectionFactory poolableConnectionFactory = null;
//     //protected PoolingDriver driver = null;
//     protected PoolingDataSource poolingDS = null;
//     protected boolean initialized = false; // first getConnection will initialize it
//     protected static HashMap<String, DBCP> pooledDataSourceMap = new HashMap<String, DBCP>();
//     public synchronized static DBCP getPoolSource(String profileName, boolean createIfNull)
//     {
//         DBCP ds =  pooledDataSourceMap.get(profileName);
//         if((ds== null) )
//         {
//             try
//             {
//                 ds = createDBCPSource(profileName);
//                 pooledDataSourceMap.put(profileName, ds);
//             }
//             catch (SQLException e)
//             {
//                 e.printStackTrace();
//             }
//         }
//         return ds;
//     }
    
//     public static void removePoolSource(String profileName)
//     {
//         DBCP ds = pooledDataSourceMap.remove(profileName);
//         if(null != ds)
//         {
//             try
//             {
          
//                 ds.connectionPool.close();
               
//             }
//             catch (Exception e)
//             {
//                 e.printStackTrace();
//             }
//         }
//     }

//     //typically only called during shutdown..
//     public static void removeAllPooledSources()
//     {
//         Set<String> profileNames = pooledDataSourceMap.keySet();
//         for (Iterator<String> profileNamesIter = profileNames.iterator(); profileNamesIter.hasNext();)
//         {
//             String currProfileName = (String) profileNamesIter.next();
//             removePoolSource(currProfileName);
//         }
//     }
    
//     public static boolean isPooled(String profileName, boolean createIfDelayedPool)
//     {
//         return (null != getPoolSource(profileName, createIfDelayedPool)); 
//     }


    
//     public DBCP(String name)
//     {
//         this.name = name;
//     }
    
//     public synchronized static DBCP initializePool(String profileName)
//     {
//         DBCP ds = getPoolSource(profileName, false);
//         if(null != ds)
//         {
//             if(!ds.initialized)
//             {
                
//                 int prefillCount = 20;
//                 for(int preCount = 0; preCount < prefillCount; preCount ++)
//                 {
//                     try
//                     {
//                     	long time = System.currentTimeMillis() ;
//                         ds.connectionPool.addObject();
//                         System.out.println("New Connection created with " + (System.currentTimeMillis() - time));
//                     }
//                     catch (Exception e)
//                     {
//                         e.printStackTrace();
//                     }
//                 }
//                 ds.initialized = true;
//             }
//         }
//         return ds;
//     }
    
//     public static Connection getConnection(String profileName) throws SQLException
//     {
//         Connection con = null;
//         DBCP ds = initializePool(profileName); // if necessary or if pooling has been delayed till first conn
//         if(null != ds)
//         {
//             con = ds.poolingDS.getConnection();
//         }
//         return con;
//     }
    
//     protected static DBCP createDBCPSource(String name) throws SQLException
//     {
//         DBCP ds = new DBCP(name);
//         ds.connectionPool = new GenericObjectPool(null);        
//         ds.poolableConnectionFactory = new PoolableConnectionFactory(new DBCPConnectionFactory(profileName),ds.connectionPool,null,null,false,true);
        


//         String validationQuery = "select count(1) from sysibm.sysdummy1" ;
//         if(null != validationQuery)
//         {
//             ds.poolableConnectionFactory.setValidationQuery(validationQuery);
//             ds.connectionPool.setTestOnBorrow(true);
//         }
   
      
//         ds.connectionPool.setMaxActive(-1);
//         ds.connectionPool.setMaxIdle(10);
//         ds.connectionPool.setWhenExhaustedAction(GenericObjectPool.WHEN_EXHAUSTED_GROW);
//         ds.connectionPool.setMaxWait(10);
//         ds.connectionPool.setMinEvictableIdleTimeMillis(10);
//         ds.connectionPool.setTimeBetweenEvictionRunsMillis(5);
//         ds.connectionPool.setNumTestsPerEvictionRun(5);
        
//         ds.poolingDS = new PoolingDataSource(ds.connectionPool);

   
        
//         return ds;
//     }
    
    
// }
//  class DBCPConnectionFactory implements ConnectionFactory
// {

    
//     public DBCPConnectionFactory(String name)
//     {
//         super();
//         this.name = name;
//     }

//     protected String name = null;
    
//     public String getName()
//     {
//         return name;
//     }

//     public void setProfileName(String name)
//     {
//         this.name = name;
//     }

//     public Connection createConnection() throws SQLException
//     {
//         Connection conn = null;
//         try
//         {
//         	String URL = "jdbc:db2://xxxx:50000/test:retrieveMessagesFromServerOnGetMessage=true;securityMechanism=3;" ;
// 			Driver jdbcDriver = null;        
// 	        @SuppressWarnings("rawtypes")
// 			Class driverClass = null;
	       
// 	        try
// 	        {
// 	            driverClass = Class.forName("com.ibm.db2.jcc.DB2Driver");
// 	            jdbcDriver = (Driver)driverClass.newInstance();
// 	        }
// 	        catch (Exception e)
// 	        {   	
// 	        	 System.out.println("Can not find DB2Driver");
// 	        }
// 	       Properties prop = new Properties() ; 	
// 	       prop.setProperty("user","db2inst1");
// 	       prop.setProperty("password","passw0rd");
// 	       prop.setProperty("connectionTimeout", "30") ; 
// 	       prop.setProperty("loginTimeout", "30") ;
	       
	       
// 	       try{
// 	    	    conn = jdbcDriver.connect(URL, prop);
// 	       }catch(Exception e){
	    	  
// 	    	   System.out.println("connection FAILURE! ");
// 	       }
	     
// 	       if(conn != null){
// 	    	   System.out.println("connected successfully!");
// 	       }
        	
        	
        	
//         }
//         catch (Exception e)
//         {
           
//             throw new SQLException(e.getMessage());
//         }
//         return conn;
//     }
    
// }
