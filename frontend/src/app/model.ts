export interface Event {
}

export class Board {
  state: number[][]
}

export class NewMove extends Event {
  board: Board
}
