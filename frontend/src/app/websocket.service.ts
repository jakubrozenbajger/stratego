import {Injectable} from '@angular/core';
import {WebSocketSubject} from "rxjs/observable/dom/WebSocketSubject";
import {Observable} from "rxjs/Observable";

@Injectable()
export class WebsocketService {

  private _wsSubject: WebSocketSubject<any>;
  private wsUrl = 'ws://localhost:9000/ws';

  constructor() {
  }

  public get wsSubject(): WebSocketSubject<any> {
    if (!this._wsSubject || this._wsSubject.closed) {
      this._wsSubject = WebSocketSubject.create(this.wsUrl);
    }
    return this._wsSubject;
  }

  get getUpdates$(): Observable<any> {
    return this.wsSubject.asObservable();
  }

  public send(msg) {
    return this.wsSubject.next(msg);
  }
}
