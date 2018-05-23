import { Attendance } from './../../registration/model/Attendance';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ClientattendanceService } from '../../../../services/client/attendance/clientattendance.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-attendanceedit',
  templateUrl: './attendanceedit.component.html',
  styleUrls: ['./attendanceedit.component.scss']
})
export class AttendanceEditComponent implements OnInit {

  private attendance: Attendance;
  private serversideErrors: {};

  constructor(private route: ActivatedRoute, private router: Router, private clientattendanceService: ClientattendanceService) { }

  ngOnInit() {
    console.log("params ", this.route.params);
    this.route.params.subscribe(params => {
      this.clientattendanceService.findAttendanceById(params['id']).subscribe(
        data => {
          this.attendance = data;
        },
        (err: HttpErrorResponse) => {
          if (err.error.details != null) {
            console.log("error ", JSON.stringify(err.error.details));
            this.serversideErrors = err.error.details;
          } else {
            console.log('server side error ', err);
            this.serversideErrors = [err.message];
          }
        }
      );
    });
  }

}
