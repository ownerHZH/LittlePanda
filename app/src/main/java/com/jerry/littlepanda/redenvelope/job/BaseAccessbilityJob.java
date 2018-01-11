package com.jerry.littlepanda.redenvelope.job;

import android.content.Context;

import com.jerry.littlepanda.redenvelope.Config;
import com.jerry.littlepanda.redenvelope.QiangHongBaoService;


/**

 */
public abstract class BaseAccessbilityJob implements AccessbilityJob {

    private QiangHongBaoService service;

    @Override
    public void onCreateJob(QiangHongBaoService service) {
        this.service = service;
    }

    public Context getContext() {
        return service.getApplicationContext();
    }

    public Config getConfig() {
        return service.getConfig();
    }

    public QiangHongBaoService getService() {
        return service;
    }
}
