package org.jfun.result;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public record Failure<T, ERR>(ERR error) implements Result<T, ERR> {

    private static final Failure<?, ?> EMPTY = new Failure<>(null);

    @SuppressWarnings("unchecked")
    public static <T, ERR> Failure<T, ERR> empty() {
        return (Failure<T, ERR>) EMPTY;
    }

    @Override
    public T get() {
        throw new RuntimeException("Invalid result provided: " + error);
    }

    @Override
    public T getOrElse(T value) {
        return value;
    }

    @Override
    public ERR getError() {
        return error;
    }

    @Override
    public ERR getErrorOrElse(ERR error) {
        return this.error;
    }

    @Override
    public Optional<T> toOptional() {
        return Optional.empty();
    }

    @Override
    public Optional<ERR> toOptionalError() {
        return Optional.of(error);
    }

    @Override
    public <U> Result<U, ERR> map(Function<? super T, ? extends U> mapper) {
        Objects.requireNonNull(mapper);
        return new Failure<>(error);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <U> Result<U, ERR> flatMap(Function<? super T, Result<U, ERR>> mapper) {
        Objects.requireNonNull(mapper);
        return (Result<U, ERR>) this;
    }

    @Override
    public <EXX> Result<T, EXX> mapError(Function<? super ERR, ? extends EXX> mapper) {
        Objects.requireNonNull(mapper);
        return new Failure<>(mapper.apply(error));
    }

    @Override
    public <EXX> Result<T, EXX> flatMapError(Function<? super ERR, Result<T, EXX>> mapper) {
        Objects.requireNonNull(mapper);
        return mapper.apply(error);
    }

    @Override
    public Result<T, ERR> filter(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        return empty();
    }

    @Override
    public Result<T, ERR> filterError(Predicate<ERR> predicate) {
        Objects.requireNonNull(predicate);
        return predicate.test(error) ? this : empty();
    }

    @Override
    public boolean isError() {
        return true;
    }

    @Override
    public boolean isOk() {
        return false;
    }
}
