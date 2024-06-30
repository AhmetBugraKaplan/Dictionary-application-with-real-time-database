package com.example.sqlitekullanimitekrarbu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sqlitekullanimitekrarbu.databinding.ActivityDetayBinding

class DetayActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetayBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val kelime = intent.getSerializableExtra("nesne") as kelimeler

        binding.textViewingilizce.text = kelime.ingilizce
        binding.textViewtRkE.text = kelime.turkce


    }
}