import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {FormBuilder, FormGroup} from "@angular/forms";
import paths from "../paths";
import {GameService} from "../game.service";

const MIN_SIZE = 4;

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {

  sizes: number[];
  startForm: FormGroup;

  constructor(private ro: Router, private fb: FormBuilder, private gameService: GameService) {
    this.sizes = Array(20).fill(MIN_SIZE).map((x, i) => i + x);
    console.log(this.sizes);
    this.createForm();
  }

  ngOnInit() {
  }

  createForm() {
    this.startForm = this.fb.group({
      boardSize: [MIN_SIZE],
    });
  }

  startGame() {
    const boardSize = this.startForm.value.boardSize;
    this.gameService.newGame(boardSize);
    return this.ro.navigate([paths.GAME])
  }
}
