package com.example.githubrepository.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

abstract class FlowUseCase<in P, R> {
    operator fun invoke(parameters: P): Flow<Result<R>> = execute(parameters)
        .catch { e -> Result.Error(Exception(e)) }

    protected abstract fun execute(parameters: P): Flow<Result<R>>
}