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
import java.util.function.Function;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-11-13)
 * @param <T1>
 *          input1
 * @param <T2>
 *          input2
 * @param <T3>
 *          input3
 * @param <T4>
 *          input4
 * @param <R>
 *          result
 */
@FunctionalInterface
public interface Function4<T1, T2, T3, T4, R> {
  R apply(T1 input1, T2 input2, T3 input3, T4 input4);

  /**
   * Given this FunctionN, it returns a curried Function(N-1) where the given first input value is set.
   *
   * @param t1 the first input value.
   * @return Function(N-1) where N is from this FunctionN.
   * If this function is Function10, it returns the curried Function9.
   * If it is Function3, it returns the curried Function2 (not BiFunction).
   * If it is Function2, it returns the curried Function.
   */
  default Function3<T2, T3, T4, R> curried(final T1 t1) {
    return (t2, t3, t4) -> apply(t1, t2, t3, t4);
  }

  /**
   * Returns a composed function that first applies this function to its input, and then applies the {@code after}
   * function to the result. If evaluation of either function throws an exception, it is relayed to the caller of the
   * composed function.
   *
   * @param <V>
   *          the type of output of the {@code after} function, and of the composed function
   * @param after
   *          the function to apply after this function is applied
   * @return a composed function that first applies this function and then applies the {@code after} function
   * @throws NullPointerException
   *           if after is null
   */
  default <V> Function4<T1, T2, T3, T4, V> andThen(final Function<? super R, ? extends V> after) {
    Objects.requireNonNull(after);
    return (input1, input2, input3, input4) -> after.apply(apply(input1, input2, input3, input4));
  }
}
