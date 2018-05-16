import { EditClientRegistrationComponent } from './../../components/client/registration/edit/editclientregistration.component';
import { CreateClientRegistrationComponent } from './../../components/client/registration/create/createclientregistration.component';
import { ClientRegistrationIndexComponent } from "../../components/client/registration/index/clientindex.component";
import { ClientAttendancenoteindexComponent } from '../../components/client/attendancenote/attendancenoteindex/attendancenoteindex.component';
import { Routes } from "@angular/router";

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
    }

];