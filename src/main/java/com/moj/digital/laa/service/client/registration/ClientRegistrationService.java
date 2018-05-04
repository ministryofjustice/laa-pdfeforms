package com.moj.digital.laa.service.client.registration;

import com.moj.digital.laa.entity.client.registration.Person;
import com.moj.digital.laa.exception.client.registration.ClientNotFoundException;
import com.moj.digital.laa.exception.client.registration.InvalidClientRegistrationDataException;
import com.moj.digital.laa.model.client.registration.ClientDTO;
import com.moj.digital.laa.repository.client.registration.ClientRegistrationRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

import static com.moj.digital.laa.exception.common.errormessage.ErrorMessage.CLIENT_NOT_FOUND;
import static com.moj.digital.laa.exception.common.errormessage.ErrorMessage.CLIENT_PERSIST_ERROR;

@Service
@Slf4j
public class ClientRegistrationService {

    private ClientRegistrationRepository clientRegistrationRepository;
    private ModelMapper modelMapper;

    public ClientRegistrationService(ClientRegistrationRepository clientRegistrationRepository, ModelMapper modelMapper) {
        this.clientRegistrationRepository = clientRegistrationRepository;
        this.modelMapper = modelMapper;
    }

    public Long save(ClientDTO clientDTO) {
        log.debug("save client called with client {}", clientDTO);
        try {
            Person client = modelMapper.map(clientDTO, Person.class);

            client.getDisabilities().stream().forEach(disability -> {
                disability.setClient(client);
            });

            log.debug("Person to be updated {}", client);
            Person savedClient = clientRegistrationRepository.save(client);
            return savedClient.getId();
        } catch (Exception e) {
            log.error("Could not persist client details because of exception {}", e);
            throw new InvalidClientRegistrationDataException(CLIENT_PERSIST_ERROR.message() + e.getMessage(), e);
        }
    }

    public ClientDTO findByUfn(String ufn) {
        log.debug("Find Person by UFN called with UFN {}", ufn);
        Person client = clientRegistrationRepository.findByUfn(ufn);
        if (client == null) {
            log.warn("Could not identify a client with the provided UFN {}", ufn);
            throw new ClientNotFoundException(String.format(CLIENT_NOT_FOUND.message(), ufn));
        }
        return modelMapper.map(client, ClientDTO.class);
    }

    public List<ClientDTO> findClientByUfnContaining(String ufn) {
        log.debug("Find by UFN containing called with UFN {}", ufn);
        List<Person> clientList = clientRegistrationRepository.findByUfnContaining(ufn);

        Type targetType = new TypeToken<List<ClientDTO>>() {
        }.getType();

        return modelMapper.map(clientList, targetType);
    }

    public Long update(ClientDTO clientDTO) {
        log.debug("update client called with client {}", clientDTO);
        ClientDTO existingClientDTO = findByUfn(clientDTO.getUfn());
        clientDTO.setId(existingClientDTO.getId());
        return save(clientDTO);
    }
}
