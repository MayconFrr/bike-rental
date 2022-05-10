package io.github.mayconfrr.bikerental.client;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException(Long id) {
        super("Client with id " + id + " not found");
    }
}
