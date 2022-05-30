package com.snappy.quicknotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.PopupMenu
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

        deleteAll.setOnClickListener{
            val popupMenu = PopupMenu(this, it)
            popupMenu.setOnMenuItemClickListener { item->
                when(item.itemId){
                    R.id.clear_all->{
                        DataObject.deleteAll()
                        GlobalScope.launch {
                            database.dao().deleteAll()
                        }
                        setRecycler()
                        true
                    }else -> false
                }
            }
            popupMenu.inflate(R.menu.menu)
            popupMenu.show()
        }


        database = Room.databaseBuilder(
            applicationContext, MyDatabase::class.java, "Quick_Note"
        ).build()

        add.setOnClickListener {
                val intent = Intent(this, CreateCard::class.java)
                startActivity(intent)
            }
        setRecycler()

    }


    private fun setRecycler() {
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = Adapter(DataObject.getAllData())
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}
