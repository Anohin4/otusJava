package ru.otus.services;

import java.util.List;
import java.util.Optional;
import ru.otus.model.dto.ClientDto;
import ru.otus.model.db.Client;
import ru.otus.dbLayer.crm.service.DBServiceClient;
import ru.otus.mapping.ClientMapper;

public class ClientServiceImpl implements ClientService {

    private final DBServiceClient dbServiceClient;
    private final ClientMapper mapper;

    public ClientServiceImpl(DBServiceClient dbServiceClient, ClientMapper mapper) {
        this.dbServiceClient = dbServiceClient;
        this.mapper = mapper;
    }

    @Override
    public ClientDto saveClient(ClientDto clientDto) {
        Client client = mapper.mapDto(clientDto);
        Client result = dbServiceClient.saveClient(client);
        return mapper.mapClient(result);
    }

    @Override
    public Optional<ClientDto> getClient(long id) {

        return dbServiceClient
                .getClient(id)
                .map(mapper::mapClient);
    }

    @Override
    public List<ClientDto> findAll() {
        List<Client> all = dbServiceClient.findAll();
        return mapper.mapClientList(all);
    }
}
