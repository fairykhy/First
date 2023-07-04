package cs.kaist.first

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.vision.v1.AnnotateImageRequest
import com.google.cloud.vision.v1.AnnotateImageResponse
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse
import com.google.cloud.vision.v1.Feature
import com.google.cloud.vision.v1.Image
import com.google.cloud.vision.v1.ImageAnnotatorClient
import com.google.cloud.vision.v1.ImageAnnotatorSettings
import com.google.protobuf.ByteString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream

object DetectText {
    @Throws(IOException::class)
    fun detectText(context: Context, uri: Uri) {
        // TODO(developer): Replace these variables before running the sample.
        val directory = Environment.getExternalStorageDirectory().absolutePath
        val scope = CoroutineScope(Dispatchers.Main)
        // 코루틴을 실행합니다.
        scope.launch {
            detectTextAsync(uri, context)
        }
    }

    // Detects text in the specified image.
    @Throws(IOException::class)
    suspend fun detectText(uri: Uri, context: Context) {
        val credentials: GoogleCredentials = GoogleCredentials.fromStream(
            context.resources.openRawResource(R.raw.madcamp_first)
        )
        val scopedCredentials = credentials.createScoped("https://www.googleapis.com/auth/cloud-platform")

        val requests: MutableList<AnnotateImageRequest> = ArrayList<AnnotateImageRequest>()
        val inputStream: InputStream = context.contentResolver.openInputStream(uri)!!
        val imgBytes: ByteString = ByteString.readFrom(inputStream)
        val img: Image = Image.newBuilder().setContent(imgBytes).build()
        val feat: Feature = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build()
        val request: AnnotateImageRequest =
            AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build()
        requests.add(request)

        ImageAnnotatorClient.create(ImageAnnotatorSettings.newBuilder().setCredentialsProvider {
            scopedCredentials
        }.build()).use { client ->
            val response: BatchAnnotateImagesResponse = client.batchAnnotateImages(requests)
            val responses: List<AnnotateImageResponse> = response.getResponsesList()
            for (res in responses) {
                if (res.hasError()) {
                    System.out.format("Error: %s%n", res.getError().getMessage())
                    return
                }

                // For full list of available annotations, see http://g.co/cloud/vision/docs
                for (annotation in res.getTextAnnotationsList()) {
                    System.out.format("Text: %s%n", annotation.getDescription())
                    System.out.format("Position : %s%n", annotation.getBoundingPoly())
                }
            }
        }
    }

    fun detectTextAsync(uri: Uri, context: Context) {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            try {
                detectText(uri, context)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}