package cat.tiki.sample.adapter.waterflow

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cat.tiki.sample.R
import cat.tiki.tikiadapter.KotlinBaseModel
import cat.tiki.tikiadapter.TikiRvAdapter
import kotlinx.android.synthetic.main.activity_tiki_recycler_view.*
import cat.tiki.tikiadapter.extendsion.dip2px

/**
 * Created by Yifa Liang on 2019-09-16.
 */
class TikiWaterflowActivity: AppCompatActivity() {
    var dataList: MutableList<KotlinBaseModel> = arrayListOf()
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tiki_recycler_view)
        context = applicationContext

        addModels()

        for (kotlinBaseModel in dataList) {
            kotlinBaseModel?.apply {
                rect.bottom = dip2px(context, 5F)
                rect.center = dip2px(context, 5F)
                rect.side = dip2px(context, 10F)
            }
        }


        val recyclerView = activity_tiki_rcv
        val rvAdapter = TikiRvAdapter(applicationContext, dataList)
        rvAdapter?.apply {
            recyclerView.adapter = this
            setRvConfig(true, recyclerView)
            registerItem(CUSTOM_TXT, TikiWaterFlowVH()
            )
            notifyDataSetChanged()
        }
    }

    private fun addModels() {
        dataList = TikiWaterflowTxt.getData().toMutableList()
    }

    companion object ItemType {
        var CUSTOM_TXT = R.layout.item_waterflow
    }

}

