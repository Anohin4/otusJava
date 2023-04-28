package ru.otus.services;

import java.util.List;
import java.util.Optional;
import ru.otus.model.dto.ClientDto;

public interface ClientService {

    ClientDto saveClient(ClientDto clientDto);

    Optional<ClientDto> getClient(long id);

    List<ClientDto> findAll();

}
