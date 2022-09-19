package com.maxataliyev_01.easyfood.activities

import android.content.Intent
import android.net.Uri
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
    private lateinit var youtubeLink:String
    private lateinit var mealMvvm:MealViewModel

    lateinit var binding: ActivityMealBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mealMvvm=ViewModelProviders.of(this)[MealViewModel::class.java]

        getMealInformationFromIntent()
        setInformationInViews()
        loadingCase()
        mealMvvm.getMealDetail(mealId)
        observeMealDetailLiveData()
        onYoutubeImageClick()

    }

    private fun onYoutubeImageClick() {
        binding.imgYouTube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private fun observeMealDetailLiveData() {
        mealMvvm.observeMealDetailLiveData().observe(this,object :Observer<Meal>{
            override fun onChanged(t: Meal?) {
                onRespondingCase()
                val meal=t
                binding.tvCategoryDetail.text="Category: ${meal!!.strCategory}"
                binding.tvAreaDetail.text="Category: ${meal.strArea}"
                binding.instruction.text=meal.strInstructions
                youtubeLink=meal.strYoutube
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
        binding.progress.visibility=View.VISIBLE
        binding.btnAddToFavorite.visibility = View.INVISIBLE
        binding.instruction.visibility = View.INVISIBLE
        binding.tvCategoryDetail.visibility = View.INVISIBLE
        binding.tvAreaDetail.visibility = View.INVISIBLE
        binding.imgYouTube.visibility = View.INVISIBLE




    }

    private fun onRespondingCase(){
        binding.progress.visibility=View.INVISIBLE
        binding.btnAddToFavorite.visibility = View.VISIBLE
        binding.instruction.visibility = View.VISIBLE
        binding.tvCategoryDetail.visibility = View.VISIBLE
        binding.tvAreaDetail.visibility = View.VISIBLE
        binding.imgYouTube.visibility = View.VISIBLE

    }
}