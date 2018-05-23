import { Attendance } from './../../registration/model/Attendance';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AttendanceNote } from '../../registration/model/AttendanceNote';
import { ClientattendanceNoteService } from './../../../../services/client/attendancenote/clientattendancenote.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-attendancenoteedit',
  templateUrl: './attendancenoteedit.component.html',
  styleUrls: ['./attendancenoteedit.component.scss']
})
export class ClientAttendanceNoteEditComponent implements OnInit {
  private serversideErrors: {};
  private attednanceNote = new AttendanceNote();

  constructor(private route: ActivatedRoute, private router: Router, private clientattendanceNoteService: ClientattendanceNoteService) { }

  ngOnInit(): void {
    console.log('params ', this.route.params)
    this.route.params.subscribe(params => {
      this.clientattendanceNoteService.findAttendanceNoteById(params['id']).subscribe(note => {
        this.attednanceNote = note;
      });
    });
  }

  private updateAttendanceNote() {

    this.clientattendanceNoteService.updateAttendanceNote(this.attednanceNote).subscribe(
      data => {
        console.log('Update updateAttendanceNote component ', data);
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
