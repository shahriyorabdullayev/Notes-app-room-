package com.example.notesroomdb.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notesroomdb.R
import com.example.notesroomdb.database.entity.Note

class NoteAdapter(val context: Context, val items: ArrayList<Note>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

//    var deleteItemDb: ((id:Int) -> Unit)? = null
//    var deleteItemList: ((position:Int) -> Unit)? = null

    var deleteItem:((item: Note) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]

        if (holder is NoteViewHolder) {
            holder.apply {

                tvDate.text = item.date
                tvNote.text = item.note
                ivDelete.setOnClickListener {
//                    deleteItemDb!!.invoke(item.noteId!!)
//                    deleteItemList!!.invoke(position)
                    deleteItem!!.invoke(item)
                    notifyDataSetChanged()

                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvDate = view.findViewById<TextView>(R.id.tv_data)
        val tvNote = view.findViewById<TextView>(R.id.tv_note)
        val ivDelete = view.findViewById<ImageView>(R.id.iv_delete)
    }


}