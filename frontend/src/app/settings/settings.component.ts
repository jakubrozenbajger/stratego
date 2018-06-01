import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {FormBuilder, FormGroup} from "@angular/forms";
import paths from "../paths";
import {GameService} from "../game.service";
import {HttpClient} from "@angular/common/http";

const MIN_SIZE = 3;
const MAX_SIZE = 20;

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {

  sizes: number[];
  startForm: FormGroup;

  constructor(private ro: Router, private fb: FormBuilder, private gameService: GameService, http: HttpClient) {
    this.sizes = Array(MAX_SIZE - MIN_SIZE + 1).fill(MIN_SIZE).map((x, i) => i + x);
    // http.get("http://localhost:9000/").subscribe(s => console.log("Backend loaded!"));
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
