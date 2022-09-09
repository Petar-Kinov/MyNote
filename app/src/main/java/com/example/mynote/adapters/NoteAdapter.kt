package com.example.mynote.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mynote.R
import com.example.mynote.modelClass.Note

class NoteAdapter(
    val context: Context,
    val noteClickInterface: NoteClickInterface,
    val noteClickDeleteInterface: NoteClickDeleteInterface
    ): RecyclerView.Adapter<NoteAdapter.ViewHolder> (){

    private val allNotes = ArrayList<Note>()

        inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            val noteTextView = itemView.findViewById<TextView>(R.id.noteTitle)
            val timeTextView = itemView.findViewById<TextView>(R.id.timeTextView)
            val deleteButton = itemView.findViewById<ImageView>(R.id.deleteButton)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.note_recyclerview_item, parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.noteTextView.setText(allNotes.get(position).noteTitle)
        holder.timeTextView.setText(allNotes.get(position).timeStamp)

        holder.deleteButton.setOnClickListener{
            noteClickDeleteInterface.onDeleteIconClick(allNotes.get(position))
        }

        holder.itemView.setOnClickListener{
            noteClickInterface.onNoteClick(allNotes.get(position))
        }
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }

    fun updateList(newList: List<Note>){
        allNotes.clear()
        allNotes.addAll(newList)
        notifyDataSetChanged()
    }
}

interface NoteClickDeleteInterface{
    fun onDeleteIconClick(note: Note)
}

interface NoteClickInterface{
    fun onNoteClick(note: Note)
}