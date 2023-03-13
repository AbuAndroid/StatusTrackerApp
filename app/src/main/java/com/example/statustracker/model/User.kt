package com.example.statustracker.model
import android.util.Log
import com.auth0.android.jwt.JWT

data class User(val idToken:String? = null){

    private val TAG = "User"

    var id=""
    var nane=""
    var email=""
    var emailVerified=""
    var picture=""
    var updateAt=""

    init {
        if(idToken!=null){
            try {
                val jwt = JWT(idToken)

                id = jwt.subject ?:""
                nane = jwt.getClaim("name").asString() ?:""
                email = jwt.getClaim("email").asString() ?:""
                emailVerified = jwt.getClaim("email_verified").asString() ?:""
                picture = jwt.getClaim("picture").asString() ?:""
                updateAt = jwt.getClaim("updated_at").asString() ?:""
            }catch (error:com.auth0.android.jwt.DecodeException){
                Log.e("decodeex", "Error occurred trying to decode JWT: ${error.toString()} ")
            }
        }else{
            Log.e("name","User is logged out - instantiating empty User object")
        }
    }
}
