import { ClientRegistrationService } from './../../../../services/client/registration/clientregistration.service';
import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Disabilities } from '../model/Disabilities';
import { RegisterClientBaseComponent } from '../../common/RegisterClientBaseComponent';

@Component({
  selector: 'app-edit',
  templateUrl: './editclientregistration.component.html',
  styleUrls: ['./editclientregistration.component.css']
})
export class EditClientRegistrationComponent extends RegisterClientBaseComponent implements OnInit {

  constructor(protected route: ActivatedRoute, protected router: Router, protected clientRegistrationService: ClientRegistrationService) {
    super();
  }

  ngOnInit(): void {
    console.log('params ',this.route.params)
    this.route.params.subscribe(params => {
      this.clientRegistrationService.editClientRegistration(params['ufn']).subscribe(editableclient => {
        console.log('edit client received ', editableclient);
        this.client = editableclient;
        this.initializeDisabilityOptions();
        this.initializeRiskAssessmentTypeSelectionDisabled();
        this.initiateVenueOtherInputField();
        this.initializeSameAsResidenceAddressFlag();
      });
    });
  }

  private updateclient() {
    this.popluateDisabilies();
    this.populateRiskAssessmentType();

    this.clientRegistrationService.updateClientRegistration(this.client).subscribe(
      data => {
        console.log('Update client component ', data);
        this.router.navigate(['index']);
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

  private initializeSameAsResidenceAddressFlag() {
    if (this.client.sameAsResidenceAddress === "Y") {
      this.sameAsResidenceAddressFlag = true;
    } else {
      this.sameAsResidenceAddressFlag = false;
    }
  }

  private initializeRiskAssessmentTypeSelectionDisabled() {
    if (this.client.riskAssessmentDone === "Y") {
      this.riskAssessmentTypeSelectionDisabled = false;
    }
  }

  private initializeDisabilityOptions() {
    this.disabilityOptions = new Array<Disabilities>();

    this.client.disabilities.forEach(option => {

      var disability = new Disabilities();
      disability.id = option.id;
      disability.clientID = option.clientID;
      disability.disabilityOption = option.disabilityOption;
      disability.selected = true;
      this.disabilityOptions.push(disability);

    });
  }

  private initiateVenueOtherInputField() {
    this.populateVenueOtherInputField();
  }

  private isYes(value) {
    if (value === "Y") {
      return true;
    }
    return false;
  }

  private isExistingClient() {
    return this.isYes(this.client.existingClient);
  }

  private isrequestSpecificSolicitor() {
    return this.isYes(this.client.requestSpecificSolicitor);
  }

  private isPreviousConviction() {
    return this.isYes(this.client.previousConviction);
  }

  private isConflictCheck() {
    return this.isYes(this.client.conflictCheck);
  }

  private isRiskAssessmentDone() {
    return this.isYes(this.client.riskAssessmentDone);
  }

  private isDisabilityOption(value) {

    var foundChecked = false;

    this.disabilityOptions.forEach(option => {
      if (option.disabilityOption === value && option.selected) {
        foundChecked = true;
      }
    })

    return foundChecked;
  }
}
