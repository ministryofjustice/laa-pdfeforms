package com.moj.digital.laa.service.client.registration;

import com.moj.digital.laa.entity.client.registration.Person;
import com.moj.digital.laa.exception.client.registration.ClientNotFoundException;
import com.moj.digital.laa.exception.client.registration.InvalidClientRegistrationDataException;
import com.moj.digital.laa.model.client.registration.ClientDTO;
import com.moj.digital.laa.repository.client.registration.ClientRegistrationRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@Import({ModelMapper.class})
public class ClientRegistrationServiceTest {

    private ClientRegistrationService clientRegistrationService;

    @Mock
    private ClientRegistrationRepository clientRegistrationRepository;

    @Before
    public void setup() {
        clientRegistrationService = new ClientRegistrationService(clientRegistrationRepository, new ModelMapper());
    }

    @Test(expected = InvalidClientRegistrationDataException.class)
    public void saveWhenClientWhenServiceFailureOccursShouldThrowException() {
        when(clientRegistrationRepository.save(any(Person.class))).thenThrow(new RuntimeException(""));
        clientRegistrationService.save(new ClientDTO());
    }

    @Test
    public void saveWhenThereIsNoInternalErrorClientShouldBeSaved() {
        ClientDTO clientDTO = new ClientDTO();
        clientRegistrationService.save(clientDTO);
    }

    @Test
    public void findByUfnWhenAValidUFNIsSentShouldReturnClient() {
        Person client = new Person();
        client.setUfn("UFN1");

        when(clientRegistrationRepository.findByUfn("UFN1")).thenReturn(client);
        ClientDTO clientDTO = clientRegistrationService.findByUfn("UFN1");
        assertThat(clientDTO.getUfn()).isEqualTo(client.getUfn());
    }

    @Test
    public void findByUfnContainingWhenAValidUFNIsSentShouldReturnMatchingClients() {
        List<Person> listOfClients = new ArrayList<>();

        Person client1 = new Person();
        client1.setUfn("UFN1");

        Person client2 = new Person();
        client2.setUfn("UFN2");

        listOfClients.add(client1);
        listOfClients.add(client2);

        when(clientRegistrationRepository.findByUfnContaining("UFN")).thenReturn(listOfClients);
        List<ClientDTO> clientDTOS = clientRegistrationService.findByUfnContaining("UFN");

        assertThat(listOfClients.size()).isEqualTo(clientDTOS.size());
        assertThat(listOfClients.get(0).getUfn()).isEqualTo(clientDTOS.get(0).getUfn());
        assertThat(listOfClients.get(1).getUfn()).isEqualTo(clientDTOS.get(1).getUfn());
    }

    @Test(expected = ClientNotFoundException.class)
    public void findByUfnWhenAnInValidUFNIsSentShouldThrowAnException() {
        when(clientRegistrationRepository.findByUfn("UFN1")).thenReturn(null);
        clientRegistrationService.findByUfn("UFN1");
    }

    @Test(expected = InvalidClientRegistrationDataException.class)
    public void updateWhenClientWhenServiceFailureOccursShouldThrowException() {
        when(clientRegistrationRepository.findByUfn("UFN1")).thenReturn(new Person());
        when(clientRegistrationRepository.save(any(Person.class))).thenThrow(new RuntimeException(""));

        ClientDTO clientDTOtoBeUpdated = new ClientDTO();
        clientDTOtoBeUpdated.setUfn("UFN1");

        clientRegistrationService.update(clientDTOtoBeUpdated);
    }

    @Test
    public void updateWhenThereIsNoInternalErrorClientShouldBeSaved() {
        when(clientRegistrationRepository.findByUfn("UFN1")).thenReturn(new Person());

        ClientDTO clientDTOtoBeUpdated = new ClientDTO();
        clientDTOtoBeUpdated.setUfn("UFN1");

        clientRegistrationService.update(clientDTOtoBeUpdated);
    }
}