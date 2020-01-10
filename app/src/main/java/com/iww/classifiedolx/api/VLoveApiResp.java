package com.iww.classifiedolx.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VLoveApiResp {

    public class GenerateOTPResp {


        @SerializedName("data")
        @Expose
        private OtpNumber data;
        @SerializedName("messageType")
        @Expose
        private String messageType;
        @SerializedName("userID")
        @Expose
        private String userID;
        @SerializedName("message")
        @Expose
        private String message;

        public OtpNumber getData() {
            return data;
        }

        public void setData(OtpNumber data) {
            this.data = data;
        }

        public String getMessageType() {
            return messageType;
        }

        public void setMessageType(String messageType) {
            this.messageType = messageType;
        }

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

public class OtpNumber
{

    @SerializedName("otp")
    @Expose
    private Integer otp;

    public Integer getOtp() {
        return otp;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }
}
    }
    public class AllCategoriesResp{
   @SerializedName("data")
        @Expose
        private List<MainCategoryData> data = null;
        @SerializedName("messageType")
        @Expose
        private String messageType;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("message")
        @Expose
        private String message;

        public List<MainCategoryData> getData() {
            return data;
        }

        public void setData(List<MainCategoryData> data) {
            this.data = data;
        }

        public String getMessageType() {
            return messageType;
        }

        public void setMessageType(String messageType) {
            this.messageType = messageType;
        }

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


        public class MainCategoryData {

            @SerializedName("categoryID")
            @Expose
            private Integer categoryID;
            @SerializedName("title")
            @Expose
            private String title;
            @SerializedName("imgUrl")
            @Expose
            private String imgUrl;
            @SerializedName("subCat")
            @Expose
            private List<SubCatData> subCat = null;


            public Integer getCategoryID() {
                return categoryID;
            }

            public void setCategoryID(Integer categoryID) {
                this.categoryID = categoryID;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public List<SubCatData> getSubCat() {
                return subCat;
            }

            public void setSubCat(List<SubCatData> subCat) {
                this.subCat = subCat;
            }
public class  SubCatData{
  @SerializedName("subID")
    @Expose
    private Integer subID;
    @SerializedName("categoryID")
    @Expose
    private Integer categoryID;
    @SerializedName("subCategoryTitle")
    @Expose
    private String subCategoryTitle;

    public Integer getSubID() {
        return subID;
    }

    public void setSubID(Integer subID) {
        this.subID = subID;
    }

    public Integer getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }

    public String getSubCategoryTitle() {
        return subCategoryTitle;
    }

    public void setSubCategoryTitle(String subCategoryTitle) {
        this.subCategoryTitle = subCategoryTitle;
    }
}
        }
        }

    public class UploadedAdsResp{

        @SerializedName("data")
        @Expose
        private UploadedAdsModel data;
        @SerializedName("messageType")
        @Expose
        private String messageType;
        @SerializedName("message")
        @Expose
        private String message;

        public UploadedAdsModel getData() {
            return data;
        }

        public void setData(UploadedAdsModel data) {
            this.data = data;
        }

        public String getMessageType() {
            return messageType;
        }

        public void setMessageType(String messageType) {
            this.messageType = messageType;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
      public  class   UploadedAdsModel {



          @SerializedName("imgData")
          @Expose
          private List<Object> imgData = null;
          @SerializedName("petData")
          @Expose
          private List<Object> petData = null;

          public List<Object> getImgData() {
              return imgData;
          }

          public void setImgData(List<Object> imgData) {
              this.imgData = imgData;
          }

          public List<Object> getPetData() {
              return petData;
          }

          public void setPetData(List<Object> petData) {
              this.petData = petData;
          }
      }
    }


    public class FavouriteResp{

        @SerializedName("data")
        @Expose
        private FavouriteModel data;
        @SerializedName("messageType")
        @Expose
        private String messageType;
        @SerializedName("message")
        @Expose
        private String message;

        public FavouriteModel getData() {
            return data;
        }

        public void setData(FavouriteModel data) {
            this.data = data;
        }

        public String getMessageType() {
            return messageType;
        }

        public void setMessageType(String messageType) {
            this.messageType = messageType;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
        public  class   FavouriteModel {



            @SerializedName("imgData")
            @Expose
            private List<Object> imgData = null;
            @SerializedName("petData")
            @Expose
            private List<Object> petData = null;

            public List<Object> getImgData() {
                return imgData;
            }

            public void setImgData(List<Object> imgData) {
                this.imgData = imgData;
            }

            public List<Object> getPetData() {
                return petData;
            }

            public void setPetData(List<Object> petData) {
                this.petData = petData;
            }
        }
    }

    public class SoldPetResp{

        @SerializedName("data")
        @Expose
        private List<Object> data = null;
        @SerializedName("messageType")
        @Expose
        private String messageType;
        @SerializedName("message")
        @Expose
        private String message;

        public List<Object> getData() {
            return data;
        }

        public void setData(List<Object> data) {
            this.data = data;
        }

        public String getMessageType() {
            return messageType;
        }

        public void setMessageType(String messageType) {
            this.messageType = messageType;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
