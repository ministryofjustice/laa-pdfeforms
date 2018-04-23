import { Disabilities } from "../Disabilities";
import { Person } from "../person";
import { OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { PersonService } from "../../../services/person.service";
import { CommonData } from "../../../staticdata/common-data";

export class CrudBaseComponent {


    protected disabilityOptions = new Array<Disabilities>();
    protected person = new Person();
    protected staticData = new CommonData();
    protected serversideErrors: {};
    protected riskAssessmentTypeSelectionDisabled = true;
    protected venueOtherDisabled = true;
    protected sameAsResidenceAddressFlag: boolean;

    popluateDisabilies() {
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

        console.log('this.person.disabilities ', this.person.disabilities);
    }

    updateDisability(value, event) {
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

        console.log('disabilityOptions after update ', this.disabilityOptions);
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

    updateRiskAssessment(event) {

        if (event.target.checked) {
            this.person.riskAssessmentDone = "Y";
            this.riskAssessmentTypeSelectionDisabled = false;
        } else {
            this.person.riskAssessmentDone = "N";
            this.riskAssessmentTypeSelectionDisabled = true;
            this.person.riskAssessmentType = undefined;
        }
    }

    populateRiskAssessmentType() {

        if (this.person.riskAssessmentDone === "N") {
            this.person.riskAssessmentType = undefined;
        }

        console.log('before submit this.person.riskAssessmentType ', this.person.riskAssessmentType);
    }

    populateVenueOtherInputField() {
        console.log('this.person.venue ', this.person.venue);
        if (this.person.venue === "Other") {
            this.venueOtherDisabled = false;
        } else {
            this.venueOtherDisabled = true;
            this.person.venueOther = undefined;
        }
    }

    venueSelected(value) {
        console.log('selected ', value);
        this.populateVenueOtherInputField();
    }

    populateAge() {
        console.log('dob ', this.person.dateOfBirth);
        var diff = (new Date().getTime() - new Date(this.person.dateOfBirth).getTime());
        diff /= (1000 * 60 * 60 * 24 * 365.25);
        var calculatedAge = Math.round(diff);

        if (calculatedAge > 0)
            this.person.age = calculatedAge;

        console.log('age ', calculatedAge);
    }

    updateCorrespondanceAddress(event) {

        if(event.target.checked){
            this.person.sameAsResidenceAddress = "Y";
        }else {
            this.person.sameAsResidenceAddress = "N";
        }

        if (event.target.checked) {
            this.person.correspondenceAddress.addressLine1 = this.person.residenceAddress.addressLine1;
            this.person.correspondenceAddress.addressLine2 = this.person.residenceAddress.addressLine2;
            this.person.correspondenceAddress.addressLine3 = this.person.residenceAddress.addressLine3;
            this.person.correspondenceAddress.postCode = this.person.residenceAddress.postCode;
        } 
    }

    updateCorrespondanceAddressOnResidenceAddressChange(){
        if(this.sameAsResidenceAddressFlag){
            this.person.correspondenceAddress.addressLine1 = this.person.residenceAddress.addressLine1;
            this.person.correspondenceAddress.addressLine2 = this.person.residenceAddress.addressLine2;
            this.person.correspondenceAddress.addressLine3 = this.person.residenceAddress.addressLine3;
            this.person.correspondenceAddress.postCode = this.person.residenceAddress.postCode;
        }
    }

}