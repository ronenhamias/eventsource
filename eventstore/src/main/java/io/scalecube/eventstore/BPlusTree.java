package io.scalecube.eventstore;

import java.util.function.Function;

public class BPlusTree {
  private Function<String, Long> keyProvider;
  private Function<String, Long> valueProvider;

  public BPlusTree(Function<String, Long> keyProvider, Function<String, Long> valueProvider) {
    this.keyProvider = keyProvider;
    this.valueProvider = valueProvider;
  }


  public Function<String, Long> getKeyProvider() {
    return keyProvider;
  }

  public Function<String, Long> getValueProvider() {
    return valueProvider;
  }

  public static void main(String[] args) {
    System.out.println("min " + Long.MIN_VALUE);
  }
}
