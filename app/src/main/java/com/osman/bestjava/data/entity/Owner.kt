package com.osman.bestjava.data.entity

import androidx.room.Entity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(
    tableName = "owner",
    primaryKeys = ["id"]
)
data class Owner(
    @Expose
    val id: Long,
    @SerializedName("avatar_url")
    val photo: String,
    @Expose
    val login: String
) : Serializable