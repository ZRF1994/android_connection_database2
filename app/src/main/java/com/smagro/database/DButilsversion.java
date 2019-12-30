package com.smagro.database;

/**
 * @author Zhang Rufei
 * @data 2019/11/12
 * @email 1106815482@qq.com
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库工具类：连接数据库用、获取数据库数据用
 * 相关操作数据库的方法均可写在该类
 *
 *
 *
 * String DRIVER = "com.mysql.cj.jdbc.Driver"; Class.forName(DRIVER); 连接地址URL为： String URL ="jdbc:mysql://localhost:3306/mysql_database?useSSL=false&serverTimezone=UTC";
 * String USERNAME = "root"; String PASSWORD = "root";
 * Connection con = DriverManager.getConnection(URL,USERNAME,PASSWORD); 我这里连接的是数据库mysql_database，
 *
 */
public class DButilsversion {

    private   static  String driver = "com.mysql.jdbc.Driver";

    private static String user = "smagro";

    private static String password = "smagro";

    private static Connection getConn(String dbName){
        Connection connection = null;
        try{

            Class.forName(driver).newInstance();
            String ip = "139.9.101.223";//
            connection = DriverManager.getConnection("jdbc:mysql://" + ip + ":3306/"+ dbName ,
                    user, password);   // 尝试建立到给定数据库URL的连接
            //
        }catch (Exception e){
            e.printStackTrace();
            //Log.e("DBUtils","异常：" +"连接失败");
        }
        return connection;
    }
    public static  List<String> result (String location_name,String station_name){
        List<String>  name_Obtain =new ArrayList<>();
        Connection connection = getConn("qxdbversion");//数据库名称
        try {
            String sql ="select ID,URL,README,PUSHNEW from tbversion where ID=(select max(ID) from tbversion);";
            if (connection != null){// connection不为null表示与数据库建立了连接
                PreparedStatement ps = connection.prepareStatement(sql);
                if (ps != null){
                    // 执行sql查询语句并返回结果集
                    ResultSet rs = ps.executeQuery();
                    if (rs != null){
                        int count = rs.getMetaData().getColumnCount();
                        while (rs.next()){
                            // 注意：下标是从1开始的
                            for (int i = 1;i <= count;i++){
                                String field = rs.getMetaData().getColumnName(i);
                                name_Obtain.add(rs.getString(field));
                            }
                        }
                        connection.close();
                        ps.close();
                        return  name_Obtain;
                    }else {
                        return null;
                    }
                }else {
                    return  null;
                }
            }else {
                return  null;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}