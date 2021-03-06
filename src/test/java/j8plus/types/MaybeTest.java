package j8plus.types;

import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.Optional;

import static testosterone.Testosterone.test;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Kevin Lee
 * @since 2019-11-13
 */
public class MaybeTest {

  @Test
  public void left() {
    final String expectedValue = "Some error";
    test("Maybe.nothing", "Maybe.nothing should create Nothing")
      .when(() ->
        Maybe.<Integer>nothing()
      )
      .then(actual ->
        assertThat(actual).isEqualTo(Maybe.nothing())
      );
  }

  @Test
  public void just() {
    final Integer expectedValue = 999;
    test("Maybe.maybe", "Maybe.maybe should create Just")
      .when(() ->
        Maybe.maybe(expectedValue)
      )
      .then(actual ->
        assertThat(actual).isEqualTo(Maybe.maybe(expectedValue))
      );
  }

  @Test
  public void fromOptional_NonEmptyCase() {
    final Integer expectedValue = 999;
    final Optional<Integer> input = Optional.ofNullable(expectedValue);
    test("Maybe.fromOptional(nonEmpty)", "Maybe.fromOptional(Optional(someValue)) should create Just")
      .when(() ->
        Maybe.fromOptional(input)
      )
      .then(actual ->
        assertThat(actual).isEqualTo(Maybe.maybe(expectedValue))
      );
  }

  @Test
  public void fromOptional_EmptyCase() {
    final String expectedValue =  "Some error";
    final Optional<Integer> input = Optional.empty();
    test("Maybe.fromOptional(empty)", "Maybe.fromOptional(Optional.empty()) should create Nothing")
      .when(() ->
        Maybe.fromOptional(input)
      )
      .then(actual ->
        assertThat(actual).isEqualTo(Maybe.nothing())
      );
  }

  @Test
  public void fromEither_LeftCase() {
    final Either<String, Integer> input = Either.left("some error");
    test("Maybe.fromEither(left)", "Maybe.fromEither(Either.left(someValue)) should return Nothing")
      .when(() ->
        Maybe.fromEither(input)
      )
      .then(actual ->
        assertThat(actual).isEqualTo(Maybe.nothing())
      );
  }

  @Test
  public void fromEither_RightCase() {
    final Integer expectedValue =  999;
    final Either<String, Integer> input = Either.right(expectedValue);
    test("Maybe.fromEither(right)", "Maybe.fromEither(Either.right(someValue)) should return Just(someValue)")
      .when(() ->
        Maybe.fromEither(input)
      )
      .then(actual ->
        assertThat(actual).isEqualTo(Maybe.maybe(expectedValue))
      );
  }

  @Test
  public void isNothing_NothingCase() {
    test("Maybe.isNothing", "Maybe.isNothing should return true for Nothing")
      .when(() ->
        Maybe.<Integer>nothing()
      )
      .then(actual ->
        assertThat(actual.isNothing()).isTrue()
      );
  }

  @Test
  public void isNothing_JustCase() {
    test("Maybe.isNothing", "Maybe.isNothing should return false for Just")
      .when(() ->
        Maybe.<Integer>maybe(1)
      )
      .then(actual ->
        assertThat(actual.isNothing()).isFalse()
      );
  }

  @Test
  public void isJust_NothingCase() {
    test("Maybe.isJust", "Maybe.isJust should return false for Nothing")
      .when(() ->
        Maybe.<Integer>nothing()
      )
      .then(actual ->
        assertThat(actual.isJust()).isFalse()
      );
  }

  @Test
  public void isJust_JustCase() {
    test("Maybe.isJust", "Maybe.isJust should return true for Just")
      .when(() ->
        Maybe.<Integer>maybe(1)
      )
      .then(actual ->
        assertThat(actual.isJust()).isTrue()
      );
  }

