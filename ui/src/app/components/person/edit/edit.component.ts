import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { Disabilities } from '../Disabilities';
import { CrudBaseComponent } from '../common/CrudBaseComponent';
import { ActivatedRoute, Router } from '@angular/router';
import { PersonService } from '../../../services/person.service';

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent extends CrudBaseComponent implements OnInit{

  constructor(protected route: ActivatedRoute, protected router: Router, protected personService: PersonService) {
    super();
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.personService.editPerson(params['ufn']).subscribe(editablePerson => {
        console.log('edit person received ', editablePerson);
        this.person = editablePerson;
        this.initializeDisabilityOptions();
        this.initializeRiskAssessmentTypeSelectionDisabled();
        this.initiateVenueOtherInputField();
      });
    });
  }

  updatePerson() {

    this.popluateDisabilies();
    this.populateRiskAssessmentType();

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

  private initializeRiskAssessmentTypeSelectionDisabled(){
    if(this.person.riskAssessmentDone === "Y"){
      this.riskAssessmentTypeSelectionDisabled = false;
    }
  }

  private initializeDisabilityOptions() {
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

  private initiateVenueOtherInputField(){
    this.populateVenueOtherInputField();
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
