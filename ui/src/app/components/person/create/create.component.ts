import { CommonData } from './../../../staticdata/common-data';
import { PersonService } from './../../../services/person.service';

import { Component } from '@angular/core';
import { Person }  from './../person'

@Component({
  selector: 'app-create',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.css']
})
export class CreatePersonComponent {

  constructor(private personService : PersonService){}

  staticData = new CommonData();
  person = new Person();

  title : String;
  foreName : String;
  surname : String;

  registerPerson(){
    console.log('registerPerson called in create person component ',this.person);
    this.personService.registerPerson(this.person).subscribe(res => console.log('person component ',res));
    console.log('response from person service received ');
  }

  updateDisability(value,event) {

    if(event.target.checked) {
      this.person.disabilityOptions.push(value);
    } else {
      var index = this.person.disabilityOptions.indexOf(value);
      if(index > -1){
        this.person.disabilityOptions.splice(index,1);
      }
    }
  }

}
