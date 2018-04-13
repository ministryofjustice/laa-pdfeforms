import { AppComponent } from './app.component';
import { Routes } from "@angular/router";
import { CreatePersonComponent } from './components/person/create/create.component';
import { HomeComponent } from "./components/person/home/home.component";

export const appRoutes: Routes = [
    {
        path : 'home',
        component : HomeComponent
    },
    {
        path : 'create',
        component : CreatePersonComponent
    }
];