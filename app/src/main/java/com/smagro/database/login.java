package com.smagro.database;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;

/**
 * @author Zhang Rufei
 * @data 2019/11/12
 * @email 1106815482@qq.com
 * 登录程序入口，登录完成后跳转带MainActivity
 */
public class  login extends Activity implements View.OnClickListener {

    private EditText edit_account,edit_password,edit_ATTRIBUTION;
    private Button   btn_account,btn_password;
    private String  db_password;
    private boolean kkk=true;
    private ProgressBar prog_login;
    private CheckBox SaveAccountPassword;
    public List<String>Invitation_code = new ArrayList<String>();
    public String account_txt , password_txt;
    public String acount_regedit,password_regedit,attributton_regedit;
    public static String kkkkk;
    public static  String new_version_name="v1.0.0";//新版本
    private String current_version="v1.0.0";//当前版本
    private String version_url,version_readme,pushnew;
    private DownloadManagerUtil downloadManagerUtil;
    long downloadId = 0;
    public Button btn_login,btn_regedit;
    private String title = "气象数据平台.apk";
    private String desc = "下载完成后，点击安装";
    private TextView TextViewPushnew;
    @Override
    /**
     * 主程序入口
     * */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        edit_account=(EditText) findViewById(R.id.edit_account);
        edit_password=(EditText) findViewById(R.id.edit_password);
        edit_ATTRIBUTION=(EditText)findViewById(R.id.edit_ATTRIBUTION);
        SaveAccountPassword=findViewById(R.id.SaveAccountPassword);
        btn_login=findViewById(R.id.btn_login);
        btn_regedit=findViewById(R.id.btn_regedit);
        prog_login=findViewById(R.id.prog_login);
        prog_login.setVisibility(View.VISIBLE);
        btn_login.setVisibility(View.INVISIBLE);
        btn_regedit.setVisibility(View.INVISIBLE);
        TextViewPushnew=(TextView)findViewById(R.id.pushnew);


        if (isConnectIsNomarl()==true){//判断是否连接网络

        }
        else {
            btn_login.setVisibility(View.INVISIBLE);
            btn_regedit.setVisibility(View.INVISIBLE);
            Toast.makeText(login.this,"网络未连接",Toast.LENGTH_SHORT).show();
        }

        makeText(login.this, "正在检查更新，请稍等", Toast.LENGTH_LONG).show();



        Invitation_code_funcation(Invitation_code);//邀请码添加
        SaveAccountPassword.setChecked(true);//默认记住密码
        SharedPreferences sp2=getSharedPreferences("Logindb",MODE_PRIVATE);
        Inquire_version(current_version,new_version_name);


