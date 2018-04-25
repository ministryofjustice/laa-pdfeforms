import { Client } from './../registration/model/Client';
import { OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { Disabilities } from "../registration/model/Disabilities";
import { CommonData } from "../../../common/staticdata/common-data";

export class RegisterClientBaseComponent {
    protected disabilityOptions = new Array<Disabilities>();
    protected client = new Client();
    protected staticData = new CommonData();
    protected serversideErrors: {};
    protected riskAssessmentTypeSelectionDisabled = true;
    protected venueOtherDisabled = true;
    protected sameAsResidenceAddressFlag: boolean;

    protected popluateDisabilies() {
        this.client.disabilities = new Array<Disabilities>();

        this.disabilityOptions.forEach(option => {

            if (option.selected) {
                var disability = new Disabilities();
                disability.id = option.id;
                disability.clientID = option.clientID;
                disability.disabilityOption = option.disabilityOption;
                this.client.disabilities.push(disability);
            }

        });

        console.log('this.client.disabilities ', this.client.disabilities);
    }
    
    protected populateRiskAssessmentType() {

        if (this.client.riskAssessmentDone === "N") {
            this.client.riskAssessmentType = undefined;
        }

        console.log('before submit this.client.riskAssessmentType ', this.client.riskAssessmentType);
    }

    protected populateVenueOtherInputField() {
        console.log('this.client.venue ', this.client.venue);
        if (this.client.venue === "Other") {
            this.venueOtherDisabled = false;
        } else {
            this.venueOtherDisabled = true;
            this.client.venueOther = undefined;
        }
    }

    private updateDisability(value, event) {
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
            newDisabilityOption.clientID = this.client.id;
            newDisabilityOption.disabilityOption = value;
            this.disabilityOptions.push(newDisabilityOption);
        }

        console.log('disabilityOptions after update ', this.disabilityOptions);
    }

    private updateExistingClient(event) {
        if (event.target.checked) {
            this.client.existingClient = "Y";
        } else {
            this.client.existingClient = "N";
        }
    }

    private updateRequestSpecificSolicitor(event) {
        if (event.target.checked) {
            this.client.requestSpecificSolicitor = "Y";
        } else {
            this.client.requestSpecificSolicitor = "N";
        }
    }

    private updatePreviousConviction(event) {
        if (event.target.checked) {
            this.client.previousConviction = "Y";
        } else {
            this.client.previousConviction = "N";
        }
    }

    private updateConflictCheck(event) {
        if (event.target.checked) {
            this.client.conflictCheck = "Y";
        } else {
            this.client.conflictCheck = "N";
        }
    }

    private updateRiskAssessment(event) {

        if (event.target.checked) {
            this.client.riskAssessmentDone = "Y";
            this.riskAssessmentTypeSelectionDisabled = false;
        } else {
            this.client.riskAssessmentDone = "N";
            this.riskAssessmentTypeSelectionDisabled = true;
            this.client.riskAssessmentType = undefined;
        }
    }


    private venueSelected(value) {
        console.log('selected ', value);
        this.populateVenueOtherInputField();
    }

    private populateAge() {
        console.log('dob ', this.client.dateOfBirth);
        var diff = (new Date().getTime() - new Date(this.client.dateOfBirth).getTime());
        diff /= (1000 * 60 * 60 * 24 * 365.25);
        var calculatedAge = Math.round(diff);

        if (calculatedAge > 0)
            this.client.age = calculatedAge;

        console.log('age ', calculatedAge);
    }

    private updateCorrespondanceAddress(event) {

        if (event.target.checked) {
            this.client.sameAsResidenceAddress = "Y";
        } else {
            this.client.sameAsResidenceAddress = "N";
        }

        if (event.target.checked) {
            this.client.correspondenceAddress.addressLine1 = this.client.residenceAddress.addressLine1;
            this.client.correspondenceAddress.addressLine2 = this.client.residenceAddress.addressLine2;
            this.client.correspondenceAddress.addressLine3 = this.client.residenceAddress.addressLine3;
            this.client.correspondenceAddress.postCode = this.client.residenceAddress.postCode;
        }
    }

    private updateCorrespondanceAddressOnResidenceAddressChange() {
        if (this.sameAsResidenceAddressFlag) {
            this.client.correspondenceAddress.addressLine1 = this.client.residenceAddress.addressLine1;
            this.client.correspondenceAddress.addressLine2 = this.client.residenceAddress.addressLine2;
            this.client.correspondenceAddress.addressLine3 = this.client.residenceAddress.addressLine3;
            this.client.correspondenceAddress.postCode = this.client.residenceAddress.postCode;
        }
    }

}