package com.xiaocool.sugarangel;

import android.app.Application;
import android.os.Environment;

import com.lidroid.xutils.util.LogcatHelper;
import com.sinocare.bluetoothle.SN_BluetoothLeConnection;

import java.io.File;

/**
 * Created by Administrator on 2017/5/17 0017.
 */

public class SugarangelAPP extends Application {
    //SD卡根目录
    public static final String DIRECTORY = Environment.getExternalStorageDirectory().getPath() + "/sugarangel/";
    @Override
    public void onCreate() {
        super.onCreate();
        createFile();
        //初始化设备sdk
        SN_BluetoothLeConnection BleConnection = SN_BluetoothLeConnection.getBlueToothBleConnection(this);
        BleConnection.initBleApplicationService();
        LogcatHelper.getInstance(this).start();

    }
    private void createFile() {
        File tempFile = new File(DIRECTORY);
        if (!tempFile.exists()) {
            tempFile.mkdirs();
        }
    }

}
