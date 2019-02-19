package com.adrianosilva.lisaflowerstore.ui.flower

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.adrianosilva.lisaflowerstore.database.FlowerDatabase

import com.adrianosilva.lisaflowerstore.databinding.FragmentFlowerDetailBinding
import com.adrianosilva.lisaflowerstore.repository.FlowerRepository
import com.adrianosilva.lisaflowerstore.viewmodel.FlowerDetailViewModel
import com.adrianosilva.lisaflowerstore.viewmodel.factory.FlowerDetailViewModelFactory


class FlowerDetailFragment : Fragment() {

    private val args: FlowerDetailFragmentArgs by navArgs()

    private lateinit var viewModel: FlowerDetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val factory = FlowerDetailViewModelFactory(FlowerRepository.getInstance(FlowerDatabase.getInstance(context!!.applicationContext).flowersDao()), args.flowerId)
        viewModel = ViewModelProviders.of(this, factory).get(FlowerDetailViewModel::class.java)

        val binding = FragmentFlowerDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        return binding.root
    }
}
