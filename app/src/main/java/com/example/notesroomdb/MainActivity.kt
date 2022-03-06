package com.example.notesroomdb

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesroomdb.adapter.NoteAdapter
import com.example.notesroomdb.database.NoteDataBase
import com.example.notesroomdb.database.entity.Note
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var btnAdd: FloatingActionButton
    private lateinit var rvMain: RecyclerView
    private lateinit var notes: ArrayList<Note>
    private lateinit var adapter: NoteAdapter

    private lateinit var dataBase:NoteDataBase

    private lateinit var itemMenuItem: MenuItem


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        (this as AppCompatActivity).setSupportActionBar(toolbar)

        window.statusBarColor = getColor(R.color.white)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        initViews()
    }

    private fun initViews() {

        dataBase = NoteDataBase.getInstance(this)

        btnAdd = findViewById(R.id.btn_add_note)
        rvMain = findViewById(R.id.rv_main)
        rvMain.layoutManager = GridLayoutManager(this, 1)



         btnAdd.setOnClickListener {
            showDialog()
        }

        notes = dataBase.noteDao().getNoteList() as ArrayList<Note>
        refreshAdapter(notes)


        adapter.deleteItem = {
            dataBase.noteDao().removeNote(it.noteId!!)
            notes.remove(it)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.more_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.delete -> {

                if (!notes.isEmpty()){
                    dataBase.noteDao().clearAll()
                    notes.removeAll(notes)
                    adapter.notifyDataSetChanged()
                    Toast.makeText(this, "O'chirildi", Toast.LENGTH_SHORT).show()
                }
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


    private fun refreshAdapter(notes: ArrayList<Note>){
        adapter = NoteAdapter(this, notes)
        rvMain.adapter = adapter
    }

    fun getDateTime(): String? {
        val simpleDateFormat = SimpleDateFormat("MMMM dd, yyyy / HH:mm:ss", Locale.getDefault())
        val date = Date()
        return simpleDateFormat.format(date)
    }

    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.custom_dialog)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        val btnSave = dialog.findViewById<CardView>(R.id.btn_save)
        val btnCancel = dialog.findViewById<CardView>(R.id.btn_cancel)
        val editNote = dialog.findViewById<EditText>(R.id.edit_text_note)

        btnSave.setOnClickListener {
            val note = Note(date = getDateTime()!!, note = editNote.text.toString())
            dataBase.noteDao().insertNote(note)
            notes.add(0, dataBase.noteDao().getNoteList().last())
            adapter.notifyDataSetChanged()
            dialog.dismiss()
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
    }



}