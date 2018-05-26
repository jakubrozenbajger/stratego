import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';


import {AppComponent} from './app.component';
import {BoardComponent} from './board/board.component';
import {WebsocketService} from "./websocket.service";
import {SettingsComponent} from './settings/settings.component';
import {RouterModule} from "@angular/router";
import Routes from "./routes";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {GameService} from "./game.service";


@NgModule({
  declarations: [
    AppComponent,
    BoardComponent,
    SettingsComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot(Routes, {}),
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [
    WebsocketService,
    GameService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
