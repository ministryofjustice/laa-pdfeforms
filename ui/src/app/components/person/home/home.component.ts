import { Observable } from 'rxjs/Observable';
import { PersonService } from './../../../services/person.service';
import { Component, OnInit } from '@angular/core';
import { Person } from '../person';
import { Subject } from 'rxjs/Subject';
import { switchMap, debounceTime, distinctUntilChanged } from 'rxjs/operators';
import { of } from 'rxjs/observable/of';
import { HttpErrorResponse } from '@angular/common/http';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  private persons$ : Observable<Person[]>;
  private searchTerms = new Subject<String>();
  private serversideErrors: {};
  
  constructor(private personService : PersonService) { }

  search(term: String){
    console.log('search term ',term);

    if(this.serversideErrors !== undefined){
      this.startSearch();
    } 
    
    this.searchTerms.next(term);
  }

  ngOnInit() {
    this.startSearch();
  }

  private startSearch(){
    this.searchTerms.pipe(

      // wait 300ms after each keystroke before considering the term
      debounceTime(300),

      // switch to new search observable each time the term changes
      distinctUntilChanged(),

      switchMap((term: string) => this.personService.searchPersons(term))
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
          this.serversideErrors = ['Unable to fetch information from the server',err.message];
        }
      }
    );

  }
}