        if(sp2.getBoolean("save",false)==true  ){    //判断是否为记住密码
            getAccountPassword();
        }

    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_login:{

                String account_txt =edit_account.getText().toString();
                String password_txt=edit_password.getText().toString();
                if(SaveAccountPassword.isChecked()){                    //当多选按钮按下时执行报损数据
                    saveAccountPassword();
                }
                else {
                    clearAccountPassword();
                }
                if(edit_account.getText().toString().trim().length()==0){
                    Toast.makeText(login.this,"用户名密码不能为空",Toast.LENGTH_LONG).show();
                    break;
                }
                    find_account(account_txt,password_txt);
                    Toast.makeText(login.this,"正在登录",Toast.LENGTH_LONG).show();
                    prog_login.setVisibility(View.VISIBLE);
                break;
            }
            case R.id.btn_regedit:{

                String edit_account_txt  = edit_account.getText().toString();
                String edit_password_txt = edit_password.getText().toString();
                String edit_ATTRIBUTION_txt=edit_ATTRIBUTION.getText().toString();
               if((edit_account.getText().toString().trim().length()!=0)){
                   if(edit_password.getText().toString().trim().length()!=0){
                       if(edit_ATTRIBUTION.getText().toString().trim().length()!=0){
                           if(Invitation_code.contains(edit_ATTRIBUTION_txt)){

                               acount_regedit= edit_account_txt;
                               password_regedit=edit_password_txt;
                               attributton_regedit=edit_ATTRIBUTION_txt;

                               AccountExits(edit_account_txt,edit_password_txt,edit_ATTRIBUTION_txt);

                               Toast.makeText(login.this,"正在注册",Toast.LENGTH_SHORT).show();

                               prog_login.setVisibility(View.VISIBLE);

                           }
                           else{
                               Toast.makeText(login.this,"邀请码错误",Toast.LENGTH_LONG).show();
                           }
                       }
                       else {
                           Toast.makeText(login.this,"邀请码不能为空",Toast.LENGTH_LONG).show();
                       }
                   }
                   else {
                       Toast.makeText(login.this,"密码不能为空",Toast.LENGTH_LONG).show();
                   }
               }
               else {
                   Toast.makeText(login.this,"账户名不能为空",Toast.LENGTH_LONG).show();
               }
            }
        }
    }

    /***
     * 判断账户和密码是否符合格式要求
     * */
    public boolean judge_account_password(String account, String password) {
        if ((account == null) | (password == null)) {
            Toast.makeText(login.this, "请输入完整的账号密码", Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            // 将正则进行匹配
            boolean matches = account.matches("[0-9][a-z][A-Z]");
            if (matches) {
                Toast.makeText(login.this, "pipei", Toast.LENGTH_LONG).show();
            } else {
                Log.e("login",account);
                Toast.makeText(login.this, "输入错误", Toast.LENGTH_LONG).show();
            }


        }
        return false;


    }
//        if (account.equals(password)){
//            Log.e("login",account );
//
//            //判断账户和密码是否符
////            if() {//判断数据库中是否存在此用户
////
////
////            }
////            else
////                {Toast.makeText(login.this,"此用户不存在",Toast.LENGTH_LONG).show();
////                return false;}
//            Toast.makeText(login.this,"正在登录",Toast.LENGTH_LONG).show();
//
//            return true;
//        }
//        else {
//
//
//            Toast.makeText(login.this,"输入格式错误",Toast.LENGTH_LONG).show();
//            return false;
//
//
//        }

    /***
     * 检查数据库中是否含有此账户和密码
     ***/
    public void find_account(final String account, final String password){
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 调用数据库工具类DBUtils的getInfoByName方法获取数据库表中数据
                List<String> name_Obtain =DBUtilisLognin.result(account);

                Message message = handler.obtainMessage();
                if(name_Obtain.size() != 0){
                    message.what = 0x12;
                    message.obj = name_Obtain;
                    handler.sendMessage(message);
                }else {
                    message.what = 0x11;
                    message.obj = "查询结果为空";

                    handler.sendMessage(message);

                }

                // 发消息通知主线程更新UI

            }
        }).start();

    }
    public void regedit_account(final String regedit_account, final String regedit_password, final String regedit_ATTRIBUTION){

        new Thread(new Runnable() {
            @Override
            public void run() {
                // 调用数据库工具类DBUtils的getInfoByName方法获取数据库表中数据

                boolean result_add=DBUtilsRegedit.add_account(regedit_account,regedit_password,regedit_ATTRIBUTION);
                Message message = handler1.obtainMessage();
                if(result_add=false){
                    message.what = 0x11;
                    message.obj = "注册失败";

                    handler1.sendMessage(message);
                }else {
                    message.what = 0x12;
                    message.obj = true;
                    handler1.sendMessage(message);
                }
            }
        }).start();
    }

    public void AccountExits(final String regedit_account, final String regedit_password, final String regedit_ATTRIBUTION){

        new Thread(new Runnable() {
            @Override
            public void run() {
                // 调用数据库工具类DBUtils的getInfoByName方法获取数据库表中数据


                 List<String> Exits_result=DBUtilsAccountExits.AccountExits(regedit_account);
                Message message = handler2.obtainMessage();
                if(Exits_result.get(0).equals("0")){
                    message.what = 0x12;
                    message.obj = "账户不存在可以注册";

                    handler2.sendMessage(message);
                }else {
                    message.what = 0x11;
                    message.obj = "账户已存在";
                    handler2.sendMessage(message);
                }
            }
        }).start();
    }

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
                    makeText(login.this, "账户或密码错误", Toast.LENGTH_LONG).show();
                    //Log.e(" MainActivity","得不到消息：");
                    prog_login.setVisibility(View.INVISIBLE);
                    break;
                case 0x12:
                    String ss="";
                    List<String> name_Obtain= (List<String>) msg.obj;
                    db_password=name_Obtain.get(0);
                    kkkkk=name_Obtain.get(1);
                    //Permission(name_Obtain.get(1));
                    password_txt=edit_password.getText().toString();

                    if(password_txt.equals(db_password)){
                        makeText(login.this, "登录成功", Toast.LENGTH_LONG).show();
                        prog_login.setVisibility(View.INVISIBLE);
                        Intent intent = new Intent(login.this,MainActivity.class);
                        startActivity(intent);
                    }
                    else{
                        makeText(login.this, "账户或密码错误", Toast.LENGTH_LONG).show();
                        prog_login.setVisibility(View.INVISIBLE);
                    }
                   break;
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
                    prog_login.setVisibility(View.INVISIBLE);
                    break;
                case 0x12:
                    prog_login.setVisibility(View.INVISIBLE);
                    break;
            }
        }
    };
    @SuppressLint("HandlerLeak")
    /**
     * 线程定义
     * */
    private Handler handler2 = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case 0x11:
                    makeText(login.this, "账户已存在", Toast.LENGTH_LONG).show();
                    prog_login.setVisibility(View.INVISIBLE);
                    break;
                case 0x12:
                    regedit_account(acount_regedit,password_regedit,attributton_regedit);
                    makeText(login.this, "注册成功", Toast.LENGTH_LONG).show();
                    prog_login.setVisibility(View.INVISIBLE);
                    break;
            }
        }
    };
    /**
    *
    *
    * */
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
    @Override
    public void onBackPressed() {//重写的Activity返回

        Intent intent = new Intent();
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        startActivity(intent);

    }
    //清除密码
    private void clearAccountPassword(){
        SharedPreferences sp=getSharedPreferences("Logindb",MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.clear();
        editor.commit();
    }
    //保存密码数据
    private void saveAccountPassword(){
        SharedPreferences sp=getSharedPreferences("Logindb",MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("loginEdt",edit_account.getText().toString());
        editor.putString("passwordEdt",edit_password.getText().toString());
        editor.putBoolean("save",true);
        editor.commit();            //写入数据
        //Toast.makeText(login.this,"sd",Toast.LENGTH_LONG).show();
    }
    //读取保存密码数据
    private void getAccountPassword(){
        SharedPreferences sp=getSharedPreferences("Logindb",MODE_PRIVATE);
        String name= sp.getString("loginEdt","");
        String password=sp.getString("passwordEdt","");
        edit_account.setText(name);
        edit_password.setText(password);
    }
    //此处添加新的邀请码，不同地区有不同地区的邀请码
    public static void Invitation_code_funcation(List Invitation_code){


        Invitation_code.add("rv2bL2");  //向列表中添加数据
        Invitation_code.add("xP2T43");  //向列表中添加数据
        Invitation_code.add("MrfKZ9");  //向列表中添加数据
        Invitation_code.add("41sEvW");
        Invitation_code.add("Sy09J8");
        Invitation_code.add("0sF0O7");
        Invitation_code.add("lJ60Pc");
        Invitation_code.add("mq5iD0");//超级用户验证码


    }
    //设置查看权限
    public void Inquire_version(final String location_name,final String station_name){
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 调用数据库工具类DBUtils的getInfoByName方法获取数据库表中数据
                List<String> name_Obtain1 =DButilsversion.result(location_name,station_name);
                Message message = handler3.obtainMessage();
                if(name_Obtain1 != null){
                    message.what = 0x12;
                    message.obj = name_Obtain1;
                }else {
                    message.what = 0x11;
                    message.obj = "查询结果为空";
                }
                // 发消息通知主线程更新UI
                handler3.sendMessage(message);
            }
        }).start();
    }
    public void version_update(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder about = new AlertDialog.Builder(login.this);
        about.setIcon(R.drawable.smagro);
        about.setTitle("版本更新");
        about.setMessage(
                "当前版本:"+current_version+"\n\n"+
                        "最新版本: "+new_version_name+"\n\n"+
                        "温馨提示：拒绝更新不能使用此软件\n\n"+
                        "点击“确定”下载最新版本"

        );
        about.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        download_new_version(version_url);


                    }
                });
        // 显示
        about.show();
    }
    private void download_new_version(String version_url) {
        downloadManagerUtil = new DownloadManagerUtil(this);
        if (downloadId != 0) {
            downloadManagerUtil.clearCurrentTask(downloadId);

        }

        downloadId = downloadManagerUtil.download(version_url, title, desc);
    }
    @SuppressLint("HandlerLeak")
    private Handler handler3 = new Handler(){
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
                    pushnew=name_Obtain1.get(3);

                    if(version_readme.equals("YES")){
                        TextViewPushnew.setText(pushnew);
                        prog_login.setVisibility(View.INVISIBLE);
                        makeText(login.this, "系统正在维护", Toast.LENGTH_LONG).show();
                        break;
                    }
                    else{
                    }

                    if (current_version.equals(new_version_name)==true){
                        TextViewPushnew.setText(pushnew);
                        btn_login.setVisibility(View.VISIBLE);
                        btn_regedit.setVisibility(View.VISIBLE);
                        prog_login.setVisibility(View.INVISIBLE);
                        makeText(login.this, "检查完成,请登录", Toast.LENGTH_LONG).show();

                    }
                    else {
                        btn_login.setVisibility(View.INVISIBLE);
                        btn_regedit.setVisibility(View.INVISIBLE);
                        prog_login.setVisibility(View.INVISIBLE);
                        version_update();
                    }

            }
        }
    };




}
