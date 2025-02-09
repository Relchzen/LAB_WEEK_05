package com.example.lab_week_05

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class ListFragment : BaseAuthFragment() {
    private lateinit var db:FirebaseFirestore
    private lateinit var coffeeListView: ListView
    private lateinit var coffeeAdapter: ArrayAdapter<String>
    private val coffeeTitles = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container:ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view:View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = FirebaseFirestore.getInstance()
        coffeeListView = view.findViewById(R.id.coffee_list_view)
        coffeeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1,
            coffeeTitles)

        coffeeListView.adapter = coffeeAdapter
        fetchCoffeeData()
    }

    private fun fetchCoffeeData() {
        coffeeTitles.clear()
        db.collection("coffees").get().addOnSuccessListener {
            documents ->
            for (document in documents) {
                val coffee = document.toObject(Coffee::class.java)
                coffeeTitles.add(coffee.title)
            }

            coffeeAdapter.notifyDataSetChanged()
        }.addOnFailureListener { e ->
            Log.w("Firestore", "Error getting documents: ", e)
        }
    }




}