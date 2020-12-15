import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {LogInComponent} from "./auth/log-in/log-in.component";
import {SignUpComponent} from "./auth/sign-up/sign-up.component";
import {UserComponent} from "./user/user.component";
import {NotFoundComponent} from "./not-found/not-found.component";
import {EditUserComponent} from "./edit-user/edit-user.component";
import {WelcomeComponent} from './welcome/welcome.component';
import {SignUpSucceedComponent} from './sign-up-succeed/sign-up-succeed.component';
import {ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {AuthModule} from "./auth/sign-up/auth.module";
import {EditorModule} from "@tinymce/tinymce-angular";
import {CreatePostComponent} from "./create-post/create-post.component";
import {NgxWebstorageModule} from 'ngx-webstorage';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {ToastrModule} from "ngx-toastr";
import {HeaderComponent} from "./header/header.component";
import {ShowPostsComponent} from './show-posts/show-posts.component';
import {PostService} from "./service/post.service";
import {CommentService} from "./service/comment.service";
import {CreateCommentComponent} from "./create-comment/create-comment.component";
import {ShowCommentsComponent} from './show-comments/show-comments.component';
import { AddVoteComponent } from './add-vote/add-vote.component';
import { ShowVotesComponent } from './show-votes/show-votes.component';


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    LogInComponent,
    SignUpComponent,
    EditUserComponent,
    UserComponent,
    NotFoundComponent,
    WelcomeComponent,
    SignUpSucceedComponent,
    CreatePostComponent,
    ShowPostsComponent,
    CreateCommentComponent,
    ShowCommentsComponent,
    AddVoteComponent,
    ShowVotesComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    AuthModule,
    EditorModule,
    NgxWebstorageModule.forRoot(),
    BrowserAnimationsModule,
    ToastrModule.forRoot()
  ],
  providers: [
    PostService,
    CommentService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
