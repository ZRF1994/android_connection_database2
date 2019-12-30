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
 * DBUtlis 类
 * 数据库工具类：连接数据库用、获取数据库数据用
 * 相关操作数据库的方法均可写在该类
 *
 *此段代码主要为获取固定数据库表格的气象信息
 *
 *
 */
public class DBUtils {




    private   static  String driver = "com.mysql.jdbc.Driver";//驱动

    private static String user = "shoujiduan";//用户名

    private static String password = "smagro";//密码
    public static String SQL;
    //Connect to the database

    private static Connection getConn(String dbName){
       Connection connection = null;
        try{
            //Log.e("DBUtils","异常：" +"正在加载");
            Class.forName(driver).newInstance();
            //Log.e("DBUtils","异常：" +"加载成功");
            String ip = "118.31.227.236";//
            connection = DriverManager.getConnection("jdbc:mysql://" + ip + ":3306/"+ dbName ,
                    user, password);   // 尝试建立到给定数据库URL的连接
//
          }catch (Exception e){
            e.printStackTrace();
            //Log.e("DBUtils","异常：" +"连接失败");
        }

        return connection;
    }


    //find max id  in all table,
    private static String find_max_id(String dbname,String tablename){
        Connection connection = getConn(dbname);//数据库名称
        try {
            String max_id ="select max(ID) from "+tablename+";";//find max id in e64xg01
            PreparedStatement ps_id = connection.prepareStatement(max_id);
            ResultSet rs_id = ps_id.executeQuery();
            int count_id = rs_id.getMetaData().getColumnCount();
            rs_id.next();
            String field_id = rs_id.getMetaData().getColumnName(1);
            return rs_id.getString(field_id);
        }catch (Exception e){
            e.printStackTrace();

            return null;
        }
    }


    public static  List<String> result (String location_name,String station_name,String ifxige){
        List<String>  name_Obtain =new ArrayList<>();
        Connection connection = getConn(location_name);//数据库名称


        try {
            if(ifxige.equals("eleven_kinds")) {
                SQL = "select 空气温度,空气湿度,风速,降水量,光照度,土壤温度_上,土壤温度_中,土壤温度_下,土壤湿度_上,土壤湿度_中,ID from " + station_name + " where ID=(select max(ID) from " + station_name + ");";
            }
            if(ifxige.equals("four_kinds")){
                SQL ="select 冠层_温度1,冠层_温度2,冠层_湿度1,冠层_湿度2,ID from "+station_name+" where ID=(select max(ID) from "+station_name+");";
            }
            //String ID_MAX=find_max_id("nx_xige","e64xg01");
            if(ifxige.equals("seven_kinds"))
            { SQL ="select 空气温度,空气湿度,风速,降水量,光照度,土壤温度,土壤湿度,ID from "+station_name+" where ID=(select max(ID) from "+station_name+");";}


            if (connection != null){// connection不为null表示与数据库建立了连接
                PreparedStatement ps = connection.prepareStatement(SQL);
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