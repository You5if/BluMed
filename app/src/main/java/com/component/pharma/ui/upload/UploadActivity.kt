package com.component.pharma.ui.upload

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.component.pharma.data.RetrofitInstance
import com.component.pharma.data.UserPreferences
import com.component.pharma.data.network.UploadApi
import com.component.pharma.data.network.UploadRequestBody
import com.component.pharma.data.repository.UploadRepository
import com.component.pharma.databinding.ActivityUploadBinding
import com.component.pharma.model.AuditColumns
import com.component.pharma.model.responses.UploadResponse
import com.component.pharma.ui.getFileName
import com.component.pharma.ui.snackbar
import retrofit2.Call
import okhttp3.MultipartBody
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*

class UploadActivity : AppCompatActivity(), UploadRequestBody.UploadCallback{

    private lateinit var userId: String
    private var selectedImage: Uri? = null
    lateinit var viewModel: UploadViewModel
    protected lateinit var userPreferences: UserPreferences
    protected val retrofitInstance = RetrofitInstance()


    private lateinit var binding: ActivityUploadBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userPreferences = UserPreferences(this)
        viewModel = UploadViewModel(UploadRepository(retrofitInstance.buildApi(UploadApi::class.java), userPreferences))

        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        binding.imageView.setOnClickListener {
//            openImageChooser()
//        }
//
//
//
//        binding.btnUpload.setOnClickListener {
//            uploadImage()
//        }

//        viewModel.prescripResponse.observe(this, Observer {
//            when(it) {
//                is Resource.Success -> {
//                    startNewActivity(HomeActivity::class.java)
//                            Toast.makeText(this, "success db", Toast.LENGTH_SHORT).show()
//
//                }
//                is Resource.Failure -> Toast.makeText(this, "error db", Toast.LENGTH_SHORT).show()
//            }
//        })




    }

    @SuppressLint("NewApi")
    private fun uploadImage() {
        if (selectedImage == null) {
            binding.layoutRoot.snackbar("Select an image first")
            return
        }



        val parcelFileDescriptor  =contentResolver.openFileDescriptor(selectedImage!!, "r", null) ?: return
        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val file = File(cacheDir, contentResolver.getFileName(selectedImage!!))
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)




//        val body = UploadRequestBody(file,"image", this)
//        UploadApi().uploadImage(
//
//                MultipartBody.Part.createFormData("image", file.name, body)
//        ).enqueue(object: Callback<UploadResponse>{
//            override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
//                binding.layoutRoot.snackbar(t.message!!)
//            }
//
//            override fun onResponse(call: Call<UploadResponse>, response: Response<UploadResponse>) {
//                sendPrescrip(response.body()?.apiPath.toString(),
//                        response.body()?.apiPath.toString(),
//                        response.body()?.extention.toString(),
//                        response.body()?.fileName.toString(),
//                        response.body()?.fullPath.toString(),
//                        response.body()?.originalFileName.toString())
//                binding.layoutRoot.snackbar(response.body().toString())
//
//            }
//        })



    }

    private fun sendPrescrip(apiImagePath:String,
    apiPath: String,
    extension: String,
    fileName:String,
    fullPath: String,
    originalFileName: String) {
        userPreferences.userId.asLiveData().observe(this, Observer {
            userId = it.toString()
            val currentDate = Calendar.getInstance().time
            val auditColumns = AuditColumns(1100001, 201, 10001, "Win32", 1, "unidentified", "unidentified", "unidentified", "26")
//            val prescrip = PrescripModel(0,
//            auditColumns,
//            "A",
//            0,
//            userId.toInt(),
//            true,
//            apiImagePath,
//            apiPath,
//            extension,
//            fileName,
//            fullPath,
//            originalFileName,
//                    currentDate
//            )

//            viewModel.sendPrescrip(prescrip)
        })
    }

    private fun openImageChooser() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
//            val mimeTypes = arrayOf("image/jpeg", "image/png")
//            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(it, REQUEST_CODE_IMAGE_PICKER)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK) {
            when(requestCode) {
                REQUEST_CODE_IMAGE_PICKER -> {
                    selectedImage = data?.data
                    binding.imageView.setImageURI(selectedImage)
                }
            }
        }
    }

    override fun onProgressUpdate(percentage: Int) {

    }

    companion object {
        private const val REQUEST_CODE_IMAGE_PICKER = 100
    }
}