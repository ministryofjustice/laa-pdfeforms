package com.moj.digital.laa.entity.person;

import com.moj.digital.laa.model.person.AddressDTO;
import com.moj.digital.laa.model.person.PersonDTO;
import com.moj.digital.laa.util.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@JsonTest
@ComponentScan(basePackages = "com.moj.digital.laa.util")
public class PersonTest {

    @Autowired
    private JsonUtil jsonUtil;

    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void verifyPersonDTOtoPersonMapping() throws IOException {
        PersonDTO personDTO = jsonUtil.personDTOFromJson();
        personDTO.setId(123L);

        Person person = modelMapper.map(personDTO,Person.class);

        assertDTOAndEntityAttributes(personDTO, person);
    }

    @Test
    public void verifyPersontoPersonDTOMapping() throws IOException {
        Person person = jsonUtil.personFromJson();
        person.setId(123L);

        PersonDTO personDTO = modelMapper.map(person, PersonDTO.class);

        assertDTOAndEntityAttributes(personDTO, person);
    }

    private void assertDTOAndEntityAttributes(PersonDTO personDTO, Person person) {
        assertThat(personDTO.getAdviceAndAssistance()).isEqualTo(person.getAdviceAndAssistance());
        assertThat(personDTO.getAllegation()).isEqualTo(person.getAllegation());
        assertThat(personDTO.getDateOfBirth()).isEqualTo(person.getDateOfBirth());
        assertThat(personDTO.getCoAccused()).isEqualTo(person.getCoAccused());
        assertThat(personDTO.getConflictCheck()).isEqualTo(person.getConflictCheck());
        assertThat(personDTO.getConflictCheckDate()).isEqualTo(person.getConflictCheckDate());
        assertThat(personDTO.getConflictCheckName()).isEqualTo(person.getConflictCheckName());
        assertThat(personDTO.getCrm3Advocate()).isEqualTo(person.getCrm3Advocate());
        assertThat(personDTO.getCrownCourt()).isEqualTo(person.getCrownCourt());
        assertThat(personDTO.getEmploymentStatus()).isEqualTo(person.getEmploymentStatus());
        assertThat(personDTO.getEthnicity()).isEqualTo(person.getEthnicity());
        assertThat(personDTO.getExistingClient()).isEqualTo(person.getExistingClient());
        assertThat(personDTO.getForename()).isEqualTo(person.getForename());
        assertThat(personDTO.getFundingDate()).isEqualTo(person.getFundingDate());
        assertThat(personDTO.getId()).isEqualTo(person.getId());
        assertThat(personDTO.getMagistrateCourt()).isEqualTo(person.getMagistrateCourt());
        assertThat(personDTO.getNationality()).isEqualTo(person.getNationality());
        assertThat(personDTO.getNiNumber()).isEqualTo(person.getNiNumber());
        assertThat(personDTO.getPoliceStation1()).isEqualTo(person.getPoliceStation1());
        assertThat(personDTO.getPoliceStation2()).isEqualTo(person.getPoliceStation2());
        assertThat(personDTO.getPreOrderWork()).isEqualTo(person.getPreOrderWork());
        assertThat(personDTO.getPreviousConviction()).isEqualTo(person.getPreviousConviction());
        assertThat(personDTO.getProofOfBenefitsRequested()).isEqualTo(person.getProofOfBenefitsRequested());
        assertThat(personDTO.getRelationshipStatus()).isEqualTo(person.getRelationshipStatus());
        assertThat(personDTO.getRepOrderAppliedFor()).isEqualTo(person.getRepOrderAppliedFor());
        assertThat(personDTO.getRepOrderGranted()).isEqualTo(person.getRepOrderGranted());
        assertThat(personDTO.getRequestSpecificSolicitor()).isEqualTo(person.getRequestSpecificSolicitor());
        assertThat(personDTO.getRequestSpecificSolicitorText()).isEqualTo(person.getRequestSpecificSolicitorText());
        //assertThat(personDTO.getRiskAssessment()).isEqualTo(person.getRiskAssessment());
        assertThat(personDTO.getSourceOfBusiness()).isEqualTo(person.getSourceOfBusiness());
        assertThat(personDTO.getSurname()).isEqualTo(person.getSurname());
        assertThat(personDTO.getTelephoneNumber()).isEqualTo(person.getTelephoneNumber());
        assertThat(personDTO.getTitle()).isEqualTo(person.getTitle());
        assertThat(personDTO.getUfn()).isEqualTo(person.getUfn());
        assertThat(personDTO.getVenue()).isEqualTo(person.getVenue());
        assertAddress(personDTO.getResidenceAddress(),person.getResidenceAddress());
        assertAddress(personDTO.getCorrespondenceAddress(),person.getCorrespondenceAddress());
    }

    private void assertAddress(AddressDTO addressDTO, Address address){
        assertThat(addressDTO.getAddressLine1()).isEqualTo(address.getAddressLine1());
        assertThat(addressDTO.getAddressLine2()).isEqualTo(address.getAddressLine2());
        assertThat(addressDTO.getAddressLine3()).isEqualTo(address.getAddressLine3());
        assertThat(addressDTO.getPostCode()).isEqualTo(address.getPostCode());
        assertThat(addressDTO.getAddressLine1()).isEqualTo(address.getAddressLine1());
    }

}