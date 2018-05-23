import { Attendance } from './../../../components/client/registration/model/Attendance';
import { Injectable } from '@angular/core';
import { ClientBaseService } from '../common/ClientBaseService';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { of } from 'rxjs/observable/of';

@Injectable()
export class ClientattendanceService extends ClientBaseService {

  constructor(protected http: HttpClient) {
    super();
  }

  makeAttendance(attendance: Attendance): Observable<Object> {
    const uri = `${this.API_URL}/client/attendance/make`;
    return this.http.post(uri, attendance);
  }

  findAttendancesByUfn(ufn: string): Observable<Attendance[]> {
    const uri = `${this.API_URL}/client/attendance/allForUFN/${ufn}`;
    console.log('search URI ', uri);

    if (!ufn.trim()) {
      return of([]);
    }
    return this.http.get<Attendance[]>(uri);
  }

  findAttendanceById(id: string): Observable<Attendance> {
    const uri = `${this.API_URL}/client/attendance/forID/${id}`;
    if (!id.trim()) {
      return of();
    }
    return this.http.get<Attendance>(uri);
  }

  updateAttendance(attendance: Attendance): Observable<Object> {
    const uri = `${this.API_URL}/client/attendance/update`;
    return this.http.put(uri, attendance);
  }

}
