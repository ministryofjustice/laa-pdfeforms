import { Person } from './../components/person/person';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';
import { of } from 'rxjs/observable/of';
import { Observable } from 'rxjs/Observable';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
}

@Injectable()
export class PersonService {

  constructor(private http: HttpClient) { }

  registerPerson(person: Person): Observable<Object> {
    const uri = 'http://localhost:8080/person/persist';
    return this.http.post(uri, person);
  }

  updatePerson(person: Person): Observable<Object> {
    const uri = 'http://localhost:8080/person/update';
    var message;
    return this.http.put(uri, person);
  }

  searchPersons(term: string): Observable<Person[]> {
    const uri = `http://localhost:8080/person/containingUfn/${term}`;

    if (!term.trim()) {
      return of([]);
    }
    return this.http.get<Person[]>(uri);
  }

  editPerson(ufn: String): Observable<Person> {
    console.log('ufn in edit component ', ufn)
    const uri = `http://localhost:8080/person/${ufn}`;

    if (!ufn.trim()) {
      return of();
    }

    return this.http.get<Person>(uri);
  }
}