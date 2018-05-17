import { ClientattendanceService } from './../../../../services/client/attendance/clientattendance.service';
import { Attendance } from './../../registration/model/Attendance';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-attendancenoteedit',
  templateUrl: './attendancenoteedit.component.html',
  styleUrls: ['./attendancenoteedit.component.scss']
})
export class ClientAttendanceNoteEditComponent implements OnInit {

  ngOnInit(): void {
    throw new Error("Method not implemented.");
  }
  constructor(){}
}
