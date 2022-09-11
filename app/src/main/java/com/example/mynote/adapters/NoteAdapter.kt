package com.example.mynote.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mynote.databinding.NoteRecyclerviewItemBinding
import com.example.mynote.modelClass.Note

class NoteAdapter(
    val context: Context,
    val noteClickInterface: NoteClickInterface,
    val noteClickDeleteInterface: NoteClickDeleteInterface
) : ListAdapter<Note, NoteAdapter.NoteViewHolder>(NoteDiffCallBack()) {

    private val allNotes = ArrayList<Note>()

    inner class NoteViewHolder(binding: NoteRecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val titleTextView = binding.noteTitle
        val timeTextView = binding.timeTextView
        val deleteButton = binding.deleteButton
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding =
            NoteRecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val noteViewHolder = NoteViewHolder(binding)

        binding.deleteButton.setOnClickListener {
            if (noteViewHolder.adapterPosition != RecyclerView.NO_POSITION) {
                noteClickDeleteInterface.onDeleteIconClick(getItem(noteViewHolder.adapterPosition))
            }
        }

        noteViewHolder.itemView.setOnClickListener {
            if (noteViewHolder.adapterPosition != RecyclerView.NO_POSITION) {
                noteClickInterface.onNoteClick(getItem(noteViewHolder.adapterPosition))
            }
        }
        return noteViewHolder
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.titleTextView.setText(getItem(position).noteTitle)
        holder.timeTextView.setText(getItem(position).timeStamp)
    }

    interface NoteClickDeleteInterface {
        fun onDeleteIconClick(note: Note)
    }

    interface NoteClickInterface {
        fun onNoteClick(note: Note)
    }

    private class NoteDiffCallBack : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }
}