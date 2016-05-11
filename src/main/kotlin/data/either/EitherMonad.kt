package data.either

import K1
import type.monad.Monad

interface EitherMonad<S>: Monad<K1<Either.µ, S>>, EitherApplicative<S>, EitherFunctor<S> {
  override fun <A> join(v: K1<K1<Either.µ, S>, K1<K1<Either.µ, S>, A>>): Either<S, A> {
    val either = Either.narrow(v)
    return when (either) {
      is Either.Right -> Either.narrow(either.value)
      is Either.Left -> Either.Left(either.value)
    }
  }

  override fun <A, B> bind(f: (A) -> K1<K1<Either.µ, S>, B>, v: K1<K1<Either.µ, S>, A>): Either<S, B> {
    return Either.narrow(super.bind(f, v))
  }
}