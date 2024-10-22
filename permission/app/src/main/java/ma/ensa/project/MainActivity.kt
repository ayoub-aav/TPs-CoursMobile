package ma.ensa.project

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity() : AppCompatActivity() {

    private val PERMISSION_RECORD_AUDIO = Manifest.permission.RECORD_AUDIO
    private val PERMISSION_ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
    private val PERMISSION_CAMERA = Manifest.permission.CAMERA

    private val PERMISSION_REQ_CODE_AUDIO = 100
    private val PERMISSION_REQ_CODE_LOCATION = 101
    private val PERMISSION_REQ_CODE_CAMERA = 102



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById<View>(R.id.main)
        ) { v: View, insets: WindowInsetsCompat ->
            val systemBars: Insets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<View>(R.id.buttonaudio).setOnClickListener {
            requestRuntimePermission(PERMISSION_RECORD_AUDIO, PERMISSION_REQ_CODE_AUDIO, "audio")
        }

        findViewById<View>(R.id.buttonlocation).setOnClickListener {
            requestRuntimePermission(PERMISSION_ACCESS_FINE_LOCATION, PERMISSION_REQ_CODE_LOCATION, "location"
            )
        }

        findViewById<View>(R.id.buttoncamera).setOnClickListener {
            requestRuntimePermission(PERMISSION_CAMERA, PERMISSION_REQ_CODE_CAMERA, "camera"
            )
        }

        updatePermissionStatus()
    }

    private fun updatePermissionStatus() {
        // Check if permissions are granted and update TextView for each one
        val audioResult = findViewById<TextView>(R.id.audioresult)
        val locationResult = findViewById<TextView>(R.id.Locationresult)
        val cameraResult = findViewById<TextView>(R.id.cameraresult)

        if (checkSelfPermission(PERMISSION_RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            audioResult.text = "Permission to Audio is ON"
        } else {
            audioResult.text = "Permission to Audio is OFF"
        }

        if (checkSelfPermission(PERMISSION_ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationResult.text = "Permission to Location is ON"
        } else {
            locationResult.text = "Permission to Location is OFF"
        }

        if (checkSelfPermission(PERMISSION_CAMERA) == PackageManager.PERMISSION_GRANTED) {
            cameraResult.text = "Permission to Camera is ON"
        } else {
            cameraResult.text = "Permission to Camera is OFF"
        }
    }

    private fun requestRuntimePermission(
        permission: String,
        requestCode: Int,
        featureName: String
    ) {
        if (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "$featureName permission is granted", Toast.LENGTH_LONG).show()
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("This app requires $featureName permission for this feature")
                .setTitle("Permission Request")
                .setCancelable(false)
                .setPositiveButton("OK") { dialog: DialogInterface, which: Int ->
                    ActivityCompat.requestPermissions(
                        this@MainActivity,
                        arrayOf(permission),
                        requestCode
                    )
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel") { dialog: DialogInterface, which: Int -> dialog.dismiss() }
            builder.show()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            when (requestCode) {
                PERMISSION_REQ_CODE_AUDIO -> Toast.makeText(
                    this, "Audio Permission Granted", Toast.LENGTH_SHORT
                ).show()

                PERMISSION_REQ_CODE_LOCATION -> Toast.makeText(this, "Location Permission Granted", Toast.LENGTH_SHORT
                ).show()

                PERMISSION_REQ_CODE_CAMERA -> Toast.makeText(this, "Camera Permission Granted", Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            val featureName = if (requestCode == PERMISSION_REQ_CODE_AUDIO) "Audio"
            else if (requestCode == PERMISSION_REQ_CODE_LOCATION) "Location"
            else "Camera"
            showPermissionDeniedDialog(featureName)
        }
        updatePermissionStatus()
    }

    private fun showPermissionDeniedDialog(featureName: String) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(
            "This feature is unavailable because " + featureName + " permission has been denied. " +
                    "Please allow this permission from settings."
        )
            .setTitle("Permission Required")
            .setCancelable(false)
            .setPositiveButton("Settings") { dialog: DialogInterface, which: Int ->
                val intent: Intent =
                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri: Uri = Uri.fromParts("package", getPackageName(), null)
                intent.setData(uri)
                startActivity(intent)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel"
            ) { dialog: DialogInterface, which: Int -> dialog.dismiss() }
        builder.show()
    }
}