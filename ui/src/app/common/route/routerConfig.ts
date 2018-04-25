import { EditClientRegistrationComponent } from './../../components/client/registration/edit/editclientregistration.component';
import { CreateClientRegistrationComponent } from './../../components/client/registration/create/createclientregistration.component';
import { ClientRegistrationIndexComponent } from "../../components/client/registration/index/clientindex.component";
import { Routes } from "@angular/router";

export const appRoutes: Routes = [
    {
        path: 'index',
        component: ClientRegistrationIndexComponent
    },
    {
        path: 'register',
        component: CreateClientRegistrationComponent
    },
    {
        path: 'edit/:ufn',
        component: EditClientRegistrationComponent
    }

];