package com.example.slotmachine

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import com.example.slotmachine.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val request_code = 5
    private val textEdit by lazy {binding.editNum}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener{
            val i = Intent(this, GameActivity::class.java)
            val myString = textEdit.text.toString()
            i.putExtra("qString", myString)
            startActivityForResult(i, request_code)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if ((requestCode == request_code) &&
            (resultCode == RESULT_OK)) {
            val betCoins = data?.getIntExtra("betCoins", 0) ?: 0
            textEdit.setText(betCoins.toString())
            Snackbar.make(findViewById(android.R.id.content), "You have ${betCoins} coins", Snackbar.LENGTH_SHORT).show()
        }
    }
}