package com.beykocbtcapp.hicat.view

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.beykocbtcapp.hicat.R
import com.beykocbtcapp.hicat.databinding.FragmentCatBinding
import com.beykocbtcapp.hicat.repository.ApiResponse
import com.beykocbtcapp.hicat.viewmodel.CatViewModel
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch


class CatFragment : Fragment() {

    private var _binding: FragmentCatBinding? = null
    private val binding get() = _binding!!

    /**
     * View Model
     */
    private lateinit var viewModel: CatViewModel

    private lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCatBinding.inflate(inflater, container, false)

        // Initialize the ViewModel
        viewModel = ViewModelProvider(requireActivity())[CatViewModel::class.java]


        navController = findNavController()

        // Observe the LiveData
        viewModel.catLiveData.observe(viewLifecycleOwner) { cat ->
            // Update UI
            when (cat) {
                is ApiResponse.Success -> {
                    val cat = cat.data
                    // Update UI with cat data
                    binding.animationView.visibility = View.GONE
                    binding.animationError.visibility = View.GONE

                    Glide.with(this)
                        .load(cat?.get(0)?.url) // image url
                        //.placeholder(R.drawable.ic_launcher_foreground) // any placeholder to load at start
                        //.error(R.drawable.ic_launcher_background)  // any image in case of error
                        .override(250, 250) // resizing
                        .centerCrop()
                        .into(binding.catImage);  // imageview object

                    binding.catImage.visibility = View.VISIBLE

                }
                is ApiResponse.Error -> {
                    val errorMessage = cat.message
                    // Show error message
                    binding.animationError.visibility = View.VISIBLE
                    binding.animationView.visibility = View.GONE
                    binding.catImage.visibility = View.GONE
                }
                is ApiResponse.Loading -> {
                    // Show loading indicator
                    binding.animationView.visibility = View.VISIBLE
                    binding.animationError.visibility = View.GONE
                    binding.catImage.visibility = View.GONE
                }
            }


        }


        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.getCat()
        }

    }

}