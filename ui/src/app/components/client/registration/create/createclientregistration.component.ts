import { ClientRegistrationService } from './../../../../services/client/registration/clientregistration.service';
import { Component } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { RegisterClientBaseComponent } from '../../common/RegisterClientBaseComponent';

@Component({
  selector: 'app-create',
  templateUrl: './createclientregistration.component.html',
  styleUrls: ['./createclientregistration.component.css']
})
export class CreateClientRegistrationComponent extends RegisterClientBaseComponent {

  constructor(protected route: ActivatedRoute, protected router: Router, protected clientRegistrationService: ClientRegistrationService) {
    super();
  }

  registerClient() {
    this.serversideErrors = undefined;
    this.popluateDisabilies();
    console.log('register client called in created component with client ', this.client);
    this.clientRegistrationService.registerClient(this.client).subscribe(
      data => {
        console.log('client component ', data);
        this.router.navigate(['index']);
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
