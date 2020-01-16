package com.iww.classifiedolx.Fragments

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
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.google.gson.Gson
import com.iww.classifiedolx.Listeners.OnFragmentInteractionListener
import com.iww.classifiedolx.LoginSignupScreen
import com.iww.classifiedolx.R
import com.iww.classifiedolx.Utilities.SharedPref
import com.iww.classifiedolx.Utilities.Utility
import com.iww.classifiedolx.Utilities.Utility.isConnected
import com.iww.classifiedolx.api.AllApiResponse
import com.iww.classifiedolx.api.AppConstants
import com.iww.classifiedolx.api.AppController
import com.iww.classifiedolx.api.interfaces.ApiService
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_my_account.*
import kotlinx.android.synthetic.main.progress_dialog.*
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

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class MyAccountFragment : Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.fl_editImg -> {
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
            R.id.tv_editProfile -> {
                showAlertDialog("Update Profile ?", "Want to update profile ! ", "profile")
            }
            R.id.tv_submitProfile -> {
                checkEditFieldValue()
            }
            R.id.tv_updatePsd -> {

                showAlertDialog("Update Password ?", "Want to change a new password ! ", "updatePassword")

            }
            R.id.tv_login ->
            {
                startActivity(Intent(context, LoginSignupScreen::class.java))

            }
        }

    }

    private fun showAlertDialog(strTitle: String, strMsg: String, strAction: String) {

        val builder = AlertDialog.Builder(context!!)
        //set title for alert dialog
        builder.setTitle(strTitle)
        //set message for alert dialog
        builder.setMessage(strMsg)
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        //performing positive action
        builder.setPositiveButton("Yes") { dialogInterface, which ->

            if (strAction.equals("profile")) {
                et_proEmail.isEnabled = true
                et_proEmail.isClickable = true
                et_proName.isEnabled = true
                et_proName.isClickable = true
                et_proPhone.isEnabled = true
                et_proPhone.isClickable = true
                fl_editImg.visibility = View.VISIBLE
                tv_editProfile.visibility = View.GONE
                tv_submitProfile.visibility = View.VISIBLE
            }
            if (strAction.equals("updatePassword")) {
                showUpdatePsdPopup()
            }
        }
          builder.setNegativeButton("No") { dialogInterface, which ->
            dialogInterface.cancel()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun showUpdatePsdPopup()
        {

            var dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.popup_update_psd)
            var tv_submitUpdatePsd = dialog.findViewById(R.id.tv_submitUpdatePsd) as TextView
            var iv_close= dialog.findViewById(R.id.iv_close) as ImageView

            var et_oldPsd= dialog.findViewById(R.id.et_oldPsd) as EditText
             var et_newPsd= dialog.findViewById(R.id.et_newPsd) as EditText
            var et_confirmPsd= dialog.findViewById(R.id.et_confirmPsd) as EditText

            tv_submitUpdatePsd.setOnClickListener(View.OnClickListener {

                var view = activity!!.currentFocus
                if (view != null) {
                    hideKeyboardFrom(context!!, view)
                    view.clearFocus()
                }

                var strOldPsd= et_oldPsd.text.replace(" ".toRegex(), "").toString()
                var strNewPsd= et_newPsd.text.replace(" ".toRegex(), "").toString()
                var strConfirmPsd= et_confirmPsd.text.replace(" ".toRegex(), "").toString()

                if (strOldPsd.trim().equals("")) {
                    Utility.snackBar(iv_profile ,"Please enter old password")

                }else  if (strNewPsd.trim().equals("")) {
                    Utility.snackBar(iv_profile ,"Please enter new password")

                }else  if (strConfirmPsd.trim().equals("")) {
                    Utility.snackBar(iv_profile ,"Please confirm new password")
                 } else  if (!strNewPsd.equals(strConfirmPsd)) {
                    Utility.snackBar(iv_profile ,"Password confirmation not matched")
                }

                else if (isConnected(context!!)) {
                     callUpdatePsdApi( strOldPsd,strNewPsd,dialog)
                    //update_password($con,$Old_Password,$New_Password,$User_Id)
                } else {
                    Utility.snackBar(iv_profile,"Please Check Internet Connection")

                }
            })

            iv_close.setOnClickListener(View.OnClickListener {
                dialog.cancel()

            })

            var lWindowParams = WindowManager.LayoutParams()
            lWindowParams.copyFrom(dialog.window.attributes)
            lWindowParams.width = WindowManager.LayoutParams.MATCH_PARENT
            lWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT
            dialog.window.attributes = lWindowParams
            dialog.show()

    }

    fun hideKeyboardFrom(context: Context, view: View) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm!!.hideSoftInputFromWindow(view.windowToken, 0)
    }


    private fun callUpdatePsdApi(  strOldPsd: String, strNewPsd: String,popUpDialog:Dialog) {
//"update_password",
        var dialog = Utility.dialog(ctx!!)
        apiService.updatePassword("update_password", strOldPsd,strNewPsd, sharedPref!!.userId)
            .enqueue(object : Callback<AllApiResponse.CommonRes> {
                override fun onResponse(
                    call: Call<AllApiResponse.CommonRes>,
                    response: Response<AllApiResponse.CommonRes>
                ) {
                    Utility.snackBar(iv_profile, "" + response.body()!!.message)
                    if (response.isSuccessful && (response.body()!!.status.equals("1"))) {
                        popUpDialog.hide()

                    } else {

                    }

                    dialog.cancel()

                }

                override fun onFailure(call: Call<AllApiResponse.CommonRes>, t: Throwable) {
                    t.printStackTrace()
                    dialog.cancel()

                }
            })
    }

    private fun checkEditFieldValue() {
        var strName = et_proName.text.toString().replace(" ".toRegex(), "")
        var strEmail = et_proEmail.text.toString().replace(" ".toRegex(), "")
        //   var strPsd = et_proPsd.text.toString().replace(" ".toRegex(), "")
        var strPhone = et_proPhone.text.toString().replace(" ".toRegex(), "")

        if (TextUtils.isEmpty(strName))
            Utility.snackBar(iv_profile, "Please enter name")
        else if (TextUtils.isEmpty(strEmail))
            Utility.snackBar(iv_profile, "Please enter email")
        else if (!Utility.isValidEmail(strEmail))
            Utility.snackBar(iv_profile, "Please enter valid Email ")
        else if (TextUtils.isEmpty(strPhone))
            Utility.snackBar(iv_profile, "Please enter mobile number")
        else if (strPhone.length != 10)
            Utility.snackBar(iv_profile, "Entered mobile number is wrong")
        else if (Utility.isConnected(context!!)) {
            callUpdateProfileApi(
                "update_profile",
                "" + et_proName.text,
                "" + et_proEmail.text,
                "" + et_proPhone.text,
                "" + sharedPref!!.password
            )
        } else {
            Utility.snackBar(iv_profile, "Please Check Internet Connection")
        }

    }


    private fun callUpdateProfileApi(
        strApiAction: String,
        name: String,
        email: String,
        phone: String,
        password: String
    ) {

        var dialog = Utility.dialog(ctx!!)
        apiService.updateProfileApi(strApiAction, name, email, phone, password, sharedPref!!.userId)
            .enqueue(object : Callback<AllApiResponse.LoginResp> {
                override fun onResponse(
                    call: Call<AllApiResponse.LoginResp>,
                    response: Response<AllApiResponse.LoginResp>
                ) {
                    Utility.snackBar(iv_profile, "" + response.body()!!.message)
                    if (response.isSuccessful && (response.body()!!.status.equals("1"))) {
                        var dataModel = response.body()!!.data[0]
                        sharedPref!!.userId = dataModel.id
                        sharedPref!!.email = dataModel.emailid
                        sharedPref!!.password = dataModel.password
                        sharedPref!!.phone = dataModel.phone
                        sharedPref!!.name = dataModel.name
                        et_proPhone.setText("" + sharedPref!!.phone)
                        et_proEmail.setText("" + sharedPref!!.email)
                        et_proName.setText("" + sharedPref!!.name)
                        et_proEmail.isEnabled = false
                        et_proEmail.isClickable = false
                        et_proName.isEnabled = false
                        et_proName.isClickable = false
                        et_proPhone.isEnabled = false
                        et_proPhone.isClickable = false
                        tv_submitProfile.visibility = View.GONE
                        tv_editProfile.visibility = View.VISIBLE
                        fl_editImg.visibility = View.GONE

                    } else {

                    }

                    dialog.cancel()

                }

                override fun onFailure(call: Call<AllApiResponse.LoginResp>, t: Throwable) {
                    t.printStackTrace()
                    dialog.cancel()

                }
            })
    }

    @Inject
    internal lateinit var apiService: ApiService


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    var ctx: Context? = null
    var TAG = "MyAccountFragment "
    var sharedPref: SharedPref? = null
    var mImageUri: Uri? = null
    internal var picturePath = ""
    var progreesDialog :Dialog?=null
    var circularProgressDrawable:CircularProgressDrawable ? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = SharedPref(context)
          progreesDialog = Utility.dialog(ctx!!)
          circularProgressDrawable  = CircularProgressDrawable(ctx as Activity)
        circularProgressDrawable!!.strokeWidth = 5f
        circularProgressDrawable!!.centerRadius = 30f
        circularProgressDrawable!!.start()
        if (sharedPref!!.userId == "") {
            ll_notLoggedIn.visibility = View.VISIBLE
            ll_loggedInVw.visibility = View.GONE
        } else {
            ll_loggedInVw.visibility = View.VISIBLE
            ll_notLoggedIn.visibility = View.GONE

            getProfileData()
        }
        tv_login.setOnClickListener(this)

        fl_editImg.setOnClickListener(this)
        tv_submitProfile.setOnClickListener(this)
        tv_editProfile.setOnClickListener(this)
        tv_updatePsd.setOnClickListener(this)
        ActivityCompat.requestPermissions(
            activity!!,
            arrayOf(
                android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            1
        )

        et_proEmail.isEnabled = false
        et_proEmail.isClickable = false
        et_proName.isEnabled = false
        et_proName.isClickable = false
        et_proPhone.isEnabled = false
        et_proPhone.isClickable = false
    }

    private fun getProfileData() {
progreesDialog!!.show()
        apiService!!.getProfileData("get_profile", sharedPref!!.userId)
            .enqueue(object : Callback<AllApiResponse.LoginResp> {
                override fun onResponse(
                    call: Call<AllApiResponse.LoginResp>,
                    response: Response<AllApiResponse.LoginResp>
                ) {
                    Log.e("getProfileData res", "" + Gson().toJson(response.body()))
                    if (response.isSuccessful && (response.body()!!.status.equals("1"))) {
                        var dataModel = response.body()!!.data[0]
                        sharedPref!!.userId = dataModel.id
                        sharedPref!!.email = dataModel.emailid
                        sharedPref!!.password = dataModel.password
                        sharedPref!!.phone = dataModel.phone
                        sharedPref!!.name = dataModel.name

                        Picasso.with(context).load(Uri.parse(AppConstants.IMAGE_Reg_img+dataModel.getProfile())).placeholder(circularProgressDrawable).error(R.drawable.app_logo).into(iv_profile)
                         et_proName.setText("" + sharedPref!!.name)
                        et_proEmail.setText("" + sharedPref!!.email)
                        et_proPhone.setText("" + sharedPref!!.phone)
                    } else {
                    }
                    progreesDialog!!.cancel()
                }

                override fun onFailure(call: Call<AllApiResponse.LoginResp>, t: Throwable) {
                    t.printStackTrace()
                    progreesDialog!!.cancel()
                }
            })

    }

    private fun chooseImage() {
        val items = arrayOf<CharSequence>("Take Photo", "Choose from Library", "Cancel")

        val builder = AlertDialog.Builder(ctx!!)
        builder.setTitle("Add Photo!")
        builder.setItems(items) { dialog, item ->
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

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
            setAndUploadImg(bitmap)

        }
        if (requestCode == 100) {
            if (mImageUri != null) {
                try {
                    val bitmap = compressImage(picturePath)
                    setAndUploadImg(bitmap)
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

    private fun setAndUploadImg(bitmap: Bitmap?) {
        iv_profile.setImageBitmap(bitmap)
        var sourceFile: File? = null
        var body1: MultipartBody.Part? = null
        if (bitmap != null) {
            sourceFile = File("" + getRealPathFromURI(getImageUri(context!!, bitmap!!)))
            val requestFile = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), sourceFile)

            body1 = MultipartBody.Part.createFormData("Profile", sourceFile!!.getName(), requestFile)
            val fileSizeInBytes = sourceFile.length()
            Log.e(
                "Profile ",
                "Bytes=" + fileSizeInBytes
            )
        }
        progreesDialog!!.show()
        apiService.updateProfileImgApi("update_profile_picture", sharedPref!!.userId, body1)
            .enqueue(object : Callback<AllApiResponse.LoginResp> {
                override fun onResponse(
                    call: Call<AllApiResponse.LoginResp>,
                    response: Response<AllApiResponse.LoginResp>
                ) {
                    Utility.snackBar(iv_profile, "" + response.body()!!.message)
                    progreesDialog!!.cancel()
                    if (response.isSuccessful && (response.body()!!.status.equals("1"))) {
                        var dataModel = response.body()!!.data[0]
                        sharedPref!!.userId = dataModel.id
                        sharedPref!!.email = dataModel.emailid
                        sharedPref!!.password = dataModel.password
                        sharedPref!!.phone = dataModel.phone
                        sharedPref!!.name = dataModel.name
                        et_proPhone.setText("" + sharedPref!!.phone)
                        et_proEmail.setText("" + sharedPref!!.email)
                        et_proName.setText("" + sharedPref!!.name)
                        Picasso.with(context).load(Uri.parse(AppConstants.IMAGE_Reg_img+dataModel.getProfile())).placeholder(circularProgressDrawable).error(R.drawable.app_logo).into(iv_profile)
                        et_proEmail.isEnabled = false
                        et_proEmail.isClickable = false
                        et_proName.isEnabled = false
                        et_proName.isClickable = false
                        et_proPhone.isEnabled = false
                        et_proPhone.isClickable = false
                        tv_submitProfile.visibility = View.GONE
                        tv_editProfile.visibility = View.VISIBLE
                        fl_editImg.visibility = View.GONE

                    } else {

                    }


                }

                override fun onFailure(call: Call<AllApiResponse.LoginResp>, t: Throwable) {
                    t.printStackTrace()
                    progreesDialog!! .cancel()

                }
            })
    }


    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
        ((ctx!! as Activity).application as AppController).component.inject(this@MyAccountFragment)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyAccountFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
