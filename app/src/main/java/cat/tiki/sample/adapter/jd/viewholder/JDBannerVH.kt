package cat.tiki.sample.adapter.jd.viewholder

import android.os.Build
import android.text.Html
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import cat.tiki.sample.R
import cat.tiki.sample.adapter.jd.JDBanner
import cat.tiki.sample.extendsion.load
import cat.tiki.tikiadapter.TikiBaseImg
import cat.tiki.tikiadapter.TikiBaseVHImpl
import cat.tiki.tikiadapter.extendsion.getAppWidth
import kotlinx.android.synthetic.main.item_jd_detail_banner.view.*

/**
 * Created by Yifa Liang on 2019-10-24.
 */
class JDBannerVH: TikiBaseVHImpl<JDBanner>() {
    override fun bindData(data: JDBanner, view: View) {
        val imgVp = view.item_jd_detail_banner_img_vp
        val titleTv = view.item_jd_detail_banner_title_tv
        val priceTv = view.item_jd_detail_banner_price_tv
        val descTv = view.item_jd_detail_banner_desc_tv

        // top img
        val appWidth = getAppWidth(view.context)
        imgVp.layoutParams = LinearLayout.LayoutParams(appWidth, appWidth)
        val adapter = JDBannerAdapter(data?.imgList)
        imgVp.adapter = adapter
        adapter.notifyDataSetChanged()

        // txt content
        val showTitle =
            "<img src='" + R.drawable.drawable_jd_phone + "'>" + "\t<img src='" + R.drawable.drawable_jd_origin + "'>" + "\t<img src='" + R.drawable.drawable_jd_double11+ "'>" + data.title
        titleTv.text = Html.fromHtml(showTitle, Html.ImageGetter {
            val id = Integer.parseInt(it)
            val drawable = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                view.context.getResources().getDrawable(id, null)
            } else {
                TODO("VERSION.SDK_INT < LOLLIPOP")
            }
            drawable.setBounds(0, 0, (drawable.getIntrinsicWidth()* 2),
                (drawable.getIntrinsicHeight()*2)
            )
            drawable
        }, null)

        descTv.text = data.content
        priceTv.text = data.price?.toString()


    }

}

class JDBannerAdapter(val imgList: MutableList<TikiBaseImg>) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
//        return super.instantiateItem(container, position)
        val imageView = ImageView(container.context)
        container.addView(imageView)
        val tikiBaseImg = imgList.get(position)
        tikiBaseImg?.apply {
            setImgParams(container.context)
            imageView.load(md5, imgWidth, imgHeight)
        }
        return imageView

    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
//        super.destroyItem(container, position, `object`)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return imgList?.size
    }

}