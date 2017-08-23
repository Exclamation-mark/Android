package com.xiaocool.sugarangel.bean;

/**
 * Created by Administrator on 2017/5/24 0024.
 */

public class HistoryDataBean {

        /**
         * devicedata_id : 5
         * temperature : 0.00
         * sampletype : 0
         * bloodsugar : 0.00
         * datatime : 11112222
         * addtime : 1495590208
         */

        private String devicedata_id;
        private String temperature;
        private String sampletype;
        private String bloodsugar;
        private String datatime;
        private String addtime;

        public String getDevicedata_id() {
            return devicedata_id;
        }

        public void setDevicedata_id(String devicedata_id) {
            this.devicedata_id = devicedata_id;
        }

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        public String getSampletype() {
            return sampletype;
        }

        public void setSampletype(String sampletype) {
            this.sampletype = sampletype;
        }

        public String getBloodsugar() {
            return bloodsugar;
        }

        public void setBloodsugar(String bloodsugar) {
            this.bloodsugar = bloodsugar;
        }

        public String getDatatime() {
            return datatime;
        }

        public void setDatatime(String datatime) {
            this.datatime = datatime;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }
    }
