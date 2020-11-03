import { Component, OnInit } from '@angular/core';
import {AuthService} from "../auth/auth.service";
import {PostService} from "../service/post.service";



@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {


  constructor( public authService: AuthService,
               public postService:PostService) { }

  ngOnInit(): void{

  }

}

