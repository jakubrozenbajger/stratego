import {Component, OnInit} from '@angular/core';
import {WebsocketService} from "../websocket.service";
import {Router} from "@angular/router";
import paths from "../paths";
import {NewMove} from "../model";
import {GameService} from "../game.service";

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

  constructor(private ws: WebsocketService, private router: Router, private gameService: GameService) {
    console.log("Start " + gameService.boardSize);
    this.prepareWsService();
  }

  unlock() {
    this.lock = false;
  }

  ngOnInit() {
    this.resetBoard()
  }

  resetBoard() {
    this.dim = this.gameService.boardSize;
    this.positions = Array(this.dim);
    this.state = Array(this.dim);
    for (let i = 0; i < this.dim; i++) {
      this.positions[i] = i;
      let arr = new Array(this.dim).fill(0);
      for (let j = 0; j < this.dim; j++)
        arr[j] = 0;
      this.state[i] = arr;
    }
  }

  updateState(newState) {
    for (let i = 0; i < this.dim; i++) {
      for (let j = 0; j < this.dim; j++) {
        this.state[i][j] = newState[i][j];
      }
    }
  }

  click(r, c) {
    console.log(r + " " + c);
    console.log(this.lock);
    if (!this.lock && !this.isPlaced(r, c)) {
      this.state[r][c] = 1;//Math.abs(this.state[r][c]) - 1;
      this.ws.send(JSON.stringify({msg: "nextMove", state: this.state}));
      this.lock = true;
    }
  }

  isPlaced(r, c) {
    return this.state[r][c]
  }

  playerColor(r, c) {
    return this.state[r][c] > 0 ? "red" : "blue";
  }

  private prepareWsService() {
    this.ws.getUpdates$.subscribe(e => this.handleEvent(e))
  }

  private handleEvent(e: any) {
    console.log("Received event:");
    console.log(e);
    if (e instanceof NewMove) {
      console.log("e instanceof NewMove");
    }
    if (this.isNewMove(e)) {
      console.log(this);
      this.lock = false;
      this.updateState(e.board.state);
    }
  }

  isNewMove(e: any) {
    return e['board'] != undefined
  }

  reset() {
    return this.router.navigate([paths.MAIN]);
  }

}
