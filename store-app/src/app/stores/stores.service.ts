import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Stores } from './stores';

@Injectable({
  providedIn: 'root'
})
export class StoresService {

  constructor(private http:HttpClient) { }

  //baseAPI = 'http://localhost:3000/stores';
  baseAPI = 'http://localhost:8083/api/store/';

  //get store by name & category with paging & sorting(default=name)
  get(keyword:string,currentPage:number,pageSize:number):Observable<any>{
    if(keyword){
      return this.http.get<any>(`${this.baseAPI}search?keyword=${keyword}&currentPage=${currentPage}&pageSize=${pageSize}`);
    }
    return this.http.get<any>(`${this.baseAPI}search?keyword=&currentPage=${currentPage}&pageSize=${pageSize}`);  
  }

  //add store
  add(payload:Stores){
    return this.http.post(this.baseAPI,payload);
  }

  //get by id
  getById(id:string):Observable<Stores>{
    return this.http.get<Stores>(this.baseAPI+`${id}`);
  }

  //update store
  update(payload:Stores){
    return this.http.put(this.baseAPI+`${payload.id}`,payload);
  }

  //delete by id
  delete(id:string){
    return this.http.delete(this.baseAPI+`${id}`);
  }

}
