package com.example.healthapp.login

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.healthapp.R
import com.example.healthapp.bbs.WorkActivity
import com.example.healthapp.fragment.MainFragment
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause.*
import com.kakao.sdk.user.UserApiClient
import kotlin.system.exitProcess
import kotlinx.android.synthetic.main.login_activity.*

class LoginActivity : AppCompatActivity() {

    private val googleSignIntent by lazy {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        GoogleSignIn.getClient(this, gso).signInIntent
    }

    companion object {
        const val RESULT_CODE =10
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        getSupportActionBar()!!.setIcon(R.drawable.appbar)
        getSupportActionBar()!!.setDisplayUseLogoEnabled(true)
        getSupportActionBar()!!.setDisplayShowHomeEnabled(true)
        getSupportActionBar()!!.setElevation(0F);

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        val loginEditId = findViewById<EditText>(R.id.loginEditId)
        val loginEditPwd = findViewById<EditText>(R.id.loginEditPwd)

        val loginBtn = findViewById<Button>(R.id.loginBtn)

        // ???????????????
        val auto = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE)
        val autoLoginUserId = auto.getString("userId", null)
        val autoLoginUserPwd = auto.getString("userPwd", null)

        if(autoLoginUserId != null && autoLoginUserPwd != null){
            val dto = LoginMemberDao.getInstance().login_M(LoginMemberDto(autoLoginUserId,autoLoginUserPwd,"","","",0,"","",3,"","", 0))

            if(dto != null){
                LoginMemberDao.user=dto

                Toast.makeText(this, "${dto.nickname}??? ???????????????", Toast.LENGTH_LONG).show()
                WorkActivity.selectedFragment = 0
                val i = Intent(this, MainFragment::class.java)
                startActivity(i)
            }else{
                Toast.makeText(this, "??????????????? ?????? ????????? ??????????????????.", Toast.LENGTH_LONG).show()
            }
        }

        // ???????????? ?????????
        loginBtn.setOnClickListener {
            val id = loginEditId.text.toString().trim()
            val pwd = loginEditPwd.text.toString().trim()
            val autoLogin = findViewById<CheckBox>(R.id.autoLogin)

            val dto = LoginMemberDao.getInstance().login_M(LoginMemberDto(id, pwd, "", "", "", 0, "", "", 0, "","", 0))

            // ?????????????????? ????????????????????? ??? ????????? ????????? ????????? ??????(??? ????????? ??????)
            if(autoLogin.isChecked){
                val auto = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE)
                val autoLoginEdit = auto.edit()

                autoLoginEdit.putString("userId", id)
                autoLoginEdit.putString("userPwd", pwd)
                autoLoginEdit.commit()
            }

            if (dto != null) {
                if(dto.auth == 3 && dto.del == 0){
                    LoginMemberDao.user = dto

                    Toast.makeText(this, "${dto.nickname}??? ???????????????", Toast.LENGTH_LONG).show()
                    WorkActivity.selectedFragment = 0
                    val i = Intent(this, MainFragment::class.java)
                    startActivity(i)
                } else if(dto.auth == 1){
                    LoginMemberDao.user = dto

                    Toast.makeText(this, "????????? ?????????", Toast.LENGTH_LONG).show()
                    WorkActivity.selectedFragment = 0
                    val a = Intent(this, MainFragment::class.java)
                    startActivity(a)
                } else {
                    Toast.makeText(this, "???????????? ??????????????? ???????????????", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "???????????? ??????????????? ???????????????", Toast.LENGTH_LONG).show()
            }

        }

