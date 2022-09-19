package com.maxataliyev_01.easyfood.fragments


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.maxataliyev_01.easyfood.MVVM.HomeViewModel
import com.maxataliyev_01.easyfood.R
import com.maxataliyev_01.easyfood.activities.MealActivity
import com.maxataliyev_01.easyfood.databinding.FragmentHomeBinding
import com.maxataliyev_01.easyfood.pojo.Meal
import com.maxataliyev_01.easyfood.pojo.MealList
import com.maxataliyev_01.easyfood.retrofit.MealApi
import com.maxataliyev_01.easyfood.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var homeMvvm:HomeViewModel
    private lateinit var randomMeal:Meal

    companion object{
        const val MEAL_ID = "com.maxataliyev_01.easyfood.fragments.idMeal"
        const val MEAL_NAME = "com.maxataliyev_01.easyfood.fragments.nameMeal"
        const val MEAL_THUMB = "com.maxataliyev_01.easyfood.fragments.thumbMeal"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = ViewModelProviders.of(this)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeMvvm.getRandomMeal()
        observeRandomMeal()
        onRandomMealClikc()
    }

    private fun onRandomMealClikc() {
        binding.imgRandomMeal.setOnClickListener {
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observeRandomMeal() {
        homeMvvm.observeRandomMealLiveData().observe(viewLifecycleOwner
        ) { meal ->
            Glide.with(this@HomeFragment)
                .load(meal!!.strMealThumb)
                .into(binding.imgRandomMeal)
            this.randomMeal=meal
        }
    }

}