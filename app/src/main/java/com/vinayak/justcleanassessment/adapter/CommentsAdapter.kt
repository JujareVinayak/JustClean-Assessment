package com.vinayak.justcleanassessment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vinayak.justcleanassessment.data.Comment
import com.vinayak.justcleanassessment.data.Post
import com.vinayak.justcleanassessment.databinding.ItemCommentBinding

class CommentsAdapter: RecyclerView.Adapter<CommentsAdapter.CommentViewHolder>() {

    val diffUtil = object : DiffUtil.ItemCallback<Comment>(){
        override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    val differ = AsyncListDiffer(this,diffUtil)

    fun submitList(list:List<Comment>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsAdapter.CommentViewHolder {
        val binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentsAdapter.CommentViewHolder, position: Int) {
        val comment = differ.currentList.get(index = position)
        holder.binding.postId.text = comment.postId.toString()
        holder.binding.commentId.text = comment.id.toString()
        holder.binding.name.text = comment.name.toString()
        holder.binding.email.text = comment.email.toString()
        holder.binding.body.text = comment.body.toString()
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class CommentViewHolder(var binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root)
}