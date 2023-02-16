import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SearchService {
  private search_url = 'http://localhost:8080/search';

  constructor(private http: HttpClient) { }

  simpleSearch(content){
    return this.http.post(`${this.search_url}/byApplicant`, content);
  }

  educationSearch(content){
    return this.http.post(`${this.search_url}/byEducation`, content);

  }

  CVSearch(content){
    return this.http.post(`${this.search_url}/byCV`, content);

  }


  CLSearch(content){
    return this.http.post(`${this.search_url}/byCL`, content);

  }

  advancedSearch(content){
    return this.http.post(`${this.search_url}/byApplicant`, content);

  }

  geoSearch(content){
    return this.http.post(`${this.search_url}/byGeoLocation`, content);

  }
}
