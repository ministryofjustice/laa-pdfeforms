import { AttendanceindexComponent } from './../../components/client/attendance/attendanceindex/attendanceindex.component';
import { EditClientRegistrationComponent } from './../../components/client/registration/edit/editclientregistration.component';
import { CreateClientRegistrationComponent } from './../../components/client/registration/create/createclientregistration.component';
import { ClientRegistrationIndexComponent } from "../../components/client/registration/index/clientindex.component";
import { ClientAttendancenoteindexComponent } from '../../components/client/attendancenote/attendancenoteindex/attendancenoteindex.component';
import { Routes } from "@angular/router";
import { ClientAttendanceNoteEditComponent } from '../../components/client/attendancenote/attendancenoteedit/attendancenoteedit.component';
import { ClientAttendanceNoteViewComponent } from '../../components/client/attendancenote/attendancenoteview/attendancenoteview.component';
import { AttendanceviewComponent } from '../../components/client/attendance/attendanceview/attendanceview.component';
import { AttendanceNoteCreateComponent } from '../../components/client/attendancenote/attendancenotecreate/attendancenotecreate.component';
import { AttendanceEditComponent } from '../../components/client/attendance/attendanceedit/attendanceedit.component';

export const appRoutes: Routes = [
    {
        path: 'client-index',
        component: ClientRegistrationIndexComponent
    },
    {
        path: 'client-register',
        component: CreateClientRegistrationComponent
    },
    {
        path: 'client-edit/:ufn',
        component: EditClientRegistrationComponent
    },
    {
        path: 'client-attendancenote-index',
        component: ClientAttendancenoteindexComponent
    },
    {
        path: 'client-attendancenote-edit/:id',
        component: ClientAttendanceNoteEditComponent
    },
    {
        path: 'client-attendancenote-view/:id',
        component: ClientAttendanceNoteViewComponent
    },

    {
        path: 'client-attendancenote-make',
        component: AttendanceNoteCreateComponent
    },
    {
        path: 'client-attendance-index',
        component: AttendanceindexComponent
    },
    {
        path: 'client-attendance-edit/:id',
        component: AttendanceEditComponent
    },
    {
        path: 'client-attendance-view/:id',
        component: AttendanceviewComponent
    }

];