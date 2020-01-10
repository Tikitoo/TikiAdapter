## TikiAdapter 介绍

TikiAdapter 是一个Kotlin 封装的简洁好用的RecyclerView Adapter。可能是最简单好用的。



## 功能

支持一对一，一对多

只需要书写Model 和Item，不需要写映射

支持自定义Decoration

支持子 View 点击事件

AndroidX 支持



## 使用



引用

    implementation 'cat.tiki:tikiadapter:0.1.7'


Entity：继承自TikiBaseModel，重写layoutId 和 column

    data class MainEntity(
        override var layoutId: Int = 0,
        override var column: Int = 1,
        var txt: String,
        var intentType: Int
        ): TikiBaseModel() {
    }



ViewHolder：继承自TikiBaseVHImpl<T>，重写bindData 方法

    // 
    class MainTxtVH : TikiBaseVHImpl<MainEntity>() {
        override fun bindData(mainModel: MainEntity, view: View) {
            val mainTxtTv = view.main_txt_tv
            mainTxtTv.text = mainModel.txt
            addClickView(mainTxtTv)
        }
    
    }



Acitivity：创建 Adapter，自定义属性

    class MainActivity : AppCompatActivity(), TikiItemClickListener {
    
    	private var mainList: MutableList<MainEntity> = mutableListOf()
    	
    	override fun onCreate(savedInstanceState: Bundle?) {
    	    super.onCreate(savedInstanceState)
    	    setContentView(R.layout.activity_main)
    	
    			mainList.add(MainEntity(TYPE_TXt, 3, "网格布局", IntentType.TYPE_GRID))
    	
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
    
    	}
    
    }



## 截图

<img src="https://tva1.sinaimg.cn/large/006tNbRwgy1garm7vehpmj30u01t0ael.jpg" width=250/> <img src="https://tva1.sinaimg.cn/large/006tNbRwgy1garm89f8dzj30u01t01kx.jpg" width=250/> <img src="https://tva1.sinaimg.cn/large/006tNbRwgy1garm848n8tj30u01t0nat.jpg" width=250/>



## 协议

    Copyright 2020 Tikitoo.
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
       http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.