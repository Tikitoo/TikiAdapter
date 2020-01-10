package cat.tiki.sample.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import cat.tiki.sample.R
import cat.tiki.sample.adapter.jd.JDDetailActivity

import kotlinx.android.synthetic.main.activity_main.*
import cat.tiki.sample.adapter.list.TikiCustomListActivity
import cat.tiki.sample.adapter.waterflow.TikiWaterflowActivity
import cat.tiki.tikiadapter.TikiRvAdapter
import cat.tiki.sample.extendsion.dip2px
import cat.tiki.tikiadapter.TikiItemClickListener
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.item_main_txt.*

class MainActivity : AppCompatActivity(), TikiItemClickListener {

    private var mainList: MutableList<MainEntity> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val recyclerView = activity_main_rcv

        mainList.add(
            MainEntity(TYPE_TXt, 3, "网格布局", IntentType.TYPE_GRID)
        )
        mainList.add(
            MainEntity(TYPE_TXt,3,"瀑布流布局", IntentType.TYPE_WATERFLOW)
        )
        mainList.add(MainEntity(TYPE_TXt,3,"京东商品详细页", IntentType.TYPE_JD_DETAIL)
        )
//        mainList.add(MainEntity(TYPE_TXt, 3, "BoxCover", IntentType.TYPE_BOX_COVER))

        for (mainModel in mainList) {
            mainModel.rect?.apply {
                bottom = dip2px(10f)
                center = dip2px(5f)
                side = dip2px(10f)
            }
        }

        val tikiRvAdapter = TikiRvAdapter(applicationContext, mainList)
        recyclerView.adapter = tikiRvAdapter
        tikiRvAdapter?.apply {
            setRvConfig(true, recyclerView)
            registerItem(TYPE_TXt, MainTxtVH())
            notifyDataSetChanged()
            setOnItemClick(this@MainActivity)
        }
    }

    override fun onItemClick(view: View, position: Int) {
        print("position: " + position)
        if (view.id == main_txt_tv.id) {
            Log.d("", "id is main_txt_tv: " + position)
        }
        val mainList = mainList?.get(position)
        val intentType = mainList?.intentType
        var cls: Class<Any>? = null
        when(intentType) {
            IntentType.TYPE_GRID -> { cls = TikiCustomListActivity::class.java as Class<Any>?}
            IntentType.TYPE_WATERFLOW -> { cls = TikiWaterflowActivity::class.java as Class<Any>?}
            IntentType.TYPE_JD_DETAIL -> { cls = JDDetailActivity::class.java as Class<Any>?}
//            IntentType.TYPE_BOX_COVER -> { cls = KotlinBoxCoverActivity::class.java as Class<Any>?}
        }

        val intent = Intent(this, cls)
        startActivity(intent)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object ItemType {
        val TYPE_TXt: Int = R.layout.item_main_txt
    }

    object IntentType {
        val TYPE_GRID: Int = 10001
        val TYPE_WATERFLOW: Int = 10002
        val TYPE_JD_DETAIL: Int = 10003
        val TYPE_BOX_COVER: Int = 10004
    }
}
