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
    var message;
    this.http.post(uri, person).subscribe(
      res => {
        console.log(res);
        message = res;
      },
      error => {
        console.log(error);
        message = error;
      }
    );
    return of(message);
  }

  updatePerson(person: Person): Observable<Object> {
    const uri = 'http://localhost:8080/person/update';
    var message;
    this.http.put(uri, person).subscribe(
      res => {
        console.log(res);
        message = res;
      },
      error => {
        console.log(error);
        message = error;
      }
    );
    return of(message);
  }
 
  searchPersons(term: string): Observable<Person[]> {
    const uri = `http://localhost:8080/person/containingUfn/${term}`;

    if (!term.trim()) {
      return of([]);
    }
    return this.http.get<Person[]>(uri)
      .pipe
      (
      tap(persons => console.log(`found ${persons.length} persons matching "${term}"`)),
      catchError(this.handleError('searchPersons', []))
      );
  }

  editPerson(ufn: String): Observable<Person> {
    console.log('ufn in edit component ',ufn)
    const uri = `http://localhost:8080/person/${ufn}`;

    if (!ufn.trim()) {
      return of();
    }

    return this.http.get<Person>(uri);
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: HttpErrorResponse): Observable<T> => {
      console.error(error.status);
      console.log('operation ', operation);
      console.log(error.message)
      return of(result as T);
    };
  }
}
