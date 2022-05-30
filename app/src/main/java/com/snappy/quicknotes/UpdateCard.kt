package com.snappy.quicknotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.room.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private lateinit var database: MyDatabase

class UpdateCard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_card)

        database = Room.databaseBuilder(
            applicationContext, MyDatabase::class.java, "Quick_Note"
        ).build()

        val deleteButton: Button = findViewById(R.id.delete_button)
        val updateButton: Button = findViewById(R.id.update_button)
        val updateTitle: TextView = findViewById(R.id.update_title)
        val updatePriority: TextView = findViewById(R.id.update_priority)

        val pos = intent.getIntExtra("id", -1)
        if (pos != -1) {
            val title = DataObject.getData(pos).title
            val priority = DataObject.getData(pos).priority
            updateTitle.setText(title)
            updatePriority.setText(priority)

            deleteButton.setOnClickListener {
                DataObject.deleteData(pos)
                GlobalScope.launch {
                    database.dao().deleteTask(
                        Entity(
                            pos + 1,
                            updateTitle.text.toString(),
                            updatePriority.text.toString()
                        )
                    )
                }
                myIntent()
            }

            updateButton.setOnClickListener {
                DataObject.updateData(
                    pos,
                    updateTitle.text.toString(),
                    updatePriority.text.toString()
                )
                GlobalScope.launch {
                    database.dao().updateTask(
                        Entity(
                            pos + 1,
                            updateTitle.text.toString(),
                            updatePriority.text.toString()
                        )
                    )
                }
                myIntent()
            }


            val goback: ImageView = findViewById(R.id.goback)
            goback.setOnClickListener{
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

        }
    }
        fun myIntent() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
}