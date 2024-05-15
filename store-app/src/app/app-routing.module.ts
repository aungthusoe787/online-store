import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AllStoresComponent } from './stores/all-stores/all-stores.component';
import { AddStoresComponent } from './stores/add-stores/add-stores.component';
import { EditStoresComponent } from './stores/edit-stores/edit-stores.component';

const routes: Routes = [
  {
    path:'',
    component:AllStoresComponent
  },
  {
    path:'add',
    component:AddStoresComponent
  },
  {
    path:'edit/:id',
    component:EditStoresComponent
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
