package ru.otus.dbLayer.crm.service;

import java.util.List;
import java.util.Optional;
import ru.otus.model.db.Client;


public interface DBServiceClient {

    Client saveClient(Client client);

    Optional<Client> getClient(long id);

    List<Client> findAll();
}
