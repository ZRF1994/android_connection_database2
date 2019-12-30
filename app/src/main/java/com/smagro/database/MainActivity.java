package com.smagro.database;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;


/**
 * @author Zhang Rufei
 * @data 2019/11/12
 * @email 1106815482@qq.com
 *
 */



public class MainActivity extends Activity implements View.OnClickListener{
    public static LineChart lineChart;

    /**
     * 按键定义区
     * */
   private TextView L1_T1,L1_T3,L1_T5,L1_T7,L2_T1,L2_T3,L2_T5,L2_T7;
   private TextView L2_T2,L2_T4,L2_T6,L2_T8;
   private TextView L1_T2,L1_T4,L1_T6,L1_T8;
   private TextView dis_label;
   private String   label,station_label;
   private Button meau,station1,station2,station3,station4,station5,station6,station7,station8,station9,btn_about;
   private ImageView L1_I1,L1_I2,L1_I3,L1_I4,L2_I1,L2_I2,L2_I3,L2_I4;
   private Button area_xige,area_jiuju,area_nongxueyuan,area_yanan,area_lilan,area_xinhuibing,area_zhihui;

   private String version_url,version_readme;
   public static  String new_version_name="v1.0.1";//新版本
   private String current_version="v1.0.1";//当前版本
   private boolean meau_two=true;//菜单二次点击
   private String url ;
   private String title = "气象数据平台.apk";
   private String desc = "下载完成后，点击安装";
   private Button btn;
   private DownloadManagerUtil downloadManagerUtil;
   public static String dis_kinds,dis_kinds_again,dis_kinds_twice="no";//四种适应testview，西鸽的11种【利兰1，志辉1  9zhong】，【利兰2，3 志辉2，3    3zhong】 【新慧彬1，2  酒局123，农学院，延安   7zhong】 dis_kinds_again再次判断，同一个站名不同数据显示形式不同
   long downloadId = 0;
   private String area_dbname,tb_name1,tb_name2,tb_name3,tb_name4,tb_name5,tb_name6,tb_name7,tb_name8,tb_name9;//不同地方的数据库和表名。数据库中分别为
   public  static  String labels;//折线图标签变量

    private String zhe_area_name;//折线图所用，属于哪个地名；
    private String zhe_local_name;//折线图站名
    public List<Entry> yyy=new ArrayList<>();
    public static ProgressBar progressBar;

    // nx_jiuju nx_xige sx_yanan nxy_xiaomai

    /**
     *
     * nc_jiuju e64jj01 e64jj02 e64jj03   e64ll01 e64ll02 e64ll03  e64xhb01 e64xhb02 e64xhb03  e64zh01 e64zh02 e64zh03
     *
     * nx_xige  e64xg01  e64xg02  e64xg03  e64xg04 e64xg05  e64xg06  e64xg07
     *
     *
     * sx_yanan  e61ya01   e61ya02   e61ya03
     *
     *
     * nxy_xiaomai   e61biaoben  e61linfen  e61luoyang  e61nanyang e61qingdao e61 qingshui  e61suqian   e61xuchang  e61yangling

     *
     * */

   @SuppressLint("HandlerLeak")
    /**
    * 线程定义
    * */
   private Handler handler = new Handler(){
       @Override
       public void handleMessage(Message msg) {

           switch (msg.what){
                case 0x11:
                    String s = (String) msg.obj;
                    makeText(MainActivity.this, "查询失败", Toast.LENGTH_LONG).show();
                    break;
                case 0x12:
                    if(dis_kinds.equals("eleven_kinds")){
                        label_judje(label,station_label);
                    String ss="";
                    List<String> name_Obtain= (List<String>) msg.obj;
                    makeText(MainActivity.this, "获取完成", Toast.LENGTH_LONG).show();

                    L1_T1.setText(name_Obtain.get(4));                    L1_T3.setText(name_Obtain.get(2));                    L1_T5.setText(name_Obtain.get(3));
                    L1_T7.setText(name_Obtain.get(0));                    L2_T1.setText(name_Obtain.get(1));                    L2_T3.setText(name_Obtain.get(8));
                    L2_T5.setText(name_Obtain.get(6));                    L2_T7.setText("NULL");
                    L1_T2.setText("光照lux");L1_T4.setText("风速m/s");L1_T6.setText("降水mm");L1_T8.setText("温度°C");
                    L2_T2.setText("湿度%");L2_T4.setText("土壤湿度%");L2_T6.setText("土壤温度°C");L2_T8.setText("水势");
                        Log.e("AAAAAAA","SA"+zhe_area_name+"         "+zhe_local_name);
                        lineChart.setVisibility(View.INVISIBLE);
                        listen_texviewForXige(zhe_area_name,zhe_local_name);
               image_visible();

                    break;}
                    if(dis_kinds.equals("seven_kinds")){
                        if(dis_kinds_twice.equals("yes")){

                            String ss="";
                            label_judje(label,station_label);
                            List<String> name_Obtain= (List<String>) msg.obj;
                            makeText(MainActivity.this, "获取完成", Toast.LENGTH_LONG).show();
                            L1_T1.setText(name_Obtain.get(0));L1_T3.setText(name_Obtain.get(1));L1_T5.setText(name_Obtain.get(2)); L1_T7.setText(name_Obtain.get(3));
                            L1_T2.setText("冠层温度1");L1_T4.setText("冠层温度2"); L1_T6.setText("冠层湿度1");L1_T8.setText("冠层湿度2");
                            image_invisible();
                            dis_kinds_twice="no";
                            Log.e("AAAAAAA","SA"+zhe_area_name+"         "+zhe_local_name);
                            lineChart.setVisibility(View.INVISIBLE);
                            Listen_TextviewForFourDis(zhe_area_name,zhe_local_name);


                            break;
                        }
                        else{

                            label_judje(label,station_label);
                            String ss="";
                            image_visible();
                            List<String> name_Obtain= (List<String>) msg.obj;
                            makeText(MainActivity.this, "获取完成", Toast.LENGTH_LONG).show();
                            L1_T1.setText(name_Obtain.get(4));L1_T3.setText(name_Obtain.get(2));L1_T5.setText(name_Obtain.get(3)); L1_T7.setText(name_Obtain.get(0));
                            L2_T1.setText(name_Obtain.get(1));L2_T3.setText(name_Obtain.get(6)); L2_T5.setText(name_Obtain.get(5));L2_T7.setText("NULL");
                            label_judje(label,station_label);
                            L1_T2.setText("光照lux");L1_T4.setText("风速m/s");L1_T6.setText("降水mm");L1_T8.setText("温度°C");
                            L2_T2.setText("湿度%");L2_T4.setText("土壤湿度%");L2_T6.setText("土壤温度°C");L2_T8.setText("水势");
                            Log.e("AAAAAAA","SA"+zhe_area_name+"         "+zhe_local_name);
                            lineChart.setVisibility(View.INVISIBLE);
                            Listen_TextviewForSevenDis(zhe_area_name,zhe_local_name);
                            break;
                        }
                    }
            }
        }
    };

