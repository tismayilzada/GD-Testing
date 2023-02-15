package com.griddynamics.gd_testing.presentation.search_repositories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.griddynamics.gd_testing.R
import com.griddynamics.gd_testing.domain.model.RepositoryModel

class RepositoriesAdapter : RecyclerView.Adapter<RepositoriesAdapter.RepositoriesVH>() {

    inner class RepositoriesVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val firtsLetter: AppCompatTextView = itemView.findViewById(R.id.firstLetter)
        private val title: AppCompatTextView = itemView.findViewById(R.id.repositoryName)
        private val desc: AppCompatTextView = itemView.findViewById(R.id.repositoryDesc)

        fun bind(model: RepositoryModel) {
            firtsLetter.text = model.name.first().uppercase()
            title.text = model.name
            desc.text = model.description
            itemView.setOnClickListener { clickListener?.invoke(model) }
        }
    }

    private var clickListener: ((RepositoryModel) -> Unit)? = null

    fun onItemClicked(listener: (RepositoryModel) -> Unit){
        this.clickListener = listener
    }

    private var list: MutableList<RepositoryModel> = mutableListOf()

    fun setList(newList: List<RepositoryModel>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    fun clearList() {
        list.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoriesVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.repository_list_item, parent, false)
        return RepositoriesVH(view)
    }


    override fun onBindViewHolder(holder: RepositoriesVH, position: Int) {
        list.getOrNull(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int = list.size

}