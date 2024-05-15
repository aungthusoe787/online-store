import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { StoresService } from '../stores.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Stores } from '../stores';

@Component({
  selector: 'app-edit-stores',
  templateUrl: './edit-stores.component.html',
  styleUrls: ['./edit-stores.component.css']
})
export class EditStoresComponent implements OnInit {

  constructor(private fb:FormBuilder,
    private storeService:StoresService,
    private route:ActivatedRoute,
    private router:Router
  ) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe((param) =>{
      let id = String(param.get('id'));
      this.getById(id);
    })
  }

  updateStoreForm = this.fb.group({
    id:[''],
    name: [''],
    description: [''],
    email: [''],
    image: [''],
    category:[''],
    address:[''],
    geolocation:[''],
  });
  noImage ="https://upload.wikimedia.org/wikipedia/commons/1/14/No_Image_Available.jpg?20200913095930";

  getById(id:string){
    this.storeService.getById(id)
    .subscribe((response) => {
      this.updateStoreForm.setValue(response);
    });
  }

  update(){
    this.storeService.update((this.updateStoreForm.value as Stores))
    .subscribe(() =>{
      this.router.navigate(["/"]);
    })
  }


}
