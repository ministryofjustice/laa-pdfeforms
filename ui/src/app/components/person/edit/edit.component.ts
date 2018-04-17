import { Component, OnInit } from '@angular/core';
import { PersonService } from '../../../services/person.service';
import { CommonData } from '../../../staticdata/common-data';
import { Person } from '../person';
import { HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit {


  constructor(private route: ActivatedRoute, private router: Router, private personService: PersonService) { }
  staticData = new CommonData();
  person = new Person();
  serversideErrors: {};

  updatePerson() {
    this.personService.updatePerson(this.person).subscribe(
      data => {
        console.log('Update person component ', data);
        this.router.navigate(['home']);
      },
      (err: HttpErrorResponse) => {
        if (err.error.details != null) {
          console.log("error ", JSON.stringify(err.error.details));
          this.serversideErrors = err.error.details;
        } else {
          console.log('server side error ', err);
          this.serversideErrors = [err.message];
        }
      }
    );
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

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.personService.editPerson(params['ufn']).subscribe(editablePerson => {
        console.log('edit person received ',editablePerson);
        this.person = editablePerson;
      });
    });
  }


}
