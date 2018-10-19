package com.example.android.noteapp.items

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.android.noteapp.R
import com.example.android.noteapp.data.Note
import com.example.android.noteapp.note.NoteDetailActivity
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.note_item_small.view.*

class NoteItemSmall(private val note: Note, private val context: Context): Item(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.note_text_textview.text = note.detail
        viewHolder.itemView.note_heading_textview.text = note.title
        Glide.with(context).load(note.imageUrl).into(viewHolder.itemView.note_header_image_imageview)

        viewHolder.itemView.item_cardview.setOnClickListener {
            val detailsIntent = Intent(context, NoteDetailActivity::class.java)
            detailsIntent.putExtra("note", note)
            context.startActivity(detailsIntent)
        }
    }

    override fun getLayout() = R.layout.note_item_small

    override fun getSpanSize(spanCount: Int, position: Int) = spanCount / 3
}