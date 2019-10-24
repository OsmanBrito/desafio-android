package com.osman.bestjava.data.entity

import androidx.room.Entity
import androidx.room.TypeConverters
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.osman.bestjava.data.converter.OwnerConverter
import java.io.Serializable


//Cada repositório deve exibir Nome do repositório, Descrição do Repositório, Nome / Foto do autor, Número de Stars, Número de Forks
@Entity(
    tableName = "repository",
    primaryKeys = ["id"]
)
data class Repository(
    @Expose
    val id: Long,
    @Expose
    val name: String,
    @Expose
    val description: String?,
    @SerializedName("stargazers_count")
    val stargazersCount: Int,
    @SerializedName("forks_count")
    val forksCount: Int,
    @TypeConverters(OwnerConverter::class)
    val owner: Owner
) : Serializable