import { Disabilities } from './../Disabilities';
import { CommonData } from './../../../staticdata/common-data';
import { PersonService } from './../../../services/person.service';

import { Component } from '@angular/core';
import { Person } from './../person'
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-create',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.css']
})
export class CreatePersonComponent {
  constructor(private personService: PersonService) { }
  staticData = new CommonData();
  person = new Person();
  disabilityOptions = [];
  serversideErrors: {};

  registerPerson() {
    this.popluateDisabilies();
    console.log('register person called in created component with person ', this.person);
    this.personService.registerPerson(this.person).subscribe(
      data => console.log('person component ', data),
      (err: HttpErrorResponse) => {
        if (err.error.details != null) {
          console.log("error ", JSON.stringify(err.error.details));
          this.serversideErrors = err.error.details;
        } else {
          console.log('server side error ', err.message);
          this.serversideErrors = [err.message];
        }
      }
    );
  }

  popluateDisabilies() {
    this.disabilityOptions.forEach(option => {
      console.log('selected option ',option);
      var d = new Disabilities();
      d.disabilityOption = option;
      console.log('d ',d);
      this.person.disabilities.push(d);
      console.log(JSON.stringify(this.person.disabilities));
    });
  }
  updateDisability(value, event) {

    if (event.target.checked) {
      this.disabilityOptions.push(value);
    } else {
      var index = this.disabilityOptions.indexOf(value);
      if (index > -1) {
        this.disabilityOptions.splice(index, 1);
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
