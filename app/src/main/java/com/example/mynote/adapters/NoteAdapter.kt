package com.example.mynote.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mynote.databinding.NoteRecyclerviewItemBinding
import com.example.mynote.modelClass.Note

class NoteAdapter(
    val noteClickInterface: (note: Note) -> Unit,
    val noteClickDeleteInterface: (note: Note) -> Unit,
) : ListAdapter<Note, NoteAdapter.NoteViewHolder>(NoteDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = NoteRecyclerviewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return NoteViewHolder(binding)
    }

    inner class NoteViewHolder(private val binding: NoteRecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.noteTitle.text = getItem(position).noteTitle

            binding.deleteButton.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    noteClickDeleteInterface(getItem(adapterPosition))
                }
            }

            binding.root.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    noteClickInterface(getItem(adapterPosition))
                }
            }
        }
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind()
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