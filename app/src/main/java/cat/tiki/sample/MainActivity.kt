package cat.tiki.sample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import cat.tiki.sample.adapter.jd.JDDetailActivity

import kotlinx.android.synthetic.main.activity_main.*
import cat.tiki.sample.adapter.list.TikiCustomListActivity
import cat.tiki.sample.adapter.waterflow.TikiWaterflowActivity
import cat.tiki.tikiadapter.TikiItemClickListener
import cat.tiki.tikiadapter.TikiRvAdapter
import cat.tiki.tikiadapter.extendsion.dip2px
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), TikiItemClickListener {
    var mainList: MutableList<MainModel> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val recyclerView = activity_main_rcv

        mainList = mutableListOf<MainModel>()
        mainList.add(MainModel(TYPE_TXt, 3, "网格布局", IntentType.TYPE_GRID))
        mainList.add(MainModel(TYPE_TXt, 3, "瀑布流布局", IntentType.TYPE_WATERFLOW))
        mainList.add(MainModel(TYPE_TXt, 3, "京东商品详细页", IntentType.TYPE_JD_DETAIL))

        for (mainModel in mainList) {
            mainModel.rect?.apply {
                bottom = dip2px(applicationContext, 10f)
                center = dip2px(applicationContext, 5f)
                side = dip2px(applicationContext, 10f)
            }
        }
        val tikiRvAdapter = TikiRvAdapter(applicationContext, mainList)
        recyclerView.adapter = tikiRvAdapter
        tikiRvAdapter.setRvConfig(true, recyclerView)
        tikiRvAdapter.registerItem(TYPE_TXt,  MainTxtVH())
        tikiRvAdapter.setOnItemClick(this)
        tikiRvAdapter.notifyDataSetChanged()
    }

    override fun onItemClick(view: View, position: Int) {
        val mainList = mainList?.get(position)
        val intentType = mainList?.intentType
        var cls: Class<Any>? = null
        when(intentType) {
            IntentType.TYPE_GRID -> { cls = TikiCustomListActivity::class.java as Class<Any>?}
            IntentType.TYPE_WATERFLOW -> { cls = TikiWaterflowActivity::class.java as Class<Any>?}
            IntentType.TYPE_JD_DETAIL -> { cls = JDDetailActivity::class.java as Class<Any>?}

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
    }
}
