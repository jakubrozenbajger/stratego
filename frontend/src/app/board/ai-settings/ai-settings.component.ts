import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {GameService} from "../../game.service";
import {AIType, MovesStrategy, PositionStrategy} from "../../model";
import * as _ from "lodash";

const MIN_DEPTH = 2;
const MAX_DEPTH = 8;

@Component({
  selector: 'app-ai-settings',
  templateUrl: './ai-settings.component.html',
  styleUrls: ['./ai-settings.component.css']
})
export class AiSettingsComponent implements OnInit {

  depths: number[];
  types: string[];
  moves: string[];
  positions: string[];

  aiSettings: FormGroup;

  constructor(private fb: FormBuilder, private gs: GameService) {
    this.depths = Array(MAX_DEPTH - MIN_DEPTH + 1).fill(MIN_DEPTH).map((x, i) => i + x);
    this.types = _.values(AIType);
    this.moves = _.values(MovesStrategy);
    this.positions = _.values(PositionStrategy);
    this.createForm();
  }

  ngOnInit() {
  }

  set type(t) {
    this.gs.type = t;
  }

  get type() {
    return this.gs.type;
  }

  set depth(d) {
    this.gs.depth = d;
  }

  get depth() {
    return this.gs.depth;
  }

  set moveStrategy(m) {
    this.gs.moveStrategy = m;
  }

  get moveStrategy() {
    return this.gs.moveStrategy;
  }


  set positionStrategy(p) {
    this.gs.positionStrategy = p;
  }

  get positionStrategy() {
    return this.gs.positionStrategy;
  }

  createForm() {
    this.aiSettings = this.fb.group({
      depth: [''],
      type: [''],
      moveStrategy: [''],
      positionStrategy: [''],
    });
  }

}
