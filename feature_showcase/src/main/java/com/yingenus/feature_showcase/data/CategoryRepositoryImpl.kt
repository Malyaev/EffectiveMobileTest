package com.yingenus.feature_showcase.data

import android.content.Context
import android.content.res.AssetManager
import android.net.Uri
import com.yingenus.feature_showcase.R
import com.yingenus.feature_showcase.domain.CategoryRepository
import com.yingenus.feature_showcase.domain.dto.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class CategoryRepositoryImpl @Inject constructor(private val context: Context): CategoryRepository {

    private companion object{
        fun fileUri(name: String)= Uri.parse("file:///android_asset/$name")
    }


    override suspend fun getCategories(): List<Category> {
        return listOf(
            Category(fileUri("phone.svg"),context.getString(R.string.phones)),
            Category(fileUri("computer.svg"),context.getString(R.string.computer)),
            Category(fileUri("helth.svg"),context.getString(R.string.health)),
            Category(fileUri("books.svg"),context.getString(R.string.books))
        )

    }

}