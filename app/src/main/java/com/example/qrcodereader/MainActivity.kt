package com.example.qrcodereader

import android.Manifest.permission.CAMERA
import android.Manifest.permission.VIBRATE
import android.R.attr.label
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.qrcodereader.databinding.ActivityMainBinding
import eu.livotov.labs.android.camview.ScannerLiveView
import eu.livotov.labs.android.camview.ScannerLiveView.ScannerViewEventListener
import eu.livotov.labs.android.camview.scanner.decoder.zxing.ZXDecoder


class MainActivity : AppCompatActivity()
{
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.statusbarcolor)

        if (checkPermission())
        {
            // if permission is already granted display a toast message
            Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
        }
        else
        {
            requestPermission();
        }

        var camera = binding.camview


        var intent=Intent(this, ScannedTextActivity::class.java)
        camera.scannerViewEventListener = object : ScannerViewEventListener
        {
            override fun onScannerStarted(scanner: ScannerLiveView)
            {
                // method is called when scanner is started
                /*Toast.makeText(this@MainActivity, "Scanner Started", Toast.LENGTH_SHORT).show()*/
            }

            override fun onScannerStopped(scanner: ScannerLiveView)
            {
                // method is called when scanner is stopped.
                /*Toast.makeText(this@MainActivity, "Scanner Stopped", Toast.LENGTH_SHORT).show()*/
            }

            override fun onScannerError(err: Throwable)
            {
                // method is called when scanner gives some error.
                Toast.makeText(this@MainActivity, "Scanner Error: " + err.message, Toast.LENGTH_SHORT).show()
            }

            override fun onCodeScanned(data: String)
            {
                intent.putExtra("data", data.toString())
                startActivity(intent)
            }

        }

    }

    override fun onResume()
    {
        super.onResume()
        val decoder = ZXDecoder()
        // 0.5 is the area where we have
        // to place red marker for scanning.
        decoder.scanAreaPercent = 0.8
        // below method will set secoder to camera.
        binding.camview.setDecoder(decoder)
        binding.camview.startScanner()
    }

    override fun onPause()
    {
        // on app pause the
        // camera will stop scanning.
        binding.camview.stopScanner()
        super.onPause()
    }

    private fun checkPermission(): Boolean
    {
        // here we are checking two permission that is vibrate
        // and camera which is granted by user and not.
        // if permission is granted then we are returning
        // true otherwise false.
        val camera_permission = ContextCompat.checkSelfPermission(applicationContext, CAMERA)
        val vibrate_permission = ContextCompat.checkSelfPermission(applicationContext, VIBRATE)
        return camera_permission == PackageManager.PERMISSION_GRANTED && vibrate_permission == PackageManager.PERMISSION_GRANTED
    }


    private fun requestPermission()
    {
        // this method is to request
        // the runtime permission.
        val PERMISSION_REQUEST_CODE = 200
        ActivityCompat.requestPermissions(this, arrayOf(CAMERA, VIBRATE), PERMISSION_REQUEST_CODE)
    }


}