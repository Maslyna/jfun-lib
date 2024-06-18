package org.jfun.result;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Represents the result of an operation that may produce a value of type T or an error of type ERR.
 * @param <T> the type of the result value
 * @param <ERR> the type of the error
 */
public sealed interface Result<T, ERR> permits Success, Failure {

    static <T, ERR> Success<T, ERR> success(T value) {
        Objects.requireNonNull(value);
        return new Success<>(value);
    }

    static <ERR> Success<Void, ERR> success() {
        return Success.empty();
    }

    static <T, ERR> Failure<T, ERR> failure(ERR error) {
        Objects.requireNonNull(error);
        return new Failure<>(error);
    }

    static <T> Failure<T, Void> failure() {
        return Failure.empty();
    }

    /**
     * Gets the value if the result is ok, otherwise throws an RuntimeException.
     * @throws RuntimeException if the result is an error
     */
    T get();

    /**
     * Gets the value if the result is ok, otherwise returns the provided default value.
     */
    T getOrElse(T defaultValue);

    /**
     * Gets the error if the result is an error, otherwise throws an RuntimeException.
     * @throws RuntimeException if the result is ok
     */
    ERR getError();

    /**
     * Gets the error if the result is an error, otherwise returns the provided default error.
     */
    ERR getErrorOrElse(ERR defaultError);

    /**
     * Converts the result to an Optional containing the value, if present.
     */
    Optional<T> toOptional();

    /**
     * Converts the error to an Optional containing the error, if present.
     */
    Optional<ERR> toOptionalError();

    /**
     * Maps the value of the result if it's ok, otherwise returns the error.
     */
    <U> Result<U, ERR> map(Function<? super T, ? extends U> mapper);

    /**
     * Maps the error of the result if it's an error, otherwise returns the value.
     */
    <EXX> Result<T, EXX> mapError(Function<? super ERR, ? extends EXX> mapper);

    /**
     * Applies a function to the value of this result if it's ok,
     * and flattens the resulting nested Result into a new Result.
     * If this result is an error, the error is propagated.
     */
    <U> Result<U, ERR> flatMap(Function<? super T, Result<U, ERR>> mapper);

    /**
     * Applies a function to the error of this result if it's an error,
     * and flattens the resulting nested Result into a new Result.
     * If this result is ok, it's returned as is.
     */
    <EXX> Result<T, EXX> flatMapError(Function<? super ERR, Result<T, EXX>> mapper);

    /**
     * Filters the result based on a predicate. If the result is ok
     * and the predicate evaluates to true, the result is returned.
     * Otherwise, an empty Failure is returned.
     */
    Result<T, ERR> filter(Predicate<? super T> predicate);

    /**
     * Filters the error of this result if it's an error and the provided
     * predicate evaluates to true for the error type. Otherwise, the
     * original result is returned.
     */
    Result<T, ERR> filterError(Predicate<ERR> predicate);


    /**
     * Returns true if the result is an error, false otherwise.
     */
    boolean isError();

    /**
     * Returns true if the result is ok, false otherwise.
     */
    boolean isOk();
}