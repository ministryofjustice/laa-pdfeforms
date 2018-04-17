
import { CreatePersonComponent } from './components/person/create/create.component';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AppComponent } from './app.component';
import { FormsModule } from '@angular/forms';
import { appRoutes } from "./routerConfig";
import { HomeComponent } from './components/person/home/home.component';
import { HttpClientModule } from "@angular/common/http";
import { PersonService } from './services/person.service';
import { EditComponent } from './components/person/edit/edit.component';

@NgModule({
  declarations: [
    AppComponent,
    CreatePersonComponent,
    HomeComponent,
    EditComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(appRoutes)
  ],
  providers: [PersonService],
  bootstrap: [AppComponent]
})

export class AppModule { }
