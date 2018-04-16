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

  constructor(private http : HttpClient) { }

  registerPerson(person : Person): Observable<Object>{
    console.log('received person in service ',JSON.stringify(person));
    const uri = 'http://localhost:8080/person/persist';
    return this.http.post(uri,person);
      // .pipe(
      //   tap(_ => console.log('created ',_)),
      //   catchError(this.handleError('registerPerson',"some value"))
      // );
  }

  searchPersons(term:string): Observable<Person[]> {
    const uri = `http://localhost:8080/person/${term}`;

    if(!term.trim()){
      return of([]);
    }
    return this.http.get<Person[]>(uri)
    .pipe
    (
      tap(persons => console.log(`found persons matching "${term}"`)),
      catchError(this.handleError('searchPersons',[]))
    );
  }

  private handleError<T> (operation = 'operation', result?: T) {
    return (error: HttpErrorResponse): Observable<T> => {
      console.error(error.status);
      console.log('operation ',operation);
      console.log(error.message)
      return of(result as T);
    };
  }
}
