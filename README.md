## TikiAdapter

TikiAdapter is a simple and easy-to-use RecyclerView Adapter written in Kotlin. Probably the easiest to use Adapter.

[中文版](https://github.com/Tikitoo/TikiAdapter/blob/master/README_ZH.md)

<br />

## Features

- Support one to one, one to many

- Just write Model and Item, no need to write mapping

- Support custom Decoration

- Support sub-view click events

- AndroidX support



<br />

## Use



<br />
Dependencies

```json
implementation 'cat.tiki:tikiadapter:0.1.7'
```


<br />
Entity: inherited from TikiBaseModel, override layoutId and column

```kotlin
data class MainEntity(
    override var layoutId: Int = 0, // item layout id
    override var column: Int = 1, // 
    var txt: String,
    var intentType: Int
    ): TikiBaseModel() {
}
```



<br />
ViewHolder: inherited from TikiBaseVHImpl <T>, override the bindData method

```kotlin
class MainTxtVH : TikiBaseVHImpl<MainEntity>() {
    override fun bindData(mainModel: MainEntity, view: View) {
        val mainTxtTv = view.main_txt_tv
        mainTxtTv.text = mainModel.txt
        addClickView(mainTxtTv)
    }

}
```


<br />

Acitivity / Fragment: Create Adapter, custom properties

```kotlin
class MainActivity : AppCompatActivity(), TikiItemClickListener {

	private var mainList: MutableList<MainEntity> = mutableListOf()
	
	override fun onCreate(savedInstanceState: Bundle?) {
	    super.onCreate(savedInstanceState)
	    setContentView(R.layout.activity_main)
	
			mainList.add(MainEntity(TYPE_TXT, 3, "网格布局", IntentType.TYPE_GRID))
	
			val tikiRvAdapter = TikiRvAdapter(applicationContext, mainList)
			recyclerView.adapter = tikiRvAdapter
			tikiRvAdapter?.apply {
			    // set layoutmanager type, true: grid, false: Satgger
			    setRvConfig(true, recyclerView)
                // add item
                registerItem(TYPE_TXT, MainTxtVH()) 
                // set On Item Click
                setOnItemClick(this@MainActivity) 
                // set data
                setData(mainList) 
			}
	}

	override fun onItemClick(view: View, position: Int) {

	}

}
```


<br />

## Screenshot

<img src="https://tva1.sinaimg.cn/large/006tNbRwgy1garm7vehpmj30u01t0ael.jpg" width=250/> <img src="https://tva1.sinaimg.cn/large/006tNbRwgy1garm89f8dzj30u01t01kx.jpg" width=250/> <img src="https://tva1.sinaimg.cn/large/006tNbRwgy1garm848n8tj30u01t0nat.jpg" width=250/>




<br />

## License

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