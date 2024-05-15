import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { StoresService } from '../stores.service';

@Component({
  selector: 'app-delete-dialog-stores',
  templateUrl: './delete-dialog-stores.component.html',
  styleUrls: ['./delete-dialog-stores.component.css']
})
export class DeleteDialogStoresComponent implements OnInit {

  constructor(private dialogRef:MatDialogRef<DeleteDialogStoresComponent>,
    private storeService:StoresService,
    @Inject(MAT_DIALOG_DATA) public data:any
  ) { }

  ngOnInit(): void {
  }

  confirmDelete(){
    this.storeService.delete(this.data.id).subscribe(() =>{
       this.dialogRef.close(this.data.id);
    })
  }
}
