package org.jfun.result;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

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

    static <T, ERR> Success<T, ERR> success() {
        return Success.empty();
    }

    static <T, ERR> Failure<T, ERR> failure(ERR error) {
        Objects.requireNonNull(error);
        return new Failure<>(error);
    }

    static <T, ERR> Failure<T, ERR> failure() {
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
     * Returns true if the result is an error, false otherwise.
     */
    boolean isError();

    /**
     * Returns true if the result is ok, false otherwise.
     */
    boolean isOk();
}