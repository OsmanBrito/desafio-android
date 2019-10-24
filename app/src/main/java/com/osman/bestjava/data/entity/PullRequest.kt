package com.osman.bestjava.data.entity

import androidx.room.Entity
import androidx.room.TypeConverters
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.osman.bestjava.data.converter.OwnerConverter
import java.io.Serializable

//Cada item da lista deve exibir Nome / Foto do autor do PR, Título do PR, Data do PR e Body do PR


@Entity(
    tableName = "pullrequest",
    primaryKeys = ["id"]
)
data class PullRequest(
    @Expose
    val id: Long,
    @Expose
    val title: String,
    @SerializedName("updated_at")
    val updateAt: String,
    @Expose
    val body: String,
    @TypeConverters(OwnerConverter::class)
    val user: Owner
) : Serializable