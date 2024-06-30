package com.example.sqlitekullanimitekrarbu

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sqlitekullanimitekrarbu.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

/* Lazım olanlar DetayActivity , Kelimeler , KelimelerAdapter , MainActivity */


class MainActivity : AppCompatActivity() , SearchView.OnQueryTextListener {

    private lateinit var kelimelerListe:ArrayList<kelimeler>
    private lateinit var adapter:kelimelerAdapter
        private lateinit var binding : ActivityMainBinding

    private lateinit var refKelimeler:DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)



        binding.toolbar.title = "Sözlük Uygulaması"
        setSupportActionBar(binding.toolbar)

        binding.rv.setHasFixedSize(true)
        binding.rv.layoutManager = LinearLayoutManager(this)


        val db = FirebaseDatabase.getInstance()

        refKelimeler = db.getReference("kelimeler")


        kelimelerListe = ArrayList()

        adapter = kelimelerAdapter(this,kelimelerListe)

        binding.rv.adapter = adapter

        tumKelimeler()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu,menu)


        val item = menu?.findItem(R.id.action_ara)
        val searchView = item?.actionView as SearchView
        searchView.setOnQueryTextListener(this)


        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        aramaYap(query)
        Log.e("Gönderilen Arama",query)
        return  true
    }

    override fun onQueryTextChange(newText: String): Boolean {
        aramaYap(newText)
        Log.e("Harf girdikçe",newText)
        return true
    }



    fun tumKelimeler(){

        refKelimeler.addValueEventListener(object : ValueEventListener{

            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {

                kelimelerListe.clear()

                for(c in snapshot.children){
                    val kelime = c.getValue(kelimeler::class.java)

                    if (kelime != null){
                        kelime.kelime_id = c.key
                        kelimelerListe.add(kelime)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }


    fun aramaYap(aramaKelime:String){

        refKelimeler.addValueEventListener(object : ValueEventListener{

            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {

                kelimelerListe.clear()

                for(c in snapshot.children){
                    val kelime = c.getValue(kelimeler::class.java)

                    if (kelime != null){
                        if (kelime.ingilizce!!.contains(aramaKelime)){
                            kelime.kelime_id = c.key
                            kelimelerListe.add(kelime)
                        }

                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }


}