  @Test
  public void map_NothingCase() {
    final String expectedValue = "Some error";
    test("Maybe.map on Nothing", "Maybe.map on Nothing should just return itself")
      .when(() ->
        Maybe.<Integer>nothing().map(i -> i * 2)
      )
      .then(actual ->
        assertThat(actual).isEqualTo(Maybe.nothing())
      );
  }

  @Test
  public void map_JustCase() {
    final Integer input = 999;
    final Integer additional = 111;
    final Integer expectedValue = input + additional;
    test("Maybe.map on Just", "Maybe.map on Just should apply the given function")
      .when(() ->
        Maybe.<Integer>maybe(input).map(i -> i + additional)
      )
      .then(actual ->
        assertThat(actual).isEqualTo(Maybe.maybe(expectedValue))
      );
  }

  @Test
  public void flatMap_NothingCase() {
    final String expectedValue = "Some error";
    test("Maybe.flatMap on Nothing", "Maybe.flatMap on Nothing should just return itself")
      .when(() ->
        Maybe.<Integer>nothing()
          .flatMap(i -> Maybe.maybe(i * 2))
      )
      .then(actual ->
        assertThat(actual).isEqualTo(Maybe.nothing())
      );
  }

  @Test
  public void flatMap_JustCase() {
    final Integer input = 999;
    final Integer additional = 111;
    final Integer expectedValue = input + additional;
    test("Maybe.flatMap on Just", "Maybe.flatMap on Just should apply the given function")
      .when(() ->
        Maybe.maybe(input).flatMap(i -> Maybe.maybe(i + additional))
      )
      .then(actual ->
        assertThat(actual).isEqualTo(Maybe.maybe(expectedValue))
      );
  }

  @Test
  public void orElse_Nothing_NothingCase() {
    test(
        "Maybe.nothing.orElse(Maybe.nothing)"
      , "Maybe.nothing.orElse(Maybe.nothing) should return Nothing"
      )
      .when(() ->
        Maybe.<Integer>nothing().orElse(() -> Maybe.nothing())
      )
      .then(actual ->
        assertThat(actual).isEqualTo(Maybe.nothing())
      );
  }

  @Test
  public void orElse_Nothing_JustCase() {
    final Integer expectedValue = 999;
    test(
        "Maybe.maybe.orElse(Maybe.nothing)"
      , "Maybe.maybe.orElse(Maybe.nothing) should return Just"
      )
      .when(() ->
        Maybe.maybe(expectedValue).orElse(() -> Maybe.nothing())
      )
      .then(actual ->
        assertThat(actual).isEqualTo(Maybe.maybe(expectedValue))
      );
  }

  @Test
  public void orElse_Just_NothingCase() {
    final Integer expectedValue = 999;
    test(
        "Maybe.maybe.orElse(Maybe.nothing)"
      , "Maybe.maybe.orElse(Maybe.nothing) should return Just"
      )
      .when(() ->
        Maybe.maybe(expectedValue).orElse(() -> Maybe.nothing())
      )
      .then(actual ->
        assertThat(actual).isEqualTo(Maybe.maybe(expectedValue))
      );
  }

  @Test
  public void orElse_Just_JustCase() {
    final Integer unexpectedValue = 0;
    final Integer expectedValue = 999;
    test(
        "Maybe.maybe(original).orElse(Maybe.maybe(another))"
      , "Maybe.maybe(original).orElse(Maybe.maybe(another)) should return Just(original)"
      )
      .when(() ->
        Maybe.maybe(expectedValue).orElse(() -> Maybe.maybe(unexpectedValue))
      )
      .then(actual ->
        assertThat(actual).isEqualTo(Maybe.maybe(expectedValue))
      );
  }

  @Test
  public void getOrElse_NothingCase() {
    final Integer alternativeValue = 999;
    test(
        "Maybe.nothing.getOrElse(() -> alternativeValue)"
      , "Maybe.nothing.getOrElse(() -> alternativeValue) should return alternativeValue"
      )
      .when(() ->
        Maybe.<Integer>nothing().getOrElse(() -> alternativeValue)
      )
      .then(actual ->
        assertThat(actual).isEqualTo(alternativeValue)
      );
  }

