import { Observable } from 'rxjs/Observable';
import { PersonService } from './../../../services/person.service';
import { Component, OnInit } from '@angular/core';
import { Person } from '../person';
import { Subject } from 'rxjs/Subject';
import { switchMap, debounceTime, distinctUntilChanged } from 'rxjs/operators';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  persons$ : Observable<Person[]>;
  private searchTerms = new Subject<String>();
  
  constructor(private personService : PersonService) { }

  search(term: String){
    this.searchTerms.next(term);
  }

  ngOnInit() {
    this.persons$ = this.searchTerms.pipe(

      // wait 300ms after each keystroke before considering the term
      debounceTime(300),

      // switch to new search observable each time the term changes
      distinctUntilChanged(),

      switchMap((term: string) => this.personService.searchPersons(term))
    );
  }

}
