import { Attendance } from './../../registration/model/Attendance';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Subject } from 'rxjs/Subject';
import { ClientattendanceService } from '../../../../services/client/attendance/clientattendance.service';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';
import { of } from 'rxjs/observable/of';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-attendanceindex',
  templateUrl: './attendanceindex.component.html',
  styleUrls: ['./attendanceindex.component.scss']
})
export class AttendanceindexComponent implements OnInit {

  private attendance$: Observable<Attendance[]>;

  private searchTerms = new Subject<String>();
  private serversideErrors: {};

  constructor(private clientattendanceService: ClientattendanceService) { }

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

      switchMap((term: string) => this.clientattendanceService.findAttendancesByUfn(term))
    ).subscribe(
      data => {
        this.attendance$ = of(data);
        this.serversideErrors = undefined;
      },
      (err: HttpErrorResponse) => {
        this.attendance$ = of([]);
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
