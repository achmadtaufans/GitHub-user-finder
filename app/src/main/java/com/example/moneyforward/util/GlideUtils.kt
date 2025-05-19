package com.example.moneyforward.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.moneyforward.R

object GlideUtils {
    fun setupGlide(ctx: Context, avatarUrl: String, placeHolder: ImageView) {
        Glide.with(ctx)
            .load(avatarUrl)
            .placeholder(R.color.teal_700)
            .into(placeHolder)
    }
}