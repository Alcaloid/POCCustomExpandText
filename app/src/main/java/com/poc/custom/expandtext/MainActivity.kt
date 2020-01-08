package com.poc.custom.expandtext

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

    private fun createRecycleView(){
        rvExp.adapter = MyAdapter()
        rvExp.layoutManager = LinearLayoutManager(this)
    }


    class MyAdapter : RecyclerView.Adapter<MyViewHolder>() {


        private val fakeList = listOf<SampleDate>(

            SampleDate(TEXT_FULL),
            SampleDate(TEXT_FULL),
            SampleDate(TEXT_SHORT),
            SampleDate(TEXT_NEW_LINE),
            SampleDate(TEXT_FULL),
            SampleDate(TEXT_FULL),
            SampleDate(TEXT_FULL),
            SampleDate(TEXT_SHORT),
            SampleDate(TEXT_FULL),
            SampleDate(TEXT_NEW_LINE),
            SampleDate(TEXT_FULL),
            SampleDate(TEXT_FULL),
            SampleDate(TEXT_NEW_LINE),
            SampleDate(TEXT_FULL),
            SampleDate(TEXT_SHORT),
            SampleDate(TEXT_NEW_LINE),
            SampleDate(TEXT_FULL),
            SampleDate(TEXT_FULL),
            SampleDate(TEXT_SHORT),
            SampleDate(TEXT_SHORT),
            SampleDate(TEXT_FULL)

        )

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
           return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_sample_expand,parent,false))
        }

        override fun getItemCount(): Int = fakeList.size

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.bindPosition(fakeList[position]){
                fakeList[position].isExpand = it
            }

            holder.setIsRecyclable(false)
        }

    }

    class MyViewHolder(private val iv: View) : RecyclerView.ViewHolder(iv) {
        private val expTextView: CustomExpandText = iv.tvExp

        fun bindPosition(
            data: SampleDate,
            onUpdate: (newState: Boolean) -> Unit
        ) {
            expTextView.setExpandableText(data.deteail)
            expTextView.isExpand = data.isExpand
            expTextView.onStateChangeListener = { oldState, newState ->
                onUpdate.invoke(newState)
            }
        }

    }

    data class SampleDate(val deteail: String, var isExpand: Boolean = false)
}
