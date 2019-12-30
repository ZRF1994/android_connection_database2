package com.smagro.database;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Zhang Rufei
 * @version $V1$
 * @data 2019/11/21
 * @data 1106815482@qq.com
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 * @查询近三十天的平均值
 */
public class DBUtilsavg {

    private   static  String driver = "com.mysql.jdbc.Driver";

    private static String user = "shoujiduan";

    private static String password = "smagro";
    //Connect to the database

    private static Connection getConn(String dbName){
        Connection connection = null;
        try{

            Class.forName(driver).newInstance();
            String ip = "118.31.227.236";//
            connection = DriverManager.getConnection("jdbc:mysql://" + ip + ":3306/"+ dbName ,
                    user, password);   // 尝试建立到给定数据库URL的连接            //
        }catch (Exception e){
            e.printStackTrace();

        }
        return connection;
    }

    //find max id  in all table,
    public static List<String> AVG (String dbname,String tbname ,String content){
        List<String> name_Obtain =new ArrayList<>();
        Connection connection = getConn(dbname);//数据库名称
        try {
            // String sql="select PASSWORD from tbaccount where ACCOUNT="  +  "'"+account +"'"+ ";";


            String sql="SELECT AVG("+content+")  FROM "+tbname+"  WHERE date_sub(curdate(), INTERVAL 30 DAY) <= date(`时间`) GROUP BY DATE_FORMAT(`时间`,'%Y-%m-%d');";

            Log.e("AAAAAAA","SA"+sql);
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
