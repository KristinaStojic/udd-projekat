import { SearchService } from './../../service/search.service';
import { SearchComponent } from './../search/search.component';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-advanced-search',
  templateUrl: './advanced-search.component.html',
  styleUrls: ['./advanced-search.component.scss']
})
export class AdvancedSearchComponent implements OnInit {

  constructor(private searchService: SearchService) { }

  ngOnInit(): void {
  }

  education = false
  fieldCount = 0
  
  fields = [
    { 
      criteria: '',
      content: '',
      op : 'AND',
    }];
    result = [
      {
        "firstName": "",
        "lastName": "",
        "education": "",
        "highlight": ""
      }
    ]

  addField() {

    this.fieldCount = this.fields.length + 1;
    const newField = { criteria: '', content: '', op: 'AND' };
    this.fields.push(newField);

    this.submit()

  }

  removeField(index: number) {
    this.fieldCount = this.fields.length - 1;
    this.fields.splice(index, 1);
    this.submit()
  }

  operator($event, index){
    this.fields[index].op = $event.target.value
    this.fields[0].op = this.fields[1].op

    console.log(this.fields[index])
  }

  criteria($event, index){
    let selected = $event.target.value
    if(selected === "education"){
      this.education = true
    }else{
      this.education = false
    }
    this.fields[index].criteria = selected
  
    console.log(this.fields[index])
  }

  educationLevel($event, index){
    this.fields[index].content = $event.target.value
    console.log(this.fields[index])
  }

  submit() {
    console.log(JSON.stringify(this.fields));
    this.fields[0].op = this.fields[1].op

    this.searchService.advancedSearch(JSON.stringify(this.fields)).subscribe(
      (data: any) => {
        this.result = data
        console.log(data)
      }
    )
  }

}
