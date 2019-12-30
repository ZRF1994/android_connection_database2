package com.smagro.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Zhang Rufei
 * @data 2019/11/12
 * @email 1106815482@qq.com
 * 代码说明：此端代码实现账户登录。判断输入账户在数据库中对应的密码是否与输入密码相等
 */
public class DBUtilisLognin {

    private   static  String driver = "com.mysql.jdbc.Driver";

    private static String user = "smagro";

    private static String password = "smagro";
    //Connect to the database

    private static Connection getConn(String dbName){
        Connection connection = null;
        try{

            Class.forName(driver).newInstance();
            String ip = "139.9.101.223";//
            connection = DriverManager.getConnection("jdbc:mysql://" + ip + ":3306/"+ dbName ,
                    user, password);   // 尝试建立到给定数据库URL的连接            //
        }catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }
    //find max id  in all table,
    public static  List<String> result (String account){
        List<String> name_Obtain =new ArrayList<>();
        Connection connection = getConn("qxdbaccount");//数据库名称
        try {
            String sql="select PASSWORD,ATTRIBUTION from tbaccount where ACCOUNT="  +  "'"+account +"'"+ ";";
            if (connection != null){// connection不为null表示与数据库建立了连接
                //Log.e(" DBUtilisLognin","数据库操作语句：" +sql);
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

