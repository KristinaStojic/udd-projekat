import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  private register_url = 'http://localhost:8080/applicant/register';

  constructor(private router: Router,
    private http: HttpClient) { }

  register(user) {
    let queryParams = {};
        queryParams = {
            observe: 'response',
            params: new HttpParams(),
            responseType: 'text'
        };

    return this.http.post(this.register_url, user, queryParams);
  }
  
}
