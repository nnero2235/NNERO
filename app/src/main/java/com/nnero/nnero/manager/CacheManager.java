package com.nnero.nnero.manager;


import com.nnero.nnero.App;
import com.nnero.nnero.util.CommonUtil;
import com.nnero.nnero.util.NLog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by nnero on 16/3/11.
 *
 * 缓存管理类 缓存主要是序列化对象
 *
 * 工作原理：根据key 计算md5作为文件名，然后将对象序列化为文件存于/data/data/package/cache文件夹下
 */
public class CacheManager {
    private static final String TAG = "CacheManager";
    private static final String CACHE_DIR = "Cache_serialize";

    private static File sCacheFilePath;

    public static void init(){
        sCacheFilePath = new File(App.getInstance().getCacheDir(),CACHE_DIR);
        if(!sCacheFilePath.exists()){
            sCacheFilePath.mkdir();
        }
    }

    public static void save(String key,Object o){
        key = CommonUtil.MD5(key);
        NLog.d(TAG,"save:"+key);
        serialize(key, o);
    }

    public static Object get(String key){
        key = CommonUtil.MD5(key);
        NLog.d(TAG,"get:"+key);
        return deserialize(key);
    }
    //序列化
    private static void serialize(String fileName,Object o){
        File file = new File(sCacheFilePath,fileName);
        if(file.exists()){
            file.delete();
        }
        NLog.d(TAG,file.getAbsolutePath());
        ObjectOutputStream objectOutputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(o);
        } catch (IOException e) {
            NLog.d(TAG,"io execption:"+e.getMessage());
        } finally {
            try {
                if(fileOutputStream != null){
                    fileOutputStream.close();;
                }
                if(objectOutputStream != null){
                    objectOutputStream.close();
                }
            } catch (IOException e) {
                NLog.d(TAG,"close exception:\n"+e.getMessage());
            }
        }
    }
    //反序列化
    private static Object deserialize(String key){
        File file = new File(sCacheFilePath,key);
        if(!file.exists()){
            return null;
        }
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(file));
            return objectInputStream.readObject();
        } catch (Exception e){
            NLog.d(TAG,e.getMessage());
        } finally {
            try{
                if(objectInputStream != null){
                    objectInputStream.close();
                }
            }catch (IOException e){
                NLog.d(TAG,"close "+e.getMessage());
            }
        }
        return null;
    }
}
