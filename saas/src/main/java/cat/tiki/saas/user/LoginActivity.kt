package cat.tiki.saas.user

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import cat.tiki.saas.R
import cn.leancloud.AVUser
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Created by Yifa Liang on 2019-11-09.
 */
class LoginActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            R.layout.activity_login
        )

        val userEt = activity_login_user_et
        val pwdEt = activity_login_pwd_et
        val loginBtn = activity_login_login_btn

        loginBtn.setOnClickListener {
            val userStr = userEt?.editableText.toString()
            val pwdStr = pwdEt?.editableText.toString()
            onLogin(userStr, pwdStr)
        }
    }

    private fun onLogin(userStr: String, pwdStr: String) {
        AVUser.logIn(userStr, pwdStr)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<AVUser> {
            override fun onSubscribe(disposable: Disposable) {}
            override fun onNext(user: AVUser) {
                // 登录成功
                print(user)
            }

            override fun onError(throwable: Throwable) {
                // 登录失败（可能是密码错误）
                Log.e("error: ", throwable?.toString())
            }

            override fun onComplete() {}
        })
    }

}