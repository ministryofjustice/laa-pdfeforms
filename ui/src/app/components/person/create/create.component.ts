import { CrudBaseComponent } from './../common/CrudBaseComponent';
import { Component } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { PersonService } from '../../../services/person.service';

@Component({
  selector: 'app-create',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.css']
})
export class CreatePersonComponent extends CrudBaseComponent {


  constructor(protected route: ActivatedRoute, protected router: Router, protected personService: PersonService) {
    super();
  }

  registerPerson() {
    this.serversideErrors = undefined;
    this.popluateDisabilies();
    console.log('register person called in created component with person ', this.person);
    this.personService.registerPerson(this.person).subscribe(
      data => {
        console.log('person component ', data);
        this.router.navigate(['home']);
      },
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
}
