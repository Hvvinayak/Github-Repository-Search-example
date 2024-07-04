package com.example.githubrepository.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.githubrepository.model.Contributor

@Entity(
    tableName = "contributors",
    foreignKeys = [ForeignKey(entity = RepoEntity::class, parentColumns = ["uuid"], childColumns = ["repo_uuid"], onDelete = ForeignKey.CASCADE)]
)
data class ContributorEntity(
    @PrimaryKey
    val uuid: String,
    @ColumnInfo(name = "repo_uuid")
    val repoUuid: String,
    val name: String,
    val avatarUrl: String?
) {
    fun toDomainModel() = Contributor(
        uuid = uuid,
        name = name,
        avatarUrl = avatarUrl
    )
}