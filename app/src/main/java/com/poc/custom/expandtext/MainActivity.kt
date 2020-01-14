package com.poc.custom.expandtext

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.poc.custom.expandtext.ui.CustomExpandText
import kotlinx.android.synthetic.main.activity_main.rvExp
import kotlinx.android.synthetic.main.item_contain_main.view.containerMain
import kotlinx.android.synthetic.main.item_sample_expand.view.tvExp


class MainActivity : AppCompatActivity() {

    companion object {
        const val TEXT_NOT_MAX_LINE_ENG = "How best to make use of comments is subject to dispute;"
        const val TEXT_LONG_WITHOUT_NEW_LINE =
            "In computer programming, a comment is a programmer-readable explanation or annotation in the source code of a computer program. They are added with the purpose of making the source code easier for humans to understand, and are generally ignored by compilers and interpreters.[1][2] The syntax of comments in various programming languages varies considerably.Comments are generally formatted as either block comments (also called prologue comments or stream comments) or line comments (also called inline comments).[3]"
        const val TEXT_WITH_NEW_LINE = "1. Title pageThe only thing difficult about preparing the title page is creating the title itself. \n" +
                "A good title must be compelling and sufficiently informative to capture the attention of readers. \n" +
                "Most titles define the main subject, along with the concepts or theories covered in the report. \n" +
                "Apart from that, the title page also consists of other blocks of information such as the submission date, \n" +
                "the names and positions of the authors, and the principal reader (or the organization) responsible for preparing the document. \n" +
                "You may also see writing templates & examples."
        const val TEXT_NUMBER = "110010011010100010101010101101001111000001111100110101001010111010010101011001011101010010101111001100100110101000101010101011010011110000011111001101010010101110100101010110010111010100101011110011001001101010001010101010110100111100000111110011010100101011101001010101100101110101001010111100110010011010100010101010101101001111000001111100110101001010111010010101011001011101010010101111001100100110101000101010101011010011110000011111001101010010101110100101010110010111010100101011110010101101010101011010101011111001010111001101001101100101101010101010"
        const val TEXT_ENG_WITHOUT_SPACE = "Afghanistan~Albania~Algeria~Andorra~Angola~Antigua~Barbuda~Argentina~Armenia~Australia~Austria~Azerbaijan~Bahamas~Bahrain~Bangladesh~Barbados~Belarus~Belgium~Belize~Benin~Bhutan~Bolivia~BosniaHerzegovina~Botswana~Brazil~Brunei~Bulgaria~Burkina~Faso~Burundi~Cambodia~Cameroon~Canada~Cape~Verde~Central~African~Republic~Chad~Chile~China~Colombia~Comorros~Congo~Costa~Rica~Cote~dIvoire~Croatia~Cuba~Cyprus~Czech~Republic~Democratic~Republic~of~the~Congo~Denmark~Djibouti~Dominica~Dominican~Republic~East~Timor~Ecuador~Egypt~El~Salvador~Equatorial~Guinea~Eritrea~Estonia~Ethiopia~Fiji~Finland~France~Gabon~Gambia~Georgia~Germany~Ghana~Greece~Grenada~Guatemala~Guinea~Guinea-"
        const val TEXT_ENG_NOT_ENOUGH_SPACE = "Start\n1\n2\n3\n4\n5\nEnd"
        const val TEXT_ENG_NOT_ENOUGH_CHAR = "D\ne\ns\ntina\nt\ni\ni\no\nn"
        const val TEXT_ENG_ENOUGH_MAX_LINE = "How best to make use of comments is\n" +
                "In computer programming, a comment\n" +
                "A good title must be compelling \n" +
                "Apart from that, the title page\n" +
                "You may also see"
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

        private val listSampleDate = listOf<DynamicTypeData.SampleDate>(
            DynamicTypeData.SampleDate(TEXT_NOT_MAX_LINE_ENG),
            DynamicTypeData.SampleDate(TEXT_ENG_ENOUGH_MAX_LINE),
            DynamicTypeData.SampleDate(TEXT_LONG_WITHOUT_NEW_LINE),
            DynamicTypeData.SampleDate(TEXT_WITH_NEW_LINE),
            DynamicTypeData.SampleDate(TEXT_NUMBER),
            DynamicTypeData.SampleDate(TEXT_ENG_NOT_ENOUGH_SPACE),
            DynamicTypeData.SampleDate(TEXT_ENG_NOT_ENOUGH_CHAR),
            DynamicTypeData.SampleDate(TEXT_ENG_WITHOUT_SPACE)
        )
        private val fakeList = listOf<DynamicTypeData>(
            DynamicTypeData.RecommendData(listSampleDate),
            DynamicTypeData.ImageData()
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
                R.layout.item_recommand_detail -> {
                    MyViewHolderRecommendV2(
                        LayoutInflater.from(parent.context).inflate(
                            R.layout.item_contain_main,
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
                    val sampleHolder = holder as MyViewHolder
                    sampleHolder.bindPosition(data) {
                        data.isExpand = it
                    }
                    sampleHolder.setIsRecyclable(false)

                }
                is DynamicTypeData.ImageData -> {

                }

                is DynamicTypeData.RecommendData -> {
                    val data = fakeList[position] as DynamicTypeData.RecommendData
                    val sampleHolder = holder as MyViewHolderRecommendV2
                    sampleHolder.binData(data.list) { state, index ->
                        data.list[index].isExpand = state
                    }
                }
            }
        }

        override fun getItemCount(): Int = fakeList.size

        override fun getItemViewType(position: Int): Int {
            return when (fakeList[position]) {
                is DynamicTypeData.SampleDate -> R.layout.item_sample_expand
                is DynamicTypeData.RecommendData -> R.layout.item_recommand_detail
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
            expTextView.onStateChangeListener = { oldState, newState ->
                onUpdate.invoke(newState)
            }
        }

    }


    class MyViewHolderImage(private val iv: View) : RecyclerView.ViewHolder(iv) {

    }

    class MyViewHolderRecommend(private val iv: View) : RecyclerView.ViewHolder(iv) {

    }


    class MyViewHolderRecommendV2(private val iv: View) : RecyclerView.ViewHolder(iv) {

        var containMainView: LinearLayout = iv.containerMain

        fun binData(
            list: List<DynamicTypeData.SampleDate>,
            onUpdate: (newState: Boolean, index: Int) -> Unit
        ) {
            list.forEachIndexed { index, dynamicTypeData ->
                val inflater =
                    iv.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

                val popup = inflater.inflate(R.layout.item_recommand_detail, null)
                val expTextView = popup.findViewById<CustomExpandText>(R.id.tvExp)
                expTextView.setExpandableText(dynamicTypeData.deteail)
                expTextView.isExpand = false
                expTextView.onStateChangeListener = { oldState, newState ->
                    onUpdate.invoke(newState, index)
                }
//                val expTextView2 = popup.findViewById<TextView>(R.id.testja)
//                expTextView2.text = dynamicTypeData.deteail
//                if (x==10){
//                    val divider = popup.findViewById<View>(R.id.divider)
//                    divider.visibility = View.GONE
//                }

//                val expTextView3 = popup.findViewById<CustomTextButtonExpand>(R.id.tvCustom)
//                expTextView3.setExpandableText(dynamicTypeData.deteail)
//                expTextView3.onStateChangeListener = { oldState, newState ->
//                    onUpdate.invoke(newState, index)
//                }
                containMainView.addView(popup)
            }


        }


    }

    sealed class DynamicTypeData {
        data class SampleDate(val deteail: String, var isExpand: Boolean = false) :
            DynamicTypeData()

        data class ImageData(val deteail: String = "") : DynamicTypeData()

        data class RecommendData(val list: List<SampleDate>) : DynamicTypeData()

    }
}
