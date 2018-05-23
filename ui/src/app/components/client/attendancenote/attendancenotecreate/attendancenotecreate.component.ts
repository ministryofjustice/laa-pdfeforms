import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ClientattendanceNoteService } from '../../../../services/client/attendancenote/clientattendancenote.service';
import { HttpErrorResponse } from '@angular/common/http';
import { AttendanceNote } from '../../registration/model/AttendanceNote';

@Component({
  selector: 'app-attendancenotecreate',
  templateUrl: './attendancenotecreate.component.html',
  styleUrls: ['./attendancenotecreate.component.scss']
})
export class AttendanceNoteCreateComponent {

  private serversideErrors: {};
  private attednanceNote = new AttendanceNote();

  constructor(private route: ActivatedRoute, private router: Router, private clientattendanceNoteService: ClientattendanceNoteService) { }

  private makeAttendanceNote() {

    this.clientattendanceNoteService.makeAttendanceNote(this.attednanceNote).subscribe(
      data => {
        console.log('makeAttendanceNote : AttendanceNoteCreateComponent ', data);
        this.router.navigate(['client-attendancenote-index']);
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
  }
}
