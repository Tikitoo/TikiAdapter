package cat.tiki.sample.adapter.list

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cat.tiki.sample.R
import cat.tiki.tikiadapter.TikiBaseModel
import cat.tiki.tikiadapter.TikiRvAdapter
import kotlinx.android.synthetic.main.activity_tiki_recycler_view.*
import cat.tiki.sample.extendsion.dip2px

/**
 * Created by Yifa Liang on 2019-09-16.
 */
class TikiCustomListActivity: AppCompatActivity() {
    var dataList: MutableList<TikiBaseModel> = arrayListOf()
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tiki_recycler_view)
        context = applicationContext

        addModels()

        for (kotlinBaseModel in dataList) {
            kotlinBaseModel?.apply {
                rect.bottom = dip2px(15F)
                rect.center = dip2px(5F)
                rect.side = dip2px(10F)

                drawRect?.apply {
                    bottom = dip2px(6f)
                }
            }
        }


        val recyclerView = activity_tiki_rcv
        val rvAdapter = TikiRvAdapter(applicationContext, dataList)
        rvAdapter?.apply {
            recyclerView.adapter = this
            setRvConfig(false, recyclerView)
            registerItem(R.layout.item_customlist_text,
                TikiCustomListTxtVH()
            )
            notifyDataSetChanged()
        }
    }

    private fun addModels() {
        dataList.add(TikiCustomTxt(CUSTOM_TXT,1,"Hello"))
        dataList.add(TikiCustomTxt(CUSTOM_TXT,2,"Blue"))
        dataList.add(TikiCustomTxt(CUSTOM_TXT,2,"Hulu"))
        dataList.add(TikiCustomTxt(CUSTOM_TXT,2,"Goods"))


        dataList.add(TikiCustomTxt(CUSTOM_TXT,1,"Goods"))
        dataList.add(TikiCustomTxt(CUSTOM_TXT,1,"HHHHHAAAAA"))

        dataList.add(TikiCustomTxt(CUSTOM_TXT,3,"Goods"))
        dataList.add(TikiCustomTxt(CUSTOM_TXT,3,"HHHHHAAAAA"))
        dataList.add(TikiCustomTxt(CUSTOM_TXT,3,"Hulu"))


        dataList.add(TikiCustomTxt(CUSTOM_TXT,2,"HHHHHAAAAA"))
        dataList.add(TikiCustomTxt(CUSTOM_TXT,2,"Hulu"))

        dataList.add(TikiCustomTxt(CUSTOM_TXT,2,"Hulu"))

        dataList.add(TikiCustomTxt(CUSTOM_TXT,1,"Goods"))
    }

    companion object ItemType {
        var CUSTOM_TXT = R.layout.item_customlist_text
    }

}

