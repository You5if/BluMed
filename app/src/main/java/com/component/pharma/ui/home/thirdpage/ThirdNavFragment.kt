package com.component.pharma.ui.home.thirdpage

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.*
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.component.pharma.R
import com.component.pharma.data.Resource
import com.component.pharma.data.network.*
import com.component.pharma.data.repository.AuthRepository
import com.component.pharma.data.repository.HomeRepository
import com.component.pharma.data.repository.UploadRepository
import com.component.pharma.databinding.FragmentThirdNavBinding
import com.component.pharma.model.AuditColumns
import com.component.pharma.model.responses.UploadResponse
import com.component.pharma.model.responses.UserProfileModel
import com.component.pharma.ui.auth.AuthViewModel
import com.component.pharma.ui.base.BaseFragment
import com.component.pharma.ui.getFileName
import com.component.pharma.ui.handleApiError
import com.component.pharma.ui.home.HomeActivity
import com.component.pharma.ui.home.HomeViewModel
import com.component.pharma.ui.snackbar
import com.component.pharma.ui.upload.UploadFragment
import com.component.pharma.ui.upload.UploadViewModel
import com.google.android.material.badge.BadgeDrawable
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class ThirdNavFragment : BaseFragment<HomeViewModel, FragmentThirdNavBinding, HomeRepository>(),UploadRequestBody.UploadCallback2 {

    val CHANNEL_ID = "channelID"
    val NOTIFICATION_ID = 0
    private var selectedImage: Uri? = null
    private lateinit var userId: String
    private lateinit var options: List<String>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        getProfile()

//        binding.btnCamera.setOnClickListener {
//            openCamera()
//        }


        userPreferences.badgeStart.asLiveData().observeForever { badge ->
            if (badge == "New") {
                binding.notificationBadge.visibility = View.VISIBLE
            }else if (badge == "Old") {
                binding.notificationBadge.visibility = View.GONE
            }else {
                binding.notificationBadge.visibility = View.GONE
            }
        }


        userPreferences.pic.asLiveData().observe(viewLifecycleOwner, Observer { pic ->
            if (pic == "") {
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

//             1   binding.etInitial.setOnClickListener {
//                    askChoices.show()
//                }
//    1            binding.etProUser.setOnClickListener {
//                    askChoices.show()
//                }
            }else {
                val options2 = arrayOf("Upload image", "Take photo", "Remove image")
                val askChoices = AlertDialog.Builder(requireContext())
                        .setItems(options2){ _, pos ->
                            when (pos) {
                                0 -> {
                                    openImageChooser()
                                }
                                1 -> {
                                    openCamera()
                                }
                                2 -> {
                                    lifecycleScope.launch {
                                        viewModel.savePic("")
                                    }
                                    saveUserProfile("",
                                            "",
                                            "",
                                            "",
                                            "",
                                            "")
//                                1    binding.etProUser.visibility = View.GONE
//                                    binding.etProUser2.visibility = View.GONE
                                    binding.etInitial.visibility = View.VISIBLE
//                            getProfile()
                                }

                            }
                        }

//               1 binding.etInitial.setOnClickListener {
//                    askChoices.show()
//                }
//               1 binding.etProUser.setOnClickListener {
//                    askChoices.show()
//                }
            }
        })




        val intent = Intent(requireContext(), HomeActivity::class.java)
        val pendingIntent = TaskStackBuilder.create(requireContext()).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val notification = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
                .setContentTitle("BlueMed")
                .setContentText("Finally testing the notification")
                .setSmallIcon(R.drawable.ic_stat_name)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .build()

        val notificaManager  =NotificationManagerCompat.from(requireContext())

//        val mainHandler = Handler(Looper.getMainLooper())
//
//        val post = mainHandler.post(object : Runnable {
//            override fun run() {
//                notificaManager.notify(NOTIFICATION_ID, notification)
//                mainHandler.postDelayed(this, 5000)
//            }
//        })



        binding.notification.setOnClickListener {
//            notificaManager.notify(NOTIFICATION_ID, notification)
            findNavController().navigate(R.id.notificationFragment)
        }
        binding.orders.setOnClickListener {
//            notificaManager.notify(NOTIFICATION_ID, notification)
            findNavController().navigate(R.id.ordersFragment)
        }

        userPreferences.firstName.asLiveData().observe(viewLifecycleOwner, Observer { firstName ->
            userPreferences.lastName.asLiveData().observe(viewLifecycleOwner, Observer { lastName ->

                    if (firstName == "") {
                        binding.userName.text = "Hello there"
                    } else {
                        if (lastName.isNullOrEmpty()) {
                            binding.userName.text = firstName
                        }else{
                            binding.userName.text = "$firstName $lastName"
                        }
                    }
            })

        })
        binding.logoutBtn.setOnClickListener {
            logout()
        }

        binding.nameEditBtn.setOnClickListener {
            findNavController().navigate(R.id.editProfileFragment)
        }

        lifecycleScope.launch {
            userPreferences.profileType.asLiveData().observe(viewLifecycleOwner, Observer { profileType ->
                userPreferences.pic.asLiveData().observe(viewLifecycleOwner, Observer { pic ->
//                    Toast.makeText(requireContext(), "${pic}", Toast.LENGTH_SHORT).show()
                    userPreferences.initial.asLiveData().observe(viewLifecycleOwner, Observer { initial ->
                        binding.etInitial.text = initial.toString()
                        binding.etInitial.visibility = View.VISIBLE
//                        if (profileType == "word") {
//                            binding.etInitial.text = initial.toString()
//                            binding.etInitial.visibility = View.VISIBLE
////                         1   binding.etProUser.visibility = View.GONE
////                            binding.etProUser2.visibility = View.GONE
//
//                        } else if (profileType == "pic") {
////                       1     Glide.with(binding.root).load("http://pharmacyapi.autopay-mcs.com/"+ pic).into(binding.etProUser)
////                            binding.etProUser.visibility = View.VISIBLE
////                            binding.etProUser2.visibility = View.GONE
//                            binding.etInitial.text = initial.toString()
//                            binding.etInitial.visibility = View.VISIBLE
////                            binding.etProUser.setImageURI(pic)
//                        } else {
////                         1   binding.etProUser2.visibility = View.VISIBLE
////                            binding.etProUser.visibility = View.GONE
//                            binding.etInitial.text = initial.toString()
//                            binding.etInitial.visibility = View.VISIBLE
//                        }
                    })
                })
            })

        }

        viewModel.ProfileResponse.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        viewModel.saveProfileType(response.value.profileType)
                        if (response.value.profileType == "word") {
                            viewModel.saveInitial(response.value.profileSource)
//                         1   binding.etProUser.visibility = View.GONE
//                            binding.etProUser2.visibility = View.GONE
                            binding.etInitial.visibility = View.VISIBLE
                        } else if (response.value.profileType == "pic") {
                            viewModel.savePic(response.value.profileSource)
//                          1  lifecycleScope.launch {
//                                userPreferences.pic.asLiveData().observe(viewLifecycleOwner, Observer { pic ->
//                                    Glide.with(binding.root).load("http://pharmacyapi.autopay-mcs.com/" + pic).into(binding.etProUser)
//                                })
//                            }
//                            binding.etProUser.visibility = View.VISIBLE
//                            binding.etProUser2.visibility = View.GONE
//                            binding.etInitial.visibility = View.GONE
                        }
                    }
                }
                is Resource.Failure -> handleApiError(response){getProfile()}
            }
        })

        viewModel.userProfileResponse.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Resource.Success -> {
//                 1   lifecycleScope.launch {
//                        userPreferences.pic.asLiveData().observe(viewLifecycleOwner, Observer { pic ->
//                            Glide.with(binding.root).load("http://pharmacyapi.autopay-mcs.com/" + pic).into(binding.etProUser)
//                        })
//                    }
                }
                is Resource.Failure -> {
                    Toast.makeText(requireContext(), "${response}", Toast.LENGTH_SHORT).show()
                }
            }
        })

    }


    fun getInitials(fullName: String): String? {
        val idxLastWhitespace = fullName.lastIndexOf(' ')
        return fullName.substring(0, 1)
    }

    fun getProfile() {
        userPreferences.userId.asLiveData().observe(requireActivity(), Observer {
            val id = it.toString()
            viewModel.getProfile(id)
        })
    }

    private fun openImageChooser() {
        selectedImage = null
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
//            val mimeTypes = arrayOf("image/jpeg", "image/png")
//            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(it, ThirdNavFragment.REQUEST_CODE_IMAGE_PICKER)
        }
    }
    private fun openCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {
            startActivityForResult(it, ThirdNavFragment.REQUEST_CODE_CAMERA)
        }
    }
    @SuppressLint("NewApi")
    private fun uploadImage() {
        if (selectedImage == null) {
            binding.layoutRoot.snackbar("Select an image first")
            return
        }



        val parcelFileDescriptor: ParcelFileDescriptor = context?.contentResolver?.openFileDescriptor(selectedImage!!, "r", null)
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


                    saveUserProfile(response.body()?.apiPath.toString(),
                            response.body()?.apiPath.toString(),
                            response.body()?.extention.toString(),
                            response.body()?.fileName.toString(),
                            response.body()?.fullPath.toString(),
                            response.body()?.originalFileName.toString())



//                binding.layoutRoot.snackbar("A"+response.body().toString())
//                binding.etProUser2.visibility = View.GONE

            }
        })



    }


    private fun saveUserProfile(apiImagePath:String,
                                apiPath: String,
                                extension: String,
                                fileName:String,
                                fullPath: String,
                                originalFileName: String) {
        var name1: String? = ""
        var name2: String? = ""
        lifecycleScope.launch {
            userPreferences.userProfileId.asLiveData().observe(viewLifecycleOwner, Observer { phUserProfileId ->
                userPreferences.firstName.asLiveData().observe(viewLifecycleOwner, Observer { firstName ->
                    userPreferences.lastName.asLiveData().observe(viewLifecycleOwner, Observer { lastName ->
                        userPreferences.userId.asLiveData().observe(viewLifecycleOwner, Observer { userId ->

                            name1 = firstName
                            name2 = lastName
                                val user = UserProfileModel(
                                        AuditColumns(1100001, 201, 10001, "Win32", 1, "unidentified", "unidentified", "unidentified", "26"),
                                        phUserProfileId!!,
                                        name1,
                                        name2,
                                        apiImagePath,
                                        apiPath,
                                        extension,
                                        fileName,
                                        fullPath,
                                        originalFileName,
                                        userId?.toInt()!!,
                                        "",
                                        false,
                                        true
                                )
                                viewModel.saveUserProfile(user)


                        })

                    })
                })
            })
            viewModel.savePic(apiPath)
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK) {
            when(requestCode) {
                REQUEST_CODE_IMAGE_PICKER -> {
                    selectedImage = data?.data


//                    binding.etProUser.setImageURI(selectedImage)
//                    binding.promImage.visibility = View.GONE
                    uploadImage()
//                  1  binding.etProUser.visibility = View.VISIBLE
//                    lifecycleScope.launch {
//                        userPreferences.pic.asLiveData().observe(viewLifecycleOwner, Observer { pic ->
//                            Glide.with(binding.root).load("http://pharmacyapi.autopay-mcs.com/" + pic).into(binding.etProUser)
//                        })
//                    }
//                    binding.etProUser2.visibility = View.GONE
//                    binding.etInitial.visibility = View.GONE

                }
                REQUEST_CODE_CAMERA -> {

                    var bmp = data?.extras?.get("data") as Bitmap
                    selectedImage = context?.let { getImageUri(it, bmp) }

//                    binding.etProUser.setImageURI(selectedImage)
//                    binding.promImage.visibility = View.GONE
                    uploadImage()
//                   1 binding.etProUser.visibility = View.VISIBLE
//                    lifecycleScope.launch {
//                        userPreferences.pic.asLiveData().observe(viewLifecycleOwner, Observer { pic ->
//                            Glide.with(binding.root).load("http://pharmacyapi.autopay-mcs.com/" + pic).into(binding.etProUser)
//                        })
//                    }
//                    binding.etProUser2.visibility = View.GONE
                    binding.etInitial.visibility = View.GONE

                }
            }
        }
    }
    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null)
        return Uri.parse(path)
    }

    companion object {
        private const val REQUEST_CODE_IMAGE_PICKER = 100
        private const val REQUEST_CODE_CAMERA = 110
    }

    override fun getViewModel() = HomeViewModel::class.java

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentThirdNavBinding.inflate(
            inflater,
            container,
            false
    )

    override fun getFragmentRepository() = HomeRepository(retrofitInstance.buildApi(HomeApi::class.java), userPreferences, db, db2)


    override fun onProgressUpdate(percentage: Int) {

    }

}