  @Test
  public void getOrElse_JustCase() {
    final Integer expectedValue = 999;
    final Integer alternativeValue = 0;
    test(
        "Maybe.maybe(expectedValue).getOrElse(() -> alternativeValue)"
      , "Maybe.maybe(expectedValue).getOrElse(() -> alternativeValue) should return expectedValue"
      )
      .when(() ->
        Maybe.maybe(expectedValue).getOrElse(() -> alternativeValue)
      )
      .then(actual ->
        assertThat(actual).isEqualTo(expectedValue)
      );
  }

  @Test
  public void orElseToNothinggMap_NothingCase() {
    final String input = "Some error";
    final String additional = " and something else";
    final String expectedValue = input + additional;
    test(
        "Maybe.nothing.orElse(Maybe.nothing).map"
      , "Maybe.nothing.orElse(Maybe.nothing).map should return Nothing"
      )
      .when(() ->
        Maybe.<Integer>nothing().orElse(() -> Maybe.<Integer>nothing()).map(s -> s + additional)
      )
      .then(actual ->
        assertThat(actual).isEqualTo(Maybe.nothing())
      );
  }

  @Test
  public void orElseToJustMap_NothingCase() {
    final String input = "Some error";
    final String additional = " and something else";
    final String expectedValue = input + additional;
    test(
        "Maybe.nothing.orElse(Maybe.maybe).map"
      , "Maybe.nothing.orElse(Maybe.maybe).map(function) should apply the given function"
      )
      .when(() ->
          Maybe.<String>nothing().orElse(() -> Maybe.maybe(input)).map(s -> s + additional)
      )
      .then(actual ->
        assertThat(actual).isEqualTo(Maybe.maybe(expectedValue))
      );
  }

  @Test
  public void orElseToNothingMap_JustCase() {
    final Integer input = 999;
    final Integer additionalValue = 1;
    final Integer expectedValue = input + additionalValue;
    test(
        "Maybe.maybe.orElse(Maybe.nothing).map"
      , "Maybe.maybe.orElse(Maybe.nothing).map(function) should apply the given function"
      )
      .when(() ->
        Maybe.maybe(input).orElse(() -> Maybe.nothing()).map(i -> i + additionalValue)
      )
      .then(actual ->
        assertThat(actual).isEqualTo(Maybe.maybe(expectedValue))
      );

  }

  @Test
  public void orElseToJustMap_JustCase() {
    final Integer input = 999;
    final Integer additionalValue = 1;
    final Integer expectedValue = input + additionalValue;
    test(
        "Maybe.maybe(original).orElse(Maybe.maybe(another)).map"
      , "Maybe.maybe(original).orElse(Maybe.maybe(another)).map(function) should apply the given function to the original"
      )
      .when(() ->
        Maybe.maybe(input).orElse(() -> Maybe.maybe(0)).map(i -> i + additionalValue)
      )
      .then(actual ->
        assertThat(actual).isEqualTo(Maybe.maybe(expectedValue))
      );

  }

  @Test
  public void orElseToNothingFlatMap_NothingCase() {
    test(
        "Maybe.nothing.orElse(Maybe.nothing).flatMap on Nothing"
      , "Maybe.nothing.orElse(Maybe.nothing).flatMap should return Nothing"
      )
      .when(() ->
        Maybe.<Integer>nothing().orElse(() -> Maybe.nothing())
          .flatMap(s -> Maybe.maybe(s + "abc"))
      )
      .then(actual ->
        assertThat(actual).isEqualTo(Maybe.nothing())
      );

  }

