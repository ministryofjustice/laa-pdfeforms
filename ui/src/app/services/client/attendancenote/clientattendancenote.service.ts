import { Injectable } from '@angular/core';
import { ClientBaseService } from '../common/ClientBaseService';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { of } from 'rxjs/observable/of';
import { AttendanceNote } from '../../../components/client/registration/model/AttendanceNote';


@Injectable()
export class ClientattendanceNoteService extends ClientBaseService{

  constructor(protected http: HttpClient) {
    super();
  }

  makeAttendanceNote(attendanceNote: AttendanceNote): Observable<Object> {
    const uri = `${this.API_URL}/client/attendanceNote/make`;
    return this.http.post(uri, attendanceNote);
  }

  findAttendanceNotesByUfn(ufn: string): Observable<AttendanceNote[]> {
    const uri = `${this.API_URL}/client/attendanceNote/allForUFN/${ufn}`;
    console.log('search URI ', uri);

    if (!ufn.trim()) {
      return of([]);
    }
    return this.http.get<AttendanceNote[]>(uri);
  }

  findAttendanceNoteById(id: string): Observable<AttendanceNote> {
    const uri = `${this.API_URL}/client/attendanceNote/forID/${id}`;
    if (!id.trim()) {
      return of();
    }
    return this.http.get<AttendanceNote>(uri);
  }

  updateAttendanceNote(attendanceNote: AttendanceNote): Observable<Object> {
    const uri = `${this.API_URL}/client/attendanceNote/update`;
    return this.http.put(uri, attendanceNote);
  }

}
