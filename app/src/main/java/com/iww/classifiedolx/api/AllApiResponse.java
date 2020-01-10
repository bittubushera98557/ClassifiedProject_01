package com.iww.classifiedolx.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllApiResponse {
    public class MainCategoryRes {
        public String status;
        public String message;
        public List<MainCategoryItem> data = null;

        public class MainCategoryItem {
            public String category_Id;
            public String category_name;
            public String image_upload;
            public String color;
            public String isActive;
        }

    }

    public class SubCategoryRes {

        public String status;
        public String message;
        public List<SubCategoryItem> data = null;

        public class SubCategoryItem {
            public String id;
            public String subcategory;
            public String category_Id;
            public String field1;
            public String field2;
            public String field3;
            public String field4;
            public String field5;
            public String field6;
            public String field7;
            public String field8;
            public String field9;
            public String field10;
            public String isActive;

        }
    }
public class AdverResp{


    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public List<AdverDataModel> data = null;

    public class  AdverDataModel
    {

        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("emailid")
        @Expose
        public String emailid;
        @SerializedName("phonenumber")
        @Expose
        public String phonenumber;
        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("city_name")
        @Expose
        public String cityName;
        @SerializedName("area_name")
        @Expose
        public String areaName;
        @SerializedName("category_Id")
        @Expose
        public String categoryId;
        @SerializedName("subcategory")
        @Expose
        public String subcategory;
        @SerializedName("field1")
        @Expose
        public String field1;
        @SerializedName("field2")
        @Expose
        public String field2;
        @SerializedName("field3")
        @Expose
        public String field3;
        @SerializedName("field4")
        @Expose
        public String field4;
        @SerializedName("field5")
        @Expose
        public String field5;
        @SerializedName("field6")
        @Expose
        public String field6;
        @SerializedName("field7")
        @Expose
        public String field7;
        @SerializedName("field8")
        @Expose
        public String field8;
        @SerializedName("field9")
        @Expose
        public String field9;
        @SerializedName("field10")
        @Expose
        public String field10;
        @SerializedName("discrption")
        @Expose
        public String discrption;
        @SerializedName("phone")
        @Expose
        public String phone;
        @SerializedName("image_upload1")
        @Expose
        public String imageUpload1;
        @SerializedName("image_upload2")
        @Expose
        public String imageUpload2;
        @SerializedName("image_upload3")
        @Expose
        public String imageUpload3;
        @SerializedName("image_upload4")
        @Expose
        public String imageUpload4;
        @SerializedName("isActive")
        @Expose
        public String isActive;
    }
}

    public class CommonRes{

        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("message")
        @Expose
        public String message;


    }

    public class LoginResp{

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("data")
        @Expose
        private List<LoginDataModel> data = null;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<LoginDataModel> getData() {
            return data;
        }

        public void setData(List<LoginDataModel> data) {
            this.data = data;
        }

        public class LoginDataModel {

            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("emailid")
            @Expose
            private String emailid;
            @SerializedName("phone")
            @Expose
            private String phone;
            @SerializedName("password")
            @Expose
            private String password;
            @SerializedName("date")
            @Expose
            private String date;
            @SerializedName("isActive")
            @Expose
            private String isActive;

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

            public String getEmailid() {
                return emailid;
            }

            public void setEmailid(String emailid) {
                this.emailid = emailid;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getIsActive() {
                return isActive;
            }

            public void setIsActive(String isActive) {
                this.isActive = isActive;
            }

        }
    }

    public class RegisterResp{


    }
}