  @Test
  public void orElseToJustFlatMap_NothingCase() {
    final Integer input = 999;
    final Integer additionalValue = 1;
    final Integer expectedValue = input + additionalValue;
    test(
        "Maybe.nothing.orElse(Maybe.maybe).flatMap"
      , "Maybe.nothing.orElse(Maybe.maybe).flatMap(function) should apply the given function"
      )
      .when(() ->
        Maybe.<Integer>nothing().orElse(() -> Maybe.maybe(input))
          .flatMap(i -> Maybe.maybe(i + additionalValue))
      )
      .then(actual ->
        assertThat(actual).isEqualTo(Maybe.maybe(expectedValue))
      );

  }

  @Test
  public void orElseToNothingFlatMap_JustCase() {
    final Integer input = 999;
    final Integer additionalValue = 1;
    final Integer expectedValue = input + additionalValue;
    test(
        "Maybe.maybe.orElse(Maybe.nothing).flatMap"
      , "Maybe.maybe.orElse(Maybe.nothing).flatMap(function) should apply the given function"
      )
      .when(() ->
        Maybe.maybe(input)
          .orElse(() -> Maybe.nothing())
          .flatMap(i -> Maybe.maybe(i + additionalValue))
      )
      .then(actual ->
        assertThat(actual).isEqualTo(Maybe.maybe(expectedValue))
      );
  }

  @Test
  public void orElseToJustFlatMap_JustCase() {
    final Integer input = 999;
    final Integer additionalValue = 1;
    final Integer expectedValue = input + additionalValue;

    test(
      "Maybe.maybe(original).orElse(Maybe.maybe(another)).flatMap"
      , "Maybe.maybe(original).orElse(Maybe.maybe(another)).flatMap(function) should apply the given function to the original"
      )
      .when(() ->
        Maybe.maybe(input)
          .orElse(() -> Maybe.maybe(0))
          .flatMap(i -> Maybe.maybe(i + additionalValue))
      )
      .then(actual ->
        assertThat(actual).isEqualTo(Maybe.maybe(expectedValue))
      );
  }

  @Test
  public void toOptional_NothingCase() {
    final Optional<Integer> expected = Optional.empty();

    test(
      "Maybe.nothing().toOptional()",
      "Maybe.nothing().toOptional() should return Optional.empty()"
    )
      .when(() ->
        Maybe.nothing()
          .toOptional()
      )
      .then(actual ->
        assertThat(actual).isEqualTo(expected)
      );
  }

  @Test
  public void toOptional_JustCase() {
    final Integer input = 1;
    final Optional<Integer> expected = Optional.ofNullable(input);

    test(
        "Maybe.maybe(original).toOptional()",
        "Maybe.maybe(original).toOptional() should return Optional.ofNullable(original)"
      )
      .when(() ->
        Maybe.maybe(input)
          .toOptional()
      )
      .then(actual ->
        assertThat(actual).isEqualTo(expected)
      );
  }

  @Test
  public void toEither_NothingCase() {
    final String leftValue = "Some Error";
    final Either<String, Integer> expected = Either.left(leftValue);

    test(
      "Maybe.nothing().toEither(leftValue)",
      "Maybe.nothing().toEither(leftValue) should return Left(leftValue)"
    )
      .when(() ->
        Maybe.<Integer> nothing()
          .toEither(() -> leftValue)
      )
      .then(actual ->
        assertThat(actual).isEqualTo(expected)
      );
  }

  @Test
  public void toEither_JustCase() {
    final Integer input = 1;
    final String leftValue = "Some Error";
    final Either<String, Integer> expected = Either.right(input);

    test(
        "Maybe.maybe(original).toEither(leftValue)",
        "Maybe.maybe(original).toEither(leftValue) should return Either.right(original)"
      )
      .when(() ->
        Maybe.maybe(input)
          .toEither(() -> leftValue)
      )
      .then(actual ->
        assertThat(actual).isEqualTo(expected)
      );
  }

