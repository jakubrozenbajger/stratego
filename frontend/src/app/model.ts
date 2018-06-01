export interface Event {
}

export class Board {
  state: number[][]
}

export class NewMove extends Event {
  board: Board
}

export class Points extends Event {
  id: number;
  count: number;
}

export interface Player {
  points: number;
  id: number;
}
