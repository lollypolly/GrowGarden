package ru.itis.polypollya.growgarden.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.json.GoogleJsonResponseException
import com.google.api.client.http.HttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.vision.v1.Vision
import com.google.api.services.vision.v1.VisionRequest
import com.google.api.services.vision.v1.VisionRequestInitializer
import com.google.api.services.vision.v1.model.*
import com.google.common.io.BaseEncoding
import com.google.common.reflect.Reflection.getPackageName
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_plant.*
import ru.itis.polypollya.growgarden.MainActivity
import ru.itis.polypollya.growgarden.R
import ru.itis.polypollya.growgarden.models.Flower
import ru.itis.polypollya.growgarden.other.NotificationReceiver
import ru.itis.polypollya.growgarden.viewmodel.FlowerViewModel
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@AndroidEntryPoint
open class AddPlantFragment : Fragment() {

    private val flowersViewModel: FlowerViewModel by viewModels()
    private var selectedImgPath = ""
    private var type = ""
    var filterWords = listOf(
        "flower",
        "plant",
        "yellow",
        "read",
        "white",
        "flora",
        "land plant",
        "flowering plant",
        "daisy family",
        "petal",
        "field",
        "plant stem",
        "macro photography",
        "purple",
        "pink",
        "photography",
        "close up",
        "blossom",
        "black and white",
        "green",
        "annual plant",
        "botany",
        "floristry",
        "biology",
        "leaf",
        "branch",
        "tree",
        "shrub",
        "nature",
        "wildflower"
    )

