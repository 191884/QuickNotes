package com.snappy.quicknotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.room.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private lateinit var database: MyDatabase
private lateinit var priority : String

class CreateCard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_card)

        database = Room.databaseBuilder(
            applicationContext, MyDatabase::class.java, "Quick_Note"
        ).build()

        val priorities = resources.getStringArray(R.array.Priorities)
        val spinner: Spinner = findViewById(R.id.spinner)
        val spinAdapter = ArrayAdapter(this, R.layout.spinner_layout, priorities)
        spinner.adapter = spinAdapter


        spinner.onItemSelectedListener = object :
            AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(
                    this@CreateCard,
                    parent?.getItemAtPosition(position).toString() + " Selected",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                priority = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
        val saveButton: Button = findViewById(R.id.save_button)
        val createTitle: TextView = findViewById(R.id.create_title)

        saveButton.setOnClickListener {
            if (createTitle.text.toString().trim { it <= ' ' }.isNotEmpty()
            ) {
                val title = createTitle.text.toString()

                DataObject.setData(title, priority)
                GlobalScope.launch {
                    database.dao().insertTask(Entity(0, title, priority))

                }

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }

        val goback: ImageView = findViewById(R.id.goback)
        goback.setOnClickListener{
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}