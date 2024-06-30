package com.example.sqlitekullanimitekrarbu

import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable

@IgnoreExtraProperties
data class kelimeler(var kelime_id:String? = ""
                     ,var ingilizce:String?="",
                     var turkce:String? = "") : Serializable {
}