    @SuppressLint("HandlerLeak")

    /**
     * 线程定义
     * */
    private Handler handler1 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x11:
                    String s = (String) msg.obj;
                    break;
                case 0x12:
                    List<String> name_Obtain1= (List<String>) msg.obj;
                    new_version_name=name_Obtain1.get(0);
                    version_url =name_Obtain1.get(1);
                    version_readme=name_Obtain1.get(2);
            }
        }
    };
    /**
     *保持后台运行
     *
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
   @Override
   /**
    * 主程序入口
    * */
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.display);


       lineChart=findViewById(R.id.LineChart);

        progressBar=findViewById(R.id.progressBar4);
       progressBar.setVisibility(View.INVISIBLE);

       // 控件的实例化
       dis_label=findViewById(R.id.dis_label);

       L1_T1   =findViewById(R.id.L1_T1);
       L1_T3   =findViewById(R.id.L1_T3);
       L1_T5   =findViewById(R.id.L1_T5);
       L1_T7   =findViewById(R.id.L1_T7);
       L2_T1   =findViewById(R.id.L2_T1);
       L2_T3   =findViewById(R.id.L2_T3);
       L2_T5   =findViewById(R.id.L2_T5);
       L2_T7   =findViewById(R.id.L2_T7);
       L2_T2   =findViewById(R.id.L2_T2);
       L2_T4   =findViewById(R.id.L2_T4);
       L2_T6   =findViewById(R.id.L2_T6);
       L2_T8   =findViewById(R.id.L2_T8);
       L1_T2   =findViewById(R.id.L1_T2);
       L1_T4   =findViewById(R.id.L1_T4);
       L1_T6   =findViewById(R.id.L1_T6);
       L1_T8   =findViewById(R.id.L1_T8);
       station1=(Button) findViewById(R.id.station1);
       station2=(Button) findViewById(R.id.station2);
       station3=(Button) findViewById(R.id.station3);
       station4=(Button) findViewById(R.id.station4);
       station5=(Button) findViewById(R.id.station5);
       station6=(Button) findViewById(R.id.station6);
       station7=(Button) findViewById(R.id.station7);
       station8=(Button) findViewById(R.id.station8);
       station9=(Button) findViewById(R.id.station9);
       btn_about=(Button)findViewById(R.id.btn_about);
       area_xige=(Button)findViewById(R.id.area_xige);
       area_jiuju=(Button)findViewById(R.id.area_jiuju);
       area_nongxueyuan=(Button)findViewById(R.id.area_nongxueyuan);
       area_yanan=(Button)findViewById(R.id.area_yanan);
       area_lilan=(Button)findViewById(R.id.area_lilan);
       area_xinhuibing=(Button)findViewById(R.id.area_xinhuibing);
       area_zhihui=(Button)findViewById(R.id.area_zhihui);
       L1_I1=findViewById(R.id.L1_I1);
       L1_I2=findViewById(R.id.L1_I2);
       L1_I3=findViewById(R.id.L1_I3);
       L1_I4=findViewById(R.id.L1_I4);
       L2_I1=findViewById(R.id.L2_I1);
       L2_I2=findViewById(R.id.L2_I2);
       L2_I3=findViewById(R.id.L2_I3);
       L2_I4=findViewById(R.id.L2_I4);


       meau=(Button) findViewById(R.id.meau);




       for(int i = 0;i < 1;i++){

           yyy.add(new BarEntry(i, 0));
       }



       Permission(login.kkkkk);


       //判断版本是否为最新，如果为最新，执行初始化
       //如果版本不是最新，不执行初始化
       station_invisible();
       area_invisible();


   }
   //最新版本，开始更新
   private void init_all(){
       station_invisible();
       area_invisible();
       judge_inter_connect();
       //Toast.makeText(MainActivity.this, "Refreshing, please wait", Toast.LENGTH_LONG).show();
       //Inquire("nx_xige","e64xg01");
   }
   //版本未更新，拒绝查询
   private void no_inin(){
       station_invisible();
       area_invisible();
       meau.setVisibility(View.INVISIBLE);
       btn_about.setVisibility(View.INVISIBLE);
   }
   //location_name为地点名。比如nx_xige为西鸽气象数据库，station_name为气象站表名
   public void Inquire(final String location_name, final String station_name, final String ifxige){
       new Thread(new Runnable() {
           @Override
           public void run() {
               // 调用数据库工具类DBUtils的getInfoByName方法获取数据库表中数据
               List<String> name_Obtain =DBUtils.result(location_name,station_name,ifxige);

               Message message = handler.obtainMessage();
               if(name_Obtain != null){
                   message.what = 0x12;
                   message.obj = name_Obtain;
               }else {
                   message.what = 0x11;
                   message.obj = "查询结果为空";
               }
               // 发消息通知主线程更新UI
               handler.sendMessage(message);
           }
       }).start();

   }
   //访问
    public void Inquire_version(final String location_name,final String station_name){
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 调用数据库工具类DBUtils的getInfoByName方法获取数据库表中数据
                List<String> name_Obtain1 =DButilsversion.result(location_name,station_name);
                Message message = handler1.obtainMessage();
                if(name_Obtain1 != null){


                    message.what = 0x12;
                    message.obj = name_Obtain1;

                }else {
                    message.what = 0x11;
                    message.obj = "查询结果为空";
                }
                // 发消息通知主线程更新UI
                handler1.sendMessage(message);


            }
        }).start();


    }
    /**
     * 设置监听
     */
    //检查版本更新
    public void onClick(View v){

        switch (v.getId()){
            case R.id.meau:{
                lineChart.setVisibility(View.INVISIBLE);

                station_invisible();

                    if (meau_two==true){
                        area_visible();
                        meau_two=false;
                    }
                    else {
                        area_invisible();
                        meau_two=true;
                    }

                break;
            }
            case R.id.area_xige:{

                dis_kinds="eleven_kinds";//西鸽数据用11种显示
                area_dbname="nx_xige";//数据库名为nx_xige
                dis_kinds_again="no";
                label="xige_label";
                station1.setText("二基地-霞多丽");  station2.setText("二基地-赤霞珠"); station3.setText("二基地-蛇龙珠");station4.setText("一基地-马瑟兰");station5.setText("观兰-西拉");station6.setText("一基地-马尔贝克");
                tb_name1="e64xg01";tb_name2="e64xg02";tb_name3="e64xg03";tb_name4="e64xg04";tb_name5="e64xg05";tb_name6="e64xg06";
                station_visible();
                station7.setVisibility(View.INVISIBLE); station8.setVisibility(View.INVISIBLE);station9.setVisibility(View.INVISIBLE);
                break;
            }
            case R.id.area_jiuju:{

                dis_kinds="seven_kinds";
                dis_kinds_again="no";
                area_dbname="nx_jiuju";
                label="jiuju_label";


                station1.setText("酒局1");station2.setText("酒局2");station3.setText("酒局3");
                tb_name1="e64jj01";tb_name2="e64jj02";tb_name3="e64jj03";
                station_visible();
                station4.setVisibility(View.INVISIBLE);station5.setVisibility(View.INVISIBLE);station6.setVisibility(View.INVISIBLE);station7.setVisibility(View.INVISIBLE);  station8.setVisibility(View.INVISIBLE);station9.setVisibility(View.INVISIBLE);
                break;
            }
            case R.id.area_nongxueyuan:{

                dis_kinds="seven_kinds";
                dis_kinds_again="no";
                area_dbname="nxy_xiaomai";
                label="nongxueyuan_label";
                station1.setText("标本区");station2.setText("临汾"); station3.setText("洛阳");station4.setText("南阳");station5.setText("青岛");station6.setText("清水");station7.setText("宿迁");station8.setText("许昌"); station9.setText("杨凌");
                tb_name1="e61biaoben";tb_name2="e61linfen";tb_name3="e61luoyang";tb_name4="e61nanyang"; tb_name5="e61qingdao";tb_name6="e61qingshui";tb_name7="e61suqian"; tb_name8="e61xuchang";tb_name9="e61yangling";
                station_visible();
                image_visible();
                break;
            }
            case R.id.area_yanan:{

                dis_kinds="seven_kinds";
                dis_kinds_again="no";
                area_dbname="sx_yanan";
                label="yanan_label";
                station2.setText("延安1");station3.setText("延安2");station4.setText("延安3");
                tb_name2="e61ya01";tb_name3="e61ya02";tb_name4="e61ya03";
                station_visible();
                station1.setVisibility(View.INVISIBLE);station5.setVisibility(View.INVISIBLE);station6.setVisibility(View.INVISIBLE);station7.setVisibility(View.INVISIBLE);station8.setVisibility(View.INVISIBLE);station9.setVisibility(View.INVISIBLE);

                break;
            }
            case R.id.area_xinhuibing:{

                dis_kinds="seven_kinds";
                dis_kinds_again="no";
                area_dbname="nx_jiuju";
                label="xinhuibing_label";
                station5.setText("新慧彬1");station6.setText("新慧彬2");
                tb_name5="e64xhb01";tb_name6="e64xhb02";
                station_visible();
                station3.setVisibility(View.INVISIBLE); station4.setVisibility(View.INVISIBLE);station1.setVisibility(View.INVISIBLE);station2.setVisibility(View.INVISIBLE);station7.setVisibility(View.INVISIBLE);station8.setVisibility(View.INVISIBLE);station9.setVisibility(View.INVISIBLE);
                break;
            }
            case R.id.area_lilan:{

                dis_kinds="seven_kinds";
                area_dbname="nx_jiuju";
                dis_kinds_again="yes";
                label="lilan_label";
                station4.setText("利兰主站");station5.setText("利兰副站1");station6.setText("利兰副站2");
                tb_name4="e64ll01";tb_name5="e64ll02";tb_name6="e64ll03";
                station_visible();
                station1.setVisibility(View.INVISIBLE);station2.setVisibility(View.INVISIBLE);station3.setVisibility(View.INVISIBLE);station7.setVisibility(View.INVISIBLE);station8.setVisibility(View.INVISIBLE);station9.setVisibility(View.INVISIBLE);
                break;
            }

            case R.id.area_zhihui:{

                dis_kinds="seven_kinds";
                dis_kinds_again="yes";
                area_dbname="nx_jiuju";
                label="zhihui_label";
                station4.setText("志辉主站");station5.setText("志辉副站1");station6.setText("志辉副站2");
                tb_name4="e64zh01";tb_name5="e64zh02";tb_name6="e64zh03";
                station_visible();
                station1.setVisibility(View.INVISIBLE);station2.setVisibility(View.INVISIBLE); station3.setVisibility(View.INVISIBLE);station7.setVisibility(View.INVISIBLE);station8.setVisibility(View.INVISIBLE);station9.setVisibility(View.INVISIBLE);
                break;
            }

                            case R.id.station1:{
                                lineChart.setVisibility(View.INVISIBLE);
                                station_label="station_1";
                                makeText(MainActivity.this,"正在查询，请稍等",Toast.LENGTH_LONG).show();
                                station_invisible();
                                area_invisible();
                                meau_two=true;
                                Inquire(area_dbname,tb_name1,dis_kinds);

                                break;


                            }
                            case R.id.station2:{
                                lineChart.setVisibility(View.INVISIBLE);
                                station_label="station_2";
                                makeText(MainActivity.this,"正在查询，请稍等",Toast.LENGTH_LONG).show();

                                if(dis_kinds_again.equals("yes")){
                                    dis_kinds_twice="yes";
                                    Inquire(area_dbname,tb_name2,"four_kinds");
                                    station_invisible();
                                    area_invisible();
                                    meau_two=true;

                                    break;
                                }

                                Inquire(area_dbname,tb_name2,dis_kinds);
                                station_invisible();
                                area_invisible();
                                meau_two=true;
                                break;
                            }
                            case R.id.station3:{
                                lineChart.setVisibility(View.INVISIBLE);
                                station_label="station_3";
                                makeText(MainActivity.this,"正在查询，请稍等",Toast.LENGTH_LONG).show();
                                if(dis_kinds_again.equals("yes")){
                                    dis_kinds_twice="yes";
                                    Inquire(area_dbname,tb_name3,"four_kinds");
                                    station_invisible();
                                    area_invisible();
                                    meau_two=true;
                                    break;
                                }
                                Inquire(area_dbname,tb_name3,dis_kinds);
                                station_invisible();
                                area_invisible();
                                meau_two=true;
                               break;
                            }
                            case R.id.station4:{
                                lineChart.setVisibility(View.INVISIBLE);
                                station_label="station_4";
                                makeText(MainActivity.this,"正在查询，请稍等",Toast.LENGTH_LONG).show();


                                Inquire(area_dbname,tb_name4,dis_kinds);
                                station_invisible();
                                area_invisible();
                                meau_two=true;
                                break;
                            }
                            case R.id.station5:{
                                lineChart.setVisibility(View.INVISIBLE);
                                station_label="station_5";
                                makeText(MainActivity.this,"正在查询，请稍等",Toast.LENGTH_LONG).show();
                                if(dis_kinds_again.equals("yes")){
                                    dis_kinds_twice="yes";
                                    Inquire(area_dbname,tb_name5,"four_kinds");
                                    station_invisible();
                                    area_invisible();
                                    meau_two=true;
                                    break;
                                }
                                Inquire(area_dbname,tb_name5,dis_kinds);
                                station_invisible();
                                area_invisible();
                                meau_two=true;
                                break;
                            }
                            case R.id.station6:{
                                lineChart.setVisibility(View.INVISIBLE);
                                station_label="station_6";
                                makeText(MainActivity.this,"正在查询，请稍等",Toast.LENGTH_LONG).show();
                                if(dis_kinds_again.equals("yes")){
                                    dis_kinds_twice="yes";
                                    Inquire(area_dbname,tb_name6,"four_kinds");
                                    station_invisible();
                                    area_invisible();
                                    meau_two=true;
                                    break;
                                }

                                Inquire(area_dbname,tb_name6,dis_kinds);
                                station_invisible();
                                area_invisible();
                                meau_two=true;
                                break;
                            }
                            case R.id.station7:{
                                lineChart.setVisibility(View.INVISIBLE);
                                station_label="station_7";
                                makeText(MainActivity.this,"正在查询，请稍等",Toast.LENGTH_LONG).show();
                                if(dis_kinds_again.equals("yes")){
                                    dis_kinds_twice="yes";
                                    Inquire(area_dbname,tb_name7,"four_kinds");
                                    station_invisible();
                                    area_invisible();
                                    meau_two=true;
                                     break;
                                }

                                Inquire(area_dbname,tb_name7,dis_kinds);
                                station_invisible();
                                area_invisible();
                                meau_two=true;
                                break;
                            }
                            case R.id.station8:{
                                lineChart.setVisibility(View.INVISIBLE);
                                station_label="station_8";
                                makeText(MainActivity.this,"正在查询，请稍等",Toast.LENGTH_LONG).show();
                                if(dis_kinds_again.equals("yes")){
                                    dis_kinds_twice="yes";
                                    Inquire(area_dbname,tb_name8,"four_kinds");
                                    station_invisible();
                                    area_invisible();
                                    meau_two=true;
                                    break;
                                }

                                Inquire(area_dbname,tb_name8,dis_kinds);
                                station_invisible();
                                area_invisible();
                                meau_two=true;
                                break;
                            }
                            case R.id.station9:{
                                lineChart.setVisibility(View.INVISIBLE);
                                station_label="station_9";
                                makeText(MainActivity.this,"正在查询，请稍等",Toast.LENGTH_LONG).show();
                                if(dis_kinds_again.equals("yes")){
                                    dis_kinds_twice="yes";
                                    Inquire(area_dbname,tb_name9,"four_kinds");
                                    station_invisible();
                                    area_invisible();
                                    meau_two=true;
                                    break;
                                }

                                Inquire(area_dbname,tb_name9,dis_kinds);
                                station_invisible();
                                area_invisible();
                                meau_two=true;
                                break;
                            }
                            case R.id.btn_about:{
                                aboutClick();
                                station_invisible();
                                area_invisible();
                                meau_two=true;

                                break;
                            }

        }
    }
    //按键隐藏
    private void station_visible(){
        station1.setVisibility(View.VISIBLE);
        station2.setVisibility(View.VISIBLE);
        station3.setVisibility(View.VISIBLE);
        station4.setVisibility(View.VISIBLE);
        station5.setVisibility(View.VISIBLE);
        station6.setVisibility(View.VISIBLE);
        station7.setVisibility(View.VISIBLE);
        station8.setVisibility(View.VISIBLE);
        station9.setVisibility(View.VISIBLE);

    }
    private void station_invisible(){
        station1.setVisibility(View.INVISIBLE);
        station2.setVisibility(View.INVISIBLE);
        station3.setVisibility(View.INVISIBLE);
        station4.setVisibility(View.INVISIBLE);
        station5.setVisibility(View.INVISIBLE);
        station6.setVisibility(View.INVISIBLE);
        station7.setVisibility(View.INVISIBLE);
        station8.setVisibility(View.INVISIBLE);
        station9.setVisibility(View.INVISIBLE);

    }
    //按键不可见
    private void area_visible(){

        area_xige.setVisibility(View.VISIBLE);
        area_jiuju.setVisibility(View.VISIBLE);
        area_nongxueyuan.setVisibility(View.VISIBLE);
        area_yanan.setVisibility(View.VISIBLE);
        area_lilan.setVisibility(View.VISIBLE);
        area_xinhuibing.setVisibility(View.VISIBLE);
        area_zhihui.setVisibility(View.VISIBLE);
    }
    private void area_invisible(){
        area_xige.setVisibility(View.INVISIBLE);
        area_jiuju.setVisibility(View.INVISIBLE);
        area_nongxueyuan.setVisibility(View.INVISIBLE);
        area_yanan.setVisibility(View.INVISIBLE);
        area_lilan.setVisibility(View.INVISIBLE);
        area_xinhuibing.setVisibility(View.INVISIBLE);
        area_zhihui.setVisibility(View.INVISIBLE);

    }
    //关于按键
    public void aboutClick(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder about = new AlertDialog.Builder(MainActivity.this);
        about.setIcon(R.drawable.smagro);
        about.setTitle("关于");
        about.setMessage(
                "当前版本:  "+current_version+"\n"+
                        "版本说明:  \n"+
                        "        "+new_version_name+"增加新的气象节点，修复已知的一些错误"+"\n\n"+
                        "技术支持：苏宝峰\n\n"+
                        "地址:  西北农林科技大学机械与电子工程学院SMAGRO实验室.\n\n"+
                        "联系方式:bfs@nwsuaf.edu.cn"
        );
        about.setPositiveButton("返回",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        // 显示
        about.show();
    }
    //版本更新显示

    //按键隐藏
    private void gone(){
        station1.setVisibility(View.GONE);
        station2.setVisibility(View.GONE);
        station3.setVisibility(View.GONE);
        station4.setVisibility(View.GONE);
        station5.setVisibility(View.GONE);
        station6.setVisibility(View.GONE);

    }
    //判断网络连接
    private boolean isConnectIsNomarl() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            String intentName = info.getTypeName();

            return true;
        } else {

            return false;
        }
    }
    //无网络拒绝查询
    private void judge_inter_connect(){
        if (isConnectIsNomarl()==true){
            meau.setEnabled(true);

        }
        else {
            meau.setEnabled(false);
        }
    }
    //下载
    private void download_new_version(String version_url) {
        downloadManagerUtil = new DownloadManagerUtil(this);
        if (downloadId != 0) {
            downloadManagerUtil.clearCurrentTask(downloadId);

        }

        downloadId = downloadManagerUtil.download(version_url, title, desc);
    }
    @Override
    public void onBackPressed() {//重写的Activity返回

        Intent intent = new Intent();
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        startActivity(intent);

    }
    private void image_visible(){
        L2_T1.setVisibility(View.VISIBLE); L2_T3.setVisibility(View.VISIBLE); L2_T5.setVisibility(View.VISIBLE); L2_T7.setVisibility(View.VISIBLE);
        L2_T2.setVisibility(View.VISIBLE); L2_T4.setVisibility(View.VISIBLE); L2_T6.setVisibility(View.VISIBLE); L2_T8.setVisibility(View.VISIBLE);

        L2_I1.setVisibility(View.VISIBLE); L2_I2.setVisibility(View.VISIBLE); L2_I3.setVisibility(View.VISIBLE); L2_I4.setVisibility(View.VISIBLE);
    }
    private void image_invisible(){
        L2_T1.setVisibility(View.INVISIBLE); L2_T3.setVisibility(View.INVISIBLE); L2_T5.setVisibility(View.INVISIBLE); L2_T7.setVisibility(View.INVISIBLE);
        L2_T2.setVisibility(View.INVISIBLE); L2_T4.setVisibility(View.INVISIBLE); L2_T6.setVisibility(View.INVISIBLE); L2_T8.setVisibility(View.INVISIBLE);

        L2_I1.setVisibility(View.INVISIBLE); L2_I2.setVisibility(View.INVISIBLE); L2_I3.setVisibility(View.INVISIBLE); L2_I4.setVisibility(View.INVISIBLE);
    }
    private void Permission(String att){
        if(att.equals("rv2bL2")){
            area_xige.setEnabled(true); area_jiuju.setEnabled(false); area_nongxueyuan.setEnabled(false);area_yanan.setEnabled(false);area_lilan.setEnabled(false);area_xinhuibing.setEnabled(false);area_zhihui.setEnabled(false);
        }
        if(att.equals("xP2T43")){
            area_xige.setEnabled(false); area_jiuju.setEnabled(true); area_nongxueyuan.setEnabled(false);area_yanan.setEnabled(false);area_lilan.setEnabled(false);area_xinhuibing.setEnabled(false);area_zhihui.setEnabled(false);
        }
        if(att.equals("MrfKZ9")){
            area_xige.setEnabled(false); area_jiuju.setEnabled(false); area_nongxueyuan.setEnabled(true);area_yanan.setEnabled(false);area_lilan.setEnabled(false);area_xinhuibing.setEnabled(false);area_zhihui.setEnabled(false);
        }
        if(att.equals("41sEvW")){
            area_xige.setEnabled(false); area_jiuju.setEnabled(false); area_nongxueyuan.setEnabled(false);area_yanan.setEnabled(true);area_lilan.setEnabled(false);area_xinhuibing.setEnabled(false);area_zhihui.setEnabled(false);
        }
        if(att.equals("Sy09J8")){
            area_xige.setEnabled(false); area_jiuju.setEnabled(false); area_nongxueyuan.setEnabled(false);area_yanan.setEnabled(false);area_lilan.setEnabled(true);area_xinhuibing.setEnabled(false);area_zhihui.setEnabled(false);
        }
        if(att.equals("0sF0O7")){
            area_xige.setEnabled(false); area_jiuju.setEnabled(false); area_nongxueyuan.setEnabled(false);area_yanan.setEnabled(false);area_lilan.setEnabled(false);area_xinhuibing.setEnabled(true);area_zhihui.setEnabled(false);
        }
        if(att.equals("lJ60Pc")){
            area_xige.setEnabled(false); area_jiuju.setEnabled(false); area_nongxueyuan.setEnabled(false);area_yanan.setEnabled(false);area_lilan.setEnabled(false);area_xinhuibing.setEnabled(false);area_zhihui.setEnabled(true);
        }
        if(att.equals("mq5iD0")){
            area_xige.setEnabled(true); area_jiuju.setEnabled(true); area_nongxueyuan.setEnabled(true);area_yanan.setEnabled(true);area_lilan.setEnabled(true);area_xinhuibing.setEnabled(true);area_zhihui.setEnabled(true);
        }



    }
    private void label_judje(String label,String station_label){
        if(label.equals("xige_label")){
            zhe_area_name="nx_xige";

            switch (station_label){
                case "station_1":{
                    dis_label.setText("二基地-霞多丽实时环境信息");
                    zhe_local_name="e64xg01";
                    break;
                }
                case "station_2":{
                    dis_label.setText("二基地-赤霞珠实时环境信息");
                    zhe_local_name="e64xg02";
                    break;

                }
                case "station_3":{
                    dis_label.setText("二基地-蛇龙珠实时环境信息");
                    zhe_local_name="e64xg03";
                    break;

                }
                case "station_4":{
                    dis_label.setText("一基地-马瑟兰实时环境信息");
                    zhe_local_name="ex64xg04";
                    break;

                }
                case "station_5":{
                    dis_label.setText("观兰-西拉实时环境信息");
                    zhe_local_name="e64xg05";
                    break;

                }
                case "station_6":{
                    dis_label.setText("一基地-马尔贝克实时环境信息");
                    zhe_local_name="e64xg06";
                    break;

                }


            }

        }
        if(label.equals("jiuju_label")){
            zhe_local_name="nx_jiuju";
            switch (station_label){
                case "station_1":{
                    zhe_local_name="e64jj01";
                    dis_label.setText("酒局1实时环境信息");break;
                }
                case "station_2":{
                    zhe_local_name="e64jj02";
                    dis_label.setText("酒局2实时环境信息");break;

                }
                case "station_3":{
                    zhe_local_name="e64jj03";
                    dis_label.setText("酒局3实时环境信息");break;

                }



            }

        }
        if(label.equals("yanan_label")){
            zhe_area_name="sx_yanan";
            switch (station_label){
                case "station_2":{
                    zhe_local_name="e61ya01";
                    dis_label.setText("延安1实时环境信息");
                    break;
                }
                case "station_3":{
                    zhe_local_name="e61ya02";
                    dis_label.setText("延安2实时环境信息");
                    break;

                }
                case "station_4":{
                    zhe_local_name="e61ya03";
                    dis_label.setText("延安3实时环境信息");
                    break;

                }



            }

        }
        if(label.equals("nongxueyuan_label")){
            zhe_area_name="nxy_xiaomai";
            switch (station_label) {
                case "station_1": {
                    zhe_local_name="e61bioaben";
                    dis_label.setText("标本区实时环境信息");
                    break;
                }
                case "station_2": {
                    zhe_local_name="e61linfen";
                    dis_label.setText("临汾实时环境信息");
                    break;

                }
                case "station_3": {
                    zhe_local_name="e61luoyang";
                    dis_label.setText("洛阳实时环境信息");
                    break;

                }
                case "station_4": {
                    zhe_local_name="e61nanyang";
                    dis_label.setText("南阳实时环境信息");
                    break;
                }
                case "station_5": {
                    zhe_local_name="e61qingdao";
                    dis_label.setText("青岛实时环境信息");
                    break;

                }
                case "station_6": {
                    zhe_local_name="e61qingshui";
                    dis_label.setText("清水实时环境信息");
                    break;

                }
                case "station_7": {
                    zhe_local_name="e61suqian";
                    dis_label.setText("宿迁实时环境信息");
                    break;
                }
                case "station_8": {
                    zhe_local_name="e61xuchang";
                    dis_label.setText("许昌实时环境信息");
                    break;

                }
                case "station_9": {
                    zhe_local_name="e61yangling";
                    dis_label.setText("杨凌实时环境信息");
                    break;

                }

            }

        }
            if(label.equals("lilan_label")){
            zhe_area_name="nx_jiuju";
                switch (station_label){
                    case "station_4":{
                        zhe_local_name="e64ll01";
                        dis_label.setText("立兰主站实时环境信息");break;
                    }
                    case "station_5":{
                        zhe_local_name="e64ll02";
                        dis_label.setText("立兰副站1实时环境信息");break;

                    }
                    case "station_6":{
                        zhe_local_name="e64ll03";
                        dis_label.setText("立兰副站2实时环境信息");break;

                    }



                }

            }
            if(label.equals("xinhuibing_label")){
                zhe_area_name="nx_jiuju";
                switch (station_label){
                    case "station_5":{
                        zhe_local_name="e64xhb01";
                        dis_label.setText("新慧彬1实时环境信息");break;
                    }
                    case "station_6":{
                        zhe_local_name="e64xhb02";
                        dis_label.setText("新慧彬2实时环境信息");break;

                    }

                }

            }

            if(label.equals("zhihui_label")){
                zhe_area_name="nx_jiuju";
                switch (station_label){
                    case "station_4":{
                        zhe_local_name="e64zh01";
                        dis_label.setText("志辉主站实时环境信息");break;
                    }
                    case "station_5":{
                        zhe_local_name="e64zh02";

                        dis_label.setText("志辉副站1实时环境信息");break;

                    }
                    case "station_6":{
                        zhe_local_name="e64zh03";
                        dis_label.setText("志辉副站2实时环境信息");break;

                    }



                }

            }




    }//某地气象信息

    //折线图
    public static  void near_Thirty(final String dbname, final String tbname, final String content){
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 调用数据库工具类DBUtils的getInfoByName方法获取数据库表中数据
                List<String> name_Obtain =DBUtilsavg.AVG(dbname,tbname,content);

                Message message = handler11.obtainMessage();
                if(name_Obtain != null){
                    message.what = 0x12;
                    message.obj = name_Obtain;
                }else {
                    message.what = 0x11;
                    message.obj = "查询结果为空";
                }
                // 发消息通知主线程更新UI
                handler11.sendMessage(message);
            }
        }).start();

    }
  @SuppressLint("HandlerLeak")
    /**
     * 线程定义
    * */
    public static Handler handler11 = new Handler(){


        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x11:
                    String s = (String) msg.obj;
                    break;
                case 0x12:
                    List<String> name_Obtain1= (List<String>) msg.obj;
                    int m;
                    List<Entry> mmm=new ArrayList<>();
                    for(int i = 0;i < 31;i++){
                        double   d   =   Double.parseDouble(name_Obtain1.get(i));
                        mmm.add(new BarEntry(i, (float) d));
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                    xxx(lineChart,mmm,labels,5f);
            }
        }
    };
    public static void xxx(LineChart lineChart, List<Entry> mmm,String labels,float CircleSize){

        //这里我们定义一下我们界面的控件
        lineChart=initLineChart(lineChart); //这里调用方法初始化柱状图
        LineData lineData=setLinedate(  mmm,labels,CircleSize); //这里调用方法初始化模拟数据
        lineChart.setData(lineData);//这里将模拟数据用于柱状图，在柱状图显示
        lineChart.invalidate();  //这里让柱状图在填充数据后刷新

        lineChart.setPinchZoom(true);

        lineChart.setDrawGridBackground(false); // 是否显示表格颜色
        lineChart.setGridBackgroundColor(Color.WHITE & 0x70FFFFFF); // 表格的的颜色，在这里是是给颜色设置一个透明度

        Legend line=lineChart.getLegend();
        line.setTextSize(10f);
        lineChart.setBackgroundColor(Color.WHITE & 0x70FFFFFF);
    }

    //        lineChart.setColor(Color.parseColor("#7d7d7d"));//线条颜色
    //        lineChart.setCircleColor(Color.parseColor("#7d7d7d"));//圆点颜色
    //lineChart.setLineWidth(1f);//线条宽度
    //到这里基本完成了柱状图的初步使用，如果想要再深一步的学习MPAndroidChart的使用，请关注我，谢谢
    //下面进行一下测试，看是否能够成功显示，这里up主使用的是mumu模拟器
    //在cmd中使用adb connect 127.0.0.1:7555就可以将mumu模拟器和Android studio相连接
    //等待的过程。。。还是很烦躁
    //数据成功显示，谢谢大家的耐心观看。。。。。

    /**
     * 这个方法用于设置柱状图数据
     * @return
     */
    /**
     * 设置折线图
     * @return
     */

    public static LineData setLinedate( List<Entry> mmm,String labels,float CircleSize){


        LineDataSet lineDataSet=new LineDataSet(mmm,labels);
        lineDataSet.setLineWidth(3.75f); // 线宽

        lineDataSet.setCircleSize(CircleSize);// 显示的圆形大小
        //lineDataSet.setColor(Color.);// 显示颜色
        //lineDataSet.setCircleColor(Color.BLUE);// 圆形的颜色

        lineDataSet.setCircleColor(Color.rgb(0,114,227));

        lineDataSet.setHighLightColor(Color.WHITE); // 高亮的线的颜色
        lineDataSet.setValueTextSize(15f);
        lineDataSet.setLabel(labels);
        lineDataSet.setFormSize(10f);//图例大小
        LineData lineData=new LineData(lineDataSet);

        return  lineData;
    }

    /**
     * 这个方法用于初始化柱状图
     * @return
     */

    public static LineChart initLineChart(LineChart lineChart){
        lineChart.setDescription(null);
        XAxis xAxis = lineChart.getXAxis();  //获取柱状图的x轴
        YAxis yAxisLeft = lineChart.getAxisLeft();   //这里获取了柱状图左侧的y轴
        YAxis yAxis1Right = lineChart.getAxisRight();//这里获取了柱状图右侧的y轴
        setAxis(xAxis,yAxisLeft,yAxis1Right);   //这里调用方法设置柱状图的轴线
        return lineChart;        //这里返回初始化完成的柱状图实例


    }
    /**
     * 这个方法用于设置柱状图的X轴Y轴
     */
    public static void setAxis(XAxis xAxis,YAxis leftYaxis,YAxis rightYaxis){
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);  //这里设置x轴在柱状图底部显示
        xAxis.setAxisLineWidth(1);  //这里设置x轴宽度
        xAxis.setAxisMinimum(0);    //这里设置x轴从0刻度开始绘画
        xAxis.setDrawAxisLine(true);//这里设置x轴显示轴线
        xAxis.setDrawGridLines(false);//这里设置x轴的表格线不显示
        xAxis.setEnabled(true);     //这里设置x轴显示

        leftYaxis.setAxisMinimum(0);//设置y轴从0刻度开始
        leftYaxis.setDrawGridLines(false);//这里设置y轴的表格线不显示
        leftYaxis.setDrawAxisLine(true);//这里设置y轴显示轴线
        leftYaxis.setAxisLineWidth(1);//这里设置y轴宽度
        leftYaxis.setEnabled(true);//这里设置左侧的y轴显示

        rightYaxis.setAxisMinimum(0);//设置y轴从0刻度开始
        rightYaxis.setDrawGridLines(false);//这里设置y轴的表格线不显示
        rightYaxis.setDrawAxisLine(true);//这里设置y轴显示轴线
        rightYaxis.setAxisLineWidth(1);//这里设置y轴宽度
        rightYaxis.setEnabled(false);//这里设置右侧的y轴不显示

    }
    private void listen_texviewForXige(final String zhe_area_name, final String zhe_local_name){


        L1_T1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    xxx(lineChart,yyy," ",0);
                    progressBar.setVisibility(View.VISIBLE);
                    makeText(MainActivity.this, "数据获取中···", Toast.LENGTH_LONG).show();


                    lineChart.setVisibility(View.VISIBLE);
                    labels="光照度";
                    near_Thirty(zhe_area_name,zhe_local_name,"光照度");
                }
            });
        L1_T3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    xxx(lineChart,yyy," ",0);
                    progressBar.setVisibility(View.VISIBLE);
                    makeText(MainActivity.this, "数据获取中···", Toast.LENGTH_LONG).show();
                    lineChart.setVisibility(View.VISIBLE);
                    labels="风速";
                    near_Thirty(zhe_area_name,zhe_local_name,"风速");
                }
            });
        L1_T5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    xxx(lineChart,yyy," ",0);
                    progressBar.setVisibility(View.VISIBLE);
                    makeText(MainActivity.this, "数据获取中···", Toast.LENGTH_LONG).show();
                    lineChart.setVisibility(View.VISIBLE);
                    labels="降水量";
                    near_Thirty(zhe_area_name,zhe_local_name,"降水量");
                }
        });
        L1_T7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xxx(lineChart,yyy," ",0);
                progressBar.setVisibility(View.VISIBLE);
                makeText(MainActivity.this, "数据获取中···", Toast.LENGTH_LONG).show();
                lineChart.setVisibility(View.VISIBLE);
                labels="空气温度";
                near_Thirty(zhe_area_name,zhe_local_name,"空气温度");
            }
        });
        L2_T1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xxx(lineChart,yyy," ",0);
                progressBar.setVisibility(View.VISIBLE);
                makeText(MainActivity.this, "数据获取中···", Toast.LENGTH_LONG).show();
                lineChart.setVisibility(View.VISIBLE);
                labels="空气湿度";
                near_Thirty(zhe_area_name,zhe_local_name,"空气湿度");
            }
        });
        L2_T3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xxx(lineChart,yyy," ",0);
                progressBar.setVisibility(View.VISIBLE);
                makeText(MainActivity.this, "数据获取中···", Toast.LENGTH_LONG).show();
                lineChart.setVisibility(View.VISIBLE);
                labels="土壤湿度";
                near_Thirty(zhe_area_name,zhe_local_name,"土壤湿度_中");
            }
        });
        L2_T5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xxx(lineChart,yyy," ",0);
                progressBar.setVisibility(View.VISIBLE);
                makeText(MainActivity.this, "数据获取中···", Toast.LENGTH_LONG).show();
                lineChart.setVisibility(View.VISIBLE);
                labels="土壤温度";
                near_Thirty(zhe_area_name,zhe_local_name,"土壤温度_中");
            }
        });
