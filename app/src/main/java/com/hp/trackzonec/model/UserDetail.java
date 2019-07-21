package com.hp.trackzonec.model;

import java.util.List;

public class UserDetail {

    /**
     * status : success
     * Driver_details : [{"id":"1","name":"ram","email":"ram@gmail.com","password":"qwerty","lat":"8.99","log":"8.11"}]
     */

    private String status;
    private List<DriverDetailsBean> Driver_details;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DriverDetailsBean> getDriver_details() {
        return Driver_details;
    }

    public void setDriver_details(List<DriverDetailsBean> Driver_details) {
        this.Driver_details = Driver_details;
    }

    public static class DriverDetailsBean {
        /**
         * id : 1
         * name : ram
         * email : ram@gmail.com
         * password : qwerty
         * lat : 8.99
         * log : 8.11
         */

        private String id;
        private String name;
        private String email;
        private String password;
        private String lat;
        private String log;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLog() {
            return log;
        }

        public void setLog(String log) {
            this.log = log;
        }
    }
}
