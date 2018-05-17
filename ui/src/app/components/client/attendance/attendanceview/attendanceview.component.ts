import { Component, OnInit } from '@angular/core';
import { Attendance } from '../../registration/model/Attendance';
import { ActivatedRoute, Router } from '@angular/router';
import { ClientattendanceService } from '../../../../services/client/attendance/clientattendance.service';

@Component({
  selector: 'app-attendanceview',
  templateUrl: './attendanceview.component.html',
  styleUrls: ['./attendanceview.component.scss']
})
export class AttendanceviewComponent implements OnInit {

  attendance = new Attendance();

  constructor(protected route: ActivatedRoute, protected router: Router, protected clientAttendanceService: ClientattendanceService) { }

  ngOnInit() {
    console.log('params ', this.route.params)
    this.route.params.subscribe(params => {
      this.clientAttendanceService.findAttendanceById(params['id']).subscribe(data => {
        console.log('data',data);
        this.attendance = data;
      });
    });
  }


}
