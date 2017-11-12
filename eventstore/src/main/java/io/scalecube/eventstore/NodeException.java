package io.scalecube.eventstore;

public class NodeException extends RuntimeException {

  public NodeException(Throwable thr) {
    super(thr);
  }

  
  public NodeException(String message) {
    super(message);
  }


}
