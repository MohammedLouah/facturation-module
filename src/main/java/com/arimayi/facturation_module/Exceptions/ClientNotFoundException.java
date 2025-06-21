package com.arimayi.facturation_module.Exceptions;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException(Integer clientId) {
        super("Client non trouvé avec l'ID : " + clientId);
    }
}
