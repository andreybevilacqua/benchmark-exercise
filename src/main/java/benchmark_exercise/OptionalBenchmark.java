package benchmark_exercise;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Optional;

public class OptionalBenchmark {

  private static final char STATUS_IF_SOME_FIELD_EXISTS = 'A';
  private static final char STATUS_IF_SOME_FIELD_IS_MISSING = 'B';

  private record DataObject(String someField){}

  @State(Scope.Thread)
  public static class DataObjectState {
    final DataObject dataObjectWithNullField = new DataObject(null);
    final DataObject dataObjectWithNonNullField = new DataObject("abc");
  }

  public static void main(String[] args) throws RunnerException {
    Options opts = new OptionsBuilder()
      .include(OptionalBenchmark.class.getSimpleName())
      .forks(1)
      .build();
    new Runner(opts).run();
  }

  @Benchmark
  public void withOptionalNull(final Blackhole blackhole, final  DataObjectState state) {
    final Character result = Optional.ofNullable(state.dataObjectWithNullField.someField)
      .map(someField -> STATUS_IF_SOME_FIELD_EXISTS)
      .orElse(STATUS_IF_SOME_FIELD_IS_MISSING);
    blackhole.consume(result);
  }

  @Benchmark
  public void withOptionalNonNull(final Blackhole blackhole, final DataObjectState state) {
    final Character result = Optional.ofNullable(state.dataObjectWithNonNullField.someField)
      .map(someField -> STATUS_IF_SOME_FIELD_EXISTS)
      .orElse(STATUS_IF_SOME_FIELD_IS_MISSING);
    blackhole.consume(result);
  }

  @Benchmark
  public void withTernaryNull(final Blackhole blackhole, final DataObjectState state) {
    final Character result = state.dataObjectWithNullField.someField == null
      ? STATUS_IF_SOME_FIELD_IS_MISSING
      : STATUS_IF_SOME_FIELD_EXISTS;
    blackhole.consume(result);
  }

  @Benchmark
  public void withTernaryNonNull(final Blackhole blackhole, final DataObjectState state) {
    final Character result = state.dataObjectWithNullField.someField == null
      ? STATUS_IF_SOME_FIELD_IS_MISSING
      : STATUS_IF_SOME_FIELD_EXISTS;
    blackhole.consume(result);
  }

  @Benchmark
  @BenchmarkMode(Mode.AverageTime)
  public void withOptionalNullAverageTime(final Blackhole blackhole, final  DataObjectState state) {
    final Character result = Optional.ofNullable(state.dataObjectWithNullField.someField)
      .map(someField -> STATUS_IF_SOME_FIELD_EXISTS)
      .orElse(STATUS_IF_SOME_FIELD_IS_MISSING);
    blackhole.consume(result);
  }

  @Benchmark
  @BenchmarkMode(Mode.AverageTime)
  public void withOptionalNonNullAverageTime(final Blackhole blackhole, final DataObjectState state) {
    final Character result = Optional.ofNullable(state.dataObjectWithNonNullField.someField)
      .map(someField -> STATUS_IF_SOME_FIELD_EXISTS)
      .orElse(STATUS_IF_SOME_FIELD_IS_MISSING);
    blackhole.consume(result);
  }

  @Benchmark
  @BenchmarkMode(Mode.AverageTime)
  public void withTernaryNullAverageTime(final Blackhole blackhole, final DataObjectState state) {
    final Character result = state.dataObjectWithNullField.someField == null
      ? STATUS_IF_SOME_FIELD_IS_MISSING
      : STATUS_IF_SOME_FIELD_EXISTS;
    blackhole.consume(result);
  }

  @Benchmark
  @BenchmarkMode(Mode.AverageTime)
  public void withTernaryNonNullAverageTime(final Blackhole blackhole, final DataObjectState state) {
    final Character result = state.dataObjectWithNullField.someField == null
      ? STATUS_IF_SOME_FIELD_IS_MISSING
      : STATUS_IF_SOME_FIELD_EXISTS;
    blackhole.consume(result);
  }
}