  @Test
  public void filter_NothingCase() {
    test("Maybe.nothing.filter", "Maybe.nothing.filter should return Nothing itself")
      .when(() ->
        Maybe.<Integer>nothing().filter(i -> true)
      )
      .then(actual ->
        assertThat(actual).isEqualTo(Maybe.nothing())
      );
  }

  @Test
  public void filter_Just_TrueCase() {
    final Integer expectedValue = 999;
    test("Maybe.maybe.filter(true case)", "Maybe.maybe.filter(true case) should return Just itself")
      .when(() ->
        Maybe.maybe(expectedValue).filter(i -> true)
      )
      .then(actual ->
        assertThat(actual).isEqualTo(Maybe.maybe(expectedValue))
      );
  }

  @Test
  public void filter_Just_FalseCase() {
    final Integer input = 999;
    test("Maybe.maybe.filter(false case)", "Maybe.maybe.filter(false case) should return Nothing")
      .when(() ->
        Maybe.maybe(input).filter(i -> false)
      )
      .then(actual ->
        assertThat(actual).isEqualTo(Maybe.nothing())
      );
  }

  @Test
  public void ap_NothingCase() {
    test("Maybe.ap on Nothing", "Maybe.ap on Nothing should just return itself")
      .when(() ->
        Maybe.<Integer>nothing().ap(() -> Maybe.maybe(i -> i * 2))
      )
      .then(actual ->
        assertThat(actual).isEqualTo(Maybe.nothing())
      );
  }

  @Test
  public void ap_JustCase() {
    final Integer input = 999;
    final Integer additional = 111;
    final Integer expectedValue = input + additional;
    test("Maybe.ap on Just", "Maybe.ap on Just should apply the function in the given Just")
      .when(() ->
        Maybe.maybe(input).ap(() -> Maybe.maybe(i -> i + additional))
      )
      .then(actual ->
        assertThat(actual).isEqualTo(Maybe.maybe(expectedValue))
      );
  }

  @Test
  public void fold_NothingCase() {
    final String expectedValue = "There is nothing";
    test("Maybe.fold on Nothing", "Maybe.fold on Nothing should apply the function for Nothing")
      .when(() ->
        Maybe.<Integer>nothing()
      )
      .then(actual ->
        assertThat(actual.isNothing()).isTrue()
      )
      .then(actual ->
        assertThat(actual.fold(() -> expectedValue, i -> String.valueOf(i))).isEqualTo(expectedValue)
      );
  }

  @Test
  public void fold_JustCase() {
    final Integer input = 999;
    final Integer additional = 111;
    final String expectedValue = String.valueOf(input + additional);
    test("Maybe.fold on Just", "Maybe.fold on Just should apply the function for Just")
      .when(() ->
        Maybe.maybe(input)
      )
      .then(actual ->
        assertThat(actual.isJust()).isTrue()
      )
      .then(actual ->
        assertThat(actual.fold(() -> "Nothing", i -> String.valueOf(i + additional))).isEqualTo(expectedValue)
      );

  }

  @Test
  public void forEach_NothingCase() {
    final Integer[] updated = { null };
    test("Maybe.forEach on Nothing", "Maybe.forEach on Nothing should do nothing")
      .when(() ->
        Maybe.<Integer>nothing()
          .forEach(i -> updated[0] = i)
      )
      .then(() ->
        assertThat(updated[0]).isNull()
      );
  }

  @Test
  public void forEach_JustCase() {
    final Integer input = 999;
    final Integer additional = 111;
    final Integer expectedValue = input + additional;
    final Integer[] updated = { null };
    test("Maybe.forEach on Just", "Maybe.forEach on Just should apply the given function with side-effect")
      .when(() ->
        Maybe.maybe(input)
          .forEach(i -> updated[0] = i + additional)
      )
      .then(() ->
        assertThat(updated[0]).isEqualTo(expectedValue)
      );
  }

