import { ClientattendanceNoteService } from './services/client/attendancenote/clientattendancenote.service';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AppComponent } from './app.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from "@angular/common/http";
import { appRoutes } from './common/route/routerConfig';
import { CreateClientRegistrationComponent } from './components/client/registration/create/createclientregistration.component';
import { EditClientRegistrationComponent } from './components/client/registration/edit/editclientregistration.component';
import { ClientRegistrationIndexComponent } from './components/client/registration/index/clientindex.component';
import { ClientRegistrationService } from './services/client/registration/clientregistration.service';
import { ClientattendanceService } from './services/client/attendance/clientattendance.service';
import { ClientAttendancenoteindexComponent } from './components/client/attendancenote/attendancenoteindex/attendancenoteindex.component';
import { ClientAttendanceNoteViewComponent } from './components/client/attendancenote/attendancenoteview/attendancenoteview.component';
import { ClientAttendanceNoteEditComponent } from './components/client/attendancenote/attendancenoteedit/attendancenoteedit.component';

@NgModule({
  declarations: [
    AppComponent,
    CreateClientRegistrationComponent,
    EditClientRegistrationComponent,
    ClientRegistrationIndexComponent,
    ClientAttendancenoteindexComponent,
    ClientAttendanceNoteViewComponent,
    ClientAttendanceNoteEditComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(appRoutes)
  ],
  providers: [ClientRegistrationService,ClientattendanceService,ClientattendanceNoteService],
  bootstrap: [AppComponent]
})

export class AppModule { }
