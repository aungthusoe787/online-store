import { Component, OnInit } from '@angular/core';
import { Stores } from '../stores';
import { StoresService } from '../stores.service';
import { MatDialog } from '@angular/material/dialog';
import { DeleteDialogStoresComponent } from '../delete-dialog-stores/delete-dialog-stores.component';
import { FormControl } from '@angular/forms';
import { keyframes } from '@angular/animations';
import { PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-all-stores',
  templateUrl: './all-stores.component.html',
  styleUrls: ['./all-stores.component.css']
})
export class AllStoresComponent implements OnInit {

  allStores:Stores[] = [];
  searchControl= new FormControl('');
  pageIndex:number=0;
  pageSize:number=3;
  totalRecords:number=0;

  constructor(private storeService:StoresService
    ,private dialog:MatDialog) { }

  ngOnInit(): void {
    this.getAPI('',this.pageIndex+1,this.pageSize);
  }

  getAPI(keyword:string,currentPage:number,pageSize:number){
    this.storeService.get(keyword,this.pageIndex,this.pageSize)
    .subscribe((response) =>{
        this.allStores = response.stores as Stores[];
        this.totalRecords = response.totalItems as number;
    })
  }

  searchStore(){
    
    let keyword= this.searchControl.value as string;
    this.pageIndex = 0;
    this.pageSize = 3;
    this.storeService.get(keyword, this.pageIndex,this.pageSize)
    .subscribe((response) => {
      if(response){
        this.allStores = response.stores as Stores[];
        this.totalRecords = response.totalItems as number;
      }
    } )
  }

  deleteItem(id:string){
    const dialogRef = this.dialog.open(DeleteDialogStoresComponent,{
      width:'300px',
      data:{id}
    });

    dialogRef.afterClosed().subscribe((result) =>{
      if(result){
        this.pageIndex = 0;
        this.pageSize = 3;
        this.totalRecords = this.totalRecords - 1;
        this.allStores = this.allStores.filter(_ => _.id !== result);
      }
    });
  }

  handlePageEvent(e:PageEvent){
    this.pageIndex = e.pageIndex;
    this.pageSize = e.pageSize;
    this.getAPI('',this.pageIndex,this.pageSize);
  }

}
