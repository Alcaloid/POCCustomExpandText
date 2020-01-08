package com.poc.custom.expandtext

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.poc.custom.expandtext.ui.CustomExpandText
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_sample_expand.view.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val TEXT_FULL =
            "The Espresso Test Recorder tool lets you create UI tests for your app without writing any test code. By recording a test scenario, " +
                    "you can record your interactions with a device and add assertions to verify UI elements in particular snapshots of your app. " +
                    "Espresso Test Recorder then takes the saved recording and automatically generates a corresponding UI test that you can run to test your app.\n" +
                    "Espresso Test Recorder writes tests based on the Espresso Testing framework, an API in AndroidX Test. " +
                    "The Espresso API encourages you to create concise and reliable UI tests based on user actions. " +
                    "By stating expectations, interactions, and assertions without directly accessing the underlying app’s activities and views, this structure prevents test flakiness and optimizes test run speed."
        const val TEXT_SHORT =
            "Espresso Test Recorder then takes the saved recording and automatically"
        const val TEXT_NEW_LINE = "Start\n2\n3\n4\n5\nEnd"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createRecycleView()

    }

    private fun createRecycleView() {
        rvExp.adapter = MyAdapter()
        rvExp.layoutManager = LinearLayoutManager(this)
    }


    class MyAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        private val fakeList = listOf<DynamicTypeData>(
            DynamicTypeData.ImageData(),
            DynamicTypeData.SampleDate(TEXT_FULL),
            DynamicTypeData.SampleDate(TEXT_FULL),
            DynamicTypeData.SampleDate(TEXT_SHORT),
            DynamicTypeData.SampleDate(TEXT_NEW_LINE),
            DynamicTypeData.SampleDate(TEXT_FULL),
            DynamicTypeData.ImageData(),
            DynamicTypeData.SampleDate(TEXT_FULL),
            DynamicTypeData.SampleDate(TEXT_FULL),
            DynamicTypeData.SampleDate(TEXT_SHORT),
            DynamicTypeData.SampleDate(TEXT_FULL),
            DynamicTypeData.SampleDate(TEXT_NEW_LINE),
            DynamicTypeData.SampleDate(TEXT_FULL),
            DynamicTypeData.SampleDate(TEXT_FULL),
            DynamicTypeData.SampleDate(TEXT_NEW_LINE),
            DynamicTypeData.SampleDate(TEXT_FULL),
            DynamicTypeData.SampleDate(TEXT_SHORT),
            DynamicTypeData.SampleDate(TEXT_NEW_LINE),
            DynamicTypeData.SampleDate(TEXT_FULL),
            DynamicTypeData.SampleDate(TEXT_FULL),
            DynamicTypeData.SampleDate(TEXT_SHORT),
            DynamicTypeData.SampleDate(TEXT_SHORT),
            DynamicTypeData.SampleDate(TEXT_FULL)

        )

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

            return when (viewType) {
                R.layout.item_sample_expand -> {
                    MyViewHolder(
                        LayoutInflater.from(parent.context).inflate(
                            R.layout.item_sample_expand,
                            parent,
                            false
                        )
                    )
                }
                else -> {
                    MyViewHolderImage(
                        LayoutInflater.from(parent.context).inflate(
                            R.layout.item_image_detail,
                            parent,
                            false
                        )
                    )
                }
            }

        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

            when (fakeList[position]) {
                is DynamicTypeData.SampleDate -> {
                    val data = fakeList[position] as DynamicTypeData.SampleDate
                    val sampleHoleder = holder as MyViewHolder
                    sampleHoleder.bindPosition(data) {
                        data.isExpand = it
                    }
                    sampleHoleder.setIsRecyclable(false)

                }
                is DynamicTypeData.ImageData -> {

                }
            }
        }

        override fun getItemCount(): Int = fakeList.size

        override fun getItemViewType(position: Int): Int {
            return when (fakeList[position]) {
                is DynamicTypeData.SampleDate -> R.layout.item_sample_expand
                else -> R.layout.item_image_detail
            }
        }

    }

    class MyViewHolder(private val iv: View) : RecyclerView.ViewHolder(iv) {
        private val expTextView: CustomExpandText = iv.tvExp

        fun bindPosition(
            data: DynamicTypeData.SampleDate,
            onUpdate: (newState: Boolean) -> Unit
        ) {
            expTextView.setExpandableText(data.deteail)
            expTextView.isExpand = data.isExpand
            expTextView.setExpandOneTime(true)
            expTextView.setExpandMoreColor(Color.RED)
            expTextView.onStateChangeListener = { oldState, newState ->
                onUpdate.invoke(newState)
            }
        }

    }


    class MyViewHolderImage(private val iv: View) : RecyclerView.ViewHolder(iv) {

    }

    sealed class DynamicTypeData {
        data class SampleDate(val deteail: String, var isExpand: Boolean = false) :
            DynamicTypeData()

        data class ImageData(val deteail: String = "") : DynamicTypeData()

    }
}
