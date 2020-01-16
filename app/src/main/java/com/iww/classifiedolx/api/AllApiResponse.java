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

    public class AdverResp {



        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("data")
        @Expose
        private List<AdverDataModel> data = null;

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

        public List<AdverDataModel> getData() {
            return data;
        }

        public void setData(List<AdverDataModel> data) {
            this.data = data;
        }

        public class AdverDataModel {


            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("cId")
            @Expose
            private String cId;
            @SerializedName("emailid")
            @Expose
            private String emailid;
            @SerializedName("phonenumber")
            @Expose
            private String phonenumber;
            @SerializedName("title")
            @Expose
            private String title;
            @SerializedName("city_name")
            @Expose
            private String cityName;
            @SerializedName("city_id")
            @Expose
            private String cityId;
            @SerializedName("area_name")
            @Expose
            private String areaName;
            @SerializedName("area_id")
            @Expose
            private String areaId;
            @SerializedName("category_Id")
            @Expose
            private String categoryId;
            @SerializedName("subcategory")
            @Expose
            private String subcategory;
            @SerializedName("field1")
            @Expose
            private String field1;
            @SerializedName("field_second1")
            @Expose
            private String fieldSecond1;
            @SerializedName("field2")
            @Expose
            private String field2;
            @SerializedName("field_second2")
            @Expose
            private String fieldSecond2;
            @SerializedName("field3")
            @Expose
            private String field3;
            @SerializedName("field_second3")
            @Expose
            private String fieldSecond3;
            @SerializedName("field4")
            @Expose
            private String field4;
            @SerializedName("field_second4")
            @Expose
            private String fieldSecond4;
            @SerializedName("field5")
            @Expose
            private String field5;
            @SerializedName("field_second5")
            @Expose
            private String fieldSecond5;
            @SerializedName("field6")
            @Expose
            private String field6;
            @SerializedName("field_second6")
            @Expose
            private String fieldSecond6;
            @SerializedName("field7")
            @Expose
            private String field7;
            @SerializedName("field_second7")
            @Expose
            private String fieldSecond7;
            @SerializedName("field8")
            @Expose
            private String field8;
            @SerializedName("field_second8")
            @Expose
            private String fieldSecond8;
            @SerializedName("field9")
            @Expose
            private String field9;
            @SerializedName("field_second9")
            @Expose
            private String fieldSecond9;
            @SerializedName("field10")
            @Expose
            private String field10;
            @SerializedName("field_second10")
            @Expose
            private String fieldSecond10;
            @SerializedName("discrption")
            @Expose
            private String discrption;
            @SerializedName("date")
            @Expose
            private String date;
            @SerializedName("image_upload1")
            @Expose
            private String imageUpload1;
            @SerializedName("image_upload2")
            @Expose
            private String imageUpload2;
            @SerializedName("image_upload3")
            @Expose
            private String imageUpload3;
            @SerializedName("image_upload4")
            @Expose
            private String imageUpload4;
            @SerializedName("show_mobile")
            @Expose
            private String showMobile;
            @SerializedName("isActive")
            @Expose
            private String isActive;
            @SerializedName("field_title1")
            @Expose
            private String fieldTitle1;
            @SerializedName("feat_field1")
            @Expose
            private String featField1;
            @SerializedName("field_title2")
            @Expose
            private String fieldTitle2;
            @SerializedName("feat_field2")
            @Expose
            private String featField2;
            @SerializedName("field_title3")
            @Expose
            private String fieldTitle3;
            @SerializedName("feat_field3")
            @Expose
            private String featField3;
            @SerializedName("field_title4")
            @Expose
            private String fieldTitle4;
            @SerializedName("feat_field4")
            @Expose
            private String featField4;
            @SerializedName("field_title5")
            @Expose
            private String fieldTitle5;
            @SerializedName("feat_field5")
            @Expose
            private String featField5;
            @SerializedName("field_title6")
            @Expose
            private String fieldTitle6;
            @SerializedName("feat_field6")
            @Expose
            private String featField6;
            @SerializedName("field_title7")
            @Expose
            private String fieldTitle7;
            @SerializedName("feat_field7")
            @Expose
            private String featField7;
            @SerializedName("field_title8")
            @Expose
            private String fieldTitle8;
            @SerializedName("feat_field8")
            @Expose
            private String featField8;
            @SerializedName("field_title9")
            @Expose
            private String fieldTitle9;
            @SerializedName("feat_field9")
            @Expose
            private String featField9;
            @SerializedName("field_title10")
            @Expose
            private String fieldTitle10;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getCId() {
                return cId;
            }

            public void setCId(String cId) {
                this.cId = cId;
            }

            public String getEmailid() {
                return emailid;
            }

            public void setEmailid(String emailid) {
                this.emailid = emailid;
            }

            public String getPhonenumber() {
                return phonenumber;
            }

            public void setPhonenumber(String phonenumber) {
                this.phonenumber = phonenumber;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getCityName() {
                return cityName;
            }

            public void setCityName(String cityName) {
                this.cityName = cityName;
            }

            public String getCityId() {
                return cityId;
            }

            public void setCityId(String cityId) {
                this.cityId = cityId;
            }

            public String getAreaName() {
                return areaName;
            }

            public void setAreaName(String areaName) {
                this.areaName = areaName;
            }

            public String getAreaId() {
                return areaId;
            }

            public void setAreaId(String areaId) {
                this.areaId = areaId;
            }

            public String getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(String categoryId) {
                this.categoryId = categoryId;
            }

            public String getSubcategory() {
                return subcategory;
            }

            public void setSubcategory(String subcategory) {
                this.subcategory = subcategory;
            }

            public String getField1() {
                return field1;
            }

            public void setField1(String field1) {
                this.field1 = field1;
            }

            public String getFieldSecond1() {
                return fieldSecond1;
            }

            public void setFieldSecond1(String fieldSecond1) {
                this.fieldSecond1 = fieldSecond1;
            }

            public String getField2() {
                return field2;
            }

            public void setField2(String field2) {
                this.field2 = field2;
            }

            public String getFieldSecond2() {
                return fieldSecond2;
            }

            public void setFieldSecond2(String fieldSecond2) {
                this.fieldSecond2 = fieldSecond2;
            }

            public String getField3() {
                return field3;
            }

            public void setField3(String field3) {
                this.field3 = field3;
            }

            public String getFieldSecond3() {
                return fieldSecond3;
            }

            public void setFieldSecond3(String fieldSecond3) {
                this.fieldSecond3 = fieldSecond3;
            }

            public String getField4() {
                return field4;
            }

            public void setField4(String field4) {
                this.field4 = field4;
            }

            public String getFieldSecond4() {
                return fieldSecond4;
            }

            public void setFieldSecond4(String fieldSecond4) {
                this.fieldSecond4 = fieldSecond4;
            }

            public String getField5() {
                return field5;
            }

            public void setField5(String field5) {
                this.field5 = field5;
            }

            public String getFieldSecond5() {
                return fieldSecond5;
            }

            public void setFieldSecond5(String fieldSecond5) {
                this.fieldSecond5 = fieldSecond5;
            }

            public String getField6() {
                return field6;
            }

            public void setField6(String field6) {
                this.field6 = field6;
            }

            public String getFieldSecond6() {
                return fieldSecond6;
            }

            public void setFieldSecond6(String fieldSecond6) {
                this.fieldSecond6 = fieldSecond6;
            }

            public String getField7() {
                return field7;
            }

            public void setField7(String field7) {
                this.field7 = field7;
            }

            public String getFieldSecond7() {
                return fieldSecond7;
            }

            public void setFieldSecond7(String fieldSecond7) {
                this.fieldSecond7 = fieldSecond7;
            }

            public String getField8() {
                return field8;
            }

            public void setField8(String field8) {
                this.field8 = field8;
            }

            public String getFieldSecond8() {
                return fieldSecond8;
            }

            public void setFieldSecond8(String fieldSecond8) {
                this.fieldSecond8 = fieldSecond8;
            }

            public String getField9() {
                return field9;
            }

            public void setField9(String field9) {
                this.field9 = field9;
            }

            public String getFieldSecond9() {
                return fieldSecond9;
            }

            public void setFieldSecond9(String fieldSecond9) {
                this.fieldSecond9 = fieldSecond9;
            }

            public String getField10() {
                return field10;
            }

            public void setField10(String field10) {
                this.field10 = field10;
            }

            public String getFieldSecond10() {
                return fieldSecond10;
            }

            public void setFieldSecond10(String fieldSecond10) {
                this.fieldSecond10 = fieldSecond10;
            }

            public String getDiscrption() {
                return discrption;
            }

            public void setDiscrption(String discrption) {
                this.discrption = discrption;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getImageUpload1() {
                return imageUpload1;
            }

            public void setImageUpload1(String imageUpload1) {
                this.imageUpload1 = imageUpload1;
            }

            public String getImageUpload2() {
                return imageUpload2;
            }

            public void setImageUpload2(String imageUpload2) {
                this.imageUpload2 = imageUpload2;
            }

            public String getImageUpload3() {
                return imageUpload3;
            }

            public void setImageUpload3(String imageUpload3) {
                this.imageUpload3 = imageUpload3;
            }

            public String getImageUpload4() {
                return imageUpload4;
            }

            public void setImageUpload4(String imageUpload4) {
                this.imageUpload4 = imageUpload4;
            }

            public String getShowMobile() {
                return showMobile;
            }

            public void setShowMobile(String showMobile) {
                this.showMobile = showMobile;
            }

            public String getIsActive() {
                return isActive;
            }

            public void setIsActive(String isActive) {
                this.isActive = isActive;
            }

            public String getFieldTitle1() {
                return fieldTitle1;
            }

            public void setFieldTitle1(String fieldTitle1) {
                this.fieldTitle1 = fieldTitle1;
            }

            public String getFeatField1() {
                return featField1;
            }

            public void setFeatField1(String featField1) {
                this.featField1 = featField1;
            }

            public String getFieldTitle2() {
                return fieldTitle2;
            }

            public void setFieldTitle2(String fieldTitle2) {
                this.fieldTitle2 = fieldTitle2;
            }

            public String getFeatField2() {
                return featField2;
            }

            public void setFeatField2(String featField2) {
                this.featField2 = featField2;
            }

            public String getFieldTitle3() {
                return fieldTitle3;
            }

            public void setFieldTitle3(String fieldTitle3) {
                this.fieldTitle3 = fieldTitle3;
            }

            public String getFeatField3() {
                return featField3;
            }

            public void setFeatField3(String featField3) {
                this.featField3 = featField3;
            }

            public String getFieldTitle4() {
                return fieldTitle4;
            }

            public void setFieldTitle4(String fieldTitle4) {
                this.fieldTitle4 = fieldTitle4;
            }

            public String getFeatField4() {
                return featField4;
            }

            public void setFeatField4(String featField4) {
                this.featField4 = featField4;
            }

            public String getFieldTitle5() {
                return fieldTitle5;
            }

            public void setFieldTitle5(String fieldTitle5) {
                this.fieldTitle5 = fieldTitle5;
            }

            public String getFeatField5() {
                return featField5;
            }

            public void setFeatField5(String featField5) {
                this.featField5 = featField5;
            }

            public String getFieldTitle6() {
                return fieldTitle6;
            }

            public void setFieldTitle6(String fieldTitle6) {
                this.fieldTitle6 = fieldTitle6;
            }

            public String getFeatField6() {
                return featField6;
            }

            public void setFeatField6(String featField6) {
                this.featField6 = featField6;
            }

            public String getFieldTitle7() {
                return fieldTitle7;
            }

            public void setFieldTitle7(String fieldTitle7) {
                this.fieldTitle7 = fieldTitle7;
            }

            public String getFeatField7() {
                return featField7;
            }

            public void setFeatField7(String featField7) {
                this.featField7 = featField7;
            }

            public String getFieldTitle8() {
                return fieldTitle8;
            }

            public void setFieldTitle8(String fieldTitle8) {
                this.fieldTitle8 = fieldTitle8;
            }

            public String getFeatField8() {
                return featField8;
            }

            public void setFeatField8(String featField8) {
                this.featField8 = featField8;
            }

            public String getFieldTitle9() {
                return fieldTitle9;
            }

            public void setFieldTitle9(String fieldTitle9) {
                this.fieldTitle9 = fieldTitle9;
            }

            public String getFeatField9() {
                return featField9;
            }

            public void setFeatField9(String featField9) {
                this.featField9 = featField9;
            }

            public String getFieldTitle10() {
                return fieldTitle10;
            }

            public void setFieldTitle10(String fieldTitle10) {
                this.fieldTitle10 = fieldTitle10;
            }
        }
    }
    public class AdverFullDetailResp {

        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("message")
        @Expose
        public String message;
        @SerializedName("data")
        @Expose
        public List<AdverFullDetailModel> data = null;
public class AdverFullDetailModel{

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("cId")
    @Expose
    public String cId;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("city_name")
    @Expose
    public String cityName;
    @SerializedName("city_id")
    @Expose
    public String cityId;
    @SerializedName("area_name")
    @Expose
    public String areaName;
    @SerializedName("area_id")
    @Expose
    public String areaId;
    @SerializedName("category_Id")
    @Expose
    public String categoryId;
    @SerializedName("subcategory")
    @Expose
    public String subcategory;
    @SerializedName("lat")
    @Expose
    public String lat;
    @SerializedName("lng")
    @Expose
    public String lng;
    @SerializedName("field1")
    @Expose
    public String field1;
    @SerializedName("field_second1")
    @Expose
    public String fieldSecond1;
    @SerializedName("field2")
    @Expose
    public String field2;
    @SerializedName("field_second2")
    @Expose
    public String fieldSecond2;
    @SerializedName("field3")
    @Expose
    public String field3;
    @SerializedName("field_second3")
    @Expose
    public String fieldSecond3;
    @SerializedName("field4")
    @Expose
    public String field4;
    @SerializedName("field_second4")
    @Expose
    public String fieldSecond4;
    @SerializedName("field5")
    @Expose
    public String field5;
    @SerializedName("field_second5")
    @Expose
    public String fieldSecond5;
    @SerializedName("field6")
    @Expose
    public String field6;
    @SerializedName("field_second6")
    @Expose
    public String fieldSecond6;
    @SerializedName("field7")
    @Expose
    public String field7;
    @SerializedName("field_second7")
    @Expose
    public String fieldSecond7;
    @SerializedName("field8")
    @Expose
    public String field8;
    @SerializedName("field_second8")
    @Expose
    public String fieldSecond8;
    @SerializedName("field9")
    @Expose
    public String field9;
    @SerializedName("field_second9")
    @Expose
    public String fieldSecond9;
    @SerializedName("field10")
    @Expose
    public String field10;
    @SerializedName("field_second10")
    @Expose
    public String fieldSecond10;
    @SerializedName("discrption")
    @Expose
    public String discrption;
    @SerializedName("date")
    @Expose
    public String date;
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
    @SerializedName("show_mobile")
    @Expose
    public String showMobile;
    @SerializedName("isActive")
    @Expose
    public String isActive;
    @SerializedName("owner_Id")
    @Expose
    public String ownerId;
    @SerializedName("owner_name")
    @Expose
    public String ownerName;
    @SerializedName("owner_email")
    @Expose
    public String ownerEmail;
    @SerializedName("owner_phone")
    @Expose
    public String ownerPhone;
    @SerializedName("owner_profile_image")
    @Expose
    public String ownerProfileImage;
    @SerializedName("Subcategory_name")
    @Expose
    public String subcategoryName;
    @SerializedName("category_name")
    @Expose
    public String categoryName;
    @SerializedName("field_title1")
    @Expose
    public String fieldTitle1;
    @SerializedName("feat_field1")
    @Expose
    public String featField1;
    @SerializedName("field_title2")
    @Expose
    public String fieldTitle2;
    @SerializedName("feat_field2")
    @Expose
    public String featField2;
    @SerializedName("field_title3")
    @Expose
    public String fieldTitle3;
    @SerializedName("feat_field3")
    @Expose
    public String featField3;
    @SerializedName("field_title4")
    @Expose
    public String fieldTitle4;
    @SerializedName("feat_field4")
    @Expose
    public String featField4;
    @SerializedName("field_title5")
    @Expose
    public String fieldTitle5;
    @SerializedName("feat_field5")
    @Expose
    public String featField5;
    @SerializedName("field_title6")
    @Expose
    public String fieldTitle6;
    @SerializedName("feat_field6")
    @Expose
    public String featField6;
    @SerializedName("field_title7")
    @Expose
    public String fieldTitle7;
    @SerializedName("feat_field7")
    @Expose
    public String featField7;
    @SerializedName("field_title8")
    @Expose
    public String fieldTitle8;
    @SerializedName("feat_field8")
    @Expose
    public String featField8;
    @SerializedName("field_title9")
    @Expose
    public String fieldTitle9;
    @SerializedName("feat_field9")
    @Expose
    public String featField9;
    @SerializedName("field_title10")
    @Expose
    public String fieldTitle10;
    @SerializedName("feat_field10")
    @Expose
    public String featField10;

}

    }
    public class CommonRes {

        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("message")
        @Expose
        public String message;


    }

    public class LoginResp {

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

            @SerializedName("profile")
            @Expose
            public String profile;
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

            public String getProfile() {
                return profile;
            }

            public void setProfile(String profile) {
                this.profile = profile;
            }
        }
    }

    public static class AddAdvertiseRequest {
        public void AddAdvertiseRequest()
        {


        }
        public String User_Email;
        public String User_Phone;
        public String Ad_title;
        public String show_mobile_on_add;
        public String cityId;
        public String cityName;
        public String areaId;
        public String areaName;
        public String CatId;
        public String CatName;
        public String SubCatId;
        public String SubCatName;
        public String field1="";
        public String field_second1="";
        public String field2="";
        public String field_second2="";
        public String field3="";
        public String field_second3="";
        public String field4="";
        public String field_second4="";
        public String field5="";
        public String field_second5="";
        public String field6="";
        public String field_second6="";
        public String field7="";
        public String field_second7="";
        public String field8="";
        public String field_second8="";
        public String field9="";
        public String field_second9="";
        public String field10="";
        public String field_second10="";

        public String Ad_Description="";
        public String image1;
        public String  image2;
        public String  image3;
        public String  image4;



    }
public class CityResp{


    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<CityItemData> data = null;

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

    public List<CityItemData> getData() {
        return data;
    }

    public void setData(List<CityItemData> data) {
        this.data = data;
    }

    public class CityItemData{
        @SerializedName("cityid")
        @Expose
        private String cityid;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("isActive")
        @Expose
        private String isActive;

        public String getCityid() {
            return cityid;
        }

        public void setCityid(String cityid) {
            this.cityid = cityid;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getIsActive() {
            return isActive;
        }

        public void setIsActive(String isActive) {
            this.isActive = isActive;
        }

    }

}

public class AreaResp{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<AreaItemData> data = null;

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

    public List<AreaItemData> getData() {
        return data;
    }

    public void setData(List<AreaItemData> data) {
        this.data = data;
    }

    public class AreaItemData{



        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("cityarea")
        @Expose
        private String cityarea;
        @SerializedName("cityid")
        @Expose
        private String cityid;
        @SerializedName("isActive")
        @Expose
        private String isActive;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCityarea() {
            return cityarea;
        }

        public void setCityarea(String cityarea) {
            this.cityarea = cityarea;
        }

        public String getCityid() {
            return cityid;
        }

        public void setCityid(String cityid) {
            this.cityid = cityid;
        }

        public String getIsActive() {
            return isActive;
        }

        public void setIsActive(String isActive) {
            this.isActive = isActive;
        }
    }
}

}






