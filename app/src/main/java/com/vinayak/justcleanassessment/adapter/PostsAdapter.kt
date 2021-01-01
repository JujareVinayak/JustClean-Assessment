package com.vinayak.justcleanassessment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vinayak.justcleanassessment.data.Post
import com.vinayak.justcleanassessment.databinding.ItemPostBinding

class PostsAdapter(private val listener: OnItemClickListener): RecyclerView.Adapter<PostsAdapter.PostViewHolder>()  {

    val diffUtil = object : DiffUtil.ItemCallback<Post>(){
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    val differ = AsyncListDiffer(this,diffUtil)

    fun submitList(list:List<Post>) = differ.submitList(list)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = differ.currentList.get(index = position)
        holder.binding.postId.text = post.id.toString()
        holder.binding.userId.text = post.userId.toString()
        holder.binding.title.text = post.getTitle().toString()
        holder.binding.body.text = post.getBody().toString()
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class PostViewHolder(var binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root){
        init {
            binding.apply {
                cardView.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val post = differ.currentList[position]
                        listener.onItemClick(post)
                    }
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(post: Post)
    }
}