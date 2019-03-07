package com.adrianosilva.lisaflowerstore.ui.flower

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.work.WorkInfo
import com.adrianosilva.lisaflowerstore.adapters.FlowerAdapter

import com.adrianosilva.lisaflowerstore.databinding.FragmentFlowerListBinding
import com.adrianosilva.lisaflowerstore.viewmodel.FlowerListViewModel
import com.adrianosilva.lisaflowerstore.viewmodel.factory.ViewModelFactory


class FlowerListFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var viewModel: FlowerListViewModel
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding = FragmentFlowerListBinding.inflate(inflater, container, false)

        viewModel = ViewModelProviders.of(this, ViewModelFactory.getInstance(activity!!.application)).get(FlowerListViewModel::class.java)

        val adapter = FlowerAdapter()
        binding.flowerListFragmentRv.adapter = adapter
        swipeRefreshLayout = binding.flowerListSrl
        binding.flowerListSrl.setOnRefreshListener(this)
        subscribeUi(adapter, binding)

        binding.flowerListAddFab.setOnClickListener {
            view!!.findNavController().navigate(FlowerListFragmentDirections.actionFlowerListFragmentToFlowerAddFragment())
        }

        return binding.root
    }

    override fun onRefresh() {
        Handler().postDelayed({
            viewModel.refreshAllFlowers()
            swipeRefreshLayout.isRefreshing = false
        },2000)
    }

    private fun subscribeUi(adapter: FlowerAdapter, binding: FragmentFlowerListBinding) {

        viewModel.outputWorkInfos.observe(this, workInfoObserver(binding))

        viewModel.getAllFlowers().observe(viewLifecycleOwner, Observer { flowers ->
            if (flowers != null)
                adapter.submitList(flowers)
        })
    }

    private fun workInfoObserver(binding: FragmentFlowerListBinding): Observer<List<WorkInfo>> {
        return Observer { listOfWorkInfo ->
            if (listOfWorkInfo.isNullOrEmpty()) {
                return@Observer
            }

            val workInfo = listOfWorkInfo[0]

            if (workInfo.state.isFinished || workInfo.state == WorkInfo.State.ENQUEUED) {
                Handler().postDelayed({ binding.flowerListFragmentProgressBar.visibility = View.GONE }, 500)
            } else {
                binding.flowerListFragmentProgressBar.visibility = View.VISIBLE
            }
        }
    }

}
