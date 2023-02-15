package com.griddynamics.gd_testing.presentation.commits

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.griddynamics.gd_testing.R
import com.griddynamics.gd_testing.domain.model.CommitModel

class CommitsAdapter : RecyclerView.Adapter<CommitsAdapter.CommitsVH>() {

    inner class CommitsVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val committerImg: AppCompatImageView = itemView.findViewById(R.id.authorImg)
        private val authorName: AppCompatTextView = itemView.findViewById(R.id.authorName)
        private val commitSha: AppCompatTextView = itemView.findViewById(R.id.commitSha)

        fun bind(model: CommitModel) {
            committerImg.load(model.committerAvatar) {
                crossfade(300)
                transformations(CircleCropTransformation())
            }
            authorName.text = model.committerName
            commitSha.text = model.sha
        }
    }

    private var list: MutableList<CommitModel> = mutableListOf()

    fun setList(newList: List<CommitModel>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommitsVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.commit_list_item, parent, false)
        return CommitsVH(view)
    }


    override fun onBindViewHolder(holder: CommitsVH, position: Int) {
        list.getOrNull(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int = list.size

}