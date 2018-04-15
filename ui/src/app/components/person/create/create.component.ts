import { CommonData } from './../../../staticdata/common-data';
import { PersonService } from './../../../services/person.service';

import { Component } from '@angular/core';
import { Person } from './../person'

@Component({
  selector: 'app-create',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.css']
})
export class CreatePersonComponent {
  constructor(private personService: PersonService) { }
  staticData = new CommonData();
  person = new Person();

  registerPerson() {
    this.personService.registerPerson(this.person).subscribe(res => console.log('person component ', res));
  }

  updateDisability(value, event) {

    if (event.target.checked) {
      this.person.disabilityOptions.push(value);
    } else {
      var index = this.person.disabilityOptions.indexOf(value);
      if (index > -1) {
        this.person.disabilityOptions.splice(index, 1);
      }
    }
  }

  updateExistingClient(event) {
    if (event.target.checked) {
      this.person.existingClient = "Y";
    } else {
      this.person.existingClient = "NÍ";
    }
  }

  updateRequestSpecificSolicitor(event) {
    if (event.target.checked) {
      this.person.requestSpecificSolicitor = "Y";
    } else {
      this.person.requestSpecificSolicitor = "N";
    }
  }

  updatePreviousConviction(event) {
    if (event.target.checked) {
      this.person.previousConviction = "Y";
    } else {
      this.person.previousConviction = "N";
    }
  }

  updateConflictCheck(event) {
    if (event.target.checked) {
      this.person.conflictCheck = "Y";
    } else {
      this.person.conflictCheck = "N";
    }
  }

  updateRiskAssessmentDone(event) {
    if (event.target.checked) {
      this.person.riskAssessmentDone = "Y";
    } else {
      this.person.riskAssessmentDone = "N";
    }
  }

  updateRiskAssessmentType(value, event) {
    this.person.riskAssessmentType = event.target.value;
  }
}
