package com.adrianosilva.lisaflowerstore.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.adrianosilva.lisaflowerstore.databinding.FragmentFlowerListBinding
import com.adrianosilva.lisaflowerstore.objects.FlowerObject

class FlowerAdapter : ListAdapter<FlowerObject, FlowerAdapter.FlowerViewHolder>(FlowerDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlowerViewHolder {
        return FlowerViewHolder(FragmentFlowerListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: FlowerViewHolder, position: Int) {
        val flower = getItem(position)
        holder.apply {
            bind(createOnClickListener(flower.id), flower)
            //itemView.tag = flower
        }
    }

    private fun createOnClickListener(flowerId: Int): View.OnClickListener {
        return View.OnClickListener {
            /*val direction = PlantListFragmentDirections.actionPlantListFragmentToPlantDetailFragment(flowerId)
            it.findNavController().navigate(direction)*/
        }
    }


    class FlowerViewHolder(private val binding: FragmentFlowerListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, item: FlowerObject) {
            binding.apply {
                //clickListener = listener
                //flowerObject = item
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