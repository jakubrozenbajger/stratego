import {Component, OnInit} from '@angular/core';
import {WebsocketService} from "../websocket.service";

@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.css']
})
export class BoardComponent implements OnInit {

  dim: number;
  positions: any[];

  state: number[][];
  lock: boolean = false;

  constructor(private ws: WebsocketService) {
    this.dim = 8;
    this.positions = Array(this.dim).fill(0).map((x, i) => i);
    this.state = Array(this.dim).fill(0).map((x, i) => Array(this.dim).fill(0));
    this.prepareWsService();
  }

  unlock() {
    this.lock = false;
  }

  ngOnInit() {
  }

  click(r, d) {
    console.log(r + " " + d);
    console.log(this.lock);
    if (!this.lock) {
      this.state[r][d] = 1;//Math.abs(this.state[r][d]) - 1;
      this.ws.send(JSON.stringify(this.state))
    }
    this.lock = true;
  }

  isPlaced(r, d) {
    return this.state[r][d]
  }

  playerColor(r, d) {
    return this.state[r][d] > 0 ? "red" : "blue";
  }

  private prepareWsService() {
    this.ws.onMsg = (el) => {
      this.lock = false;
      console.log(el)
      // if (el instanceof Array)
      // this.state = el;
    };
  }
}
