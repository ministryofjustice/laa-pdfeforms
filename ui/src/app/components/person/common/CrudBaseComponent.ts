import { Disabilities } from "../Disabilities";
import { Person } from "../person";
import { OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { PersonService } from "../../../services/person.service";
import { CommonData } from "../../../staticdata/common-data";

export class CrudBaseComponent  {


    protected disabilityOptions = new Array<Disabilities>();
    protected person = new Person();
    protected staticData = new CommonData();
    protected serversideErrors: {};
  
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

        console.log('this.person.disabilities ',this.person.disabilities);
    }

    updateDisability(value, event) {

        //console.log('value ', value, " checked status ", event.target.checked);
        //console.log('disabilityOptions ', this.disabilityOptions);
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
    
        console.log('disabilityOptions after update ',this.disabilityOptions);
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


}