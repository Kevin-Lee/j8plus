/**
 * Copyright 2014 Lee, Seong Hyun (Kevin)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package j8plus.types.annoying;

import j8plus.types.Runner;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author Lee, Seong Hyun (Kevin)
 * @since 2015-05-24
 */
public class AnnoyingFuns {

  /**
   * Returns Function which does exactly the same as the given AnnoyingFunction but without throwing a checked exception that that annoying one might throw.
   *
   * e.g.)
   * <pre>
   *   &lt;E, T&gt; T doItWithAnnoyance(E whatEver) throws Exception {
   *     throw new Exception("Annoying exception!");
   *   }
   *
   *   &lt;E, T&gt; T doSomething(Function&lt;E, T&gt; function) {
   *     final E someInput = getSomeInput();
   *     return function.apply(someInput);
   *   }
   *
   *   // ...
   *   doSomething(this::doItWithAnnoyance);    // compile time error: Unhandled exception: java.lang.Exception
   *   doSomething(x -&gt; doItWithAnnoyance(x));  // compile time error: Unhandled exception: java.lang.Exception
   *
   *   // but if you use shh(),
   *   doSomething(shh(this::doItWithAnnoyance));    // NO compile time error anymore
   *   doSomething(shh(x -&gt; doItWithAnnoyance(x)));  // NO compile time error anymore
   * </pre>
   *
   * @param function The given AnnoyingFunction which may or may not throw a checked Exception.
   * @param <T> the input type of the function
   * @param <R> the result type of the function
   * @return Function which can handle any checked Exception thrown by the given AnnoyingFunction and wrap it with an unchecked exception that is RuntimeException.
   */
  public static <T, R> Function<T, R> shh(final AnnoyingFunction<T, R> function) {
    Objects.requireNonNull(function, "The function cannot be null.");
    return object -> {
      try {
        return function.apply(object);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    };
  }

  /**
   * Returns BiFunction which does exactly the same as the given AnnoyingBiFunction but without throwing a checked exception that that annoying one might throw.
   *
   * e.g.)
   * <pre>
   *   &lt;T, U, R&gt; R doItWithAnnoyance2(T input1, U input2) throws Exception {
   *     throw new Exception("Annoying exception!");
   *   }
   *
   *   &lt;T, U, R&gt; R doSomething(BiFunction&lt;T, U, R&gt; function) {
   *     // ...
   *     return function.apply(someInput1, someInput2);
   *   }
   *
   *   doSomething(shh(this::doItWithAnnoyance2));            // No compile time error
   *   doSomething(shh((x, y) -&gt; doItWithAnnoyance2(x, y)));  // No compile time error
   * </pre>
   *
   * @param function The given AnnoyingBiFunction which may or may not throw a checked Exception.
   * @param <T> the input1 type of the function
   * @param <U> the input2 type of the function
   * @param <R> the result type of the function
   * @return BiFunction which can handle any checked Exception thrown by the given AnnoyingBiFunction and wrap it with an unchecked exception that is RuntimeException.
   */
  public static <T, U, R> BiFunction<T, U, R> shh(final AnnoyingBiFunction<T, U, R> function) {
    Objects.requireNonNull(function, "The function cannot be null.");
    return (input1, input2) -> {
      try {
        return function.apply(input1, input2);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    };
  }

  public static Runner shh(final AnnoyingRunnable annoyingRunnable) {
    Objects.requireNonNull(annoyingRunnable, "The given AnnoyingRunner cannot be null.");
    return () -> {
      try {
        annoyingRunnable.run();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    };
  }

  public static <T> Supplier<T> shh(final AnnoyingSupplier<T> annoyingSupplier) {
    Objects.requireNonNull(annoyingSupplier, "The given AnnoyingSupplier cannot be null.");
    return () -> {
      try {
        return annoyingSupplier.get();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    };
  }

  public static class consumer {

    public static <T> Consumer<T> shh(final AnnoyingConsumer<T> annoyingConsumer) {
      Objects.requireNonNull(annoyingConsumer, "The given AnnoyingConsumer cannot be null.");
      return input -> {
        try {
          annoyingConsumer.accept(input);
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
      };
    }

  }

  public static class predicate {

    /**
     * Returns Predicate which does exactly the same as the given AnnoyingPredicate but without throwing a checked exception that the annoying one might throw. Instead, it may throw an unchecked exception (i.e. RuntimeException)
     *
     * e.g.)
     * <pre>
     *   &lt;E&gt; boolean checkWithAnnoyance(E whatEver) throws Exception {
     *     throw new Exception("Annoying exception!");
     *   }
     *
     *   &lt;E&gt; boolean checkSomething(Predicate&lt;E&gt; predicate) {
     *     final E someInput = getSomeInput();
     *     return predicate.test(someInput);
     *   }
     *
     *   // ...
     *   checkSomething(this::checkWithAnnoyance);       // compile time error: Unhandled exception: java.lang.Exception
     *   checkSomething(x -&gt; checkWithAnnoyance(x));  // compile time error: Unhandled exception: java.lang.Exception
     *
     *   // but if you use shh(),
     *   checkSomething(shh(this::checkWithAnnoyance));       // NO compile time error anymore
     *   checkSomething(shh(x -&gt; checkWithAnnoyance(x)));  // NO compile time error anymore
     * </pre>
     *
     * @param predicate The given AnnoyingPredicate which may or may not throw a checked Exception.
     * @param <T> the input type of the predicate
     * @return Predicate which can handle any checked Exception thrown by the given AnnoyingFunction and wrap it with an unchecked exception that is RuntimeException.
     */
    public static <T> Predicate<T> shh(final AnnoyingPredicate<T> predicate) {
      Objects.requireNonNull(predicate, "The predicate cannot be null.");
      return value -> {
        try {
          return predicate.test(value);
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
      };
    }

    /**
     * Returns BiPredicate which does exactly the same as the given AnnoyingBiPredicate but without throwing a checked exception that the annoying one might throw.
     *
     * e.g.)
     * <pre>
     *   &lt;T, U&gt; boolean checkWithAnnoyance2(T input1, U input2) throws Exception {
     *     throw new Exception("Annoying exception!");
     *   }
     *
     *   &lt;T, U&gt; boolean doSomething(BiPredicate&lt;T, U&gt; predicate) {
     *     // ...
     *     return predicate.test(someInput1, someInput2);
     *   }
     *
     *   doSomething(shh(this::checkWithAnnoyance2));               // No compile time error
     *   doSomething(shh((x, y) -&gt; checkWithAnnoyance2(x, y)));  // No compile time error
     * </pre>
     *
     * @param predicate The given AnnoyingBiFunction which may or may not throw a checked Exception.
     * @param <T> the input1 type of the predicate
     * @param <U> the input2 type of the predicate
     * @return BiPredicate which can handle any checked Exception thrown by the given AnnoyingBiPredicate and wrap it with an unchecked exception that is RuntimeException.
     */
    public static <T, U> BiPredicate<T, U> shh(final AnnoyingBiPredicate<T, U> predicate) {
      Objects.requireNonNull(predicate, "The predicate cannot be null.");
      return (input1, input2) -> {
        try {
          return predicate.test(input1, input2);
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
      };
    }
  }

  public static <T, EX extends Throwable> T getOrRethrowCause(
    final Class<EX> expectedCause, final Supplier<T> supplier
  ) throws EX {
    try {
      return supplier.get();
    } catch (final Throwable throwable) {
      final Throwable cause = throwable.getCause();
      if (expectedCause.isAssignableFrom(cause.getClass())) {
        @SuppressWarnings("unchecked")
        final EX causeEx = (EX) cause;
        throw causeEx;
      } else {
        throw throwable;
      }
    }
  }

  public static <EX extends Throwable> void runOrRethrowCause(
    final Class<EX> expectedCause, final Runner runner
  ) throws EX {
    try {
      runner.run();
    } catch (final Throwable throwable) {
      final Throwable cause = throwable.getCause();
      if (expectedCause.isAssignableFrom(cause.getClass())) {
        @SuppressWarnings("unchecked")
        final EX causeEx = (EX) cause;
        throw causeEx;
      } else {
        throw throwable;
      }
    }
  }

  public static <T> T getOrRethrowCause(
    final Predicate<Throwable> rethrowableCause, final Supplier<T> supplier
  ) throws Throwable {
    try {
      return supplier.get();
    } catch (final Throwable throwable) {
      final Throwable cause = throwable.getCause();
      if (rethrowableCause.test(cause)) {
        throw cause;
      } else {
        throw throwable;
      }
    }
  }

  public static void runOrRethrowCause(
    final Predicate<Throwable> rethrowableCause, final Runner runner
  ) throws Throwable {
    try {
      runner.run();
    } catch (final Throwable throwable) {
      final Throwable cause = throwable.getCause();
      if (rethrowableCause.test(cause)) {
        throw cause;
      } else {
        throw throwable;
      }
    }
  }

}