        // ????????? API ????????? ????????????
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                when {
                    error.toString() == AccessDenied.toString() -> {
                        Toast.makeText(this, "????????? ?????? ???????????????(?????? ??????)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == InvalidClient.toString() -> {
                        Toast.makeText(this, "???????????? ?????? ????????????", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == InvalidGrant.toString() -> {
                        Toast.makeText(this, "?????? ????????? ???????????? ?????? ????????? ??? ?????? ???????????????", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == InvalidRequest.toString() -> {
                        Toast.makeText(this, "?????? ???????????? ???????????????", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == InvalidScope.toString() -> {
                        Toast.makeText(this, "???????????? ?????? scope ID?????????", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == Misconfigured.toString() -> {
                        Toast.makeText(this, "????????? ???????????? ????????????(android key hash)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == ServerError.toString() -> {
                        Toast.makeText(this, "?????? ?????? ???????????????", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == Unauthorized.toString() -> {
                        Toast.makeText(this, "?????? ?????? ????????? ????????????", Toast.LENGTH_SHORT).show()
                    }
                    else -> { // Unknown
                        Toast.makeText(this, "?????????????????????.", Toast.LENGTH_SHORT).show()
                        Log.d("~~~~error", error.toString())
                    }
                }
            }

            // ????????? ???????????? ????????? ????????? ????????? ??????????????? ??????????????????.
            else if (token != null) {
                Toast.makeText(this, "???????????? ?????????????????????.", Toast.LENGTH_SHORT).show()
                UserApiClient.instance.me { user, error ->
                    if(error!=null){
                        Log.e(TAG,"????????? ?????? ?????? ??????", error)
                    }
                    else if(user != null) {
                        Log.i(TAG, "????????? ?????? ?????? ??????+${user.id}")
                        Log.i(TAG, "????????? ?????? ?????? ??????+${user.kakaoAccount!!.profile!!.nickname}")
                        Log.i(TAG, "????????? ?????????${user.kakaoAccount!!.email}")

                        val kakaoName = user.kakaoAccount!!.profile!!.nickname
                        val kakaoNum = user.id.toString()
                        val email = user.kakaoAccount!!.email

                        val checkId = LoginMemberDao.getInstance().getId_M(LoginMemberDto(kakaoNum,"","","","",0,"","",0,"","", 0))
                        if(checkId!="n"){
                            val kakaoRegi = LoginMemberDao.getInstance().register_M(LoginMemberDto(kakaoNum,kakaoNum,kakaoName,kakaoName," ",0,email," ",4,"","", 0))
                            if(kakaoRegi == "y"){
                                val kakaoLogin = LoginMemberDao.getInstance().login_M(LoginMemberDto(kakaoNum,kakaoNum,kakaoName,kakaoName," ",0,email," ",4,"","", 0))
                                LoginMemberDao.user = kakaoLogin
                                Toast.makeText(this, "${kakaoName}??? ???????????????.", Toast.LENGTH_SHORT).show()
                                WorkActivity.selectedFragment = 0
                                val i = Intent(this, MainFragment::class.java)
                                startActivity(i)
                            }else{
                                Toast.makeText(this, "????????? ????????? ??????", Toast.LENGTH_SHORT).show()
                            }
                        }else{
                            val kakaoLogin = LoginMemberDao.getInstance().login_M(LoginMemberDto(kakaoNum,kakaoNum,kakaoName,kakaoName," ",0,email," ",4,"","", 0))
                            if(kakaoLogin != null){
                                LoginMemberDao.user = kakaoLogin
                                Toast.makeText(this, "${kakaoName}??? ???????????????.", Toast.LENGTH_SHORT).show()
                                WorkActivity.selectedFragment = 0
                                val i = Intent(this, MainFragment::class.java)
                                startActivity(i)
                            }else{
                                Toast.makeText(this, "????????? ????????? ??????", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }

        // ????????? ?????????
        kakao_login_button.setOnClickListener{
            if(UserApiClient.instance.isKakaoTalkLoginAvailable(this)){
                UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)
            }else{
                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }

        val moveRegi = findViewById<TextView>(R.id.moveRegi)

        // ?????????????????? ??????
        moveRegi.setOnClickListener{
            val i = Intent(this, RegiActivity::class.java)
            startActivity(i)
        }

        val moveFindIdPwd = findViewById<TextView>(R.id.moveFIndIdPwd)

        moveFindIdPwd.setOnClickListener{
            val i = Intent(this, FindActivity::class.java)
            startActivity(i)
        }

        sign_in_button.setOnClickListener {
            startActivityForResult(googleSignIntent, RESULT_CODE)
        }


    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == RESULT_OK && requestCode == RESULT_CODE) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)

            result?.let{
                if(it.isSuccess){
                    val googleUserName = it.signInAccount?.displayName
                    val googleUserEmail = it.signInAccount?.email
                    val googleUserIdtoken = it.signInAccount?.id
                    val googleUserNickname = it.signInAccount?.givenName

                    Log.i("email", it.signInAccount?.email!!) //?????????
                    Log.i("name", it.signInAccount?.displayName!!) //??????
                    Log.i("idtoken", it.signInAccount?.id!!) // ??????
                    Log.i("nickname", it.signInAccount?.givenName!!) //?????????

                }else{
                    Log.e("Value", "error")
                }
            }

            if(result!!.isSuccess){
                firebaseLogin(result.signInAccount!!)
            }
        }
    }

    // ????????????????????? ?????????????????? ?????????
    private fun firebaseLogin(googleAccount: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(googleAccount.idToken, null)

        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
            if(it.isSuccessful) {
                val googleUserName = it.result.user?.displayName
                val googleUserEmail = it.result.user?.email
                val googleUserIdtoken = it.result.user?.uid
                Log.i("@@@@@?????????????????????????????????",it.result?.user?.displayName!!)
                Log.i("@@@@@????????????????????????????????????",it.result?.user?.email!!)
                Log.i("@@@@@uid",it.result?.user?.uid!!)

                val checkId = LoginMemberDao.getInstance().getId_M(LoginMemberDto(googleUserIdtoken,"","","","",0,"","",0,"","", 0))
                if(checkId != "n"){
                    val googleRegi = LoginMemberDao.getInstance().register_M(LoginMemberDto(googleUserIdtoken,googleUserIdtoken,googleUserName,googleUserName," ",0,googleUserEmail," ",5,"","", 0))
                    if(googleRegi == "y"){
                        val googleLogin = LoginMemberDao.getInstance().login_M(LoginMemberDto(googleUserIdtoken,googleUserIdtoken,googleUserName,googleUserName," ",0,googleUserEmail," ",5,"","", 0))
                        LoginMemberDao.user = googleLogin
                        Toast.makeText(this,"${googleUserName}??? ???????????????.",Toast.LENGTH_LONG).show()
                        WorkActivity.selectedFragment = 0
                        val i = Intent(this,WorkActivity::class.java)
                        startActivity(i)
                    }else{
                        Toast.makeText(this,"?????? ????????? ??????",Toast.LENGTH_LONG).show()
                    }
                }else{
                    val googleLogin = LoginMemberDao.getInstance().login_M(LoginMemberDto(googleUserIdtoken,googleUserIdtoken,"","","",0,"","",0,"","", 0))
                    if(googleLogin != null){
                        LoginMemberDao.user = googleLogin
                        Toast.makeText(this,"${googleUserName}??? ???????????????.",Toast.LENGTH_LONG).show()
                        WorkActivity.selectedFragment = 0
                        val i = Intent(this, WorkActivity::class.java)
                        startActivity(i)
                    }else{
                        Toast.makeText(this,"?????? ????????? ??????",Toast.LENGTH_LONG).show()
                    }
                }

            }else {

            }
        }.addOnFailureListener {

        }
    }

    private var backPressedTime: Long = 0

    override fun onBackPressed() {
        Log.d("TAG", "????????????")

        if(System.currentTimeMillis() - backPressedTime >= 1500){
            // ?????? ?????? ?????????
            backPressedTime = System.currentTimeMillis()
            Toast.makeText(this, "'??????' ????????? ?????? ??? ???????????? ?????? ???????????????.", Toast.LENGTH_SHORT).show()
        }else{
            ActivityCompat.finishAffinity(this)
            System.runFinalization()
            exitProcess(0)
        }
    }

}


