package com.smagro.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Zhang Rufei
 * @data 2019/11/12
 * @email 1106815482@qq.com
 * 代码说明：此端代码实现账户登录。判断输入账户在数据库中对应的密码是否与输入密码相等
 */

public class DBUtilsRegedit {
    private   static  String driver = "com.mysql.jdbc.Driver";

    private static String user = "smagro";

    private static String password = "smagro";
    private static boolean result_add;

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
            //Log.e("DBUtils","异常：" +"连接失败");
        }

        return connection;
    }

    //find max id  in all table,
//    public static List<String> result ( String regedit_account,  String regedit_password,  String regedit_ATTRIBUTION){
//        List<String> name_Obtain =new ArrayList<>();
//        Connection connection = getConn("dbaccount");//数据库名称
//        Log.e("DBUtilsRegedit","异常2222222：" +connection);
//        try {
//            //insert into tbaccount (ACCOUNT,PASSWORD,ATTRIBUTION) values ('SD','DAS','DWA');
//
//            String sql="insert into tbaccount (ACCOUNT,PASSWORD,ATTRIBUTION) VALUES ("+"'"+regedit_account+"'"+","+"'" +regedit_password+"'"+","+"'"+regedit_ATTRIBUTION+"'"+");";
//            if (connection != null){// connection不为null表示与数据库建立了连接
//                Log.e(" DBUtilisLognin","qqqqqqqqqqq：" +sql);
//                PreparedStatement ps = connection.prepareStatement(sql);
//                ps.execute();
//
//
//                }
//            }else {
//                return  null;
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }

    public static Boolean add_account(String regedit_account,  String regedit_password,  String regedit_ATTRIBUTION){
        Connection connection = getConn("qxdbaccount");//数据库名称


                try {

                    String sql="insert into tbaccount (ACCOUNT,PASSWORD,ATTRIBUTION) VALUES ("+"'"+regedit_account+"'"+","+"'" +regedit_password+"'"+","+"'"+regedit_ATTRIBUTION+"'"+");";


                        PreparedStatement ps = connection.prepareStatement(sql);
                        ps.execute();

                       result_add=true;


                    }
                    catch (SQLException e1) {
                    e1.printStackTrace();
                    result_add=false;
                }
        return result_add;


            }



}

