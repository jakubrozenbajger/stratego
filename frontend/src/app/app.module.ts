import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';


import {AppComponent} from './app.component';
import {BoardComponent} from './board/board.component';
import {WebsocketService} from "./websocket.service";
import {SettingsComponent} from './settings/settings.component';
import {RouterModule} from "@angular/router";
import {routes} from "./routes";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {GameService} from "./game.service";
import { AiSettingsComponent } from './board/ai-settings/ai-settings.component';


@NgModule({
  declarations: [
    AppComponent,
    BoardComponent,
    SettingsComponent,
    AiSettingsComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot(routes),
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
