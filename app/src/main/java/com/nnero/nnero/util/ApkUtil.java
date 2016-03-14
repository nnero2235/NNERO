package com.nnero.nnero.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NNERO on 16/1/22.
 */
public class ApkUtil {

    /**
     * 获得app 版本code
     * @param context
     * @return
     */
    public static int getAppVersionCode(Context context){
        PackageInfo packInfo = null;
        try {
            packInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return 0;
        }
    }

    /**
     * 获取应用版本名称
     * @param context
     * @return
     */
    public static String getAppVersionName(Context context){
        PackageInfo packInfo = null;
        try {
            packInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "0";
        }
    }

    /**
     * 安装apk文件 调用该方法会弹出安装界面
     * @param apkPath apk路径
     * @param context 上下文
     */
    public static void installApk(String apkPath,Context context) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + apkPath),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * 卸载应用 调用该方法会调用系统卸载页面
     * @param packageName 包名
     * @param context 上下文
     */
    public static void uninstallApk(String packageName,Context context){
        Uri packageURI = Uri.parse("package:" + packageName);
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
        context.startActivity(uninstallIntent);
    }

    /**
     * 启动已安装的应用
     * @param componentName 用于启动应用
     * @param context 上下文
     */
    public static void startApp(ComponentName componentName,Context context){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setComponent(componentName);
        context.startActivity(intent);
    }

    /**
     * 获取已安装的apk信息 并且可以过滤Category类型
     * @param context
     * @return
     */
    public static List<ResolveInfo> getInstalledApkInfo(Context context){
        Intent mainIntent = new Intent();
        mainIntent.setAction(Intent.ACTION_MAIN);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER); //这里决定过滤的apk 比如Lean_back
        return context.getPackageManager().queryIntentActivities(mainIntent, 0);
    }

    /**
     * 获取其他app应用名称
     * @param info
     * @param context
     * @return
     */
    public static String getAppName(ResolveInfo info,Context context){
        //获取应用的名称
        CharSequence appName = info.loadLabel(context.getPackageManager());
        if(appName == null){
            appName = info.activityInfo.name;
        }
        return appName.toString();
    }

    /**
     * 获取其他app应用icon
     * @param info
     * @param context
     * @return
     */
    public static Drawable getAppIcon(ResolveInfo info,Context context){
        return info.activityInfo.loadIcon(context.getPackageManager());
    }

    /**
     * 获取其他app的ComponeName 该name用于启动该应用
     * @param info
     * @param context
     * @return
     */
    public static ComponentName getAppComponentName(ResolveInfo info,Context context){
        return  new ComponentName(
                info.activityInfo.applicationInfo.packageName,
                info.activityInfo.name);
    }

    /**
     * 获取 未安装 但在sd卡目录的.apk文件的信息 ApplicationInfo
     * @param apkPath
     * @param context
     * @return
     */
    public static ApplicationInfo getUnInstalledApkInfoFromPath(String apkPath,Context context){
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(apkPath,
                PackageManager.GET_ACTIVITIES);
        if(info != null) {
            info.applicationInfo.sourceDir = apkPath;
            info.applicationInfo.publicSourceDir = apkPath;
            return info.applicationInfo;
        }else {
            return null;
        }
    }

    /**
     * 获取apk icon 从applicationInfo
     * @param info
     * @param context
     * @return
     */
    public static Drawable getApkIconFromAppInfo(ApplicationInfo info,Context context) {
        return info.loadIcon(context.getPackageManager());
    }

    /**
     * 获取apk name 从ApplicationInfo
     * @param info
     * @param context
     * @return
     */
    public static String getApkNameFromAppInfo(ApplicationInfo info,Context context){
        return info.loadLabel(context.getPackageManager()).toString();
    }

    /**
     * 获取已安装的应用 不包含系统应用 （注意该方法和 上面那个不同  无法用category过滤)
     * @param context
     * @return
     */
    public static List<PackageInfo> getInstalledApkExceptSystem(Context context){
        List<PackageInfo> packageInfos = context.getPackageManager().getInstalledPackages(0);
        List<PackageInfo> infos = new ArrayList<>();
        for(PackageInfo packageInfo:packageInfos){
            if((packageInfo.applicationInfo.flags& ApplicationInfo.FLAG_SYSTEM)==0) {//这里是屏蔽系统应用
                infos.add(packageInfo);
            }
        }
        return infos;
    }
}
