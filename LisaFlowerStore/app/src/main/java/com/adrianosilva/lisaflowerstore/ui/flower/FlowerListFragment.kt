package com.adrianosilva.lisaflowerstore.ui.flower

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adrianosilva.lisaflowerstore.adapters.FlowerAdapter

import com.adrianosilva.lisaflowerstore.databinding.FragmentFlowerListBinding
import com.adrianosilva.lisaflowerstore.objects.FlowerObject
import com.adrianosilva.lisaflowerstore.viewmodel.FlowerListViewModel
import com.adrianosilva.lisaflowerstore.viewmodel.FlowerListViewModelFactory


class FlowerListFragment : Fragment() {

    private lateinit var viewModel: FlowerListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding = FragmentFlowerListBinding.inflate(inflater, container, false)

        viewModel = ViewModelProviders.of(this, FlowerListViewModelFactory.getInstance(activity!!.application)).get(FlowerListViewModel::class.java)

        val adapter = FlowerAdapter()
        binding.flowerListFragmentRv.adapter = adapter
        subscribeUi(adapter)

        //viewModel.insertFlower(FlowerObject(null, "Florzinha", "TESTESTESTEST", 24.99))
        //viewModel.insertFlower(FlowerObject(null, "Florzona", "TESTESTESTEST", 32.99))

        return binding.root
    }

    private fun subscribeUi(adapter: FlowerAdapter) {
        viewModel.getAllFlowers().observe(viewLifecycleOwner, Observer { flowers ->
            if (flowers != null) adapter.submitList(flowers)
        })
    }


}
