package com.snappy.quicknotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColor
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var database: MyDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getWindow().setStatusBarColor(this.resources.getColor(R.color.colorStatusbar))

        val add: Button = findViewById(R.id.add)
        val deleteAll: Button = findViewById(R.id.deleteAll)



        database = Room.databaseBuilder(
            applicationContext, MyDatabase::class.java, "Quick_Note"
        ).build()

        add.setOnClickListener {
                val intent = Intent(this, CreateCard::class.java)
                startActivity(intent)
            }

        deleteAll.setOnClickListener {
            DataObject.deleteAll()
            GlobalScope.launch {
                database.dao().deleteAll()
            }
            setRecycler()
        }
        setRecycler()
    }

    private fun setRecycler() {
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = Adapter(DataObject.getAllData())
        recyclerView.layoutManager = GridLayoutManager(this, 2)
    }
}
