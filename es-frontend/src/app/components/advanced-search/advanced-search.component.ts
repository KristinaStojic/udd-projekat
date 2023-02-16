import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-advanced-search',
  templateUrl: './advanced-search.component.html',
  styleUrls: ['./advanced-search.component.scss']
})
export class AdvancedSearchComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  education = false

  fields = [
    { 
      criteria: '',
      content: '',
      op : 'AND',
    }];

  addField() {
    const fieldCount = this.fields.length + 1;
    const newField = { criteria: '', content: '', op: 'AND' };
    this.fields.push(newField);
    this.submit()

  }

  removeField(index: number) {
    this.fields.splice(index, 1);
    this.submit()
  }

  operator($event, index){
    this.fields[index].op = $event.target.value
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
    console.log(this.fields);
  }

}
