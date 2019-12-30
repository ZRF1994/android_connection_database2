package com.smagro.database;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
/**
 * @author Zhang Rufei
 * @data 2019/11/12
 * @email 1106815482@qq.com
 * 代码说明：此端代码实现账户登录。判断输入账户在数据库中对应的密码是否与输入密码相等
 */
public class DownloadManagerUtil {



    private Context mContext;

    public DownloadManagerUtil(Context context) {
        mContext = context;
    }

    public long download(String url, String title, String desc) {
        Uri uri = Uri.parse(url);
        DownloadManager.Request req = new DownloadManager.Request(uri);
        //设置WIFI下进行更新

        //下载中和下载完后都显示通知栏
        req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        //使用系统默认的下载路径 此处为应用内 /android/data/packages ,所以兼容7.0
        req.setDestinationInExternalFilesDir(mContext, Environment.DIRECTORY_DOWNLOADS, title);
        //通知栏标题
        req.setTitle(title);
        //通知栏描述信息
        req.setDescription(desc);
        //设置类型为.apk
        req.setMimeType("application/vnd.android.package-archive");
        //获取下载任务ID
        DownloadManager dm = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        return dm.enqueue(req);
    }

    /**
     * 下载前先移除前一个任务，防止重复下载
     * @param downloadId
     */
    public void clearCurrentTask(long downloadId) {
        DownloadManager dm = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        try {
            dm.remove(downloadId);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
    }


}
