package com.jerry.littlepanda.Utils;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by jerry.hu on 18/07/17.
 */

public class FileUtil {
    private static int FILE_SIZE = 4*1024;
    private static String TAG = "FileUtil";
    public static boolean hasSdcard(){
        String status = Environment.getExternalStorageState();
        if(status.equals(Environment.MEDIA_MOUNTED)){
            return true;
        }
        return false;
    }
    public static boolean createPath(String path){
        File f = new File(path);
        if(!f.exists()){
            Boolean o = f.mkdirs();
            Log.i(TAG, "create dir:"+path+":"+o.toString());
            return o;
        }
        return true;
    }
    public static boolean exists(String file){
        return new File(file).exists();
    }
    public static File saveFile(String file, InputStream inputStream){
        File f = null;
        OutputStream outSm = null;
        try{
            f = new File(file);
            String path = f.getParent();
            if(!createPath(path)){
                Log.e(TAG, "can't create dir:"+path);
                return null;
            }
            if(!f.exists()){
                f.createNewFile();
            }
            outSm = new FileOutputStream(f);
            byte[] buffer = new byte[FILE_SIZE];
            while((inputStream.read(buffer)) != -1){
                outSm.write(buffer);
            }
            outSm.flush();
        }catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }finally{
            try{
                if(outSm != null) outSm.close();
            }catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        Log.v(TAG,"[FileUtil]save file:"+file+":"+Boolean.toString(f.exists()));
        return f;
    }
    public static Drawable getImageDrawable(String file){
        if(!exists(file)) return null;
        try{
            InputStream inp = new FileInputStream(new File(file));
            return BitmapDrawable.createFromStream(inp, "img");
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
