import { Component, OnInit } from '@angular/core';
import { PersonService } from '../../../services/person.service';
import { CommonData } from '../../../staticdata/common-data';
import { Person } from '../person';
import { HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Disabilities } from '../Disabilities';

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit {


  constructor(private route: ActivatedRoute, private router: Router, private personService: PersonService) { }
  staticData = new CommonData();
  person = new Person();
  disabilityOptions = new Array<Disabilities>();
  serversideErrors: {};

  updatePerson() {

    this.popluateDisabilies();

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

  private popluateDisabilies() {
    this.person.disabilities = new Array<Disabilities>();

    this.disabilityOptions.forEach(option => {

      if (option.selected) {
        var disability = new Disabilities();
        disability.id = option.id;
        disability.personID = option.personID;
        disability.disabilityOption = option.disabilityOption;
        this.person.disabilities.push(disability);
      }

    });
  }

  updateDisability(value, event) {

    console.log('value ', value, " checked status ", event.target.checked);
    console.log('disabilityOptions ', this.disabilityOptions);
    var notFound = true;

    this.disabilityOptions.forEach(option => {

      if (option.disabilityOption === value) {
        option.selected = event.target.checked;
        notFound = false;
      }

    });

    if (notFound && event.target.checked) {
      var newDisabilityOption = new Disabilities();
      newDisabilityOption.selected = true;
      newDisabilityOption.personID = this.person.id;
      newDisabilityOption.disabilityOption = value;
      this.disabilityOptions.push(newDisabilityOption);
    }

  }

  updateExistingClient(event) {
    if (event.target.checked) {
      this.person.existingClient = "Y";
    } else {
      this.person.existingClient = "N";
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

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.personService.editPerson(params['ufn']).subscribe(editablePerson => {
        console.log('edit person received ', editablePerson);
        this.person = editablePerson;
        this.populateDisabilityOptions();
      });
    });
  }

  private populateDisabilityOptions() {
    this.disabilityOptions = new Array<Disabilities>();

    this.person.disabilities.forEach(option => {

      var disability = new Disabilities();
      disability.id = option.id;
      disability.personID = option.personID;
      disability.disabilityOption = option.disabilityOption;
      disability.selected = true;
      this.disabilityOptions.push(disability);

    });

  }

  private isYes(value) {
    if (value === "Y") {
      return true;
    }
    return false;
  }

  isExistingClient() {
    return this.isYes(this.person.existingClient);
  }

  isrequestSpecificSolicitor() {
    return this.isYes(this.person.requestSpecificSolicitor);
  }

  isPreviousConviction() {
    return this.isYes(this.person.previousConviction);
  }

  isConflictCheck() {
    return this.isYes(this.person.conflictCheck);
  }

  isRiskAssessmentDone() {
    return this.isYes(this.person.riskAssessmentDone);
  }

  isDisabilityOption(value) {

    var foundChecked = false;

    this.disabilityOptions.forEach(option => {
      if (option.disabilityOption === value && option.selected) {
        foundChecked = true;
      }
    })

    return foundChecked;
  }

}
