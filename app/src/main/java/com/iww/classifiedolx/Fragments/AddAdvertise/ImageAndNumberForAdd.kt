package com.iww.classifiedolx.Fragments.AddAdvertise

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.iww.classifiedolx.AdsDoneActivity
import com.iww.classifiedolx.Listeners.OnFragmentInteractionListener
import com.iww.classifiedolx.MainActivity
import com.iww.classifiedolx.NavigationHost
import com.iww.classifiedolx.R
import com.iww.classifiedolx.Utilities.SharedPref
import com.iww.classifiedolx.Utilities.Utility
import com.iww.classifiedolx.api.AllApiResponse
import com.iww.classifiedolx.api.AppController
import com.iww.classifiedolx.api.interfaces.ApiService
import kotlinx.android.synthetic.main.fragment_show_or_hide_number_for_add.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ImageAndNumberForAdd : Fragment(), View.OnClickListener {
    @SuppressLint("ResourceAsColor")
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.chkBox_no -> {
                chkBox_no.isChecked = true
                chkBox_no.setTextColor(R.color.colorAccent)
                chkBox_yes.isChecked = false
                chkBox_yes.setTextColor(R.color.colorBlack)
                showMobileChk="0"
            }
            R.id.chkBox_yes -> {
                chkBox_yes.isChecked = true
                chkBox_yes.setTextColor(R.color.colorAccent)
                chkBox_no.isChecked = false
                chkBox_no.setTextColor(R.color.colorBlack)
                showMobileChk="1"
            }

            R.id.tv_chooseImg1 -> {
                flag = 1
                ActivityCompat.requestPermissions(
                    activity!!,
                    arrayOf(
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ),
                    1
                )
                chooseImage()

            }

            R.id.tv_chooseImg2 -> {
                flag = 2
                ActivityCompat.requestPermissions(
                    activity!!,
                    arrayOf(
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ),
                    1
                )
                chooseImage()
            }

            R.id.tv_chooseImg3 -> {
                flag = 3
                ActivityCompat.requestPermissions(
                    activity!!,
                    arrayOf(
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ),
                    1
                )
                chooseImage()
            }

            R.id.tv_chooseImg4 -> {
                flag = 4
                ActivityCompat.requestPermissions(
                    activity!!,
                    arrayOf(
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ),
                    1
                )
                chooseImage()

            }
            R.id.tv_submitAds -> {
                checkFieldValue()
            }
            R.id.fl_imageAndNumberForAdd -> {

            }

        }
    }

    private fun checkFieldValue() {

        if (!chkBox_yes.isChecked && !chkBox_no.isChecked) {
            Utility.snackBar(tv_mobile, "Please check yes or no (for show mobile on Ads) ")
        } else
            if (!isImageSelected1 && !isImageSelected2 && !isImageSelected3 && !isImageSelected4) {
                Utility.snackBar(tv_mobile, "Please choose at least one image")

            } else {
                submitAdvertisementApi()
            }
    }

    // TODO: Rename and change types of parameters
    //   private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    var addNewAdvertiseRequestParam: AllApiResponse.AddAdvertiseRequest? = null
    var ctx: Context? = null
    var sharedPref: SharedPref? = null
    var mImageUri: Uri? = null
    var flag = 0
    private var lastBitmap1: Bitmap? = null
    private var lastBitmap2: Bitmap? = null
    private var lastBitmap3: Bitmap? = null
    private var lastBitmap4: Bitmap? = null

    private var isImageSelected1 = false
    private var isImageSelected2 = false
    private var isImageSelected3 = false
    private var isImageSelected4 = false
