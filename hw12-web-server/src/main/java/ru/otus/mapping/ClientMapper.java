package ru.otus.mapping;

import static java.util.Objects.isNull;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.otus.model.dto.ClientDto;
import ru.otus.model.db.Client;
import ru.otus.model.db.Phone;

@Mapper
public interface ClientMapper {

    List<ClientDto> mapClientList(List<Client> client);

    @Mapping(target = "phoneNumber", source = "phone")
    @Mapping(target = "address", source = "address.street")
    ClientDto mapClient(Client client);

    @Mapping(target = "phone", source = "phoneNumber")
    @Mapping(target = "address.street", source = "address")
    Client mapDto(ClientDto clientDto);


    default String mapPhone(List<Phone> phoneList) {
        if (isNull(phoneList) || phoneList.isEmpty()) {
            return null;
        }
        return phoneList.get(0).getNumber();
    }

    default List<Phone> mapPhone(String phone) {
        if (isNull(phone)) {
            return null;
        }
        return List.of(new Phone(phone));
    }

}
