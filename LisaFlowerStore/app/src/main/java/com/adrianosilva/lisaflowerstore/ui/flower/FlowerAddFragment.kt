package com.adrianosilva.lisaflowerstore.ui.flower

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController

import com.adrianosilva.lisaflowerstore.databinding.FragmentFlowerAddBinding
import com.adrianosilva.lisaflowerstore.objects.FlowerObject
import com.adrianosilva.lisaflowerstore.viewmodel.FlowerAddViewModel
import com.adrianosilva.lisaflowerstore.viewmodel.ViewModelFactory

private lateinit var viewModel: FlowerAddViewModel

class FlowerAddFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding = FragmentFlowerAddBinding.inflate(inflater, container, false)

        viewModel = ViewModelProviders.of(this, ViewModelFactory.getInstance(activity!!.application)).get(FlowerAddViewModel::class.java)

        bind(binding, createOnClickListener(binding))

        return binding.root
    }

    private fun createOnClickListener(binding: FragmentFlowerAddBinding): View.OnClickListener {
        return View.OnClickListener {
            viewModel.insertFlower(FlowerObject(null,
                binding.flowerAddFragmentNameEt.text.toString(),
                binding.flowerAddFragmentDescriptionEt.text.toString(),
                binding.flowerAddFragmentPriceEt.text.toString().toDouble()))
            view!!.findNavController().navigate(FlowerAddFragmentDirections.actionFlowerAddFragmentToFlowerListFragment())
        }
    }

    private fun bind(binding: FragmentFlowerAddBinding ,listener: View.OnClickListener) {
        binding.apply {
            clickListener = listener
            executePendingBindings()
        }
    }

}
