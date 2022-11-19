package com.yingenus.feature_mycart.domain.usecase

import com.yingenus.core.Result
import com.yingenus.feature_mycart.domain.dto.Cart
import com.yingenus.feature_mycart.domain.repository.CartRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import javax.inject.Inject

internal class GetMyCartUsecaseImpl @Inject constructor(
    private val cartRepository: CartRepository,
    ): GetMyCartUsecase {
    override suspend fun invoke(): Result<Cart> {
        return cartRepository.getMyCart()
    }
}