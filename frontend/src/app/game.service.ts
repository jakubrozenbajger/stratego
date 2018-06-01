import {Injectable} from '@angular/core';
import {AiSettings, AIType, Player, Points} from "./model";

const DEFAULT_DEPTH = 3;
const DEFAULT_AI_TYPE = AIType.MIN_MAX;

@Injectable()
export class GameService {

  private dimension: number;
  players: Player[] = [{id: 1, points: 0}, {id: -1, points: 0}];

  // ai
  depth: number = DEFAULT_DEPTH;
  type: string = DEFAULT_AI_TYPE;


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

  getAiSettings(): AiSettings {
    return {
      depth: +this.depth,
      aiType: this.type
    };
  }

}
