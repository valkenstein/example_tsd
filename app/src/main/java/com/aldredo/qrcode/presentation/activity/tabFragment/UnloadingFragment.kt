package com.aldredo.qrcode.presentation.activity.tabFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aldredo.qrcode.R
import com.aldredo.qrcode.di.component.ActivityComponent
import com.aldredo.qrcode.presentation.adapter.NomenclatureAdapter
import com.aldredo.qrcode.presentation.presenter.TaskWindowViewModel
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class UnloadingFragment(override val nameFragment: String) : BaseFragment() {
    private val nomenclatureAdapter = NomenclatureAdapter()
    private var param1: String? = null
    private var param2: String? = null

    @Inject
    lateinit var taskWindowViewModel: TaskWindowViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        ActivityComponent.create(requireContext())?.inject(this)
        //taskWindowViewModel.init()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.bt_right).setOnClickListener {}
        view.findViewById<RecyclerView>(R.id.recycler_view).apply {
            adapter = nomenclatureAdapter
            layoutManager = LinearLayoutManager(view.context)
        }
        taskWindowViewModel.getNomenclatures().observe(viewLifecycleOwner, {
            nomenclatureAdapter.submitList(it)
        })

        taskWindowViewModel.getProgressBar().observe(viewLifecycleOwner, {
            view.findViewById<ProgressBar>(R.id.progress_bar).visibility =
                if (it) View.VISIBLE else View.GONE
        })

        taskWindowViewModel.getStatusMessage().observe(viewLifecycleOwner, {
            Snackbar.make(view, it, 2500).show()
        })

        taskWindowViewModel.getErrorMessages().observe(viewLifecycleOwner, {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })
    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        @JvmStatic
        fun newInstance() =
            UnloadingFragment("Надо разгрузить").apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}