import { RegisterService } from './../../service/register.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-workers',
  templateUrl: './workers.component.html',
  styleUrls: ['./workers.component.scss']
})
export class WorkersComponent implements OnInit {

  constructor(private registerService:  RegisterService) { }

  result = [
    {
      "name": "",
      "address": "",
      "education": "",
      "id": ""
    }
  ]

  ngOnInit(): void {
    this.registerService.getAll().subscribe(
      (data: any) => {
        this.result = data
      }
    )
  }

}
