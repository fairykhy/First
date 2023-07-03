package cs.kaist.first

import android.app.Activity.RESULT_OK
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class GalleryFragment : Fragment() {

    private val REQUEST_READ_EXTERNAL_STORAGE = 1000

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_READ_EXTERNAL_STORAGE)

        val view = inflater.inflate(R.layout.fragment_gallery, container, false)
        val imageList: ArrayList<String> = ArrayList<String>()
        val index = arguments?.getInt("index")

//        val imageResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
//            result ->
//                if(result.resultCode == RESULT_OK){
//                    val imageUri = result.data?.data
//                    if (imageUri != null) {
//                        imageList.add(imageUri)
//
//                    }
//                }
//        }
//
//        val intent = Intent(Intent.ACTION_PICK)
//        intent.setDataAndType(
//            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//            "image/*"
//        )
//        imageResult.launch(intent)
        val cursor = requireActivity().contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC")
        if(cursor!=null){
            while(cursor.moveToNext()){
                // 사진 경로 Uri 가져오기
                val uri = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                imageList.add(uri)
            }
            cursor.close()
        }

//        println("I'm index:$index")
        if (index!=null){
//            println("Hello")
            imageList.remove(imageList[index])
        }

        val recadapter = GalleryRecyclerAdapter(imageList,requireContext())
        val recyclerView = view.findViewById<RecyclerView>(R.id.gallery_view)

        recyclerView.layoutManager = GridLayoutManager(requireContext(),3)
        recyclerView.adapter = recadapter

        // 이미지를 받아오기
        // 그러면 그 이미지를 list에 넣어야합니다.
        // list를 통째로 adapter에 넣어줍니다.

        ///////// Adapter
        // LIST에 정보가 담겨있잖아요
        // WHILE문처럼 하나씩 돌면서 image를 띄워주는거에요
        // 그레서 그냥 이렇게 하면 됨

        return view
    }

}
