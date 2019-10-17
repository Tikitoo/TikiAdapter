package cat.tiki.sample.adapter

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cat.tiki.sample.R
import cat.tiki.tikiadapter.KotlinBaseModel
import cat.tiki.tikiadapter.KotlinBaseRvAdapter
import kotlinx.android.synthetic.main.biz_show_activity_custom_list.*
import cat.tiki.tikiadapter.extendsion.dip2px

/**
 * Created by Yifa Liang on 2019-09-16.
 */
class KotlinCustomListActivity: AppCompatActivity() {
    var dataList: MutableList<KotlinBaseModel> = arrayListOf()
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.biz_show_activity_custom_list)
        context = applicationContext

        addModels()

        for (kotlinBaseModel in dataList) {
            kotlinBaseModel?.apply {
                rect.bottom = dip2px(context, 15F)
                rect.center = dip2px(context, 5F)
                rect.side = dip2px(context, 10F)
            }
        }


        val recyclerView = biz_show_activity_custom_list_rcv
        val rvAdapter = KotlinBaseRvAdapter(applicationContext, dataList)
        rvAdapter?.apply {
            recyclerView.adapter = this
            setRvConfig(false, recyclerView)
            registerItem(R.layout.bizshow_item_customlist_text,
                KotlinCustomListTxtVH()
            )
            notifyDataSetChanged()
        }
    }

    private fun addModels() {
        dataList.add(KotlinCustomTxt(CUSTOM_TXT,1, "Hello"))
        dataList.add(KotlinCustomTxt(CUSTOM_TXT,2,"Blue"))
        dataList.add(KotlinCustomTxt(CUSTOM_TXT,2,"Hulu"))

        dataList.add(
            KotlinCustomTxt(
                CUSTOM_TXT,
                2,
                "Goods"
            )
        )


        dataList.add(
            KotlinCustomTxt(
                CUSTOM_TXT,
                1,
                "Goods"
            )
        )
        dataList.add(
            KotlinCustomTxt(
                CUSTOM_TXT,
                1,
                "HHHHHAAAAA"
            )
        )



        dataList.add(
            KotlinCustomTxt(
                CUSTOM_TXT,
                3,
                "Goods"
            )
        )
        dataList.add(
            KotlinCustomTxt(
                CUSTOM_TXT,
                3,
                "HHHHHAAAAA"
            )
        )
        dataList.add(
            KotlinCustomTxt(
                CUSTOM_TXT,
                3,
                "Hulu"
            )
        )


        dataList.add(
            KotlinCustomTxt(
                CUSTOM_TXT,
                2,
                "HHHHHAAAAA"
            )
        )
        dataList.add(
            KotlinCustomTxt(
                CUSTOM_TXT,
                2,
                "Hulu"
            )
        )

        dataList.add(
            KotlinCustomTxt(
                CUSTOM_TXT,
                2,
                "Hulu"
            )
        )

        dataList.add(
            KotlinCustomTxt(
                CUSTOM_TXT,
                1,
                "Goods"
            )
        )
    }

    companion object ItemType {
        var CUSTOM_TXT = R.layout.bizshow_item_customlist_text
    }

}

