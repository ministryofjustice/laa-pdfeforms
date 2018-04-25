import { Observable } from 'rxjs/Observable';
import { Component, OnInit } from '@angular/core';
import { Subject } from 'rxjs/Subject';
import { switchMap, debounceTime, distinctUntilChanged } from 'rxjs/operators';
import { of } from 'rxjs/observable/of';
import { HttpErrorResponse } from '@angular/common/http';
import { Client } from '../model/Client';
import { ClientRegistrationService } from './../../../../services/client/registration/clientregistration.service';

@Component({
  selector: 'app-home',
  templateUrl: './clientindex.component.html',
  styleUrls: ['./clientindex.component.css']
})
export class ClientRegistrationIndexComponent implements OnInit {

  private persons$: Observable<Client[]>;
  private searchTerms = new Subject<String>();
  private serversideErrors: {};

  constructor(private clientRegistrationService: ClientRegistrationService) { }

  search(term: String) {
    console.log('search term ', term);

    if (this.serversideErrors !== undefined) {
      this.startSearch();
    }

    this.searchTerms.next(term);
  }

  ngOnInit() {
    this.startSearch();
  }

  private startSearch() {
    this.searchTerms.pipe(

      // wait 300ms after each keystroke before considering the term
      debounceTime(300),

      // switch to new search observable each time the term changes
      distinctUntilChanged(),

      switchMap((term: string) => this.clientRegistrationService.searchClients(term))
    ).subscribe(
      data => {
        this.persons$ = of(data);
        this.serversideErrors = undefined;
      },
      (err: HttpErrorResponse) => {
        this.persons$ = of([]);
        if (err.error.details != null) {
          console.log("error ", JSON.stringify(err.error.details));
          this.serversideErrors = err.error.details;
        } else {
          console.log('server side error ', err);
          this.serversideErrors = ['Unable to fetch information from the server', err.message];
        }
      }
    );

  }
}
