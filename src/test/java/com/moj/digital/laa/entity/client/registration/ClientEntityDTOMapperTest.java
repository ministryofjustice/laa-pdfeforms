package com.moj.digital.laa.entity.client.registration;

import com.moj.digital.laa.entity.client.registration.Address;
import com.moj.digital.laa.entity.client.registration.Person;
import com.moj.digital.laa.model.client.registration.AddressDTO;
import com.moj.digital.laa.model.client.registration.ClientDTO;
import com.moj.digital.laa.util.JsonTestUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
@JsonTest
@ComponentScan(basePackages = "com.moj.digital.laa.util")
public class ClientEntityDTOMapperTest {

    @Autowired
    private JsonTestUtil jsonTestUtil;

    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void verifyClientDTOtoClientEntityMapping() throws IOException {
        ClientDTO clientDTO = jsonTestUtil.clientDTOFromJson();
        clientDTO.setId(123L);

        Person client = modelMapper.map(clientDTO,Person.class);

        assertDTOAndEntityAttributes(clientDTO, client);
    }

    @Test
    public void verifyClientEntityToClientDTOMapping() throws IOException {
        Person client = jsonTestUtil.clientFromJson();
        client.setId(123L);

        ClientDTO clientDTO = modelMapper.map(client, ClientDTO.class);

        assertDTOAndEntityAttributes(clientDTO, client);
    }

    private void assertDTOAndEntityAttributes(ClientDTO clientDTO, Person client) {
        assertThat(clientDTO.getAdviceAndAssistance()).isEqualTo(client.getAdviceAndAssistance());
        assertThat(clientDTO.getAllegation()).isEqualTo(client.getAllegation());
        assertThat(clientDTO.getDateOfBirth()).isEqualTo(client.getDateOfBirth());
        assertThat(clientDTO.getCoAccused()).isEqualTo(client.getCoAccused());
        assertThat(clientDTO.getConflictCheck()).isEqualTo(client.getConflictCheck());
        assertThat(clientDTO.getConflictCheckDate()).isEqualTo(client.getConflictCheckDate());
        assertThat(clientDTO.getConflictCheckName()).isEqualTo(client.getConflictCheckName());
        assertThat(clientDTO.getCrm3Advocate()).isEqualTo(client.getCrm3Advocate());
        assertThat(clientDTO.getCrownCourt()).isEqualTo(client.getCrownCourt());
        assertThat(clientDTO.getEmploymentStatus()).isEqualTo(client.getEmploymentStatus());
        assertThat(clientDTO.getEthnicity()).isEqualTo(client.getEthnicity());
        assertThat(clientDTO.getExistingClient()).isEqualTo(client.getExistingClient());
        assertThat(clientDTO.getForename()).isEqualTo(client.getForename());
        assertThat(clientDTO.getFundingDate()).isEqualTo(client.getFundingDate());
        assertThat(clientDTO.getId()).isEqualTo(client.getId());
        assertThat(clientDTO.getMagistrateCourt()).isEqualTo(client.getMagistrateCourt());
        assertThat(clientDTO.getNationality()).isEqualTo(client.getNationality());
        assertThat(clientDTO.getNiNumber()).isEqualTo(client.getNiNumber());
        assertThat(clientDTO.getPoliceStation1()).isEqualTo(client.getPoliceStation1());
        assertThat(clientDTO.getPoliceStation2()).isEqualTo(client.getPoliceStation2());
        assertThat(clientDTO.getPreOrderWork()).isEqualTo(client.getPreOrderWork());
        assertThat(clientDTO.getPreviousConviction()).isEqualTo(client.getPreviousConviction());
        assertThat(clientDTO.getProofOfBenefitsRequested()).isEqualTo(client.getProofOfBenefitsRequested());
        assertThat(clientDTO.getRelationshipStatus()).isEqualTo(client.getRelationshipStatus());
        assertThat(clientDTO.getRepOrderAppliedFor()).isEqualTo(client.getRepOrderAppliedFor());
        assertThat(clientDTO.getRepOrderGranted()).isEqualTo(client.getRepOrderGranted());
        assertThat(clientDTO.getRequestSpecificSolicitor()).isEqualTo(client.getRequestSpecificSolicitor());
        assertThat(clientDTO.getRequestSpecificSolicitorText()).isEqualTo(client.getRequestSpecificSolicitorText());
        //assertThat(clientDTO.getRiskAssessment()).isEqualTo(client.getRiskAssessment());
        assertThat(clientDTO.getSourceOfBusiness()).isEqualTo(client.getSourceOfBusiness());
        assertThat(clientDTO.getSurname()).isEqualTo(client.getSurname());
        assertThat(clientDTO.getTelephoneNumber()).isEqualTo(client.getTelephoneNumber());
        assertThat(clientDTO.getTitle()).isEqualTo(client.getTitle());
        assertThat(clientDTO.getUfn()).isEqualTo(client.getUfn());
        assertThat(clientDTO.getVenue()).isEqualTo(client.getVenue());
        assertAddress(clientDTO.getResidenceAddress(), client.getResidenceAddress());
        assertAddress(clientDTO.getCorrespondenceAddress(), client.getCorrespondenceAddress());
    }

    private void assertAddress(AddressDTO addressDTO, Address address){
        assertThat(addressDTO.getAddressLine1()).isEqualTo(address.getAddressLine1());
        assertThat(addressDTO.getAddressLine2()).isEqualTo(address.getAddressLine2());
        assertThat(addressDTO.getAddressLine3()).isEqualTo(address.getAddressLine3());
        assertThat(addressDTO.getPostCode()).isEqualTo(address.getPostCode());
        assertThat(addressDTO.getAddressLine1()).isEqualTo(address.getAddressLine1());
    }

}