import { Attendance } from './../../registration/model/Attendance';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AttendanceNote } from '../../registration/model/AttendanceNote';
import { ClientattendanceNoteService } from './../../../../services/client/attendancenote/clientattendancenote.service';

@Component({
  selector: 'app-attendancenoteedit',
  templateUrl: './attendancenoteedit.component.html',
  styleUrls: ['./attendancenoteedit.component.scss']
})
export class ClientAttendanceNoteEditComponent implements OnInit {
  attednanceNote = new AttendanceNote();

  constructor(protected route: ActivatedRoute, protected router: Router, protected clientattendanceNoteService: ClientattendanceNoteService) { }

  ngOnInit(): void {
    console.log('params ', this.route.params)
    this.route.params.subscribe(params => {
      this.clientattendanceNoteService.findAttendanceNoteById(params['id']).subscribe(note => {
        this.attednanceNote = note;
      });
    });
  }
}
