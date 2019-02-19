package com.adrianosilva.lisaflowerstore.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.adrianosilva.lisaflowerstore.databinding.FlowerListItemBinding
import com.adrianosilva.lisaflowerstore.objects.FlowerObject
import com.adrianosilva.lisaflowerstore.ui.flower.FlowerListFragmentDirections
import com.adrianosilva.lisaflowerstore.viewmodel.FlowerListViewModel

class FlowerAdapter : ListAdapter<FlowerObject, FlowerAdapter.FlowerViewHolder>(FlowerDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlowerViewHolder {
        val binding = FlowerListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FlowerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FlowerViewHolder, position: Int) {
        val flower = getItem(position)
        holder.apply {
            bind(createOnClickListener(flower.id!!), flower)
            //itemView.tag = flower
        }
    }

    private fun createOnClickListener(flowerId: Int): View.OnClickListener {
        return View.OnClickListener {
            val direction = FlowerListFragmentDirections.actionFlowerListFragmentToFlowerDetailFragment(flowerId)
            it.findNavController().navigate(direction)
        }
    }


    class FlowerViewHolder(private val binding: FlowerListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, item: FlowerObject) {
            binding.apply {
                clickListener = listener
                flowerObject = item
                executePendingBindings()
            }
        }
    }
}

private class FlowerDiffCallback : DiffUtil.ItemCallback<FlowerObject>() {

    override fun areItemsTheSame(oldItem: FlowerObject, newItem: FlowerObject): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FlowerObject, newItem: FlowerObject): Boolean {
        return oldItem == newItem
    }

}