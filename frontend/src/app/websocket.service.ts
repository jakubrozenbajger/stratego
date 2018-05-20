import {Injectable} from '@angular/core';
import {Subject} from "rxjs/Subject";
import {WebSocketSubject} from "rxjs/observable/dom/WebSocketSubject";

@Injectable()
export class WebsocketService {

  constructor() {
    this.connect('ws://localhost:9000/ws');
  }

  private sub: Subject<any>;
  public onMsg;

  public connect(url): Subject<any> {
    if (!this.sub) {
      this.sub = this.create(url);
    }
    return this.sub;
  }

  private create(url): WebSocketSubject<any> {
    const ws = WebSocketSubject.create(url);
    ws.subscribe(
      (msg) => {
        this.onMsg(msg);
      },
      e => console.warn(e),
      () => console.warn('Completed')
    );
    return ws;
  }

  public send(msg) {
    return this.sub.next(msg);
  }
}
