package cat.tiki.saas.user

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cat.tiki.saas.R
import cn.leancloud.AVUser
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_register.*


/**
 * Created by Yifa Liang on 2019-11-09.
 */
class RegisterActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            R.layout.activity_register
        )

        val userEt = activity_register_user_et
        val pwdEt = activity_register_pwd_et
        val registerBtn = activity_register_register_btn

        registerBtn?.setOnClickListener {
            val userStr = userEt?.editableText.toString()
            val pwdStr = pwdEt?.editableText.toString()
            onRegister(userStr, pwdStr)
        }
    }

    private fun onRegister(userStr: String, pwdStr: String) {
        // 创建实例
        val user = AVUser()

        // 等同于 user.put("username", "Tom")
        user.username = userStr
        user.password = pwdStr

        // 可选
//        user.email = "tom@leancloud.rocks"
//        user.mobilePhoneNumber = "+8618200008888"

        // 设置其他属性的方法跟 AVObject 一样
//        user.put("gender", "secret")

        user.signUpInBackground()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<AVUser> {
            override fun onSubscribe(disposable: Disposable) {}
            override fun onNext(user: AVUser) {
                // 注册成功
                println("注册成功。objectId：" + user.objectId)
            }

            override fun onError(throwable: Throwable) {
                // 注册失败（通常是因为用户名已被使用）
                println(throwable?.message)
            }

            override fun onComplete() {}
        })
    }

}