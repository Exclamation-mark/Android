package com.xiaocool.sugarangel.utils;

import android.util.Log;


import com.xiaocool.sugarangel.SugarangelAPP;
import com.xiaocool.sugarangel.net.NetConstant;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import okhttp3.Call;

/**
 * Created by hzh on 17/1/13.
 */

public class UploadImageUtils {

    public static void uploadImg(String filePath, final OnUploadListener onUploadListener) {
        final String fileName = "yyy" + System.currentTimeMillis() + ".jpg";
        String newPath = SugarangelAPP.DIRECTORY + fileName;
        //压缩图片
        ImageCompressUtils.compressPicture(filePath,newPath);

        OkHttpUtils.post().url(NetConstant.UPLOAD_IMAGE)
                .addFile("upfile", fileName, new File(newPath))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        onUploadListener.onError(e);
                        Log.d("uploadimg", e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equals("success")) {
                                onUploadListener.onSuccess(fileName);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            onUploadListener.onError(e);
                        }
                        Log.d("uploadimg", response);
                    }
                });
    }

    public interface OnUploadListener{
        void onSuccess(String imgName);
        void onError(Exception e);
    }


}