//        L2_T7.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                labels="空气温度";
//                near_Thirty(zhe_area_name,zhe_local_name,"空气温度");
//            }
//        });

    }

    private void Listen_TextviewForSevenDis(final String zhe_area_name, final String zhe_local_name){
        lineChart.setVisibility(View.VISIBLE);
        L1_T1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xxx(lineChart,yyy," ",0);
                progressBar.setVisibility(View.VISIBLE);
                makeText(MainActivity.this, "数据获取中···", Toast.LENGTH_LONG).show();
                lineChart.setVisibility(View.VISIBLE);
                labels="光照度";
                near_Thirty(zhe_area_name,zhe_local_name,"光照度");
            }
        });
        L1_T3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xxx(lineChart,yyy," ",0);
                progressBar.setVisibility(View.VISIBLE);
                makeText(MainActivity.this, "数据获取中···", Toast.LENGTH_LONG).show();
                lineChart.setVisibility(View.VISIBLE);
                labels="风速";
                near_Thirty(zhe_area_name,zhe_local_name,"风速");
            }
        });
        L1_T5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xxx(lineChart,yyy," ",0);
                progressBar.setVisibility(View.VISIBLE);
                makeText(MainActivity.this, "数据获取中···", Toast.LENGTH_LONG).show();
                lineChart.setVisibility(View.VISIBLE);
                labels="降水量";
                near_Thirty(zhe_area_name,zhe_local_name,"降水量");
            }
        });
        L1_T7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xxx(lineChart,yyy," ",0);
                progressBar.setVisibility(View.VISIBLE);
                makeText(MainActivity.this, "数据获取中···", Toast.LENGTH_LONG).show();
                lineChart.setVisibility(View.VISIBLE);
                labels="空气温度";
                near_Thirty(zhe_area_name,zhe_local_name,"空气温度");
            }
        });
        L2_T1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xxx(lineChart,yyy," ",0);
                progressBar.setVisibility(View.VISIBLE);
                makeText(MainActivity.this, "数据获取中···", Toast.LENGTH_LONG).show();
                lineChart.setVisibility(View.VISIBLE);
                labels="空气湿度";
                near_Thirty(zhe_area_name,zhe_local_name,"空气湿度");
            }
        });
        L2_T3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xxx(lineChart,yyy," ",0);
                progressBar.setVisibility(View.VISIBLE);
                makeText(MainActivity.this, "数据获取中···", Toast.LENGTH_LONG).show();
                lineChart.setVisibility(View.VISIBLE);
                labels="土壤湿度";
                near_Thirty(zhe_area_name,zhe_local_name,"土壤湿度");
            }
        });
        L2_T5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xxx(lineChart,yyy," ",0);
                progressBar.setVisibility(View.VISIBLE);
                makeText(MainActivity.this, "数据获取中···", Toast.LENGTH_LONG).show();
                lineChart.setVisibility(View.VISIBLE);
                labels="土壤温度";
                near_Thirty(zhe_area_name,zhe_local_name,"土壤温度");
            }
        });

    }

    private void Listen_TextviewForFourDis(final String zhe_area_name, final String zhe_local_name){

        L1_T1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                xxx(lineChart,yyy," ",0);
                progressBar.setVisibility(View.VISIBLE);
                makeText(MainActivity.this, "数据获取中···", Toast.LENGTH_LONG).show();
                lineChart.setVisibility(View.VISIBLE);
                labels="冠层温度1";
                near_Thirty(zhe_area_name,zhe_local_name,"冠层_温度1");
            }
        });
        L1_T3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xxx(lineChart,yyy," ",0);
                progressBar.setVisibility(View.VISIBLE);
                makeText(MainActivity.this, "数据获取中···", Toast.LENGTH_LONG).show();
                lineChart.setVisibility(View.VISIBLE);
                labels="冠层温度2";
                near_Thirty(zhe_area_name,zhe_local_name,"冠层_温度2");
            }
        });
        L1_T5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lineChart.setVisibility(View.VISIBLE);
                labels="冠层湿度1";
                near_Thirty(zhe_area_name,zhe_local_name,"冠层_湿度1");
            }
        });
        L1_T7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lineChart.setVisibility(View.VISIBLE);
                labels="冠层湿度2";
                near_Thirty(zhe_area_name,zhe_local_name,"冠层_湿度2");
            }
        });

    }


/**
 *
 * nc_jiuju e64jj01 e64jj02 e64jj03   e64ll01 e64ll02 e64ll03  e64xhb01 e64xhb02 e64xhb03  e64zh01 e64zh02 e64zh03
 *
 * nx_xige  e64xg01  e64xg02  e64xg03  e64xg04 e64xg05  e64xg06  e64xg07
 *
 *
 * sx_yanan  e61ya01   e61ya02   e61ya03
 *
 *
 * nxy_xiaomai   e61biaoben  e61linfen  e61luoyang  e61nanyang e61qingdao e61 qingshui  e61suqian   e61xuchang  e61yangling

 */



}