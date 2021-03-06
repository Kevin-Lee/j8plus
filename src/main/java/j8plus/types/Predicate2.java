/**
 * Copyright 2014 Lee, Seong Hyun (Kevin)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package j8plus.types;

import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-11-13)
 * @param <T1>
 *          input1
 * @param <T2>
 *          input2
 */
@FunctionalInterface
public interface Predicate2<T1, T2> extends BiPredicate<T1, T2> {

  /**
   * valuates this predicate on the given arguments.
   *
   * @param input1 the first input argument
   * @param input2 the second input argument
   * @return {@code true} if the input arguments match the predicate,
   * otherwise {@code false}
   */
  @Override
  boolean test(final T1 input1, final T2 input2);

  /**
   * Given this PredicateN, it returns a curried Predicate(N-1) where the given first input value is set.
   *
   * @param t1 the first input value.
   * @return Predicate(N-1) where N is from this PredicateN.
   * If this predicate is Predicate10, it returns the curried Predicate9.
   * If it is Predicate3, it returns the curried Predicate2 (not BiPredicate).
   * If it is Predicate2, it returns the curried Predicate.
   */
  default Predicate<T2> curried(final T1 t1) {
    return (t2) -> test(t1, t2);
  }

  /**
   * Returns a composed predicate that represents a short-circuiting logical AND of this predicate and another. When
   * evaluating the composed predicate, if this predicate is {@code false}, then the {@code other} predicate is not
   * evaluated.
   *
   * <p>
   * Any exceptions thrown during evaluation of either predicate are relayed to the caller; if evaluation of this
   * predicate throws an exception, the {@code other} predicate will not be evaluated.
   *
   * @param other
   *          a predicate that will be logically-ANDed with this predicate
   * @return a composed predicate that represents the short-circuiting logical AND of this predicate and the
   *         {@code other} predicate
   * @throws NullPointerException
   *           if other is null
   */
  @Override
  default Predicate2<T1, T2> and(final BiPredicate<? super T1, ? super T2> other) {
    Objects.requireNonNull(other);
    /* @formatter:off */
    return
        (final T1 input1, final T2 input2) ->
          test(input1, input2) &&
          other.test(input1, input2);
    /* @formatter:on */
  }

  /**
   * Returns a predicate that represents the logical negation of this predicate.
   *
   * @return a predicate that represents the logical negation of this predicate
   */
  @Override
  default Predicate2<T1, T2> negate() {
    return (final T1 input1, final T2 input2) -> !test(input1, input2);
  }

  /**
   * Returns a composed predicate that represents a short-circuiting logical OR of this predicate and another. When
   * evaluating the composed predicate, if this predicate is {@code true}, then the {@code other} predicate is not
   * evaluated.
   *
   * <p>
   * Any exceptions thrown during evaluation of either predicate are relayed to the caller; if evaluation of this
   * predicate throws an exception, the {@code other} predicate will not be evaluated.
   *
   * @param other
   *          a predicate that will be logically-ORed with this predicate
   * @return a composed predicate that represents the short-circuiting logical OR of this predicate and the
   *         {@code other} predicate
   * @throws NullPointerException
   *           if other is null
   */
  @Override
  default Predicate2<T1, T2> or(final BiPredicate<? super T1, ? super T2> other) {
    Objects.requireNonNull(other);
    /* @formatter:off */
    return
        (final T1 input1, final T2 input2) ->
          test(input1, input2) ||
          other.test(input1, input2);
    /* @formatter:on */
  }
}
