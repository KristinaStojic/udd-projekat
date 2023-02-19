import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  constructor() { }
  name = ""

  ngOnInit(): void {
  }

  login(){
    if(this.name === "Zaposleni 1"){
      localStorage.setItem('id', "1");
    }
    else if(this.name === "Zaposleni 2"){
      localStorage.setItem('id', "2");
    }
    else if(this.name === "Zaposleni 3"){
      localStorage.setItem('id', "3");
    }
  }

}
