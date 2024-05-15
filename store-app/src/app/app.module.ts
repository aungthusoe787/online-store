import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from '../material-module';
import {HttpClientModule} from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { AllStoresComponent } from './stores/all-stores/all-stores.component';
import { AddStoresComponent } from './stores/add-stores/add-stores.component';
import { EditStoresComponent } from './stores/edit-stores/edit-stores.component';
import { DeleteDialogStoresComponent } from './stores/delete-dialog-stores/delete-dialog-stores.component';

@NgModule({
  declarations: [
    AppComponent,
    AllStoresComponent,
    AddStoresComponent,
    EditStoresComponent,
    DeleteDialogStoresComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
