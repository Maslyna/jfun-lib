package org.jfun.result;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public record Success<T, ERR>(T value) implements Result<T, ERR> {

    private static final Success<?, ?> EMPTY = new Success<>(null);

    @SuppressWarnings("unchecked")
    public static <T, ERR> Success<T, ERR> empty() {
        return (Success<T, ERR>) EMPTY;
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public T getOrElse(T value) {
        return this.value;
    }

    @Override
    public ERR getError() {
        throw new RuntimeException("Provided valid result: " + value);
    }

    @Override
    public ERR getErrorOrElse(ERR err) {
        return err;
    }

    @Override
    public Optional<T> toOptional() {
        return Optional.of(value);
    }

    @Override
    public Optional<ERR> toOptionalError() {
        return Optional.empty();
    }

    @Override
    public <U> Result<U, ERR> map(Function<? super T, ? extends U> mapper) {
        Objects.requireNonNull(mapper);
        return new Success<>(mapper.apply(value));
    }

    @Override
    public <EXX> Result<T, EXX> mapError(Function<? super ERR, ? extends EXX> mapper) {
        Objects.requireNonNull(mapper);
        return new Success<>(value);
    }

    @Override
    public <U> Result<U, ERR> flatMap(Function<? super T, Result<U, ERR>> mapper) {
        Objects.requireNonNull(mapper);
        return mapper.apply(value);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <EXX> Result<T, EXX> flatMapError(Function<? super ERR, Result<T, EXX>> mapper) {
        Objects.requireNonNull(mapper);
        return (Result<T, EXX>) this;
    }

    @Override
    public Result<T, ERR> filter(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        return predicate.test(value) ? this : empty();
    }

    @Override
    public Result<T, ERR> filterError(Predicate<ERR> predicate) {
        return this;
    }


    @Override
    public boolean isError() {
        return false;
    }

    @Override
    public boolean isOk() {
        return true;
    }
}