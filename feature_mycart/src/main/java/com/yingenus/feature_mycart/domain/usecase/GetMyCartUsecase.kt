package com.yingenus.feature_mycart.domain.usecase

import com.yingenus.feature_mycart.domain.dto.Cart

internal interface GetMyCartUsecase {
    suspend fun invoke(): com.yingenus.core.Result<Cart>
}