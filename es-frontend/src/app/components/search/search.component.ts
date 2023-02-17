import { SearchService } from './../../service/search.service';
import { Component, OnInit } from '@angular/core';
import { ThisReceiver } from '@angular/compiler';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss']
})
export class SearchComponent implements OnInit {

  content = ""
  phrase = false
  criteria = 0
  result = [
    {
      "firstName": "",
      "lastName": "",
      "education": "",
      "highlight": ""
    }
  ]
  showLevel = false

  constructor(private searchService: SearchService) { }

  ngOnInit(): void {
  }

  selectField($event){
    this.criteria = $event.target.value
    if(this.criteria == 2){
      this.showLevel = true
    }
    else{
      this.showLevel = false
    }
  }

  educationLevel($event){
    this.content = $event.target.value
  }

  selectPhrase(){
    if(this.phrase){
      this.phrase = true
    }
    else{
      this.phrase = false;
    }
  }

  submit(){

    var content = {
      "content": this.content,
      "phrase": this.phrase
    }
    console.log(content)

    if(this.criteria == 1){
      this.searchService.simpleSearch(content).subscribe(
        (data: any) => {
          this.result = data
          console.log(this.result)
        }
      )
    }
    else if(this.criteria == 2){
      this.searchService.educationSearch(content).subscribe(
        (data: any) => {
          this.result = data
          console.log(this.result)
        }
      )
    }
    else if(this.criteria == 3){
      this.searchService.CVSearch(content).subscribe(
        (data: any) => {
          this.result = data
          console.log(this.result)
        }
      )
    }
    else if(this.criteria == 4){
      this.searchService.CLSearch(content).subscribe(
        (data: any) => {
          this.result = data
          //const regex = /<b>(.*?)<\/b>/g;
          //const boldedText = text.replace(regex, '<strong>$1</strong>');
          // for(let r of this.result){
          //   r.highlight = r.highlight.replace(regex, '<strong>$1</strong>');
          // }
          console.log(this.result)
        }
      )
    }
  }

}
