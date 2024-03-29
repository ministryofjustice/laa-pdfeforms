import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';
import { of } from 'rxjs/observable/of';
import { Observable } from 'rxjs/Observable';
import { environment } from '../../../../environments/environment';
import { Client } from './../../../components/client/registration/model/Client';
import { ClientBaseService } from '../common/ClientBaseService';

@Injectable()
export class ClientRegistrationService extends ClientBaseService {

  constructor(protected http: HttpClient) {
    super();
  }

  registerClient(client: Client): Observable<Object> {
    const uri = `${this.API_URL}/client/register`;
    return this.http.post(uri, client);
  }

  updateClientRegistration(client: Client): Observable<Object> {
    const uri = `${this.API_URL}/client/update`;
    return this.http.put(uri, client);
  }

  searchClients(term: string): Observable<Client[]> {
    const uri = `${this.API_URL}/client/containingUfn/${term}`;
    console.log('search URI ', uri);

    if (!term.trim()) {
      return of([]);
    }
    return this.http.get<Client[]>(uri);
  }

  editClientRegistration(ufn: String): Observable<Client> {
    console.log('ufn in edit component ', ufn)
    const uri = `${this.API_URL}/client/${ufn}`;

    if (!ufn.trim()) {
      return of();
    }

    return this.http.get<Client>(uri);
  }
}