    @Inject
    lateinit var mainPrefs: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_add_plant, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        add_img_btn.setOnClickListener {
            if (context?.applicationContext?.let { it1 ->
                    ContextCompat.checkSelfPermission(
                        it1,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                }
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    222
                )
                selectImg()
            } else selectImg()
        }
        option1.setOnClickListener {
            if (name_flower.text.isEmpty() || about_multy_tv.text.isEmpty()) {
                Toast.makeText(context, "Пож-ста, заполните все поля!", Toast.LENGTH_SHORT).show()
            } else {
                option1.setBackgroundResource(R.drawable.ripple)
                if (mainPrefs.getBoolean("is_push_enabled", false)) {
                    val calendar = Calendar.getInstance()
                    calendar.set(Calendar.HOUR_OF_DAY, 9)
                    calendar.set(Calendar.MINUTE, 0)
                    calendar.set(Calendar.SECOND, 0)
                    val intent = Intent(context, NotificationReceiver::class.java)
                    intent.putExtra("flowerName", name_flower.text)
                    intent.putExtra("flowerDesc", about_multy_tv.text)
                    intent.putExtra("flowerId", Random().nextInt(100))
                    val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
                    val alarmManager =
                        context?.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
                    val hoursToMillis = TimeUnit.HOURS.toMillis(3600000)
                    alarmManager.setRepeating(
                        AlarmManager.RTC,
                        calendar.timeInMillis,
                        hoursToMillis,
                        pendingIntent
                    )
                }
            }
        }
        option_2.setOnClickListener {
            if (name_flower.text.isEmpty() || about_multy_tv.text.isEmpty()) {
                Toast.makeText(context, "Пож-ста, заполните все поля!", Toast.LENGTH_SHORT).show()
            } else {
                option_2.setBackgroundResource(R.drawable.ripple)
                if (mainPrefs.getBoolean("is_push_enabled", false)) {
                    val calendar = Calendar.getInstance()
                    calendar.set(Calendar.HOUR_OF_DAY, 9)
                    calendar.set(Calendar.MINUTE, 0)
                    calendar.set(Calendar.SECOND, 0)
                    val intent = Intent(context, NotificationReceiver::class.java)
                    intent.putExtra("flowerName", name_flower.text)
                    intent.putExtra("flowerDesc", about_multy_tv.text)
                    intent.putExtra("flowerId", Random().nextInt(100))
                    val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
                    val alarmManager =
                        context?.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
                    val hoursToMillis = TimeUnit.HOURS.toMillis(7200000)
                    alarmManager.setRepeating(
                        AlarmManager.RTC,
                        calendar.timeInMillis,
                        hoursToMillis,
                        pendingIntent
                    )
                }
            }
        }
        option_3.setOnClickListener {
            if (name_flower.text.isEmpty() || about_multy_tv.text.isEmpty()) {
                Toast.makeText(context, "Пож-ста, заполните все поля!", Toast.LENGTH_SHORT).show()
            } else {
                option_3.setBackgroundResource(R.drawable.ripple)
                if (mainPrefs.getBoolean("is_push_enabled", false)) {
                    val calendar = Calendar.getInstance()
                    calendar.set(Calendar.HOUR_OF_DAY, 9)
                    calendar.set(Calendar.MINUTE, 0)
                    calendar.set(Calendar.SECOND, 0)
                    val intent = Intent(context, NotificationReceiver::class.java)
                    intent.putExtra("flowerName", name_flower.text)
                    intent.putExtra("flowerDesc", about_multy_tv.text)
                    intent.putExtra("flowerId", Random().nextInt(100))
                    val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
                    val alarmManager =
                        context?.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
                    val hoursToMillis = TimeUnit.HOURS.toMillis(10800000)
                    alarmManager.setRepeating(
                        AlarmManager.RTC,
                        calendar.timeInMillis,
                        hoursToMillis,
                        pendingIntent
                    )
                }
            }
        }
        add_flower_btn.setOnClickListener {
            if (name_flower.text.isNotEmpty()) {
                if (about_multy_tv.text.isNotEmpty()) {
                    type = when {
                        indoor_btn.isChecked -> "indoor"
                        outdoor_btn.isChecked -> "outdoor"
                        else -> "indoor"
                    }
                    val flower = Flower(
                        0, name_flower.text.toString(), about_multy_tv.text.toString(),
                        selectedImgPath, type
                    )
                    flowersViewModel.insertFlower(flower)
                    Toast.makeText(context, "Цветок добавлен!", Toast.LENGTH_SHORT).show()
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.main_fragment_container, MyPlantsFragment())
                        ?.commit()
                }
            } else Toast.makeText(context, "Введите имя цветочка!", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun selectImg() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        if (activity?.packageManager?.let { intent.resolveActivity(it) } != null) {
            startActivityForResult(intent, 129)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 129) {
            val resultUri: Uri? = data?.data
            var `is`: InputStream? = null
            try {
                `is` = resultUri?.let { activity?.contentResolver?.openInputStream(it) }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
            val bitmap = BitmapFactory.decodeStream(`is`)
            flower_img.setImageBitmap(bitmap)
            selectedImgPath = resultUri?.let { getPathFromUri(it) }!!
            callCloudVision(bitmap)
        }
    }

    private fun convertResponseToString(response: BatchAnnotateImagesResponse): String? {
        var counter = 0
        var message: String? = "It should be:\n\n"
        val labels: List<EntityAnnotation> = response.responses[0].labelAnnotations
        if (labels != null) {
            for (label in labels) {
                val parsedWord: String = label.description
                val bPass = filterWords.contains(parsedWord)
                if (bPass) continue
                if (counter == 5) break
                counter++
                message += java.lang.String.format(
                    Locale.US,
                    "%f%%: %s",
                    label.score * 100,
                    parsedWord
                )
                message += "\n"
            }
        } else {
            message += "nothing"
        }
        if (counter == 0) {
            if (labels != null) {
                for (label in labels) {
                    message += java.lang.String.format(
                        Locale.US,
                        "%f%%: %s",
                        label.getScore() * 100,
                        label.getDescription()
                    )
                    message += "\n"
                }
            } else {
                message += "nothing"
            }
        }
        return message
    }

    @SuppressLint("StaticFieldLeak")
    @Throws(IOException::class)
    private fun callCloudVision(bitmap: Bitmap) {
        object : AsyncTask<Any?, Void?, String?>() {
            @SuppressLint("StaticFieldLeak")
            override fun doInBackground(vararg params: Any?): String? {
                try {
                    val httpTransport: HttpTransport = AndroidHttp.newCompatibleTransport()
                    val jsonFactory: GsonFactory? = GsonFactory.getDefaultInstance()
                    val CLOUD_VISION_API_KEY = "AIzaSyDcaRDj3BZQFtpqtkBWcjCXVyIg9nCjzIg"
                    val requestInitializer: VisionRequestInitializer =
                        object : VisionRequestInitializer(CLOUD_VISION_API_KEY) {
                            @SuppressLint("StaticFieldLeak")
                            @Throws(IOException::class)
                            override fun initializeVisionRequest(visionRequest: VisionRequest<*>) {
                                super.initializeVisionRequest(visionRequest)
                                val packageName: String =
                                    getPackageName(MainActivity::class.java)
                                visionRequest.requestHeaders
                                    .set("X-Android-Package", packageName)
                                val sig: String? = activity?.packageManager?.let {
                                    getSignature(
                                        it,
                                        packageName
                                    )
                                }
                                visionRequest.requestHeaders.set("X-Android-Cert", sig)
                            }
                        }
                    val builder: Vision.Builder =
                        Vision.Builder(httpTransport, jsonFactory, null)
                    builder.setVisionRequestInitializer(requestInitializer)
                    val vision: Vision = builder.build()
                    val batchAnnotateImagesRequest = BatchAnnotateImagesRequest()
                    batchAnnotateImagesRequest.requests = object :
                        ArrayList<AnnotateImageRequest?>() {
                        init {
                            val annotateImageRequest = AnnotateImageRequest()
                            val base64EncodedImage = Image()
                            val byteArrayOutputStream = ByteArrayOutputStream()
                            bitmap.compress(
                                Bitmap.CompressFormat.JPEG,
                                90,
                                byteArrayOutputStream
                            )
                            val imageBytes: ByteArray = byteArrayOutputStream.toByteArray()
                            base64EncodedImage.encodeContent(imageBytes)
                            annotateImageRequest.image = base64EncodedImage
                            annotateImageRequest.features = object : ArrayList<Feature?>() {
                                init {
                                    val labelDetection = Feature()
                                    labelDetection.type = "LABEL_DETECTION"
                                    labelDetection.maxResults = 15
                                    add(labelDetection)
                                }
                            }
                            add(annotateImageRequest)
                        }
                    }
                    val annotateRequest: Vision.Images.Annotate =
                        vision.images().annotate(batchAnnotateImagesRequest)
                    annotateRequest.disableGZipContent = true
                    Log.d("TAGCHECK", "created Cloud Vision request object, sending request")
                    val response: BatchAnnotateImagesResponse = annotateRequest.execute()
                    return convertResponseToString(response)
                } catch (e: GoogleJsonResponseException) {
                    Log.d("TAGCHECK", "failed to make API request because " + e.content)
                } catch (e: IOException) {
                    Log.d(
                        "TAGCHECK", "failed to make API request because of other IOException " +
                                e.message
                    )
                }
                return "Cloud Vision API request failed. Check logs for details."
            }

            override fun onPostExecute(result: String?) {
                about_multy_tv.setText(result)
            }
        }.execute()
    }

    open fun getSignature(pm: PackageManager, packageName: String): String? {
        return try {
            val packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            if (packageInfo?.signatures == null || packageInfo.signatures.isEmpty() || packageInfo.signatures[0] == null
            ) {
                null
            } else signatureDigest(packageInfo.signatures[0])
        } catch (e: PackageManager.NameNotFoundException) {
            null
        }
    }

    private fun signatureDigest(sig: android.content.pm.Signature): String? {
        val signature: ByteArray = sig.toByteArray()
        return try {
            val md: MessageDigest = MessageDigest.getInstance("SHA1")
            val digest: ByteArray = md.digest(signature)
            BaseEncoding.base16().lowerCase().encode(digest)
        } catch (e: NoSuchAlgorithmException) {
            null
        }
    }

    private fun getPathFromUri(uri: Uri): String? {
        val pathToFile: String?
        val cursor: Cursor? = activity?.contentResolver?.query(uri, null, null, null, null)
        if (cursor == null) pathToFile = uri.path
        else {
            cursor.moveToFirst()
            val index = cursor.getColumnIndex("_data")
            pathToFile = cursor.getString(index)
            cursor.close()
        }
        return pathToFile
    }
}