package com.example.qrcodereader

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.qrcodereader.databinding.ActivityScannedTextBinding

class ScannedTextActivity : AppCompatActivity()
{
    lateinit var binding:ActivityScannedTextBinding
    lateinit var data:String
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanned_text)
        binding=DataBindingUtil.setContentView(this, R.layout.activity_scanned_text)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.statusbarcolor)

        data=""
        data = intent.getStringExtra("data").toString()
        binding.idTVscanned.text=data.toString()


        binding.ivCopy.setOnClickListener {
            var label="text"
            val clipboard: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText(label, data)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, "link copied Successfully", Toast.LENGTH_SHORT).show()
        }
    }
}