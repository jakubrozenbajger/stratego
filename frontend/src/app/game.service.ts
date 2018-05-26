import {Injectable} from '@angular/core';

@Injectable()
export class GameService {

  private dimension: number;

  constructor() {
  }

  public newGame(dim: number) {
    this.dimension = dim;
  }

  get boardSize() {
    return this.dimension;
  }

}
