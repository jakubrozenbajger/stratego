import {SettingsComponent} from "./settings/settings.component";
import {BoardComponent} from "./board/board.component";
import Paths from "./paths";

export default [{
  path: Paths.MAIN,
  component: SettingsComponent
}, {
  path: Paths.GAME,
  component: BoardComponent
}
];
