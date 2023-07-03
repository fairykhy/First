package cs.kaist.first

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.googlecode.tesseract.android.TessBaseAPI
import org.w3c.dom.Text
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream


class ReceiptFragment : Fragment() {
    //사용되는 이미지
    var image : Bitmap? = null
    // Tess API reference
    var mTess : TessBaseAPI? = null
    //언어 데이터가 있는 경로
    var datapath = ""
    var OCRTextView : TextView? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val receiptView = inflater.inflate(R.layout.fragment_receipt, container, false)
        val runOCRTextView = receiptView.findViewById<TextView>(R.id.runOCRTextView)
        OCRTextView = receiptView.findViewById(R.id.OCRTextView)

        image = BitmapFactory.decodeResource(resources, R.drawable.sample2)
        val receiptImageView = receiptView.findViewById<ImageView>(R.id.imageView)
        receiptImageView.setImageBitmap(image)

        // 언어파일 경로
        datapath = requireContext().getFilesDir().toString() + "/tesseract/"

        Log.d("datapath",datapath)
        checkFile(File(datapath + "tessdata/"))

        //Tesseract API 언어 세팅
        val lang = "kor"

        mTess = TessBaseAPI()
        mTess!!.init(datapath,lang)
        runOCRTextView.setOnClickListener {
            processImage(receiptView)
        }

        return receiptView
    }
    fun processImage(view: View?) {
        var OCRresult: String? = null
        mTess!!.setImage(image)
        OCRresult = mTess!!.utF8Text
        OCRTextView!!.text = OCRresult
    }

    private val langFileName = "kor.traineddata"
    private fun copyFiles() {
        try {
            val filepath = datapath + "tessdata/" + langFileName
            val assetManager = requireActivity().assets
            val instream: InputStream = assetManager.open(langFileName)
            val outstream: OutputStream = FileOutputStream(filepath)
            val buffer = ByteArray(1024)
            var read: Int
            while (instream.read(buffer).also { read = it } != -1) {
                outstream.write(buffer, 0, read)
            }
            outstream.flush()
            outstream.close()
            instream.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun checkFile(dir: File) {
        //디렉토리가 없으면 디렉토리를 만들고 그후에 파일을 카피
        if (!dir.exists() && dir.mkdirs()) {
            copyFiles()
        }
        //디렉토리가 있지만 파일이 없으면 파일카피 진행
        if (dir.exists()) {
            val datafilepath = datapath + "tessdata/" + langFileName
            val datafile = File(datafilepath)
            if (!datafile.exists()) {
                copyFiles()
            }
        }
    }


}