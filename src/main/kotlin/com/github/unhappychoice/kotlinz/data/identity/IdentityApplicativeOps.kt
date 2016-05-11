package com.github.unhappychoice.kotlinz.data.identity

import com.github.unhappychoice.kotlinz.K1
import com.github.unhappychoice.kotlinz.curried
import com.github.unhappychoice.kotlinz.type.applicative.ApplicativeOps

interface IdentityApplicativeOps: ApplicativeOps<Identity.µ>, IdentityMonad {
  override fun <A, B> liftA(f: (A) -> B): (K1<Identity.µ, A>) -> Identity<B> {
    return { a -> Identity.narrow(a) ap Identity.pure(f) }
  }

  override fun <A, B, C> liftA2(f: (A, B) -> C): (K1<Identity.µ, A>, K1<Identity.µ, B>) -> Identity<C> {
    return { a1, a2 ->
      val i1 = Identity.narrow(a1)
      val i2 = Identity.narrow(a2)
      i2 ap (i1 fmap f.curried())
    }
  }

  override fun <A, B, C, D> liftA3(f: (A, B, C) -> D): (K1<Identity.µ, A>, K1<Identity.µ, B>, K1<Identity.µ, C>) -> Identity<D> {
    return { a1, a2, a3 ->
      val i1 = Identity.narrow(a1)
      val i2 = Identity.narrow(a2)
      val i3 = Identity.narrow(a3)
      i3 ap (i2 ap (i1 fmap f.curried()))
    }
  }
}