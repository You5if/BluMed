package com.component.pharma.ui.upload

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import android.provider.MediaStore.Images
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.component.pharma.data.Resource
import com.component.pharma.data.network.UploadApi
import com.component.pharma.data.network.UploadRequestBody
import com.component.pharma.data.repository.UploadRepository
import com.component.pharma.databinding.FragmentUploadBinding
import com.component.pharma.model.AuditColumns
import com.component.pharma.model.PrescripModel
import com.component.pharma.model.responses.UploadResponse
import com.component.pharma.ui.base.BaseFragment
import com.component.pharma.ui.getFileName
import com.component.pharma.ui.home.thirdpage.ThirdNavFragment
import com.component.pharma.ui.snackbar
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*


class UploadFragment : BaseFragment<UploadViewModel, FragmentUploadBinding, UploadRepository>(), UploadRequestBody.UploadCallback {

    private var selectedImage: Uri? = null
    private lateinit var userId: String



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val options2 = arrayOf("Upload image", "Take photo")
        val askChoices = AlertDialog.Builder(requireContext())
                .setItems(options2){ _, pos ->
                    when (pos) {
                        0 -> {
                            openImageChooser()
                        }
                        1 -> {
                            openCamera()
                        }

                    }
                }
        binding.imageView.setOnClickListener {
            askChoices.show()
        }
        binding.imageText.setOnClickListener {
            askChoices.show()
        }
        binding.btnAddAnother.setOnClickListener {
            askChoices.show()
        }
//        binding.btnCamera.setOnClickListener {
//            openCamera()
//        }
        binding.btnUpload.setOnClickListener {
            binding.btnUpload.visibility = View.GONE
            binding.cpPbar.visibility = View.VISIBLE
            binding.btnAddAnother.visibility = View.GONE
            uploadImage()
        }



        viewModel.prescripResponse.observe(viewLifecycleOwner, Observer { it ->
            binding.cpPbar.visibility = View.GONE
            when(it) {
                is Resource.Success -> {
//                    startNewActivity(HomeActivity::class.java)
                    binding.imageView.visibility = View.GONE
                    binding.imageText.visibility = View.VISIBLE
                    binding.plus.visibility = View.VISIBLE
                    binding.btnUpload.visibility = View.GONE
                    binding.btnAddAnother.visibility = View.GONE
                    binding.successMessage.visibility = View.VISIBLE
//                    Toast.makeText(context, "Uploaded successfully", Toast.LENGTH_SHORT).show()


                }
                is Resource.Failure -> {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()

                }

            }
        })
    }

//    private fun uploadImage() {
//        if (selectedImage == null) {
//            binding.layoutRoot.snackbar("Select an image first")
//            return
//        }
//
//        val parcelFileDescriptor = contentResolver.openFileDescriptor
//
//    }

    private fun openImageChooser() {
        selectedImage = null
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
//            val mimeTypes = arrayOf("image/jpeg", "image/png")
//            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(it, REQUEST_CODE_IMAGE_PICKER)
        }
    }
    private fun openCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {
            startActivityForResult(it, REQUEST_CODE_CAMERA)
        }
    }
    @SuppressLint("NewApi")
    private fun uploadImage() {
        if (selectedImage == null) {
            binding.layoutRoot.snackbar("Select an image first")
            return
        }



        val parcelFileDescriptor: ParcelFileDescriptor  = context?.contentResolver?.openFileDescriptor(selectedImage!!, "r", null)
                ?: return
        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val file = File(requireContext().cacheDir, requireContext().contentResolver.getFileName(selectedImage!!))
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)




        val body = UploadRequestBody(file,"image", UploadFragment(), ThirdNavFragment())
        UploadApi().uploadImage(

                MultipartBody.Part.createFormData("image", file.name, body)
        ).enqueue(object: Callback<UploadResponse> {
            override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                binding.layoutRoot.snackbar("hello ${t.message!!}")
            }

            override fun onResponse(call: Call<UploadResponse>, response: Response<UploadResponse>) {
                sendPrescrip(response.body()?.apiPath.toString(),
                        response.body()?.apiPath.toString(),
                        response.body()?.extention.toString(),
                        response.body()?.fileName.toString(),
                        response.body()?.fullPath.toString(),
                        response.body()?.originalFileName.toString())
//                binding.layoutRoot.snackbar("A"+response.body().toString())
            }
        })



    }

    private fun sendPrescrip(apiImagePath:String,
                             apiPath: String,
                             extension: String,
                             fileName:String,
                             fullPath: String,
                             originalFileName: String) {
        userPreferences.userId.asLiveData().observe(this, androidx.lifecycle.Observer {
            userId = it.toString()
            val currentDate = Calendar.getInstance().time
            val auditColumns = AuditColumns(1100001, 201, 10001, "Win32", 1, "unidentified", "unidentified", "unidentified", "26")
            val prescrip = PrescripModel(0,
            auditColumns,
            0,
            userId.toInt(),
            "",
            0,
            "",
            apiImagePath,
            apiPath,
            extension,
            fileName,
            fullPath,
            originalFileName,
            currentDate)


            viewModel.sendPrescrip(prescrip)
            selectedImage = null
            binding.imageView.setImageURI(null)

        })

    }


//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if(resultCode == Activity.RESULT_OK) {
//            when(requestCode) {
//                REQUEST_CODE_IMAGE_PICKER -> {
//                    if (data != null) {
//                        selectedImage = data.data
//                    }
//                    binding.imageView.setImageURI(selectedImage)
//                }
//            }
//        }
//    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK) {
            when(requestCode) {
                REQUEST_CODE_IMAGE_PICKER -> {
                    selectedImage = data?.data
                    binding.imageView.setImageURI(selectedImage)
//                    binding.cpPbar.visibility = View.GONE
//                    binding.btnUpload.visibility = View.VISIBLE
                    binding.imageView.visibility = View.VISIBLE
                    binding.imageText.visibility = View.GONE
                    binding.plus.visibility = View.GONE
                    binding.btnUpload.visibility = View.VISIBLE
                    binding.btnAddAnother.visibility = View.VISIBLE
                    binding.successMessage.visibility = View.GONE
                }
                REQUEST_CODE_CAMERA -> {
                    var bmp = data?.extras?.get("data") as Bitmap
                    selectedImage = context?.let { getImageUri(it, bmp) }
                    binding.imageView.visibility = View.VISIBLE
                    binding.imageText.visibility = View.GONE
                    binding.plus.visibility = View.GONE
                    binding.btnUpload.visibility = View.VISIBLE
                    binding.btnAddAnother.visibility = View.VISIBLE
                    binding.successMessage.visibility = View.GONE
                    binding.imageView.setImageURI(selectedImage)
                }
            }
        }
    }
    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null)
        return Uri.parse(path)
    }

    companion object {
        private const val REQUEST_CODE_IMAGE_PICKER = 100
        private const val REQUEST_CODE_CAMERA = 110
    }
    override fun getViewModel() = UploadViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentUploadBinding.inflate(
        inflater,
        container,
        false
    )

    override fun getFragmentRepository() = UploadRepository(retrofitInstance.buildApi(UploadApi::class.java), userPreferences)
    override fun onProgressUpdate(percentage: Int) {

    }


}