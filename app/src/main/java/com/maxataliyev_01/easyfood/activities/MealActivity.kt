package com.maxataliyev_01.easyfood.activities

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.maxataliyev_01.easyfood.MVVM.MealViewModel
import com.maxataliyev_01.easyfood.R
import com.maxataliyev_01.easyfood.databinding.ActivityMealBinding
import com.maxataliyev_01.easyfood.fragments.HomeFragment
import com.maxataliyev_01.easyfood.pojo.Meal

class MealActivity : AppCompatActivity() {
    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var mealThumb:String
    private lateinit var mealMvvm:MealViewModel

    lateinit var binding: ActivityMealBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mealMvvm=ViewModelProviders.of(this)[MealViewModel::class.java]

        getMealInformationFromIntent()
        setInformationInViews()

        mealMvvm.getMealDetail(mealId)
        observeMealDetailLiveData()

    }

    private fun observeMealDetailLiveData() {
        mealMvvm.observeMealDetailLiveData().observe(this,object :Observer<Meal>{
            override fun onChanged(t: Meal?) {
                val meal=t
                binding.tvCategoryDetail.text="Category: ${meal!!.strCategory}"
                binding.tvAreaDetail.text="Category: ${meal.strArea}"
                binding.instruction.text=meal.strInstructions
            }
        })
    }

    private fun setInformationInViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)
        binding.collapsingToolbar.title=mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getMealInformationFromIntent() {
        val intent=intent
        mealId=intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName=intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb=intent.getStringExtra(HomeFragment.MEAL_THUMB)!!

    }

    private fun loadingCase(){
        binding.btnAddToFavorite.visibility = View.INVISIBLE
        binding.instruction.visibility = View.INVISIBLE
        binding.btnAddToFavorite.visibility = View.INVISIBLE



    }

    private fun onRespondingCase(){

    }
}