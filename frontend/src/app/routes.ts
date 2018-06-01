import {SettingsComponent} from "./settings/settings.component";
import {BoardComponent} from "./board/board.component";
import Paths from "./paths";

export const routes = [{
  path: Paths.MAIN,
  component: SettingsComponent
}, {
  path: Paths.GAME,
  component: BoardComponent
}
];
