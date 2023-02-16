import { SearchService } from './../../service/search.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-geo-search',
  templateUrl: './geo-search.component.html',
  styleUrls: ['./geo-search.component.scss']
})
export class GeoSearchComponent implements OnInit {

  content = {
    "city": "",
    "radius": ""
  }

  result = [
    {
      "firstName": "",
      "lastName": "",
      "education": "",
      "highlight": ""
    }
  ]

  constructor(private searchService: SearchService) { }

  ngOnInit(): void {
  }

  submit(){
    this.searchService.geoSearch(this.content).subscribe(
      (data: any) => {
        this.result = data
        console.log(this.result)
      }
    )
  }

}