var showMobileChk=""
    @Inject
    internal lateinit var apiService: ApiService
    internal var picturePath = ""
    var TAG = "ImageAndNumberForAdd "
    var progreesDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            //       param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = SharedPref(context)
       progreesDialog = Utility.dialog(ctx!!)
     showHideDialog(false)
        tv_mobile.text = "Verified Number     " + sharedPref!!.phone
        chkBox_no.setOnClickListener(this)
        chkBox_yes.setOnClickListener(this)
        tv_chooseImg1.setOnClickListener(this)
        tv_chooseImg2.setOnClickListener(this)
        tv_chooseImg3.setOnClickListener(this)
        tv_chooseImg4.setOnClickListener(this)
        tv_submitAds.setOnClickListener(this)
        fl_imageAndNumberForAdd.setOnClickListener(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_or_hide_number_for_add, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context ) {
        super.onAttach(context)
        ctx = context
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
        ((ctx!! as Activity).application as AppController).component.inject(this@ImageAndNumberForAdd)
    }

    private fun chooseImage() {
        val items = arrayOf<CharSequence>("Take Photo", "Choose from Library", "Cancel")

        val builder = AlertDialog.Builder(ctx!!)
        builder.setTitle("Add Photo!")
        builder.setItems(items) { dialog, item ->
            dialog.dismiss()
            if (items[item] == "Take Photo") {


                cameraIntent()
            } else if (items[item] == "Choose from Library") {

                galleryIntent()
            } else if (items[item] == "Cancel") {

                dialog.dismiss()
            }
        }
        builder.show()
    }

    private fun galleryIntent() {
        val pickPhotoIntent =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(pickPhotoIntent, 200)
    }

    private fun cameraIntent() {
        val intent = Intent("android.media.action.IMAGE_CAPTURE")
        var photo: File? = null
        try {
            photo = createImageFile()
            try {
                mImageUri = Uri.fromFile(photo)
            } catch (e: Exception) {

            }
            try {
                mImageUri = FileProvider.getUriForFile(
                    ctx!!,
                    "com.iww.classifiedolx.fileprovider",
                    photo
                )
            } catch (e: Exception) {

            }

            Log.e("valueeeee ", "---cameraIntent pass---$mImageUri")
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri)
            //start camera intent
            startActivityForResult(intent, 100)
        } catch (e: Exception) {
            Log.e("$TAG cameraIntent excep", "Can't create file to take picture!")
            Log.e("$TAG cameraIntent excep", "" + e.message)

        }

    }

    fun getFilename(): String {
        val file = File(Environment.getExternalStorageDirectory().path, "MyFolder/Images")
        if (!file.exists()) {
            file.mkdirs()
        }
        return file.absolutePath + "/" + System.currentTimeMillis() + ".jpg"
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(imageFileName, ".jpg", storageDir)
        picturePath = image.absolutePath

        Log.e("currentPhotoPath___img", "--$image")
        Log.e("currentPhotoPath", "--$picturePath")

        return image
    }

    fun compressImage(filePath: String): Bitmap? {

        var scaledBitmap: Bitmap? = null

        val options = BitmapFactory.Options()

        //      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
        //      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true
        var bmp = BitmapFactory.decodeFile(filePath, options)

        var actualHeight = options.outHeight
        var actualWidth = options.outWidth

        //      max Height and width values of the compressed image is taken as 816x612

        val maxHeight = 816.0f
        val maxWidth = 612.0f
        var imgRatio = (actualWidth / actualHeight).toFloat()
        val maxRatio = maxWidth / maxHeight

        //      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight
                actualWidth = (imgRatio * actualWidth).toInt()
                actualHeight = maxHeight.toInt()
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth
                actualHeight = (imgRatio * actualHeight).toInt()
                actualWidth = maxWidth.toInt()
            } else {
                actualHeight = maxHeight.toInt()
                actualWidth = maxWidth.toInt()

            }
        }

        //      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight)

        //      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false

        //      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true
        options.inInputShareable = true
        options.inTempStorage = ByteArray(16 * 1024)

        try {
            //          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options)
        } catch (exception: OutOfMemoryError) {
            exception.printStackTrace()

        }

        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888)
        } catch (exception: OutOfMemoryError) {
            exception.printStackTrace()
        }

        val ratioX = actualWidth / options.outWidth.toFloat()
        val ratioY = actualHeight / options.outHeight.toFloat()
        val middleX = actualWidth / 2.0f
        val middleY = actualHeight / 2.0f

        val scaleMatrix = Matrix()
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY)

        val canvas = Canvas(scaledBitmap!!)
        canvas.matrix = scaleMatrix
        canvas.drawBitmap(
            bmp,
            middleX - bmp.width / 2,
            middleY - bmp.height / 2,
            Paint(Paint.FILTER_BITMAP_FLAG)
        )

        //      check the rotation of the image and display it properly
        val exif: ExifInterface
        try {
            exif = ExifInterface(filePath)

            val orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION, 0
            )
            Log.d("EXIF", "Exif: $orientation")
            val matrix = Matrix()
            if (orientation == 6) {
                matrix.postRotate(90f)
                Log.d("EXIF", "Exif: $orientation")
            } else if (orientation == 3) {
                matrix.postRotate(180f)
                Log.d("EXIF", "Exif: $orientation")
            } else if (orientation == 8) {
                matrix.postRotate(270f)
                Log.d("EXIF", "Exif: $orientation")
            }
            scaledBitmap = Bitmap.createBitmap(
                scaledBitmap, 0, 0,
                scaledBitmap.width, scaledBitmap.height, matrix,
                true
            )
        } catch (e: IOException) {
            e.printStackTrace()
        }

        var out: FileOutputStream? = null
        val filename = getFilename()
        try {
            out = FileOutputStream(filename)

            //          write the compressed bitmap at the destination specified by filename.
            scaledBitmap!!.compress(Bitmap.CompressFormat.JPEG, 80, out)

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

        return scaledBitmap

    }

    fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val heightRatio = Math.round(height.toFloat() / reqHeight.toFloat())
            val widthRatio = Math.round(width.toFloat() / reqWidth.toFloat())
            inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
        }
        val totalPixels = (width * height).toFloat()
        val totalReqPixelsCap = (reqWidth * reqHeight * 2).toFloat()
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++
        }

        return inSampleSize
    }
    private fun showHideDialog(dialogStatus: Boolean) {
        if (dialogStatus == true && progreesDialog != null) {


            progreesDialog!!.show()

        }
        if (dialogStatus == false && progreesDialog!= null) {
            progreesDialog!!.hide()
            progreesDialog!!.cancel()
            progreesDialog!!.dismiss()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        showHideDialog(false)
        if (requestCode == 200 && data != null
            && data.data != null
        ) {
            val uri = data.data
            val projection = arrayOf(MediaStore.Images.Media.DATA)

            val cursor = ctx!!.contentResolver.query(uri, projection, null, null, null)
            cursor.moveToFirst()

            val columnIndex = cursor.getColumnIndex(projection[0])
            picturePath = cursor.getString(columnIndex) // returns null
            cursor.close()
            Log.e("selectedImagePath", picturePath)
            val bitmap = compressImage(picturePath)
            setImagesInView(bitmap)

        }
        if (requestCode == 100) {
            if (mImageUri != null) {
                try {
                    val bitmap = compressImage(picturePath)
                    setImagesInView(bitmap)
                } catch (e: Exception) {
                    Log.e(TAG, "exception $e")
                }

            } else {
                Toast.makeText(context, "Please try again", Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun getRealPathFromURI(contentUri: Uri): String? {
        try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = activity!!.managedQuery(contentUri, proj, null, null, null)
            val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(column_index)
        } catch (e: Exception) {
            return contentUri.path
        }

    }

    private fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "title", null)
        return Uri.parse(path)
    }

    private fun setImagesInView(bitmap: Bitmap?) {


        if (flag == 1) {
            isImageSelected1 = true
            lastBitmap1 = bitmap
            iv_addImg1.setImageBitmap(bitmap)
        }
        if (flag == 2) {
            isImageSelected2 = true
            lastBitmap2 = bitmap
            iv_addImg2.setImageBitmap(bitmap)
        }
        if (flag == 3) {
            isImageSelected3 = true
            lastBitmap3 = bitmap
            iv_addImg3.setImageBitmap(bitmap)
        }
        if (flag == 4) {
            isImageSelected4 = true
            lastBitmap4 = bitmap
            iv_addImg4.setImageBitmap(bitmap)
        }


    }

    private fun submitAdvertisementApi() {
        showHideDialog(true)

        var sourceFile: File? = null
        var body1: MultipartBody.Part? = null
        var body2: MultipartBody.Part? = null
        var body3: MultipartBody.Part? = null
        var body4: MultipartBody.Part? = null
        if (isImageSelected1 == true && lastBitmap1 != null) {
            sourceFile = File("" + getRealPathFromURI(getImageUri(ctx!!, lastBitmap1!!)))
            val requestFile = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), sourceFile)

            body1 = MultipartBody.Part.createFormData("image1", sourceFile!!.getName(), requestFile)
            val fileSizeInBytes = sourceFile.length()
            Log.e(
                "image1 ",
                "Bytes=" + fileSizeInBytes
            )
        }

        if (isImageSelected2 == true && lastBitmap2 != null) {
            sourceFile = File("" + getRealPathFromURI(getImageUri(ctx!!, lastBitmap2!!)))
            val requestFile = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), sourceFile)

            body2 = MultipartBody.Part.createFormData("image2", sourceFile!!.getName(), requestFile)
            val fileSizeInBytes = sourceFile.length()
            Log.e(
                "image2 ",
                "Bytes=" + fileSizeInBytes
            )
        }
        if (isImageSelected3 == true && lastBitmap3 != null) {
            sourceFile = File("" + getRealPathFromURI(getImageUri(ctx!!, lastBitmap3!!)))
            val requestFile = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), sourceFile)

            body3 = MultipartBody.Part.createFormData("image3", sourceFile!!.getName(), requestFile)
            val fileSizeInBytes = sourceFile.length()

            Log.e(
                "image3 ",
                "Bytes=" + fileSizeInBytes
            )
        }
        if (isImageSelected4 == true && lastBitmap4 != null) {
            sourceFile = File("" + getRealPathFromURI(getImageUri(ctx!!, lastBitmap4!!)))
            val requestFile = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), sourceFile)

            body4 = MultipartBody.Part.createFormData("image4", sourceFile!!.getName(), requestFile)
            val fileSizeInBytes = sourceFile.length()
            Log.e(
                "image4 ",
                "Bytes=" + fileSizeInBytes
            )
        }
        /*Add_Ad($con,$User_Id,$User_Email,$User_Phone,$Ad_title,$cityId,$cityName,$areaId,$areaName,$CatId,$SubCatId,$field1,$field_second1,
        $field2,$field_second2,$field3,$field_second3,$field4,$field_second4,$field5,$field_second5,$field6,$field_second6,$field7,$field_second7,$field8,$field_second8,$field9,$field_second9,$field10,$field_second10,$Ad_Description,$image1,$image2,$image3,$image4)*/



        apiService.addNewAdvertisementApi(
            "Add_Ad",
            "" + sharedPref!!.userId,
            "" + addNewAdvertiseRequestParam!!.Ad_title,
            "" + addNewAdvertiseRequestParam!!.Ad_Description,
            ""+showMobileChk,
            "" + addNewAdvertiseRequestParam!!.cityId,
            "" + addNewAdvertiseRequestParam!!.cityName,
            "" + addNewAdvertiseRequestParam!!.areaId,
            "" + addNewAdvertiseRequestParam!!.areaName,
            "" + addNewAdvertiseRequestParam!!.CatId,
            "" + addNewAdvertiseRequestParam!!.SubCatId,
            "" + addNewAdvertiseRequestParam!!.field1,
            "" + addNewAdvertiseRequestParam!!.field_second1,
            "" + addNewAdvertiseRequestParam!!.field2,
            "" + addNewAdvertiseRequestParam!!.field_second2,
            "" + addNewAdvertiseRequestParam!!.field3,
            "" + addNewAdvertiseRequestParam!!.field_second3,
            "" + addNewAdvertiseRequestParam!!.field4,
            "" + addNewAdvertiseRequestParam!!.field_second4,
            "" + addNewAdvertiseRequestParam!!.field5,
            "" + addNewAdvertiseRequestParam!!.field_second5,
            "" + addNewAdvertiseRequestParam!!.field6,
            "" + addNewAdvertiseRequestParam!!.field_second6,
            "" + addNewAdvertiseRequestParam!!.field7,
            "" + addNewAdvertiseRequestParam!!.field_second7,
            "" + addNewAdvertiseRequestParam!!.field8,
            "" + addNewAdvertiseRequestParam!!.field_second8,
            "" + addNewAdvertiseRequestParam!!.field9,
            "" + addNewAdvertiseRequestParam!!.field_second9,
            "" + addNewAdvertiseRequestParam!!.field10,
            "" + addNewAdvertiseRequestParam!!.field_second10,
            body1,
            body2,
            body3,
            body4
        )
            .enqueue(object : Callback<AllApiResponse.LoginResp> {
                override fun onResponse(
                    call: Call<AllApiResponse.LoginResp>,
                    response: Response<AllApiResponse.LoginResp>
                ) {

                    Utility.snackBar(tv_mobile, "" + response.body()!!.message)
                    showHideDialog(false)


                    if (response.isSuccessful && (response.body()!!.status.equals("1"))) {
/*
                        Utility.enterNextReplaceFragment(
                            R.id.fl_imageAndNumberForAdd,
                            AdsDoneFrag.newInstance(0,""+response.body()!!.message+"\n\n Ads Id: "+response.body()!!.data[0].id),
                            (ctx as MainActivity).supportFragmentManager
                        )
*/

                   //     activity!!.supportFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

                        var intentMain = Intent((ctx!! as Activity), AdsDoneActivity::class.java)
                    //    intentMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                        intentMain.putExtra("message", /*"3"*/ ""+response.body()!!.message+"\n\n Ads Id: "+response.body()!!.data[0].id)
                        startActivity(intentMain)
                        (context as Activity).finish()
//                        (ctx as Activity as NavigationHost).navigateTo(AdsDoneFrag.newInstance(0, response.body()!!.message), true, true)
                   /*     val first = fragmentManager!!.getBackStackEntryAt(1)
                        fragmentManager!!.popBackStack(first.id, FragmentManager.POP_BACK_STACK_INCLUSIVE)
*/
                    } else {

                    }


                }

                override fun onFailure(call: Call<AllApiResponse.LoginResp>, t: Throwable) {
                    t.printStackTrace()
                    showHideDialog(false)


                }
            })

    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: AllApiResponse.AddAdvertiseRequest, param2: String) =
            ImageAndNumberForAdd().apply {
                arguments = Bundle().apply {
                    addNewAdvertiseRequestParam = param1
                    //  putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
