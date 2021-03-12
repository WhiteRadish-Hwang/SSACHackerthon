package com.example.ssachackerthon.upload

import android.Manifest
import android.app.Activity
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.ssachackerthon.MainActivity
import com.example.ssachackerthon.R
import com.example.ssachackerthon.base.BaseFragment
import com.example.ssachackerthon.databinding.FragmentUploadBinding
import com.example.ssachackerthon.upload.UploadContentFragment.Companion.imageListKey
import com.example.ssachackerthon.upload.adapter.UploadGridRecyclerAdapter
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class UploadFragment:BaseFragment<FragmentUploadBinding>(FragmentUploadBinding::bind, R.layout.fragment_upload), LoaderManager.LoaderCallbacks<Cursor> {
    private val TAG = "TAG"
    private var  pickImageFromAlbum = 0
    private var  fbStorage: FirebaseStorage? = null
    private var  uriPhoto: Uri? = null
    private var  viewProfile: View? = null

    private var imageCount = 0
    private var imageList = ArrayList<String>()

    lateinit var uploadRecyclerAdapter: UploadGridRecyclerAdapter
    lateinit var gridLayoutManager: GridLayoutManager

    private val IMAGE_LOADER_ID = 1
    private val listOfAllImages = ArrayList<String>()

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreateLoader(p0: Int, p1: Bundle?): Loader<Cursor> {

        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
        val selection: String? = null     //Selection criteria
        val selectionArgs = arrayOf<String>()  //Selection criteria
        val sortOrder: String? = null

        return CursorLoader(
            activity!!.applicationContext,
            uri,
            projection,
            selection,
            selectionArgs,
            sortOrder)
    }

    override fun onLoadFinished(loader: Loader<Cursor>, cursor: Cursor?) {
        listOfAllImages.clear()
        cursor?.let {
            val columnIndexData = it.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

            while (it.moveToNext()) {
                listOfAllImages.add(it.getString(columnIndexData));
            }
            Log.d(TAG, "onLoadFinished: $listOfAllImages")
            Log.d(TAG, "onLoadFinished: ${listOfAllImages.size}")
            setRecyclerAdapter()
        }
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loaderManager: LoaderManager = LoaderManager.getInstance(this)
        loaderManager.initLoader(IMAGE_LOADER_ID, null, this)

        viewProfile = view

        fbStorage = FirebaseStorage.getInstance()
        Log.d("TAG", "fbStorage: $fbStorage")

        binding.uploadNextArrow.setOnClickListener{
            (activity as MainActivity).addUploadFragment(UploadContentFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(imageListKey, imageList)
                }
                Log.d(TAG, "arguments: $arguments")
            })
        }

//        val photoPickIntent = Intent(Intent.ACTION_PICK)
//        photoPickIntent.type = "image/*"
//        photoPickIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
//        startActivityForResult(photoPickIntent, pickImageFromAlbum)

    }



    fun setRecyclerAdapter() {
        uploadRecyclerAdapter = UploadGridRecyclerAdapter(context, listOfAllImages)
        binding.uploadRecycler.apply {
            gridLayoutManager = GridLayoutManager(context, 4, GridLayoutManager.VERTICAL, false)
            layoutManager = gridLayoutManager

            // 메인 리사이클러 아이템클릭 리스터
            uploadRecyclerAdapter.let {
                it.setMyUploadItemClickListener(object :
                    UploadGridRecyclerAdapter.MyUploadItemClickListener {
                    override fun onItemClick(position: Int) {
                        Log.d(TAG, "position: $position")
                        Log.d(TAG, "listOfAllImages: $listOfAllImages")
                        Log.d(TAG, "imageCount: $imageCount")
                        if (imageCount > 3) {
                            Toast.makeText(context, R.string.photo_count_limit, Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            if (listOfAllImages[position] !in imageList) {
                                imageCount++
                                imageList.add(listOfAllImages[position])

                                Glide.with(binding.uploadImg).load(listOfAllImages[position])
                                    .into(binding.uploadImg)
                            } else {
                                imageCount--
                                imageList.remove(listOfAllImages[position])
                                if (imageList.isNotEmpty()) Glide.with(binding.uploadImg)
                                    .load(imageList[imageCount - 1]).into(binding.uploadImg)
                                else binding.uploadImg.setImageResource(R.color.white)
                            }
                            Log.d(TAG, "onItemClick: $imageList")

                        }


                    }
                })

            }

            setHasFixedSize(true)
            adapter = uploadRecyclerAdapter
            Log.d(TAG, "setRecyclerAdapter: ")
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.d("TAG", "requestCode: $requestCode, pickImageFromAlbum: $pickImageFromAlbum")
        Log.d("TAG", "resultCode: $resultCode, resultCode: ${Activity.RESULT_OK}")
        if (requestCode == pickImageFromAlbum) {
            if (resultCode == Activity.RESULT_OK) {
                if (data == null) {
                    
                }else {
                    if (data.clipData == null) {
                        Log.d("TAG", "onActivityResult: 다중선택 불가 기기")
                        Toast.makeText(context, "다중선택 불가 기기",Toast.LENGTH_SHORT).show()
                    } else {
                        val clipData: ClipData = data.clipData!!
                        Log.d("TAG", clipData.itemCount.toString())
                        
                        if (clipData.itemCount > 3) {
                            Toast.makeText(context, "사진은 3장까지 선택 가능합니다.", Toast.LENGTH_SHORT).show()
                        } else if (clipData.itemCount == 1) {
                            uriPhoto = data.data
                            if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                                funImageUpload(viewProfile!!)
                            }
                        }
                        
                        
                    }
                }


//                uriPhoto = data?.data
//                binding.imageView6.setImageURI(uriPhoto)
//                Log.d(TAG, "uriPhoto: $uriPhoto")
//                if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//                    funImageUpload(viewProfile!!)
//                }
            }
        }

    }

    private fun funImageUpload(view: View) {
        val timeStamp = SimpleDateFormat("yyyyMMDD_HHmmss").format(Date())
        val imgFileName = "IMAGE_${timeStamp}_.png"
        val storageRef = fbStorage?.reference?.child("images")?.child(imgFileName)



        Log.d(TAG, "imgFileName: $imgFileName  storageRef: $storageRef")

        storageRef?.putFile(uriPhoto!!)?.addOnSuccessListener {
            Log.d(TAG, "funImageUpload: uploadSuccess")
            Toast.makeText(view.context, "Image Uploaded", Toast.LENGTH_SHORT).show()
        }
        storageRef?.putFile(uriPhoto!!)?.addOnFailureListener{
            Log.d(TAG, "funImageUpload: uploadFailure")
            Toast.makeText(view.context, "Image Uploaded Failure", Toast.LENGTH_SHORT).show()
        }

    }



}