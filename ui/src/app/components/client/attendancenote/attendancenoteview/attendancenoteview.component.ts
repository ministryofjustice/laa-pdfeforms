import { ClientattendanceNoteService } from './../../../../services/client/attendancenote/clientattendancenote.service';
import { AttendanceNote } from './../../registration/model/AttendanceNote';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-attendancenoteview',
  templateUrl: './attendancenoteview.component.html',
  styleUrls: ['./attendancenoteview.component.scss']
})
export class ClientAttendanceNoteViewComponent implements OnInit {

  attednanceNote: AttendanceNote;

  constructor(protected route: ActivatedRoute, protected router: Router, protected clientattendanceNoteService: ClientattendanceNoteService) { }

  ngOnInit() {
    console.log('params ', this.route.params)
    this.route.params.subscribe(params => {
      this.clientattendanceNoteService.findAttendanceById(params['id']).subscribe(note => {
        this.attednanceNote = note;
      });
    });
  }

}
