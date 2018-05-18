import { Observable } from 'rxjs/Observable';
import { Component, OnInit } from '@angular/core';
import { Subject } from 'rxjs/Subject';
import { switchMap, debounceTime, distinctUntilChanged } from 'rxjs/operators';
import { HttpErrorResponse } from '@angular/common/http';
import { of } from 'rxjs/observable/of';
import { AttendanceNote } from '../../registration/model/AttendanceNote';
import { ClientattendanceNoteService } from '../../../../services/client/attendancenote/clientattendancenote.service';

@Component({
  selector: 'app-attendancenoteindex',
  templateUrl: './attendancenoteindex.component.html',
  styleUrls: ['./attendancenoteindex.component.scss']
})
export class ClientAttendancenoteindexComponent implements OnInit {

  private attendanceNote$: Observable<AttendanceNote[]>;

  private searchTerms = new Subject<String>();
  private serversideErrors: {};

  constructor(private clientattendanceNoteService: ClientattendanceNoteService) { }

  ngOnInit() {
    this.startSearch();
  }

  search(term: String) {
    console.log('search term ', term);

    if (this.serversideErrors !== undefined) {
      console.log('start search to be called ')
      this.startSearch();
    }

    this.searchTerms.next(term);
  }

  private startSearch() {
    console.log('start search called ')
    this.searchTerms.pipe(

      // wait 300ms after each keystroke before considering the term
      debounceTime(300),

      // switch to new search observable each time the term changes
      distinctUntilChanged(),

      switchMap((term: string) => this.clientattendanceNoteService.findAttendanceNotesByUfn(term))
    ).subscribe(
      data => {
        this.attendanceNote$ = of(data);
        this.serversideErrors = undefined;
      },
      (err: HttpErrorResponse) => {
        this.attendanceNote$ = of([]);
        if (err.error.details != null) {
          console.log("error ", JSON.stringify(err.error.details));
          this.serversideErrors = err.error.details;
        } else {
          console.log('server side error ', err);
          this.serversideErrors = ['Unable to fetch information from the server', 'Please try again later'];
        }
      }
    );

  }

}
