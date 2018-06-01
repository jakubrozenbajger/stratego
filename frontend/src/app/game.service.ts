import {Injectable} from '@angular/core';
import {Player, Points} from "./model";

@Injectable()
export class GameService {

  private dimension: number;

  players: Player[] = [{id: 1, points: 0}, {id: -1, points: 0}];

  constructor() {
  }

  public newGame(dim: number) {
    this.dimension = dim;
    this.players = [{id: 1, points: 0}, {id: -1, points: 0}]
  }

  get boardSize() {
    return this.dimension;
  }

  updatePoints(points: Points) {
    this.players.filter((p, _) => p.id == points.id)[0].points += points.count;
  }

}
