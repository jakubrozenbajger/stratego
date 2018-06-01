import {Component, OnInit} from '@angular/core';
import {WebsocketService} from "../websocket.service";
import {Router} from "@angular/router";
import paths from "../paths";
import {NewMove, Points} from "../model";
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

  debug() {
    return JSON.stringify({state: this.state, lock: this.lock});
  }

  get players() {
    return this.gameService.players;
  }

  ngOnInit() {
    this.resetBoard()
  }

  resetBoard() {
    if (this.gameService.boardSize > 0) {
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
    } else {
      this.reset()
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
    if (!this.lock && !this.isPlaced(r, c)) {
      this.ws.send(JSON.stringify({msg: "nextMove", position: {row: r, column: c}, board: {state: this.state}}));
      this.lock = true;
      this.state[r][c] = 1;  // Math.abs(this.state[r][c]) - 1;
    }
  }

  isPlaced(r, c) {
    return this.state[r][c]
  }

  getColor(val) {
    if (val === 0) {
      return "";
    } else if (val > 0) {
      return "red"
    } else {
      return "blue"
    }
  }

  playerColor(r, c) {
    return this.getColor(this.state[r][c]);
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
      e = e as NewMove;
      this.lock = false;
      this.updateState(e.board.state);
    }
    if (this.isPoints(e)) {
      e = e as Points;
      this.gameService.updatePoints(e);
    }
  }

  isNewMove(e: any) {
    return e['board'] != undefined
  }

  isPoints(e: any) {
    return e['count'] != undefined && e['id'] != undefined
  }

  reset() {
    this.ws.send(JSON.stringify({msg: "close"}));
    return this.router.navigate([paths.MAIN]);
  }

}
