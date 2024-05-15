import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { StoresService } from '../stores.service';
import { Router } from '@angular/router';
import { Stores } from '../stores';

@Component({
  selector: 'app-add-stores',
  templateUrl: './add-stores.component.html',
  styleUrls: ['./add-stores.component.css']
})
export class AddStoresComponent implements OnInit {

  constructor(private fb:FormBuilder,private storeService:StoresService,private router:Router) { }

  ngOnInit(): void {
  }

  addStroesForm = this.fb.group({
    name: [''],
    description: [''],
    email: [''],
    image: [''],
    category:[''],
    address:[''],
    geolocation:[''],
  });

  noImage ="https://upload.wikimedia.org/wikipedia/commons/1/14/No_Image_Available.jpg?20200913095930";
  //craete stores
  create(){
    this.storeService.add(this.addStroesForm.value as Stores)
    .subscribe(() =>{
        this.router.navigate(['/']);
    })
  }

}
