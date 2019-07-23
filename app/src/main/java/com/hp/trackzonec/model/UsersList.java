package com.hp.trackzonec.model;

import java.util.List;

public class UsersList {

    /**
     * status : success
     * Driver_details : [{"id":"1","name":"ram","email":"ram@gmail.com","password":"qwerty","lat":"8.6889","log":"8.447"},{"id":"3","name":"ram","email":"rami@gmail.com","password":"qwerty","lat":"8.755","log":"7.578"},{"id":"4","name":"jack","email":"jack123@gmail.com","password":"123","lat":"123.45","log":"343.676"},{"id":"5","name":"qwerty","email":"qwert@qw.com","password":"qertyu","lat":"8.253695","log":"52.2356"},{"id":"6","name":"hdhshsh","email":"ggg@gag.nn","password":"qhateyeu","lat":"0","log":"0"},{"id":"7","name":"harish","email":"qwerty@qwerty.com","password":"qwerty","lat":"0","log":"0"},{"id":"8","name":"harish","email":"q@q.com","password":"asdf","lat":"0","log":"0"},{"id":"9","name":"harish","email":"harish@harish.com","password":"harish","lat":"0","log":"0"},{"id":"10","name":"arti","email":"arti@outlook.com","password":"arti","lat":"8.5299994","log":"76.9382206"},{"id":"11","name":"mamoj","email":"manojmj420b69@gmail.com","password":"manoj","lat":"0","log":"0"},{"id":"12","name":"hari","email":"har@outlook.com","password":"har","lat":"8.4852785","log":"76.9574587"}]
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
         * lat : 8.6889
         * log : 8.447
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
