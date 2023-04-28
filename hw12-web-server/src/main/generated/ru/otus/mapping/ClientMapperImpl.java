package ru.otus.mapping;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import ru.otus.model.dto.ClientDto;
import ru.otus.model.db.Address;
import ru.otus.model.db.Client;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-28T09:39:17+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.2 (Amazon.com Inc.)"
)
public class ClientMapperImpl implements ClientMapper {

    @Override
    public List<ClientDto> mapClientList(List<Client> client) {
        if ( client == null ) {
            return null;
        }

        List<ClientDto> list = new ArrayList<ClientDto>( client.size() );
        for ( Client client1 : client ) {
            list.add( mapClient( client1 ) );
        }

        return list;
    }

    @Override
    public ClientDto mapClient(Client client) {
        if ( client == null ) {
            return null;
        }

        ClientDto clientDto = new ClientDto();

        clientDto.setPhoneNumber( mapPhone( client.getPhone() ) );
        clientDto.setAddress( clientAddressStreet( client ) );
        clientDto.setId( client.getId() );
        clientDto.setName( client.getName() );

        return clientDto;
    }

    @Override
    public Client mapDto(ClientDto clientDto) {
        if ( clientDto == null ) {
            return null;
        }

        Client client = new Client();

        client.setAddress( clientDtoToAddress( clientDto ) );
        client.setPhone( mapPhone( clientDto.getPhoneNumber() ) );
        client.setId( clientDto.getId() );
        client.setName( clientDto.getName() );

        return client;
    }

    private String clientAddressStreet(Client client) {
        if ( client == null ) {
            return null;
        }
        Address address = client.getAddress();
        if ( address == null ) {
            return null;
        }
        String street = address.getStreet();
        if ( street == null ) {
            return null;
        }
        return street;
    }

    protected Address clientDtoToAddress(ClientDto clientDto) {
        if ( clientDto == null ) {
            return null;
        }

        Address address = new Address();

        address.setStreet( clientDto.getAddress() );

        return address;
    }
}
