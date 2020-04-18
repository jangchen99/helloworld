package com.example.howltwostagram2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.GoogleSignatureVerifier
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    var auth: FirebaseAuth? = null
    var googleSignInClient: GoogleSignInClient? = null
    var GOOGLE_LOGIN_CODE = 9001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        email_login_btn.setOnClickListener {
            signinAndSignup() //이거 누를떄 마다 밑에 fun 실행되도록 하는 것.
        }
        google_sign_in_btn.setOnClickListener {
            googlelogin()
        }
        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))//google로그인에 접근할 수 있는 키 같은 것
            .requestEmail()//googleservicejson에 다 이런 내용들이 저장되어있다고 함.
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

    }
    fun googlelogin() {
        var signInIntent = googleSignInClient?.signInIntent
        startActivityForResult(signInIntent, GOOGLE_LOGIN_CODE)

    }
//2nd 스텝
        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)//구글 정보가 여기로 넘어옴 창이 꺼지면서 이전 정보 넘어오도록
        //구글 정보만 넘어오도록 필터링을 해줘야함.
        if (requestCode == GOOGLE_LOGIN_CODE) {
            var result = Auth.GoogleSignInApi.getSignInResultFromIntent(data) /// 문제
            if (result.isSuccess) {
                var account = result.signInAccount //성공시 firebase에 정보를 넘겨주는 단계
                firebaseAuthWithGoogle(account!!)  // 여기서 내가 fun을 또 지정해줘야함.

            }
        }
    }
    fun firebaseAuthWithGoogle(account: GoogleSignInAccount){
        var credential = GoogleAuthProvider.getCredential(account.idToken,null)
        auth?.signInWithCredential(credential)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    moveMainPage(auth?.currentUser)
                    Toast.makeText(this, "로그인 성공", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

    fun signinAndSignup() {
        auth?.createUserWithEmailAndPassword(email_et.text.toString(), password_et.text.toString())
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "아이디 생성이 완료되었습니다.", Toast.LENGTH_LONG).show()

                } else if (task.exception?.message == null) {
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                } else {
                    signinEmail()
                    //login해주는 코드 이미 id 있고, 에러도 안나는 상황이면. 이메일로그인!
                }
            }
    }

    fun signinEmail() {
        auth?.signInWithEmailAndPassword(email_et.text.toString(), password_et.text.toString())
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    moveMainPage(auth?.currentUser)
                    Toast.makeText(this, "로그인 성공", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                }
            }
    }
    //코틀린은 변수 선언자가 뒤로(여기선 FIrebaseUser)
    fun moveMainPage(user: FirebaseUser?) {
        if (user != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }



 }