  @Test
  public void hashCode_NothingCase() {
    final int expected = Objects.hashCode(Maybe.nothing());
    test("Maybe.hashCode on Nothing", "Maybe.hashCode on Nothing should return the hashCode of Nothing")
      .when(() ->
        Maybe.<Integer>nothing().hashCode()
      )
      .then(actual ->
        assertThat(actual).isEqualTo(expected)
      );
  }

  @Test
  public void hashCode_JustCase() {
    final Integer input = 999;
    final int expected = Objects.hashCode(input);
    test("Maybe.hashCode on Just", "Maybe.hashCode on Just should return the hashCode of Just data")
      .when(() ->
        Maybe.maybe(input).hashCode()
      )
      .then(actual ->
        assertThat(actual).isEqualTo(expected)
      );
  }

  @Test
  public void toString_NothingCase() {
    final String expected = "Maybe = Nothing";
    test("Maybe.toString on Nothing", "Maybe.toString on Nothing should return \"Nothing\"")
      .when(() ->
        Maybe.<Integer>nothing().toString()
      )
      .then(actual ->
        assertThat(actual).isEqualTo(expected)
      );
  }

  @Test
  public void toString_JustCase() {
    final Integer input = 999;
    final String expected = "Maybe<Integer> = Just(" + input + ")";
    test(
      "Maybe.toString on Maybe.maybe(" + input + ")",
      "Maybe.toString on Just should return " + expected
    )
      .when(() ->
        Maybe.maybe(input).toString()
      )
      .then(actual ->
        assertThat(actual).isEqualTo(expected)
      );
  }

  @Test
  public void toString_Nested_JustCase() {
    final Integer input = 999;
    final String expected = "Maybe<Maybe<Integer>> = Just(Just(" + input + "))";
    test(
      "Maybe.toString on Maybe.maybe(Maybe.maybe(" + input + "))",
      "Maybe.toString on Just should return " + expected
    )
      .when(() ->
        Maybe.maybe(Maybe.maybe(input)).toString()
      )
      .then(actual ->
        assertThat(actual).isEqualTo(expected)
      );
  }

  @Test
  public void toString_Nested_JustCase2() {
    final Integer input = 999;
    final String expected = "Maybe<Maybe<Maybe<Integer>>> = Just(Just(Just(" + input + ")))";
    test(
      "Maybe.toString on Maybe.maybe(Maybe.maybe(Maybe.maybe(" + input +")))",
      "Maybe.toString on Just should return " + expected
    )
      .when(() ->
        Maybe.maybe(Maybe.maybe(Maybe.maybe(input))).toString()
      )
      .then(actual ->
        assertThat(actual).isEqualTo(expected)
      );
  }

  @Test
  public void toString_Nested_JustCase3() {
    final Integer input = 999;
    final String expected = "Maybe<Maybe<Maybe<Maybe<Integer>>>> = Just(Just(Just(Just(" + input + "))))";
    test(
      "Maybe.toString on Maybe.maybe(Maybe.maybe(Maybe.maybe(Maybe.maybe(" + input + "))))",
      "Maybe.toString on Just should return " + expected
    )
      .when(() ->
        Maybe.maybe(Maybe.maybe(Maybe.maybe(Maybe.maybe(input)))).toString()
      )
      .then(actual ->
        assertThat(actual).isEqualTo(expected)
      );
  }

  @Test
  public void toString_Nested_JustCase_with_Nothing() {
    final String expected = "Maybe<Maybe<Maybe<Maybe>>> = Just(Just(Just(Nothing)))";
    test(
      "Maybe.toString on Maybe.maybe(Maybe.maybe(Maybe.maybe(Maybe.nothing())))",
      "Maybe.toString on Just should return " + expected
    )
      .when(() ->
        Maybe.maybe(Maybe.maybe(Maybe.maybe(Maybe.nothing()))).toString()
      )
      .then(actual ->
        assertThat(actual).isEqualTo(expected)
      );
  }

}