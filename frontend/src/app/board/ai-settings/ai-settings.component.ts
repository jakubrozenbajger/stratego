import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {GameService} from "../../game.service";
import {AIType} from "../../model";

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

  aiSettings: FormGroup;

  constructor(private fb: FormBuilder, private gs: GameService) {
    this.depths = Array(MAX_DEPTH - MIN_DEPTH + 1).fill(MIN_DEPTH).map((x, i) => i + x);
    this.types = [AIType.MIN_MAX, AIType.ALPHA_BETA];
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

  createForm() {
    this.aiSettings = this.fb.group({
      depth: [''],
      type: [''],
    });
  }

}
